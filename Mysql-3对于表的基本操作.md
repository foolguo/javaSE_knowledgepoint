# Mysql-3对于表的基本操作

## 1.创建表

在创建表时可以规定当前表的字符集，校验规则，存储引擎

```mysql
CREATE TABLE table_name (
field1 datatype,
field2 datatype,
field3 datatype
) character set 字符集 collate 校验规则 engine 存储引擎;
```

filed标志字段名，datatype表示数据类型

```mysql
create table users (
id int,
name varchar(20) comment '用户名',
password char(32) comment '密码是32位的md5值',
birthday date comment '生日'
) character set utf8 engine MyISAM;
```

上面的coment是mysql的注释

在创建表的时候还可以规定主键，默认值，字段不能为空，等

## 2.查看表结构

```mysql
desc 表名;
```

##3.修改表

1.添加表的字段（列）

```mysql
ALTER TABLE tablename ADD (column datatype [DEFAULT expr][,column datatype]...);
```

2.删除表的字段

```mysql
ALTER TABLE tablename DROP (column);
```

3.修改字段类型

```mysql
ALTER TABLE tablename MODIfy (column datatype [DEFAULT expr][,column datatype]...);
```

4.修改表名

```mysql
ALTER TABLE tablename rename to newname
```

## 4.在表中插入数据

单行插入

```mysql
insert into 表名(字段名) values (数据)；
```

多行插入

```mysql
insert into 表名(字段名) values (数据1),(数据2)；
```

字段名省略默认每个字段都要插入， 也可以规定插入字段名个数

## 5.删除表

```mysql
drop table 表名
```



##MySql的数据类型

在定义数据库表的时候，需要数据类型，mysql的数据类型分为下面几类



**数值类型**

tinyint:占两个字节，分为带符号的和不带符号的，如果定义表示不去要负数 则  tinyint unsigned；

bit(M)：位字段类型，M表示有几位，M可以从1~64，如果M被忽略则默认为1  （如果是1的话值是0,1）；

**小数类型**

float（m,d） m表示小数总长，d表示小数点后的长度  

列：如果m=4，d=2,那么对于  float 值的范围为（-99.99,99.99）  float unsigned(0-99.99);



decimal(m,d)   decimal（m,d） m表示小数总长，d表示小数点后的长度  

列：如果m=4，d=2,那么对于  decimalt 值的范围为（-99.99,99.99）  decimal unsigned(0-99.99);

  这样一看好像两个是一样的 但是decimal的精度高，float的精度大概是小数点后7位

**字符串类型**

**char（L）**：表示定长的字符串，L表示字符的数目，最多可以有255个字符；

**varchar（L**）：表示变长字符串，L表示字符最大长度65535，但是其中有1-3个字节被用来存放长度大小，剩下的是有65532，由于字符集的不同，varchar可存放的字符长度也不同，如果是utf8则一个字符占3个字节，最大长度就是，65562/3，如果是jbk编码，最大长度是 65532/2；

特点：

char（L）：适合存放定长的字符串 比如电话号，ID等，(因为不管有没有达到当前char所达到的最大长度，都会占满)（L*3）

varchar(L)：适合存放不定长度的数据，比如名字地址等，通常长度  1+字符长度*3（在utf8编码下）



**日期类型**：

date：yy-mm-dd  占用3个字节

datatime；yy-mm-dd  HH:ii:ss占用8个字节

timestamp:显示当前系统  日期时间 yy-mm-dd  HH:ii:ss占用4个字节  （在表中插入数据时，可以不写，自动生成）

**enum和set**

1.enum:enum：枚举，“单选”类型；
	enum('选项1','选项2','选项3',...);

enum设置类若干个选项，但是在表存储的时候不是按照真实的值来存储的，是按照1,2,3 ...这样的递增顺序存储的

2.set：集合，“多选”类型；
	set('选项值1','选项值2','选项值3', ...);

set设置类若干个选项，但是在表存储的时候不是按照真实的值来存储的，按照,2,4,8,16,32，.... 最多64个

创建一张表：

```mysql
create table votes(
 username varchar(30),
 hobby set('登山','游泳','篮球','武术'),
 gender enum('男','女'));
```

插入数据

```mysql
insert into votes values('雷锋', '登山,武术', '男');

insert into votes values('Juse','登山,武术',2);

select * from votes where gender=2;

```



在查询时如果想查询爱好是登山的童鞋像下面的方式是查不出来的

```mysql
select * from votes where hobby='登山'
```

这样只是会查出爱好只有一个且为登山的同学

应该用 find_set_in(a,abcd)函数

```mysql
select * from votes where find_in_set('登山',hobby);
```

