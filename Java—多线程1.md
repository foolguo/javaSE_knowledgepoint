

#Java—多线程1

## 进程与线程

进程：操作系统中一个程序的执行周期。

线程：进程中的一个任务，一个进程可以有多个线程。

**·······区别·······**

a.每个进程拥有自己的一整套变量，是操作系统中资源分配的最小单位。

线程依托于进程存在，多个线程共享的资源。线程是资源调度的最小单位。

b.启动撤销一个进程要比启动撤销一个线程大的多。（轻量级）

c.没有进程就没有线程，进程一旦终止，其内的线程全部撤销。



高并发：同一时刻线程的访问量非常高。



DDos：黑客技术

## 多线程的实现

**1.1 继承Thread类实现多线程**

java.lang.Thread   JDK1.0提供。

新建一个类的最简单的方法就是直接继承Thread类而后覆写类中的run()【线程的入口】方法，相当于主方法。

**无论哪种方式实现多线程，线程的启动一律调用Thread类的start()方法**。

​	start（）方法解析：

​	a.首先检查线程状态是否为0(线程的默认状态为0表示为启动)，如果线程已经启动再次调用start方法会抛出IllegalThreadStateException（非受查异常）。**一个线程的start()方法只能调用一次。**

​	b.private Native void start0() 通过start0()真正将线程启动。（但是这是一个C语言方法，Native方法是和c语言方法同名的方法）。

​	c**.JVM调用start0()方法进行资源分配与系统调度**，准备好资源启动线程后回调run方法来执行线程的具体任务。

**2.2 Runable接口实现多线程（JDK1.0）**

java.lang包下

```java
class MyThread implements Runnable{
    private String title;
    public MyThread(String title) {
        this.title = title;
    }
    @Override
    public void run() {
        for(int i=0;i<3;i++){
            System.out.println("当前线程："+title+"  "+i);
        }
    }
}
public class Test {
    public static void main(String[] args) {
        MyThread myThread1=new MyThread("A");
        MyThread myThread2=new MyThread("B");
        Thread thread=new Thread(myThread1);
        Thread thread1=new Thread(myThread2);
        thread.start();
        thread1.start();
    }
}
```

@FunctionalInterface(当前接口中是否只有一个抽象方法)

a.Java中多线程的处理就是一个典型的代理模式。其中Thread类完成资源调度、系统分配辅助线程业务类；自定义的线程类辅助真实业务类实现。

b.区别：使用Runnable接口实现的多线程程序类可以更好描述资源共享。

​	例子......

**3.3 callable类实现多线程**

java.util.concurrent.Callable

juc包：java高并发开发程序包：（lock体系，集合类 （安全并发 ConcurrentHashMap，CopyOnWriteArrayList））

```java
java.util.concurrent.Callable<V>:
V call() throws Exception;//线程执行后有返回值：
```

```java
java.util.concurrent.Future<V>:
V get() throws InterruptedException, ExecutionException;//取得Callable接口call方法的返回值
```

当线程需要返回值时只能采用Callable接口实现多线程：

```java
class MyThread implements Callable<String>{
        private int ticket=10;

    @Override
    public String call() throws Exception {
        for (int i=0;i<10;i++){
            System.out.println("还剩下"+ticket--+"票");
        }
        return "票买光了";
    }
}
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable callable=new MyThread();
        FutureTask<String> futureTask=new FutureTask<>(callable);
        Thread thread=new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }
}

```



## 多线程的操作方法

3.1线程的命名与取得：

a.命名

第一组：通过构造方法将线程命名；

··在Thread  构造方法命名；

 public Thread(String name)

 public Thread(Runnable target, String name)

··通过setName给线程命名：public final synchronized void setName(String name)

··取得线程名称：public final String getName()

b.线程的取得

在通过Thread的静态方法 ：public static native Thread currentThread(); 

**Java中的main方法实际上是主线程（main）**

```java
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread run=new MyThread();
        run.run();//直接调用run实际上是启动主线程
    }
}

```



## 线程的同步与死锁

## 线程池

## volatile关键字

