# Lamda表达式（java中的函数式编程）

注意点：

1.实际上是对匿名内部类的优化:

2.使用Lamda表达式实现接口里面只能有一个**抽象方法**，可以有普通方法和静态方法；

3.JDK8新增了一个注解  @FunctionalInterface  表示该接口用Lamda表达式实现

只能用于接口声明，检查当前接口是否只有一个抽象方法；



Lamda表达式语法：

1.方法无返回值

a.方法体实现只有一行：

（）->具体实现代码；

b.方法体有多行实现；

（）->{

​	多行代码的具体实现

​	};

```java
@FunctionalInterface //注解
interface ISubject{
    void test();
}
public class Test {
    public static void main(String[] args) {
        ISubject subject=
                new ISubject() {
                    //相当于 implements  ISubject
                    @Override
                    public void test() {
                        System.out.println("hehe");
                    }
                };
                    subject.test();
                    ISubject subject1=()-> System.out.println("hehe");//方法体只有一行
                    ISubject subject2=()-> {//方法体有多行
                        System.out.println("hehe");
                        System.out.println("haha");
                    }
                    subject1.test();//调用subject1的test();
                    subject2.test();//调用subject2的test();
        
    }
}
```





2.方法有返回值

​	a.只有一行代码，省略return；

​	ISubject subject=(r1,r2)->r1+r2;

```java
@FunctionalInterface
interface ISubject{
    int add(int r1,int r2);
}
public class Test{
    public static void main(String[] args) {
        ISubject subject=(r1,r2)->r1+r2;
        System.out.println(subject.add(10,20));
    }
}
```

​	b.方法体有多行代码，此时return不能省略

```java
@FunctionalInterface
interface ISubject{
    int add(int r1,int r2);
}
public class Test{
    public static void main(String[] args) {
        ISubject subject=(r1,r2)->{
            r1=r1+10;
            r2=r2+10;
            return r1+r2;
        };
        System.out.println(subject.add(10,20));
    }
}
```



##方法引用：一般结合Lamda表达式使用

1.首先JDK8之前，只可以引用数组，类，接口；（指向一个对象或者数组的堆内存地址）

**2.JDK8对方法进行引用**（四种）  方法引用是引用的方法的具体实现

​	a.引用摸个类的静态方法   **语法   类名::静态方法名**

```java
interface ISubject1<P,R>{
    /*int add(int r1,int r2);*/
    R switchPara(P p);
}
public class Test {
    public static void main(String[] args) {
        //方法引用 静态方法
      ISubject1<Integer,String> stringISubject1=
              String::valueOf;
        System.out.println(stringISubject1.switchPara(10)+10);
    }
}
```

​	b.引用某个对象的普通方法    **语法   对象::方法名**

```java
interface ISubject1<R>{
    R switchPara();
}
public class Test {
    public static void main(String[] args) {
        ISubject1<String> subject="hello"::toLowerCase;
        System.out.println(subject.switchPara());
    }
}
```

​	c.引用某个类的普通方法    语法   类名::方法名

```java
interface ISubject1<R,P>{
    R switchPara(P p1,P p2);
}
public class Test {
    public static void main(String[] args) {
        ISubject1<Integer,String> subject=String::compareTo;
        System.out.println(subject.switchPara("郭","方"));//相当于调用了compareTo();
    }
}
```

​	d.引用某个类的构造方法；

```java
interface ISubject1<R,PI,PS>{
    R switchPara(PI name,PS age);
}
class Person{
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public  String toString(){
        return "Person{"+"age="+age+",name="+name+'}';
    }
}
public class Test {
    public static void main(String[] args) {
        ISubject1<Person,String,Integer> subject1=Person::new;
        Person per1=subject1.switchPara("guo ",21);
        System.out.println(per1);
    }
}
```



