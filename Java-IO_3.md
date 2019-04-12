# Java-IO_3

## 内存流

1.内存输入流：ByteArrayInputStream  ，  CharArrayReader

2.内存输出流：ByteArrayOutputStream， CharArrayWriter

内存输入流是将内容输入到内存中，输出流是将内存中的内容输出

```java
public class Test {
    public static void main(String[] args) throws IOException {
        //获取内存输入流
        ByteArrayInputStream in=new ByteArrayInputStream("Hello world".getBytes());
        //获取内存输出流
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        int len=-1;
        while ((len=in.read())!=-1){
            out.write(Character.toUpperCase(len));//每个字符读取然后每个字符大写
        }
        System.out.println(out);//直接输出输出流对象，覆写了toString方法
        in.close();
        out.close();
    }
}
```

这样做的目的是要转换的内容多的话的话在内存操作快

##打印流

打印流的意义，打印流实际上就是输出流OutputStream，Writer的强化版本为了解决输出流只可以输出byte和char的问题，我们来看：如果你要输出一个int类型

首先你要将 int---->String ----->byte  才可以进行输出

先来自己实现一个打印流

```java
class PrintUtil{
    private OutputStream outputStream;

    public PrintUtil(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public void print(String value) throws IOException {
        outputStream.write(value.getBytes());
    }
    public void println(String value) throws IOException {
        this.print(value.getBytes()+"\n");
    }
    public void print(int value) throws IOException {
        print(String.valueOf(value));
    }
    public void println(int value) throws IOException {
        println(String.valueOf(value));
    }
    public void print(double value) throws IOException {
        print(String.valueOf(value));
    }
    public void println(double  value) throws IOException {
        println(String.valueOf(value));
    }
}
public class Test {
    public static void main(String[] args) throws IOException {

        PrintUtil printUtil=new PrintUtil
                (new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\Test.txt")));
        printUtil.print("haha");
        printUtil.println(1234);
```

Java为我们提供了打印流：PrintStream   PrintWriter（用的较多）

打印流实际上是，装饰设计模式，核心依然是某个类的功能，但是为了得到更好的操作效果，让其支持的功能更多一些。

## 格式化输出

在PrintStream有一个格式化输出，有一个

```java
public PrintStream printf(String format,Objrct ...args)
```

在String类中也有一个格式化字符串的方法

```java
public static String format(String format,Objrct ...args)
```

## 系统输出

标准输出public final static PrintStram out

错误输出public final static PrintStram err

标准输入(键盘)public final static InputStram in

它们都来自System类，我们一直用的System.out.println()都是IO操作

实际上是用来PrintStream的println方法；

## 系统输入

Java没有提供直接的用户输入，所以还是要靠IO完成

我们先看历史方法，由于System.in实际上是InputStream的对象，所以可以直接

```java
public class Test {
    public static void main(String[] args) throws IOException {
        InputStream in=System.in;
        byte[] bytes=new byte[1024];
        System.out.println("请输入信息");
        int len=in.read(bytes);
        System.out.println("内容为"+new String(bytes,0,len));

    }
}

```

但是上面的方法有一个大大的缺陷就是超过1024，就不能读了，所以我们改了一下

```java
public class Test {
    public static void main(String[] args) throws IOException {
        InputStream in=System.in;
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] bytes=new byte[10];
        System.out.println("请输入信息");
        int len =-1;
        while((len=in.read(bytes))!=-1){
            out.write(bytes,0,len);
            if(len<bytes.length){
                break;
            }
        }
        out.close();
        in.close();
    }
}
```

但是这种方式依旧很麻烦，要创建一个内存流，先要将输入的内容读取到内存

所以又有一个BufferedReader

```java
public class Test {
    public static void main(String[] args) throws IOException {
        InputStream in=System.in;
        BufferedReader buff=new BufferedReader(new InputStreamReader(in));
        System.out.println("请输入内容");
        String str=buff.readLine();
        System.out.println(str);
    }
}
```

首先，BufferedReader的构造方法传入参数是

```java
public BufferedReader(Reader in)
```

但是终端对象System.in是InputStream 所以要用转换流转换成字符流在传入

**但是，BufferedReader进行输入最大的缺点就是输入内容都是字符串**



所以在java.util包下提供了一个Scanner类，构造方法 可以传入

```java
public Scanner(InputStream source)
```

并且，还可以自定义结束符号(分割符)

```java
public Scanner useDelimiter(Pattern pattern)
```

判断是否由指定类型

```java
public boolean hasNextXXX()
```

取得指定类型

```java
public 数据类型 nextXXX()
```

```java
public class Test {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);//创建输入对象
		scanner.useDelimeter("\\.");//指定点为分隔符
        while(scanner.hasNextInt()){//如果输入的字符是整形
            System.out.println(scanner.nextInt());//进行输出
        }
        
    }
}
```

而且Scanner还可以代替InputStream类的功能

**将文件的所有内容输入到程序，输出代码**

```java
public class Test {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(new File(
                "E:\\java I\\2019_4_10_IO_1\\src\\Test.java"
        ));
        scanner.useDelimiter("\n");
        while(scanner.hasNext()){
            System.out.println(scanner.next());
        }
    }
}

```

## 序列化和反序列化

序列化:将实例化对象转换为二进制的数据流进行传输或者保存到文本中

反序列化：将二进制数据流转换成实例化对象输出到程序中

要实现序列化和反序列化就要实现一个接口  java.io.Serializable;但是这个类本身没有任何内容  就是一个标记

序列化：ObjectOutputStream    

反序列化：ObjectInputStream

##transient关键字

如果一个类的属性被transient修饰，那么这个类在序列化时就不会被序列化，即不会被保存（密码肯定不希望被保存所以可以用transient修饰）；