# Java—多线程2

## Java中的其他方法

**1.线程休眠（sleep）单位为毫秒：**

a.指的是让线程暂缓执行，到了预计时间再恢复执行

b.线程休眠会立即交出CPU，让CPU去执行其他任务。线程休眠**不会释放对象锁。**

```java
public static native void sleep(long millis) throws InterruptedException;
```



**2.线程让步（yeild）**

a.暂停当前正在执行的线程对象，并执行其他线程，**会让当前线程交出CPU，但是不会立即交出（系统调度）。**

b.yiled()交出CPU后只能让拥有相同优先级的线程获得线程，不会释放对象锁。

c.什么再成为就绪状态，还是靠系统调度。

```java
public static native void yield();
```

**3.join方法**

a.等待该线程终止，如果在主线程中调用该方法，会让主线程休眠，让调用该方法的线程先执行完然后再执行主线程，就算子线程休眠，主线程也不会恢复执行（sleep没交锁）。

b.join是Object类中的wait()做了一层包装。

## 线程优先级[1,10]

线程优先级是指，优先级越高，有可能先执行而已。

1.设置优先级：

public final void setPriority(int newPriority)

2.取得优先级：

public final void getPriority()

有三个内置的优先级：

Thread.MAX_PRIORITY=10;

Thread.NORM_PRORITY=5;

Thread.MIN_PRORITY=1;

**线程优先级具有继承性，在主线程调用的子线程优先级这两个线程的优先级一样**



## 线程停止

1.设置标记位

2.使用stop方法强制进行停止（废弃掉了）

3.使用Interrupt停止()

InterruptedException:

a.线程处于非阻塞，直接将中断标志位设置为true。

b.如果一个线程是一个受阻塞的线程，给受阻塞的线程发送一个中断信号，受阻塞的线程退出阻塞状态。

c.一个线程在运行中，其中断位被改成true，一旦调用了wait、sleep以及join的一种，立马抛出一个InterruptException，且中断标志被程序自动清除，重新设置为false；

如果阻塞是将线程标志位该为true，并抛出异常。





## 守护线程（后台线程）

守护线程是一种特殊的线程，属于陪伴线程。

Java：用户线程，守护线程。

isDaemon();判断线程是否是守护线程。

典型的守护线程：垃圾回收线程。

只要当前JVM存在任何一个用户线程没有结束，守护线程就一直在工作，只有当最后一个用线程停止后 守护线程会随着JVM一同停止。

setDeamon() 将当前线程设置为守护线程。（必须在线程启动直线设置，否则throw new IllegalThreadStateException());

Java中启动的线程默认为用户线程，包括主线程。