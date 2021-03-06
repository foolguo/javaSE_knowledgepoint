#JVM虚拟机-2

##垃圾回收机制

1.如何判断对象已死

1.引用计数法

概念：给对象增加一个引用计数器，当有一个地方引用它时，技术区+1；当引用失效时，计数器-1；任何时候计数器为0就是对象不能再被使用，即对象以死。

缺点：不能解决对象的循环引用问题。JVM没有采用

循环引用例子例子：

```

```

2.可达性分析算法：

Java采用可达性分析算法来判断对象是否存活（C#，Lisp）

核心思想：通过一系列“GC Roots”的对象作为起点，从这些节点开始向下搜索对象，搜索走过的路径，称为“引用链”，当一个对象到任意一个GC    Roots对象没有任何的引用链相连时（从GC Roots到对象不可达），证明对象已死。

Java中能作为GC Root是的对象包含以下四种：

1.虚拟机栈中引用的对象

2.静态变量引用的对象

3.常量引用的对象

4。本地方法栈中引用的对象。

## 在JDK1.2之后对引用概念进行了扩充

强引用（Strong Reference）：类似于Object obj = new Object();      在JVM中只要强引用还在，垃圾回收器永远不会回收次对象实例。

软引用（Soft Reference）：用来描述一些有用但不必须对象。对于仅被软引用指向的对象，在系统将要发生内存溢出之前，会将所有软引用对象进行垃圾回收。若内存够用，这些对象任然保留。在JDK1.2之后提供SoftReference来实现软引用。

弱引用（Weak Reference）：弱引用比软引用更差一些；仅被弱引用关联的对象最多只能生存到下一次GC开始之前。当垃圾回收期开始工作时，无论当前内存是否够用，都会回收掉仅被弱引用关联的对象。JDK1.2之后，使用Weak Reference来实现弱引用。

虚引用（Phantom Refrence）：也称作幽灵引用或幻影引用，是最弱的一种引用关系。一个对象是否由虚引用的存在，完全不会对其生存时间产生影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用的唯一目的就是在这个对象被GC之前，收到一个系统通知。JDK1.2之后提供了 Phantom Reference来描述虚引用。

## 对象自我拯救

protected void finalize() throws Throwable{}

在可达性分析算法中不可达的对象，并非“非死不可”，所有不可达的对象处于“缓刑”阶段。

要宣告一个对象彻底死亡，需要经历两次标记过程：

若对象在进行可达性分析之后发现到GC Roots 不可达，此对象会进行第一次标记并且进行一次筛选过程。筛选的条件是此对象是否由必要条件是此对象是否有必要执行finalize().当对象没有覆盖finalize() 方法 或finalize()方法已经被JVM调用过，JVM会将此对象彻底宣判死亡。

筛选成功(对象覆写了finalize方法并未被执行)，会将此对象放入F-Queue，如果对象在finalize()成功自救（此对象与任意一个GC Roots建立联系）对象会在第二次表几种被移除回收集合，成功存活；

若对象在finalize中任然与GC Roots不可达，宣告死亡。

## 回收方法区：

方法区回收两部分内容：两部分内容：废弃常量和无用的类

没有任何引用指向它并且内存不够用才有可能回收它；

判断一个类是无用类：

1.该类的所有对象都已经被回收（Java堆中不存在该类的人很实例）

2.加载类的类加载器已经被回收

3.该类的Class对象没有在任何其他地方被引用，也无法通过反射访问该类所有内容。