# Java-IO序列化

序列化：将内存中保存的对象变为二进制流进行输出或者保存在文本中。

 将对象变为二进制流。

 不是所有类都有序列化的功能，要想类实现序列化要实现serializable接口。    

 这个接口里面没有任何方法，只是一个标识接口。



##序列化和反序列化

要使用io包中提供的两个处理类：

序列化类：ObjectOutputStream：Output的直接子类

构造方法（选择目标终端）：public ObjectOutputStream(OutputStream out){}

核心方法：public final void writerObject(Object obj) throw IOException:将obj变成二进制流输出到目标终端：

```java
class Teacher implements Serializable{
    private  String name;
    private  String job;


    public Teacher(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + job +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
```



```java
public class  Test{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher stu = new Teacher("鬼灯","辅佐官");
        File file=new File("D:\\Test.text");
        OutputStream outputStream=new FileOutputStream(file);
        ObjectOutputStream oss= new ObjectOutputStream(outputStream);
        oss.writeObject(stu);
        oss.close();
        outputStream.close();
       /* InputStream inputStream=new FileInputStream(file);
        ObjectInputStream iss=new ObjectInputStream(inputStream);
        Teacher stu1= (Teacher) iss.readObject();
        System.out.println(stu1.getName()+"  "+stu1.getJob());
        System.out.println(stu1==stu);*/
    }
}
```

注：这个保存在文件中是一堆你看不懂的乱码

反序列化类：ObjectInputStream：

构造方法（选择反序列化的目标终端）：将二进制流反序列化为对象

```java
public class  Test{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
     
      	InputStream inputStream=new FileInputStream(file);
        ObjectInputStream iss=new ObjectInputStream(inputStream);
        Teacher stu1= (Teacher) iss.readObject();
        System.out.println(stu1.getName()+"  "+stu1.getJob());
        System.out.println(stu1==stu);
    }
}
```

![](E:\javaSE_knowledgepoint\序列化运行结果.PNG)

## transient关键字

若希望类中的若干属性不被序列化，可以在属性前添加transient关键字。

如果在 Teache  中  属性前加上  transient 关键字 job 那么这个属性不被保存、

那么结果变为  ：

![](E:\javaSE_knowledgepoint\transien结果.PNG)