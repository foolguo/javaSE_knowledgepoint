# Java—多线程4—monitor机制

##对象锁（monitor）

同步代码块：

执行同步代码块后首先执行monitorenter指令，退出时执行monitorexit指令

使用内建锁(synchronize)实现同步，关键在于要获取指定对象monitor监视器，当线程获取monitor后才继续向下执行，否则就只能等待。这个过程是互斥的，每个时刻只能有一个线程获取对象的monitor。

通常一个monitor指令会包含若干个monitorexit指令，原因是JVM需要确保锁在正常执行路径和在异常执行路径上都可以正常的解锁。

同步方法：当使用synchronize修饰方法时，编译后的字节码中访问标记多了一个ACC_SYNCHRONIZE。该标记表示，进入该方法时，JVM均需要进行monitorenter操作，退出该方法时，无论正常返回，JVM均需要进行monitorexit操作。

**锁的获取过程：**

当执行monitorenter时，如果目标锁对象的monitor计数器为0，表示此对象没有被任何其他锁持有。此时JVM会将锁持有线程设置为当前线程，并将计数器加1；如果目标计数器不为0，判断锁持有的线程是否是当前线程，如果是计数器再次加1（锁的可重入性）。如果所对象的持有线程不是当前线程，当前线程需要等待，直至持有线程释放锁。

**锁的释放过程：**

当执行monitorexit时，JVM会将对象计数器-1.当技术器减为0时，代表该对象已经被释放。



## 证明锁的可冲入性和互斥性：

可重入：

```java
class MyThread implements Runnable{

    @Override
    public void run() {
        test1();
    }
    public synchronized  void test1(){
        if(Thread.currentThread().getName().equals("A")){
            System.out.println("A线程进入test1方法");
        }
            teat2();
    }
    public  synchronized void teat2(){

            System.out.println(Thread.currentThread().getName()+"进入test2方法");
    }
}
public class Test{
    public static void main(String[] args) {
        MyThread mt=new MyThread();
        new Thread(mt,"A").start();
    }
}


```



互斥性：

```java
class MyThread implements Runnable{

    @Override
    public void run() {
        test1();
        teat2();
    }
    public synchronized  void test1(){
        if(Thread.currentThread().getName().equals("A")){
            while(true){}
        }
    }
    public  synchronized void teat2(){
        if(Thread.currentThread().getName().equals("B")){
         System.out.println("线程B进入同步方法2");
        }else{
        System.out.println(Thread.currentThread().getName()+"进入test2方法");
        }


    }
}
public class Test{
    public static void main(String[] args) {
        MyThread mt=new MyThread();
        new Thread(mt,"A").start();
        //-------确保A线程先启动-------
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //----------------------------
       new Thread(mt,"B").start();
    }
}


```

互斥性：同一时刻只有一个线程持有锁：

线程A，进入test1，线程B：不能进入test2

上面的代码让线程A先进入，然后循环，

如果线程B可以进入test2，那么就会打印"线程B进入同步方法2"  

但是事实上 线程一直处于阻塞状态，没有进入代码块。



