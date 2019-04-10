# **Java**-反射

##1.什么反射：

先看正向操作，先有类，再根据类产生类的实例化对象；

反射就是根据得到类的Class对象，在更加Class对象实例化类

任何类的都有一个描述自己的Class类，记录他的构造方法，普通方法，属性等

## 2.取得类的Class对象

1.Object类里面的getClass()

```java
public final native Class<?> getClass();
```

2.类.Class

3.通过Class里面的forName("类的全名称");

```java
 public static Class<?> forName(String className)
```

Class类有newInstance方法可以实例化对象

```java
public T newInstance()
    throws InstantiationException, IllegalAccessException
```

改造工厂方法：

```java
interface Fruit{
    void buy();
}
class Apple implements Fruit{

    @Override
    public void buy() {
        System.out.println("买苹果");
    }
}
class Banana implements Fruit{

    @Override
    public void buy() {
        System.out.println("买香蕉");
    }
}
class Factory{
    //传入类的全名称，这样不管商品类如何增加都不用改工厂类
    public static Fruit getInstance(String classNmae) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Fruit fruit=null;
        Class cls=Class.forName(classNmae);
        fruit= (Fruit) cls.newInstance();
        cls.getConstructors();
        return fruit;
    }
}
public class Test{
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Fruit fruit=Factory.getInstance("com.bit.reflect.Apple");
        fruit.buy();

    }
}
```







##3.取得类的构造方法Constructor

| 取得类构造方法的                                             | 描述                                 |
| ------------------------------------------------------------ | ------------------------------------ |
| public Constructor<?>[] getConstructors() throws SecurityException | 获取本类和父类的所有public的构造方法 |
| public Constructor<?>[] getDeclaredConstructors() throws SecurityException | 获取本类声明的所有权限的构造方法     |
| public Constructor<T> getConstructor(Class<?>... parameterTypes)     throws NoSuchMethodException, SecurityException | 获取本类指定参数的构造方法           |
| public Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)     throws NoSuchMethodException, SecurityException | 获得本类所有权限的构造方法           |

**得到Constructor后可以根据    **

**public T newInstance(Object ... initargs)    throws InstantiationException, IllegalAccessException,           IllegalArgumentException, InvocationTargetException**

这里是Constructor 类的方法

先定义Person和Student类，Student继承Person

```java
class Person {
    private String name;
    public Integer age;
    public   static Integer ID;


    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    protected Person(Integer age) {
        this.age = age;
    }
    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public  static void print(){

    }
}


class Student extends Person{
    private String school;
    private  Student() {
    }

    public Student(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
```



```java

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
      Person per = new Person();
        /**
        三种取得Class对象的方法
        */
        Class cls1 = per.getClass();
        Class cls2 = Person.class;
        Class cls3 = Class.forName("com.bit.reflect.Student");
        
        /*
        	1.getDeclaredConstructors()所有权限
        */
        Constructor[] constructor1=cls1.getDeclaredConstructors();
        /*
        	2.getConstructors()
        */
         Constructor[] constructor2=cls1.getConstructors();
        /*
        	3.getConstructor()
        */
        Constructor constructor3 = cls1.getConstructor(String.class);
        /*
        	4.getDeclaredConstructor()
        */
        Constructor constructor4 = cls3.getDeclaredConstructor(String.class);
        Object obj=constructor4.newInstance()
    }
}
```

## 4.取得Method的对象

| 取得类方法的                                                 | 描述                                                 |
| ------------------------------------------------------------ | ---------------------------------------------------- |
| public Method[] getDeclaredMethods() throws SecurityException | 取得本类中的所有权限的方法包括static                 |
| public Method getDeclaredMethod(String name, Class<?>... parameterTypes)     throws NoSuchMethodException, SecurityException | 取得本类中指定名字的的任意权限的方法，包括static方法 |
| public Method[] getMethods() throws SecurityException        | 取得本类和父类public权限的方法 包括static            |
| public Method getMethod(String name, Class<?>... parameterTypes)     throws NoSuchMethodException, SecurityException | 取得本类和父类指定名字public权限的方法包括static     |

```java
public Object invoke(Object obj, Object... args)
    throws IllegalAccessException, IllegalArgumentException,
       InvocationTargetException
```

**invoke方法，取得方法**



```java
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
      Person per = new Person();
        /**
        三种取得Class对象的方法
        */
        Class cls1 = per.getClass();
        Class cls2 = Person.class;
        Class cls3 = Class.forName("com.bit.reflect.Student");
        Method method1=cls2.getMethod("getName");
        System.out.println(method1.invoke(object));
        Method method2=cls2.getMethod("getAge");
        System.out.println(method2.invoke(object));
    }
}
```

## 5.取得类的属性

| 取得类的属性                                                 | 描述                                             |
| ------------------------------------------------------------ | ------------------------------------------------ |
| public Field[] getFields() throws SecurityException          | 取得本类和父类的public权限的所有属性包括static   |
| public Field getField(String name)     throws NoSuchFieldException, SecurityException | 取得本类和父类指定名字public权限的属性包括static |
| public Field[] getDeclaredFields() throws SecurityException  | 取得本类中所有权限的属性 包括static              |
| public Field getDeclaredField(String name)     throws NoSuchFieldException, SecurityException | 取得本类中指定名字所有权限的属性，包括static     |

还有一组取得和设置属性的方法

**取得值**

```java
public Object get(Object obj)
    throws IllegalArgumentException, IllegalAccessException
```

**设置值**

```java
public void set(Object obj, Object value)
    throws IllegalArgumentException, IllegalAccessException
```



```java
import java.io.File;

import java.lang.reflect.Constructor;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

      Person per = new Person();

        /**

        三种取得Class对象的方法

        */

        Class cls1 = per.getClass();
        Class cls2 = Person.class;
        Class cls3 = Class.forName("com.bit.reflect.Student");
        Object object=cls1.getConstructor(String.class,Integer.class).
                newInstance("hah",22);

        Field fields1=cls1.getDeclaredField("name");
        fields1.setAccessible(true);//由于name是私有的，所以无法直接取得所以要动态破坏封装性 ，再一次进程中有效
        System.out.println(fields1.get(object));

    }

}

```

```java
public void setAccessible(boolean flag) throws SecurityException//动态破话封装性
```