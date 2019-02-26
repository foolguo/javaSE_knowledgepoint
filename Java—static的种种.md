# Java—static修饰代码块和成员变量运行顺序

### static关键字可以干什么

1.修饰类的属性

2.修饰方法

3.static放在代码块之前，代码块为静态代码块，目的是初始化类，为类的属性初始化。

4.静态内部类

### static关键字不能干什么

1.不能修饰外部类；（类存在的意义就是为了实例化，但是static与对象实例化无关，所以不能修饰外部类）

2.不能修饰局部变量；

### 有static时类运行顺序

```java
public class Test{
    static {
        System.out.println("Test的静态块");
    }
    private static Test t1=new Test();
    private static Test t2=new Test();

    {
        System.out.println("Test构造块");
    }
    public Test(){
        System.out.println("Test构造方法");
    }
    public static void main(String[] args) {
        Test t3=new Test();
    }
}
```

![](E:\javaSE_knowledgepoint\2016_2_26运行结果.PNG)

这似乎类加载时是静态块优先于静态成员变量，我们将它们调换顺序



```java
public class Test{
    private static Test t1=new Test();
    private static Test t2=new Test();

    static {
        System.out.println("Test的静态块");
    }

    {
        System.out.println("Test构造块");
    }
    public Test(){
        System.out.println("Test构造方法");
    }
    public static void main(String[] args) {
        Test t3=new Test();
    }
}
```



运行结果

![](E:\javaSE_knowledgepoint\static种种运行.PNG)

我们可以看到，这一次静态成员变量优先于静态块运行，由此我们可以知道静态块和静态成员变量的加载顺序与他们的位置有关，并没有严格的限制；