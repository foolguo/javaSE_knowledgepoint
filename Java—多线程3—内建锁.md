# Java—多线程3—内建锁

同步问题：多个线程轮番抢占共享资源带来的问题。

```java
class MyThread implements Runnable{
    private int ticket=10;

    @Override
    public void run() {
        while (ticket > 0) {
           try {
                 Thread.sleep(1000);
          } catch (InterruptedException e) {
                 e.printStackTrace();
             }
            System.out.println("当前线程" + Thread.currentThread() + "还剩下" + ticket-- + "票");
          }
     }
}
public class Test {
    public static void main(String[] args) {
        MyThread mt=new MyThread();
        new Thread(mt,"线程A").start();
        new Thread(mt,"线程B").start();
        new Thread(mt,"线程C").start();
    }
}

```





解决：一次只让一个线程进入循环，这样就避免了三个线程同时修改数据导致最终的票数错误



## synchronize关键字

1.同步代码块

同步代码块，在方法中定义，用synchronize关键字定义的代码块

```java
synchronize(对象){}
```

其中，对象可以是

**要使用同步代码块必须要设置一个锁对象，一般可以锁当前对象this**

改变上述代码

```java
 public void run() {
        synchronized (this) {
            while (ticket > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程" + Thread.currentThread() + "还剩下" + ticket-- + "票");
            }
        }
    }
```

**上述操作表示同一时刻只能有一个线程进入同步代码块修改数据，也就是同一时刻只能有一个对象拿到锁**

2.同步方法

**在方法上添加synchronize关键字，等同于同步代码块锁当前对象**

```java
 public   void run() {
     while (ticket > 0) {
         sale();
     }
}
public synchronized void sale(){
     try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程" + Thread.currentThread() + "还剩下" + ticket-- + "票");
}
```



**还有一种情况：**

在普通类中也可以有同步方法：

下面这段代码我们期望的是一次只有一个线程进入testA()调用testB(),但是事实不是如此，三个线程同时进入testA()，所以 synchronize没有锁住代码：

这是因为3个线程同时进入run()方法，并且每个线程都可以拿到A的实例化对象a，相当于每个线程都有一个对象锁，所以可以同时进test();

```java
class A{
    public synchronized void testA(){
        System.out.println("方法A"+"当前线程"+Thread.currentThread().getName());
        testB();
    }
    public synchronized void testB(){
        System.out.println("方法B"+"当前线程"+Thread.currentThread().getName());
    }
}
class MyThread implements Runnable{

    @Override
    public void run() {
        A a=new A();
        a.testA();
    }
}

public class Test {
    public static void main(String[] args) {
        MyThread mt=new MyThread();
        new Thread(mt,"线程A").start();
        new Thread(mt,"线程B").start();
        new Thread(mt,"线程C").start();
    }
}
```

改进：全局锁

方案1： 

既然上述问题是三个线程都拿到了对象锁产生的问题，所以我们可以值产生一个A的实例化对象，这样就只有一个对象拿到了锁。

```java
class A{
    public synchronized void testA(){
        System.out.println("方法A"+"当前线程"+Thread.currentThread().getName());
        testB();
    }
    public synchronized void testB(){
        System.out.println("方法B"+"当前线程"+Thread.currentThread().getName());
    }
}

class MyThread implements Runnable{
    private  A a;
    public MyThread(A a) {
        this.a = a;
    }
    @Override
    public void run() {
        a.testA();
    }
}
public class Test {
    public static void main(String[] args) {
        MyThread mt=new MyThread(new A());
        new Thread(mt,"线程A").start();
        new Thread(mt,"线程B").start();
        new Thread(mt,"线程C").start();
    }
}
```



方案2：在原代码上加上

```java
    public synchronized static void testA(){
        System.out.println("方法A"+"当前线程"+Thread.currentThread().getName());
        testB();
    }
    public synchronized static  void testB(){
        System.out.println("方法B"+"当前线程"+Thread.currentThread().getName());
    }
```



##总结：

**同步代码块：**

1.锁类实例对象

synchronize(this){}

2.锁类对象

synchronize(类名称.class){}

3.锁任意实例对象

synchronize(obj){}

**同步方法：**

普通方法+synchronize：锁的是当前对象

静态方法+synchronize：锁的是类对象—全局锁

