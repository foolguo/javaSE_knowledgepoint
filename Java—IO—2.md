# Java—I/O—2

> 1.打印流
>
> 2.系统输入输出
>
> 3.序列化
>
> 4.trainsent

##1.打印流

1.打印流解决的问题：不管是字节流还是字符流只可以输出字节和字符，如果需要输出别的类型就要进行类型转换

在进行类型转换时，比如一个   int—>byte    先要将int转换成String  再转换成byte，如果要输出多个时，就要不停的进行转换，所以打印流就是简化了这个转换的过程将其封装成类。

```java
class PrintUtil{
    OutputStream outputStream;
    //传入一个
    public PrintUtil(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public void print(String str) throws IOException {
        outputStream.write(str.getBytes());
    }
    public void println(String str) throws IOException {
        print(str+"\r\n");
    }
    public void print(int num) throws IOException {
        print(String.valueOf(num));
    }
    public void println(int num) throws IOException {
        println(String.valueOf(num));
    }
}
public class Test{
    public static void main(String[] args) throws IOException {
        File file=new File("C:\\Users\\admin\\Desktop\\Test.txt");
        OutputStream outputStream=new FileOutputStream(file,true);
        PrintUtil printUtil=new PrintUtil(outputStream);
        printUtil.println("hehe");
        printUtil.println(1909);
        printUtil.print("你好");
        outputStream.close();
    }
}
```



**打印流流实际上就是包装模式，核心还是将内容输出到终端，这种设计模式称之为包装设计模式。**

Java提供了几种设计模式：

字节打印流PrintStream、字符打印流PrintWriter，只要传入一个字节流输出对象，就可以实现各种数据类型的输出。