# Java-IO-拷贝

具体过程：

1.创建源文件的终端对象，创建目标文件的终端对象

2.源文件创建输出流，目标地址创建输入流

3.读取文件，在单个字节输出。

```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file1=new File("C:\\Users\\admin\\Desktop\\匹配.PNG");
        File file2=new File("C:\\Users\\admin\\Desktop\\匹配(副本).PNG");
        InputStream inputStream=new FileInputStream(file1);
        OutputStream outputStream=new FileOutputStream(file2);
        int len=-1;
        while((len=inputStream.read())!=-1){
            outputStream.write(len);
        }
        outputStream.close();
        inputStream.close();

    }
}

```

这样单哥字节太慢，还可以 创建一个内存缓冲区

```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file1=new File("C:\\Users\\admin\\Desktop\\匹配.PNG");
        File file2=new File("C:\\Users\\admin\\Desktop\\匹配(副本).PNG");
        InputStream inputStream=new FileInputStream(file1);
        OutputStream outputStream=new FileOutputStream(file2);
        byte buff=new byte[1024];
        int len=-1;
        while((len=inputStream.read(buff))!=-1){
            outputStream.write(len);
        }
        outputStream.close();
        inputStream.close();

    }
}

```



