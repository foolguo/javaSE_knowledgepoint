# 三范式：—BCNF（数据表设计）

1NF：所有关系数据表都必须满足的范式，表中所有属性必须为院子的，不可再分的，表中不能有子表

2NF：表中所有非主属性不存在对主码的部分函数依赖

列  定义一个数据表

SGrade（Sno,Cno,Grade,SName,Sdept）

主码：（Sno,Cno）

函数依赖 ：（Sno，Cno）—>Grade（函数依赖）

​			Sno—>Sname  (部分函数依赖)

3NF：表中所有非主属性不存在对主码的传递函数依赖；

比如，在SCrade中加入一个Sloc（宿舍号）  并且不同系不能住在一起

所以导致了     Sno—>Sdept      SDept—>Sloc





解决第二范式和第三范式的问题：通过分解表来解决

2NF:将Sname 从SGrade删除，并且新建一张表来存放。