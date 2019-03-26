#单级VO设置

目的，一次同时设置多个属性值；

设置一个员工信息类

```java
package com.bit.reflect;

/**
 * @Author: yd
 * @Date: 2019/3/25 20:09
 * @Version 1.0
 */
public class Emp {
    private String name;
    private String job;
    private String age;

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", sge='" + sge + '\'' +
                '}';
    }

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

    public String getSge() {
        return sge;
    }

    public void setSge(String sge) {
        this.sge = sge;
    }
}
```



如果上述的类要进行属性值的设置的话要分别设置 name job age 三个属性值；

为了方便操作我们统一将Emp类的属性值的设置交给 另一个类EmpAction设置；

```java
package com.bit.reflect;

/**
 * @Author: yd
 * @Date: 2019/3/25 20:10
 * @Version 1.0
 */

/**
 *让用户真实操作的类
 */
public class EmpAction {
    private Emp emp=new Emp();//真实类
    /**
     * 设置值
     */
    public void setValue(String value)throws Exception{
        BeanUtils.setBeanUtils(this,value);  //传入当前对象，方便调用get方法取得真实类的实例化对象

    }

    public Emp getEmp() {
        return emp;
    }
}

```

将具体的设置方法再封装一个工具类 BeanUtils 这样做的目的是如果下次再有 类名.属性名1:XXX|类名.属性名2.XXX要进行操作的话,就可以直接调用这个类:

进行设置的核心逻辑

```java
package com.bit.reflect;

/**
 * @Author: yd
 * @Date: 2019/3/25 20:13
 * @Version 1.0
 */

import java.lang.reflect.Method;

/**
 * 真正设置值的；类， 只要传入参数是规定的都可以就行操作
 */
public class BeanUtils {
    /**
     *
     * @param obj
     * @param setValue   传入数据类型的形式是  emp.name:haha|emp.job:xx|emp.age:xx
     */
    public static void setBeanUtils(Object obj,String setValue)throws Exception{
       //将字符串通过|分为  emp.name:haha   emp.job:xx  emp.age:xx
        String[] temp=setValue.split("\\|");//由于|是为操作符，要进行转义
        
        //分别对每个属性进行操作
        for (int i=0;i<temp.length;i++){
            String[] result=temp[i].split(":");
            //真正要设置的属性的值  
            String realValue=result[1];
            //真实类名
            String className=result[0].split("\\.")[0];
            //真实属性名
            String attName=result[0].split("\\.")[1];
            
            //通过反射取得真实类（这里是员工类）的实例化对象
            //不能通过obj直接调用，因为接收的类型是Object类
            Object realObj=getRealObject(obj,className);
            
            //通过反射取得真实主题类对象
            Class<?> cls=realObj.getClass();
            //拼凑set方法名
            String realAttName="set"+initCap(attName);
            //获取类的Method对象
            Method method=cls.getMethod(realAttName,String.class);
            //通过invoke设置值  相当于emp.setXXX=xxx;
            method.invoke(realObj,realValue);

        }
    }
        /**
     * 此方法为取得真实主题类对象
     * @param obj
     * @param className
     * @return
     * @throws Exception
     */
    private static Object getRealObject(Object obj,String className)throws Exception{
        Class<?> cls=obj.getClass();//获取XXAction的Class对象
        String realAtr="get"+initCap(className);//取得get方法名
        Method method=cls.getMethod(realAtr);
        return method.invoke(obj);

    }
    private static String initCap(String className){
        return className.substring(0,1).toUpperCase()+
                className.substring(1);

    }
}

```

