# Java—6  内建锁优化

所谓内建锁的优化就是优化线程的等待时间

##**CAS机制**

1.CAS:全称  Compare And Swap  比较交换机制

**CAS是一种乐观锁机制：**

悲观锁：在任意时刻都有线程竞争锁，获取锁成功的线程会阻塞获取锁失败的线程成功获取锁。

乐观锁（lock）：假设所有线程访问共享资源均不会发生冲突，既然不会发生冲突，就不会阻塞其他线程。不会阻塞线程

**CAS（无锁操作）**：有三个值V(内存实际存放的值)，O（预期值），N更新后的值。

当V==O，说明上次该线程操作后没有别的线程再修改值，因此可以将N存入V中；

当V！=O，说明上次该线程操作后，有其他线程修改了V值，因此不能讲N替换到内存中，返回V值，并且不断尝试修改V值。

JDK1.6之前的内建锁最大的问题是，每当有线程竞争锁失败就会将线程阻塞，这样阻塞和唤起线程消耗了大量的资源。

CAS不是武断的将竞争锁失败的线程挂起来，而是会让失败的线程自旋尝试获取锁，到了一定次数再将线程挂起。

具体策略：如果上次自旋获取到锁，这次 自旋时间就长一些，否则，此次自旋时间就短一些。



**CAS的问题**

ABA问题：线程1    A     线程2   A—>B   线程3   B—>A   线程1  : ???

理论上来说 线程1是可以修改的，但是如果修改了，线程3就会瘫痪。

解决思路：数据库的乐观锁机制，加入一个版本号：   包java.util.concurrent.atomic的   AtomicReference类

公平性问题：处于自旋状态的线程会比阻塞状态的线程更容易获取锁。



在同步代码块中，获得对象monitor监视器就是获得了对象的锁。无非就是对象的锁就是对象头里的一个标记





## 偏向锁

HotSpot的作者发现，在大多数情况下，线程时不存在竞争的，为了降低 阻塞和唤醒线程的代价引入了偏向锁

在任意时刻只有一个线程持有锁。

**偏向锁的获取过程**

1.一个线程访问同步代码块并获取锁时时，会在对象头和栈帧中存储锁偏向的线程ID，先看对象头持有的线程ID是否为自己的线程ID，如果是直接访问同步代码块。

2.如果不是，查看偏向锁字段，如果为0，说明对象锁还没有被任何线程获得，将偏向锁字段改为1，并且将对象头的线程ID改成自己的。

3，如果为1，说明对象锁已经被别的线程获得，当前线程不断自旋获取对象锁或升级成轻量级锁。(后者可能性较大)。一般来说   偏向锁每自旋一次  mark word中的 Epoch就加1  当 Epoch=40时，说明该锁已经不适合当做偏向锁。升级为轻量级锁



**偏向锁的释放：**

偏向锁的释放代价较大，通常要到达全局安全点（CPU上没有执行有用字节码）才可以释放

## 轻量级

在不同时间，多个线程请求一把锁。

**加锁过程:**

1.当线程访问同步代码块并加锁时，JVM会在线程中开辟存放锁记录的栈帧，将对象头的mark word复制到锁记录中   称为 displace mark word。

2.使用CAS将对象头中的Mark word 替换成指向锁记录的指针。

3.如果成功说明，当前成功获取锁，如果失败说明当前有其他线程竞争锁，不断自旋尝试获取锁。

解锁：

1.将锁记录中的 Displace Mark Word替换会 Mark Word 。

2.如果成功，成功释放锁，如果失败，说明有其他线程竞争锁，锁直接膨胀成重量级锁



