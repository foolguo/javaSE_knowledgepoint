# 反射（EE框架的基础）

正向：先有类，然后根据类创建对象；

**反射：根据现有对象倒推类的组成**

反射的核心：有一个类Class，任何一个类在JVM中都有**唯一**的一个class对象，**此对象记录该类的组成结构**，用户只能取得无法创建；

要想在java中操作反射，首先取得该类的class对象；（JVM会拷贝一份类的组成结构）

三种方法取得类 class对象：

1.在Object类中有一个 native 的 getClass()对象  ：对象方法

2.通过 类名.class调用；

3,.通过Class.forName("类的全名称") 里的一个静态方法调用 ：

```java
public class Test {
    public static void main(String[] args) {
        Date date=new Date();
        Class<Date> cls=(Class<Date>) date.getClass();//第一种方式   Object里的getClass()
        System.out.println(cls);
        Class<Date> cls1=(Class<Date>) Date.class;//第二种方式  Date.class
        System.out.println(cls1);
        try {
            Class<?> cls2=Class.forName("java.util.Date");//第三种方式  Class类里面的forName  
            System.out.println(cls2.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```



有一个方法 Class 提供的newInstance() 方法



## 反射与工厂方法模式

在反射前的简单工厂模式

通过字符串的匹配进行类的解耦。 没次增加新的分支，就要加if语句，

现在

#2.反射与类操作（package construtor Method Filed）

##2.1取得父类或者父接口信息（Class提供）

  ·取得包信息-Package（描述包信息的类）：public getPackage getPackage()；

  ·取得父类的Class对象：   getSuperClass()：返回 Class<? super T>;

  ·getInterfaces() :  返回一个Class数组Class<?>[]    （因为可能有多个父接口）

##2.2反射与构造方法-Constructor（描述类的构造方法信息）

·取得类中指定参数的构造：

​	a.(只能取得public权限的方法)     <u>public Constructor getConstructor(Class...parameterTypr){}</u>

​	b.(取得类中）  <u>public Constructor[] getDeclaredConstructor(){}</u>

·取得类中所有构造方法：

​	a.(public权限)					<u>public Constructor[]  getConstructors(){}</u>

​     	b.（与权限无关）				<u>public Constructor[] getDeclaredConstructors(){}</u>

<u>**注：一定只是取得本类中的构造方法，不能取得父类的  ，继承无关**</u>



**重点：Class提供的newInctance()方法,调用的是类中的无参构造，如果没有无参构造或者权限不是public，此方法无法使用！！！**|下

##关注Constructor类的如下方法：

public T newInstance(Object...initarges);

重要：<u>如果类中没有无参构造，则只能调用Constructor类提供的newInstance()方法使用有参构造来实例化对象</u>

```java
class Person{
    private String name;
    private int age;
    private Person(){}
    protected Person(String name){}
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    public String toString(){
        return name+" "+age;
    }
}
public class Test{
    public static void main(String[] args)throws Exception {
        //获取类对象
        Class<Person> cls=Person.class;
        //通过类对象取得类指定参数的构造方法
        Constructor<?> constructor=cls.getConstructor(String.class,int.class);
        //相当于Person per=new Person("郭佳玥"，20)；
        Person per=(Person) constructor.newInstance("郭佳玥",20);//Constructor类的无参构造
        System.out.println(per.toString());
    }
}
```

## 2.3取得类中普通方法（重点）

取得类中指定的普通方法：

public Method getMethod(String name，Class<?>...parameterTypes){}

public Method getDeclaredMethod(String name，Class<?>...parameterTypes){}

取得类中所有普通方法：

public Method[] getMethods(){}//在本类和父类中找所有public的方法 包括静态方法

public Method[] getDeclaredMethods(){}在本类中找所有权限的方法

取得普通方法是为了用，所以有一个invoke（Object obj，Object...args）  obj是类的对象，args普通方法参数





## 2.4取得类中属性-同

1.取得类中指定名称属性

public Field getField(String name)

public Fied getDeclaredFied(String name)

2.取得类所有属性

public Field getFields() throws securityException

public Field getDeclaredFields() throws securityException

