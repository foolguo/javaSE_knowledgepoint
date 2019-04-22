# Java—多线程 生产和消费者模型

##生产者消费者模型

生产者和消费者不直接通信 而是通过一个阻塞队列通信



##**wait()方法**

使得当前线程立刻停止运行，处于等待状态，并将当前状态置入锁对象的等待队列中，直到被通知（notify（））或中断为止。

**使用条件：只能在同步方法或者同步代码块中使用，必须是内建锁。**

wait()调用后立刻释放对象锁。

1.一直等，直到被唤醒或者中断。

```java
public final void wait() throws InterruptedException {
    wait(0);
}
```

2.超时等待。若在规定时间内未被唤醒，则线程退出

```java
public final native void wait(long timeout) throws InterruptedException;
```

3.在2的基础上进行纳秒级别的参数传入

```java
public final void wait(long timeout, int nanos) throws InterruptedException
```

##notify（）

唤醒处于等待中的线程

使用条件：notify必须在同步方法或者同步代码块中调用，用来唤醒等待该对象的其他线程，如果有多个线程在等待，随机挑选一个唤醒（但是具体唤醒哪一个是版本决定的，JDK8 默认是等待队列的第一个）

```java
public final native void notify();
```

notify方法调用后不会立刻释放对象锁，要等到当前线程执行完后释放对象锁。

**wait和 notify必须操作的是统一个对象**



```java
class MyThread implements Runnable{
    private boolean flag;
    private Object obj;

    public MyThread(boolean flag, Object obj) {
        this.flag = flag;
        this.obj = obj;
    }

    public void waitMethod() throws InterruptedException {
        synchronized (obj){
            System.out.println(Thread.currentThread().getName()+"开始");
            obj.wait();
            System.out.println(Thread.currentThread().getName()+"结束");
        }
    }
    public void notifyMethod(){
        synchronized (obj){
            System.out.println(Thread.currentThread().getName()+"开始");
            obj.notify();
            System.out.println(Thread.currentThread().getName()+"结束");
        }
    }

    @Override
    public void run() {
        if(flag){
            try {
                waitMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            notifyMethod();
        }
    }
}

public class Test{
    public static void main(String[] args) throws InterruptedException {
        Object obj=new Object();
        MyThread mt1=new MyThread(true,obj);
        MyThread mt2=new MyThread(false,obj);
        new Thread(mt1,"wait").start();
        Thread.sleep(1000);
        new Thread(mt2,"notify").start();
    }
}
```

运行结果：

wait开始
notify开始
notify结束
wait结束



可以看出，wait不唤醒会一直等下去，但是notify（）调用后不会立刻释放对象锁，而是要到当前线程执行完才叫锁。

## 线程由运行态到阻塞态

1.调用sleep，立刻交出CPU，不释放锁

2.线程调用阻塞时IO（BIO）方法

3.线程获取锁失败进入阻塞状态

4，线程调用wait()

5.线程调用suspend（），将线程挂起

 **每个锁对象都有两个队列。**

1.同步队列，存储获取锁失败的线程。

2.另一个称为等待队列存储调用wait（）等待线程。

将线程唤醒实际上是将等待队列的线程移到同步队列中竞争锁。