# Java-IO

##File类

File类是一个普通类，可以直接实例化对象

其构造方法必须传入  绝对路径，URI，父路径子路径

它有几个常用方法：还有一个File.separator表示分割长度

```java
public boolean exists()
```



```java
public class Test {
    public static void main(String[] args) throws IOException {
        /*实例化对象 取得File的实例化对象   ，但是windows和Linux的路径分割符不同不同
        所以有一个File.separator常量表示分割符
        * */
        /*File file=new File("C:\\Users\\admin\\Desktop\\test.txt");*/
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"com"+File.separator+"do"+File.separator+"Test.java");
        //看看当前父目录对象是否存在，不存在创建父目录
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();//注意不能直接file.mkdirs这样会将你要创建的文件也弄成一个目录
        }
        if(!file.exists()){//查看文件是否存在
            file.createNewFile();//不存在创建一个新文件
        }else{//存在的话删除
            file.delete();
        }
       if(file.exists()&&file.isFile()) {
            System.out.println(file.length());//查看文件长度
            System.out.println(file.lastModified());//查看文件最后一次修改信息
        }
    }
}
```



输出文件目录信息：

```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop");
        printAllFile(file);
    }
    public static void printAllFile(File file){
        if(file.isFile()){
            System.out.println(file);
        }else{
            if(file.exists()&&file.isDirectory()) {
                File[] files = file.listFiles();
                    for (File i : files) {
                        printAllFile(i);
                }
            }
        }
    }
}
```



##字节输出流

首先OutputStream是一个抽象类，要获取字节输出流，必须要用它的子类FileOutStream来取得输出流

```java
public class Test {
    public static void main(String[] args) throws IOException {
        //获取终端对象
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"Test.txt");
        //创建输出流
        OutputStream outputStream=new FileOutputStream(file);
        //调用write方法输出到终端,并且传入的对象必须是字节数组，
        // 字节流,如果没有文件也不要createNewFile会自动创建
        outputStream.write("哈哈".getBytes());
		// 关闭流
        outputStream.close();
        //这种方式表示追加
        OutputStream outputStream1=new FileOutputStream(file,true);
        outputStream1.write("呵呵".getBytes());
        outputStream1.close();
    }
```

write方法

```java
public void write(byte b[]) throws IOException//传入一个字节数组
public void write(byte b[], int off, int len) throws IOException//从off开始，输出len个字符
public void write(int b) throws IOException //每次输出b个字节
```

##字节输入流

```java
public class Test {
    public static void main(String[] args) throws IOException {

        //获取终端对象
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"Test.txt");
        //创建输出流
        InputStream inputStream=new FileInputStream(file);
        //创建缓冲区
        byte[] bytes=new byte[1024];
        //调用read方法
        int len=inputStream.read(bytes);
        System.out.println(new String(bytes,0,len));
        //关闭输入流
        inputStream.close();
    }

```

read方法

```java
//一次读取一个字节数组的（缓冲区）长度，当缓冲区的长度>终端文件的长度，返回终端文件的长度
//否则返回缓冲区的长度，如果没有字节，返回-1
public int read(byte b[])
public int read()//一次读取一个字节，返回-1时结束
```

要注意：字节流使用后一定要关闭，要不然会造成资源浪费

所以java提供了一种自动关闭的机制 下面的伪代码要将创建输出流的过程成如下形式

```java
 try (OutputStream outputStream=new FileOutputStream(file,true);){
            outputStream.write("1111".getBytes());
        }catch (Exception e){}
```

在这里提的一点，字节输出、输入流都是原生操作，而下面介绍的字符输出和输入流是原生流处理后的操作

## 字符输出流

Writer是抽象类，要有FileWriter来实例化

```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"Test.txt");
    
    Writer writer=new FileWriter(file);
    writer.write("ahh");
    writer.close();
    }
}
```

里面的writer方法用法和字节输出流一致，只不顾传参是字符串或者字符

## 字符输入流

Reader是抽象了，用其子类FileReader实例化



```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"Test.txt");
                Reader reader=new FileReader(file);
		char[] data=new char[1024];
		int len=reader.read(data);
		reader.close();
    }
}
```

在如果要指定缓冲区，类型必须是char类型的

总结：字符流对中文友好

## 转换流

前面说了字符流是处理后的操作，那么这个处理过程就交给转换流完成

OutputStreamWriter，InputStreamReader，

这两种流分别是Writer的直接子类，FileWriter的直接父类

​			   Reader的直接子类，FileReader的直接父类

在使用时，实例化是传入字节流就行

```java
public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("C:"+File.separator+"Users"+File.separator+"admin"+File.separator+
                "Desktop"+File.separator+"Test.txt");
               OutputStream outputstream=new FileOutputStream(file);
        	   Writer out=new OutputStreamWriter(outputstream);
        		out.write("haha");
        	out.close();
        	outputstream.close();
    }
}
```



