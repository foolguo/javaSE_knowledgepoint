#Java-线程1

##1.线程和进程

进程：一个程序的执行周期叫做一个进程；

线程：一个程序同时执行多个任务。每个任务就是一个进程

进程和线程的区别:每个进程自己拥有一套变量而多个线程共同使用一套变量。

进程结束，线程也不存在。

应用：在游览器上下载一次下载图片，电影等不同任务，这个就是线程的引用

##2.创建一个简单线程（通过Thread类）

通过集成Tread类来实现线程

```java
class A extends Thread{
    private String title;
    public A(String title){
        this.title=title;
    }
    @Override
    public void run() {
        System.out.println("启动一个线程"+title);
    }
}
public class Test{
    public static void main(String[] args) {
            A a1=new A("1");
            A a2=new A("2");
            A a3=new A("3");
            a1.start();
            a2.start();
            a3.start();
    }
}
```

注意：通常run()方法就相当于main方法的主方法，要启动一个线程我们不能直接调用run方法，而是要调用

Thread类里面的start()方法来启动线程，否则，直接调用run()方法就是简单的打印； 

##3.通过Runnable创建线程：

首先，Runnable是Tread的父接口，要说明一点的是所有线程的启动都要通过Thread方法的start(),但是直接继承Thread类会有继承局限，所以我们通常实现Runnable来实现接口；

```java
class C implements Runnable{
    @Override
    public void run() {
        System.out.println("当前线程"+Thread.currentThread().getName());
    }
}
public class Test{
    public static void main(String[] args) {
        C c = new C();
        /*
           还可以通过内部类
          Runnable c=new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());
            }
        }
        //lamda表达式
        Runnable c=()->System.out.println("当前线程"+Thread.currentThread().getName());
        */
        Thread thread1 = new Thread(c,"线程1");
        Thread thread2= new Thread(c,"线程2");
        Thread thread3= new Thread(c,"线程3");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
```

但是线程执行的顺序并不是由程序中写的顺序觉定的   要看系统调度

可以这样做的原因是因为，Thread类的构造方法可以传入Runable接口。

**这种模式其实是代理模式，通过传入真实主题类对象c来调用其的run()方法**

**还有，所有线程的启动都是要同过start()方法启动的**

来介绍一下Thread类的常用方法

| 方法名                                                 | 方法名方法名作用   |
| ------------------------------------------------------ | ------------------ |
| public Thread(Runnable target){}                       | 传入Runnable接口   |
| public Thread(Runnable target, String name){}          | 传入接口并设置名字 |
| public static native Thread currentThread();           | 获得当前线程       |
| public final synchronized void setName(String name) {} | 设置线程名字       |
| public final String getName()                          | 获得线程名字       |



##4.了解Callable类

Callable是一个接口，但是它的抽象方法call()方法可以有返回值

```java
class A implements Callable<String>{
    private int ticket=10;
    @Override
    public String call() throws Exception {

            while(ticket>0){
                System.out.println("剩余票数" + ticket--);
            }
        return "没有票了";
    }
}
public class Test{
    public static void main(String[] args) {
        FutureTask task=new FutureTask(new A());
        new Thread(task,"黄牛1").start();
        new Thread(task,"黄牛2").start();
        new Thread(task,"黄牛3").start();

    }
}
```

## 5.观察主线程

前面说过，线程一定要用start() 方法调用，我们来看这个现象

```java
class MyThread implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
public class Test{
    public static void main(String[] args) {
    MyThread myThread=new MyThread();
    myThread.run();//子线程调用
    new Thread(myThread).start();//主线程调用

    }
}
```

其实用run()调用，其实是主线程调用run()方法，所以，主方法也是一个线程........   所有子线程都是通过主线程调用的



图片

## 6.线程优先级；

获得线程的优先级：

```java
public final int getPriority()
```

设置线程优先级

```java
 public final void setPriority(int newPriority)
```

线程优先级   用【1,10的整数来描述】  还定义了一些常量来描述

```java
public final static int MIN_PRIORITY = 1;
public final static int NORM_PRIORITY = 5;
public final static int  MAX_PRIORITY = 5;
```

**线程优先级有，继承性，在那个线程调用线程，那么这两个线程就有一样的继承性**

就是在A线程调用B线程，那么B线程和A线程有相同的继承性；

```java
class MyThread1 implements Runnable{
    @Override
    public void run() {
        System.out.println("线程m1优先级:  "+Thread.currentThread().getPriority());
        MyThread2 myThread2=new MyThread2();
        new Thread(myThread2).start();
    }
}
class  MyThread2 implements Runnable{
    @Override
    public void run() {
        System.out.println("线程m2优先级：  "+Thread.currentThread().getPriority());
    }
}
public class Test{
    public static void main(String[] args) {
    MyThread1 myThread1=new MyThread1();
    Thread thread=new Thread(myThread1);
    thread.setPriority(Thread.MIN_PRIORITY);
    thread.start();

    }
}
```

## 7.守护线程

守护线程是一个特殊的线程，属于陪伴线程。线程可以分两种，守护线程和用户线程，只要任何一个用户线程没有结束，那么守护线程就不会结束，典型的守护线程就是垃圾回收线程。

两个方法：1.isDaemon()该线程是否是个守护线程

​		    2.setDaemon()设置守护线程

## 8.线程的几种状态

·阻塞状态，·就绪状态，·运行状态，

在这里我们介绍几种方法

sleep()方法(线程休眠)：线程会立刻交出CPU，不会释放对象锁让线程从运行态到阻塞态

```java
public static native void sleep(long millis) throws InterruptedException;
```

yield()(线程让步)：线程会交出CPU，不会立刻交出，让有相同优先级的线程交出CPU，不释放对象锁

让线程从运行态到就绪态

```java
public static native void yield();
```

join()方法：等待线程终止：在那个线程执行就让那个线程休眠，join()从运行态到阻塞态，会释放对象锁。

对wait()的一种包装。

```java
 public final void join() throws InterruptedException 
```



##9.Interrupt()方法

首先，先介绍一stop()方法，他的作用是终止线程，不管线程内还有没有任务没有执行完，但是这种方法及其不推荐使用，因为会产生残碎数据；



首先，Runnable是Tread的父接口，要说明