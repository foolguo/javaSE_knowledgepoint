java-类加载器

1.类加载的概念，

javac  类名.java   这个过程就是将.java文件转换成与平台无关的.class二进制文件

而类加载就是  java  类名   这个的过程，就是将主类加载到JVM虚拟机的过程；

  类加载器就是将.class文件读取到JVM虚拟机的模块；

```java
class Member{}
public class TestLoard {
    public static void main(String[] args) {
        Class cls=Member.class;
        System.out.println(cls.getClassLoader());
        System.out.println(cls.getClassLoader().getParent());
        System.out.println(cls.getClassLoader().getParent().getParent());
    }
}
```



2.类加载器

BootStarp(启动类加载器)

使用C++实现，是JVM的一部分，被JVM虚拟机直接引用，加载java_HOME\lib下的能被虚拟机识别的文件

ExtClassLoader(扩展类加载器)

使用java实现，被java程序直接引用，负责加载java_HOME\lib\ext能被虚拟机识别的文件

APPClassLoader(应用程序类加载器)

使用java实现，被java程序直接引用，赋值加载(ClassPath)用户自定义的类库。



3.双亲委派模型

定义：下面这张图是双亲委派模型



工作流程：在进行类加载的时候，每个加载器不会直接进行加载而是直接将其交付给上一层的类加载器，就是说每个类都要先交给启动类加载器（BootStrap），其现在自己的加载目录寻找，当发现不能发现这个加载请求后才会交给下层加载。



意义：这样做可以保持程序的稳定。

举个例子：我们可以自己定义一个Object类，交给类加载器进行加载，如果没有双亲委派模型，那么这个Object类就会在APPClassLoader(应用程序类加载器)加载，那么java所有的类就会继承你的Object类，那么（呵呵呵呵呵），乱套了。所以双亲委派模型保证Object类在所有环境下都是一个类。