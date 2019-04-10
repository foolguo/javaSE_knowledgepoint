java-反射单级VO操作

主要用于一次可以设置多个属性值；

例如我们现在定义一个Person类，里面有姓名和年龄属性；如果我们调用set来设置属性，要设置两次，如果人数增多并且属性值不是两个就要多次调用set方法；

例子

```java

public class Emp {
    private String name;
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
     @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}

public class Test {
    public static void main(String[] args) {
        Emp emp=new Emp();
        emp.setName("托尼老师");
        emp.setJob("前台");
                      
    }
}
```

现在有需求要一次性设置这两个值  传入  属性1：xxx|属性2：xxx

首先创建一个EmpAction类，这个类是真实交给用户操作的类

```java
public class EmpAction {
    private Emp emp=new Emp();
    public void setValue(String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BeanUtil.setBeanValue(this,value);
    }
    public  Emp getEmp(){
        return emp;
    }
}

```



还有一个工具类，是设置值的类

```java
public class BeanUtil {
    public static void setBeanValue(Object obj,String str) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] result=str.split("\\|");
        for(int i=0;i<result.length;i++){
            String name=result[i].split(":")[0];
            String value=result[i].split(":")[1];
            //获取类对象EMP-通过传入的EmpAction的实例化对象obj调用
            Object currentObj=getObject(obj);
            //调用真实类的setter类
            setValue(currentObj,value,name);//传入emp对象，要设置的值，方法
        }
    }
    public static Object getObject(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName="getEmp";
        Class cls=obj.getClass();//获取cls对象
        Method method=cls.getMethod(methodName);//获取对象的get方法
        return method.invoke(obj);//通过invoke调用invoke方法----->获取到EMP对象
    }
    public static void setValue(Object obj,String value,String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName="set"+intiCap(name);
        Class cls=obj.getClass();
        Method method=cls.getMethod(methodName,String.class);
        method.invoke(obj,value);//通过invoke设置值

    }
    public static String intiCap(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
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