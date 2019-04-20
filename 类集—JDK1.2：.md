类集—JDK1.2：

1.动态数组—解决数组长度固定问题



1.总体结构

有两大类   Colloection—保存单个对象的最大父接口

​				—List

​					—ArrayList

​					—Vector

​					—LinkedList

​				—Set

​					—TreeSet

​					—HashSet

​				—Queue

​			 Map—保存一个对象的最大父接口

​				—HashMap

​				—TreeMap

​				—Hashtable

​				—ConrrentHashMap

2.常用方法：

3.集合变量

Iterator（共有推荐使用）

ListIterator（List）

Enumaration（Vector）

for-each（都可以用）

4.删除操作

下面这两种是集合遍历产生的：



fail-fast机制：

​	ConcurrentModificationException：

产生原因：在遍历集合过程中使用集合产生的异常；

参数   modCount：fail—fast 机制  每修改一次modCount++；

取得迭代器时：expectedModCount=modCount；

当在集合变量过程中删除元素，modCount-1    这时  expectedModCount!=modCount；抛出ConcurrentModificationException.

发生在   HashSet  ，ArrayList、 HashMap

作业：尽量保证并发情况下集合遍历一定返回正确的数据。避免脏读。

fail—safe机制：

集合CopyOnwriterArrayList 、ConcurrentHashMap等包下的线程集合安全。



4.常用子类详解：

ArrayList：

原码：加载策略  懒加载  （add）扩容策略：一次扩容1.5倍

Vector：上来就初始化为10：add 扩容策略，在保存第11个元素 扩容成两倍

LinkedList：双链表

对比：

版本、线程安全性、底层结构；   原码级别（初始化策略、扩容策略）



Map

**1**.整体结构：

保存建值对的最大接口：

在HashMap下有一个子类：LinkedHashMap是有序Map，有序指的是元素的添加顺序

TreeMap有序Map，序指的是Comparator或Compareable导致的顺序

**2，常用方法**

put    get

**3.Map集合的遍历?**

Map—>Set？

Set接口与Map接口关系：

Set接口只是穿了小马甲的Map接口而已，Set接口本质都是通过Map接口来存储元素的，都是讲元素存储到Map的key值而已，value都是用共同的一个空Object对象。

如何输出？

Map接口内部有一个Entry接口

Map保存 一个个的 Map.Entry对象

要用迭代输出：

A.首先 用entrySet()   Map.entry对象转换为Set集合

B.用迭代输出：

**4.常用子类的解析：**

HashMap源码解析：

1.成员变量:树化、数据结构

2.构造函数

3.put与get

4.哈希算法，扩容，性能