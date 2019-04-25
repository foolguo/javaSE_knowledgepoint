# Java—多线程9  Condition的await和signal机制

##两种机制等待队列的对比

1.Object的wait和notify是与内建锁（对象监视器）搭配使用，完成线程的等待与通知机制。本地方法实现。

2.Condition的await、signal是与Lock体系配合实现线程的等待与通知，Java语言层面的实现，具有更高的控制与扩展性

**Condition机制有一下三个独有特性：**

1.Condition支持**不响应中断**，而Object类提供的wait不支持。

2.Condition支持**多个等待队列**，而Object wait只有一个等待队列。

3.Condition**支持设置解锁时间**，而Object，wait只支持设置超时时间。



 ## Condition

**await方法**

|                                                              |                                                   |
| ------------------------------------------------------------ | ------------------------------------------------- |
| public final void await() throws InterruptedException        | （同wait（）方法）                                |
| public final void awaitUninterruptibly()                     | 等待过程中不响应中断                              |
| public final boolean await(long time, TimeUnit unit)         | 在I的基础上增加了超时等待功能，可以自定义时间单位 |
| public final boolean awaitUntil(Date deadline)         throws InterruptedException | 特性3，支持设置截止时间                           |

**Condition等待队列**

Condition队列与AQS中的同步队列共享结点（Node）数据结构。带有头尾指针的单向队列。

**获取：**

1.先要获取一个Lock实例对象。

2.获取与Lock绑定的Condition对象。

3.每当调用lock.newCondition()就会在Lock锁上新增一个等待队列。（特性2）

**应用场景：**使用Condition实现有界队列，当队列为空时队列的获取（删除）操作会阻塞获取（删除）线程，知道队列中新增元素；当队列已满时，队列的插入操作会阻塞插入线程，知道队列出现空位

## await实现原理

```java
public final void await() throws InterruptedException {
    //响应中断
    if (Thread.interrupted())
        throw new InterruptedException();
    //将线程封装为Node入等待队列
    Node node = addConditionWaiter();
    //释放拿到的同步状态
    long savedState = fullyRelease(node);
    int interruptMode = 0;
    //
    while (!isOnSyncQueue(node)) {
      //将线程不在同步队列后将其阻塞置为wait状态
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    //在同步队列中排队获取锁
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
    //处理中断状态
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```



如何将当前线程插入等待队列？addConditionWaiter();

```java
private Node addConditionWaiter() {
    Node t = lastWaiter;
    // If lastWaiter is cancelled, clean out.
    if (t != null && t.waitStatus != Node.CONDITION) {
        //情况所有等待队列中状态不为Condition的结点
        unlinkCancelledWaiters();
        //将最新的尾结点赋值
        t = lastWaiter;
    }
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    if (t == null)
        firstWaiter = node;
    else
        t.nextWaiter = node;
    lastWaiter = node;
    return node;
}
```

将线程包装为Node结点尾插入等待队列后，线程释放锁过程fullyRelease(Node node)

```java
final long fullyRelease(Node node) {
    boolean failed = true;
    try {
        //获取当前同步状态
        long savedState = getState();
        //调用release方法释放同步状态
        if (release(savedState)) {
            failed = false;
            return savedState;//释放成功为9
        } else {
            //抛异常
            throw new IllegalMonitorStateException();
        }
    } finally {
        //若在释放过程中出现异常，将当前节点取消。
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}
```



线程如何可以从await方法退出？

```java
while (!isOnSyncQueue(node)) {
  //将线程不在同步队列后将其阻塞置为wait状态
    LockSupport.park(this);
    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
        break;
}
```
a、在等待时被中断，通过break退出循环。

b、被唤醒后置入同步队列，退出循环。

##Signal方法

```java
public final void signal() {
    //判断当前对象是否拿到锁
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    //拿到当前等待队列的头结点
    Node first = firstWaiter;
    if (first != null)
        //唤醒头结点
        doSignal(first);
}
```

doSignal：

```java
private void doSignal(Node first) {
    do {
        //表示当前头结点是空的
        if ( (firstWaiter = first.nextWaiter) == null)
            //将头结点移除
            lastWaiter = null;
        first.nextWaiter = null;
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
}
```

transferForSignal(Node node)

```java
final boolean transferForSignal(Node node) {
	
    //将结点使用enq方法尾插到同步队列中
    Node p = enq(node);

    int ws = p.waitStatus;
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        LockSupport.unpark(node.thread);
    return true;
}
```



