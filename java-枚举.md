# java-枚举

1.首先java中的枚举结构实际上是一个类，它继承与java中的一个抽象类，Enum

2.枚举定义结构；

```java
enum{
    对象1，对象2....对象n；
}
```

枚举的对象定义在结构的首行；

3.枚举其实就是一个多例模式，所以要理解枚举，先要看一个多例模式

```java
class Season{
    private static Season Spring=new Season("春");
    private static Season Summer=new Season("夏");
    private static Season Fall=new Season("秋");
    private static Season Winner=new Season("冬");
    private String title;
    public Season(String title){
        this.title=title;
    }
    public String toString(){
        return this.title;
    }
    public static Season getInterence(String ch){
        switch (ch){
            case "春":return Spring;
            case "夏":return Summer;
            case "秋":return Fall;
            case "冬":return Winner;
        }
        return null;
    }
}
public class Test{
    public static void main(String[] args) {
        Season Spring=Season.getInterence("春");
        System.out.println(Spring);
    }
}
```



上述代码如果用枚举实现：代码如下

```java
enum Season {
   //相当于 private Season Spring=new Seanson();
    Spring,Summer,Fall,Winter;
}
public class Test {
    public static void main(String[] args) {
        Season[] season=Season.values();//取得当前枚举类所有对象
        for(Season i:season){
            //ordinal()取得当前对象的索引
            //name()取得当前对象的名字
            System.out.println(i.ordinal()+i.name());
        }
    }
}
```

![](E:\javaSE_knowledgepoint\枚举.PNG)

Enum

类中有几个常用方法:

1.构造方法：

```java
public Enum(int ordinal,String name){}
```

2.取得当前对象名

```java
public final String name(){}
```

3.取得当前对象索引

```java
pulic final int ordinal(){}//从0开始
```

4.取得当前枚举类的所有对象:

values()  ;  返回一个对象数组

 

------------------------------------------

1.既然，enum是一个类结构，那么它可以拥有构造方法，普通属性，普通方法，静态属性，静态方法

2.enum可以实现接口但是不能继承类（因为默认继承了Enum类）

3.枚举对象必须写在第一行

```java
interface PrintHelper{
    void print();
}
enum Season implements PrintHelper{
    Spring("春天"),Summer("夏天"),Fall("秋"),Winter("冬");
    private String title;
    private Season(String title){
        this.title=title;
    }
    public String toString(){
        return this.title;
    }

    @Override
    public void print() {
        System.out.println(this.name());
    }
}
public class Test {
    public static void main(String[] args) {
        Season[] season=Season.values();
        season[0].print();
        System.out.println(season[1].toString());
    }
}
```

![](E:\javaSE_knowledgepoint\枚举1.PNG)