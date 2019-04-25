java-反射单级VO操作

主要用于一次可以设置多个属性值；

例如我们现在定义一个Person类，里面有姓名和年龄属性；如果我们调用set来设置属性，要设置两次，如果人数增多并且属性值不是两个就要多次调用set方法；

例子

```java

class Emp{
    private String name;
    private String dept;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}

```

现在有需求要一次性设置这两个值  传入  属性1：xxx|属性2：xxx

首先创建一个EmpAction类，这个类是真实交给用户操作的类

```java
class EmpAction{
    private Emp emp=new Emp();
    public  void setValue(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BeanUtil.setValue(this,name);
    }

    public Emp getEmp() {
        return emp;
    }
}

```



还有一个工具类，是设置值的类

```java
class BeanUtil{
    /*
    * 传入：
    * emp.name:HaHa|emp.dept: 上班
    * */
    public static void setValue(Object obj,String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String[] value=name.split("\\|");
        for(int i=0;i<value.length;i++){
            //属性值HaHa
            String realValue=value[i].split(":")[1];
            //类名 emp
            String objectValue=value[i].split(":")[0].split("\\.")[0];
            //属性名 name
            String objectName=value[i].split(":")[0].split("\\.")[1];
            Object objectAction=getObject(obj,objectValue);
            setValueObject(objectAction,objectName,realValue);

        }
    }
    public static Object getObject(Object obj,String objectValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class cls=obj.getClass();
        String str="get"+spellName(objectValue);
        Method method=cls.getMethod(str);
        return method.invoke(obj);
    }
    public static void setValueObject(Object obj,String objectName,String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Class cls=obj.getClass();
            String str = "set"+spellName(objectName);
            Method method=cls.getMethod(str,String.class);
            method.invoke(obj,value);

    }
    public static String spellName(String name){
        return name.substring(0,1).toUpperCase()+name.substring(1);
    }
}
```

```java
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String str="name:托尼|job:造型师";
      EmpAction empAction=new EmpAction();
      empAction.setValue(str);
        System.out.println(empAction.getEmp());

    }
}
```

这样就可以一次性设置多个值