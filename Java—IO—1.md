# Java—I/O—2

>1.File
>
>2.输入输出流和转换流
>
>3.内存流
>
>4.编码方式

## 1.File

File是一个普通类，在java.io包下，表示的是可进行流操作的文件终端对象

File的构造方法有几个重载：

1.可以传入URI；2.可以传入String（路径名）

有几个常用方法：

| 方法                                              | 解释                                                 |
| ------------------------------------------------- | ---------------------------------------------------- |
| public boolean exists（）                         | 文件是否存在                                         |
| public boolean createNewFile() throws IOException | 创建一个新文件                                       |
| public boolean delete()                           | 文件删除                                             |
| public long lastModified()                        | 文件最后修改日期                                     |
| public long length()                              | 文件长度                                             |
| public boolean mkdirs()                           | 创建多级父目录                                       |
| public boolean isFile()                           | 指定指定终端是否适宜个文件                           |
| public boolean isDirectory()                      | 指定终端是否是一个目录                               |
| public File getParentFile()                       | 或许文件父路径                                       |
| public File[] listFiles()                         | 文件是输出路径下所有文件，但是如果文件是目录则不输出 |

```java
//创建一个终端对象，如果存在则删除，如果不存在那么创建一个新对象
public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("D:\\Test.txt");
        if(file.exists()&&file.isFile()){
            file.delete();
        }else{
            file.createNewFile();
        }
    }
}
```



输出桌面上所有文件：为了避免文件太大而阻塞性开一个线程去执行

```java
public class Test {
    public static void main(String[] args) throws IOException {
      File file=new File("C:\\Users\\admin\\Desktop");
        System.out.println(Thread.currentThread().getName()+"开始");
        new Thread(new Runnable(){
            @Override
            public void run() {
                AllListFiles(file);
            }
        }).start();
        System.out.println(Thread.currentThread().getName()+"结束");

    }
    public static void AllListFiles(File file){
        //如果是一个文件则输出
        if(file.isFile()){
            System.out.println(file);
        }else{
            //如果不是文件并且存在且是一个目录
            if(file.exists()&&file.isDirectory()){
                //调用listFiles()输出全部文件
                File[] file1=file.listFiles();
                //递归
                for (File i:file1){
                    AllListFiles(i);
                }
            }
        }
    }
}
```



如果创建一个文件，并且文件目录并不存在，那么可以使用mkdirs()创建多级父目录

```java
public class Test{
    public static void main(String[] args) throws IOException {
        File file=new File("D:\\hello\\world\\Test.txt");
        if (file.exists()&&file.isFile()){
            System.out.println(file);
        }else{
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}
```



## 2.输入输出流和转换流

输入输出流分为两类（顶层接口）：

1.字节输入输出流：InputStream，OutputStream

2.字符输入输出流：Reader，Writer

其中，有两个常用的方法

write()：

| 方法                                                         | 解释                                   |
| ------------------------------------------------------------ | -------------------------------------- |
| public void write(int b) throws IOException                  | 将文件输出到目标终端，每次输出一个字节 |
| public void write(byte b[]) throws IOException               | 将文件输出到目标终端，每次输出一个数组 |
| public void write(byte b[], int off, int len) throws IOException | 输出指定的字节输入到目标终端终         |

read() 

| 方法                                                         | 解释                                       |
| ------------------------------------------------------------ | ------------------------------------------ |
| public int read() throws IOException                         | 将目标终端的内容输入到程序中，一次单个字节 |
| public int read(byte b[])                                    | 一次输入b数组                              |
| public int read(byte b[], int off, int len) throws IOException | 一次数出指定长度                           |

read()方法是有返回值的：

返回值代表数据的读取个数，

1.如果要读取的数据长度>接收数组长度，说明文件还没有读完，返回数组长度 反之，则返回，读取数组的长度，如果返回值为-1，代表已经读完public int read(byte b[])：

2.一次读取len个字节，如果取满了，返回len，如果没有则返回读取的数据个数，如果读取到最后，没有数据返回-1   public int read(byte b[], int off, int len) throws IOException；

3.一次读取单个字节，读完返回-1，public int read() throws IOExcepti**on**

**文件流要注意：在使用流后一定要进行关闭流操作，会一直占用内存所以一定要关闭流**

```java
public class Test{
    public static void main(String[] args) throws IOException {
       File file=new File("D:\\Text.txt");
        OutputStream outputStream=new FileOutputStream(file);
        outputStream.write("Haha".getBytes());
        outputStream.close();
    }
```

Java还提供了一个自动关闭流的方法 和 try—catch一起使用

```java
public class Test{
    public static void main(String[] args) throws IOException {

       File file=new File("D:\\Test.txt");
        //创建流的方式
        try ( OutputStream outputStream=new FileOutputStream(file)){
            outputStream.write("Haha".getBytes());
        }catch (Exception e){
            
        }
    }
```

**文件的输入：**

```java
public class Test{
    public static void main(String[] args) throws IOException {
       
       File file=new File("D:\\Test.txt");
       InputStream inputStream=new FileInputStream(file);
       byte[] data=new byte[1024];
       int len=0;
       while((len=inputStream.read(data))!=-1){
           System.out.println(new String(data,0,len));
       }
       inputStream.close();
    }
}
```



但是OutputStream outputStream=new FileOutputStream(file)，会覆盖文件中已有内容，如果想追加

OutputStream outputStream=new FileOutputStream(file,true)  将可追加 设置为true即可

········上述将的是字节流，主要是用于传输一些，视频，音乐，图片比较方便 ，但是如果要用于中文就不是特别友好了·······

**Write和Reader：**

和上述用法基本一致，构造方法为：

```java
Writer writer=new FileWriter(file);
Reader reader=new FileReader(file);
```

但是，他是对字符的操作，

write() 方法可以穿出字符串，字符数组，单个字符

reader()方法只能用字符数组



**转换流，就是将字节流转换成字符流的操作，是Write和Reader的子类**

**构造方法**

```java
Writer writer1=new OutputStreamWriter(字节流);
Reader reader1=new InputStreamReader(new FileInputStream(file));
```

## 3.内存流

1.内存输入流（将指定内容输入内存中）：ByteArrayOutputStream ，CharArrayWriter

2.内存输出流（将内容从内存中输出)：ByteArrayInputStream，CharArrayReader

```java
public class Test{
    public static void main(String[] args) throws IOException {
        InputStream inputStream=new ByteArrayInputStream("String".getBytes());
        OutputStream outputStream=new ByteArrayOutputStream();
        int len=0;
        while(true){
             len=inputStream.read();
             if(len==-1){
                 break;
             }
             SoutputStream.write(Character.toUpperCase(len));
        }
        System.out.println(outputStream);
        inputStream.close();
        outputStream.close();
    }
}
```

## 4.编码方式

1.UNICODE:适合所有语言，GB2312只包含简体中文。

2.JBK：中文，支持简体中文和繁体中文。

3.ISO8859-1：国际编码（预览器默认编码）

4.UTF-8：unicode和ISO8859-1的组合



**基本上95%产出乱码的原因是因为编解码不一致，还有一种是数据丢失导致的编码**

