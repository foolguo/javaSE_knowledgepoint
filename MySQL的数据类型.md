# MySQL的数据类型

![](E:\javaSE_knowledgepoint\mySQL的数据类型.PNG)



##·tinyint和bit(M)

1.1  tinyint有符号的范围是（-128~127）

```mysql
mysql> use db1;
Database changed
mysql> create table tt1(num tinyint);
Query OK, 0 rows affected (0.56 sec)

mysql> insert into tt1 values(1);
Query OK, 1 row affected (0.05 sec)

mysql> insert into tt1 values(127);
Query OK, 1 row affected (0.13 sec)

mysql> insert into tt1 values(-128);
Query OK, 1 row affected (0.13 sec)

mysql> insert into tt1 values(-129);
  ERROR 1264 (22003): Out of range value for column 'num' at row 1//
mysql> insert into tt1 values(128);
  ERROR 1264 (22003): Out of range value for column 'num' at row 1
mysql> select * from tt1;
+------+
| num  |
+------+
|    1 |
|  127 |
| -128 |
+------+
```

1.2.无符号的范围是（0~255） ；

如果要规定字段是无符号的数，则在定义时

```mysql
create table tt1(num tinyint unsigned );
```

2.1 bit有M位  bit字段在显示地时候默认按ASCII显示

```mysql
mysql> create table tt2(num bit(10));
Query OK, 0 rows affected (0.47 sec)

mysql> insert into tt2 values(65);
Query OK, 1 row affected (0.12 sec)

mysql> insert into tt2 values(97);
Query OK, 1 row affected (0.09 sec)

mysql> select *from tt2;
+------+
| num  |
+------+
|  A   |
|  a   |
+------+
```

bit字段的M 可以定义为1 这时，可以设置的数只是0和1；

## float(M,D)和decimal(M,D)

float(M,D) 和decimal(M,D)都表示小数一共有M位，其中小数位占(D);

其中不同的是 decimal的精度更高；而float一般精度为7；

```mysql
mysql> insert into tt9 values(12.12345678,12.12345678);
Query OK, 1 row affected (0.11 sec)

mysql> select *from tt9;
+-------------+-------------+
| num1        | num2        |
+-------------+-------------+
| 12.12345695 | 12.12345678 |
+-------------+-------------+
1 row in set (0.00 sec)
```



##char(L)和varchar(L)

char(L)  表示L表示可以存储的长度，单位为字符，最大长度可以是225字符；L一旦确定，表示不管插入数据的大小，都会讲字段占满  ，适合存放定长的字段

varchar (L):可变长字符串，L表示字符长度，最大可存放65535；

但是  utf8 编码时一个字符站占 3个字节，去掉三个字节的存放长度的信息  最长可定义为 （65535-3）/3=21844;

gbk 编码时 一个字符占2个字节  ，最大长度为（6553503）/2=32766

![](E:\javaSE_knowledgepoint\mysql char和varchar的比较.PNG)





##date,datatime timestamp

![](E:\javaSE_knowledgepoint\mysql 类型 日期.PNG)



```mysql
mysql> create table tt8(t1 date,t2 datetime,t3 timestamp);

Query OK, 0 rows affected (0.33 sec)

mysql> insert into tt8 values('2019-3-25','2019-3-25 15:34:21',now());

Query OK, 1 row affected (0.14 sec)

mysql> insert into tt8(t1,t2) values('2019-3-25','2019-3-25 15:34:21');

Query OK, 1 row affected (0.09 sec)

mysql> select *from tt8;

+------------+---------------------+---------------------+

| t1         | t2                  | t3                  |

+------------+---------------------+---------------------+

| 2019-03-25 | 2019-03-25 15:34:21 | 2019-03-25 15:34:53 |

| 2019-03-25 | 2019-03-25 15:34:21 | 2019-03-25 15:35:10 |

+------------+---------------------+---------------------+

```

其中timestamp时间戳可以不写，默认当前时间

##enum（枚举）和set（集合）

enum定义时有多个选项，但是只可以选择一个 比如说性别

set 定义时有对个选项 ，但是可以选择多个  比如 爱好

```mysql
mysql> create table tt11(name varchar(10),age varchar(3),hobby set('吃饭','睡觉','打豆豆'),gender enum('男','女'));
Query OK, 0 rows affected (0.36 sec)

mysql> insert into table tt11('HAHA','30','吃饭,睡觉','女');
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'table tt11('HAHA','30','吃饭,睡觉','女')' at line 1
mysql> insert into table tt11 values ('HAHA','30','吃饭,睡觉','女');
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'table tt11 values ('HAHA','30','吃饭,睡觉','女')' at line 1
mysql> insert into  tt11 values ('HAHA','30','吃饭,睡觉','女');
Query OK, 1 row affected (0.11 sec)

mysql> insert into  tt11 values ('GG','38','打豆豆','女');
Query OK, 1 row affected (0.09 sec)

mysql> insert into  tt11 values ('momo','38','打豆豆,睡觉','男');
Query OK, 1 row affected (0.11 sec)

mysql> select * from tt11;
+------+------+-------------+--------+
| name | age  | hobby       | gender |
+------+------+-------------+--------+
| HAHA | 30   | 吃饭,睡觉   | 女     |
| GG   | 38   | 打豆豆      | 女     |
| momo | 38   | 睡觉,打豆豆 | 男     |
+------+------+-------------+--------+
3 rows in set (0.00 sec)
```

现在如果要查询所有有共同爱好的人

用select find_in_set(sub,str_list');

```mysql
mysql> create table tt12(
    -> name varchar(2),
    -> hobby set('吃饭','睡觉','打豆豆'),
    -> gender enum('男','女')
    -> );
Query OK, 0 rows affected (0.48 sec)

mysql> insert into tt12 values('张三','吃饭','男');
Query OK, 1 row affected (0.13 sec)

mysql> insert into tt12 values('花花','吃饭,打豆豆','女');
Query OK, 1 row affected (0.09 sec)

mysql> insert into tt12 values('王麻子','吃饭,打豆豆','1');
ERROR 1406 (22001): Data too long for column 'name' at row 1

//这里错是因为 那么初始设置的长度太短  用alter table midify修改
mysql> alter table tt12 modify name varchar(4);
Query OK, 0 rows affected (0.19 sec)
Records: 0  Duplicates: 0  Warnings: 0

//111111
mysql> insert into tt12 values('王麻子','吃饭,打豆豆','1');
Query OK, 1 row affected (0.11 sec)

mysql> select *from tt12 where find_in_set('打豆豆',hobby);
+--------+-------------+--------+
| name   | hobby       | gender |
+--------+-------------+--------+
| 花花   | 吃饭,打豆豆 | 女     |
| 王麻子 | 吃饭,打豆豆 | 男     |
+--------+-------------+--------+
2 rows in set (0.00 sec)
```

在上述//11111

中枚举栏我插入的是  1而不是  ‘男’

```mysql
mysql> insert into tt12 values('王麻子','吃饭,打豆豆','1');
Query OK, 1 row affected (0.11 sec)
```

枚举在表创建的时候设置若干个值，但是在实际插入的时候单元格只有一个值，为了效率，enum实际上存储的是数字  从1-65536个，当我们添加枚举是也可以添加对应的数据编号

set：集合，“多选”类型；
set('选项值1','选项值2','选项值3', ...);
该设定只是提供了若干个选项的值，最终一个单元格中，设计可存储了其中任意多个值；而且出于效率考虑，这些
值实际存储的是“数字”，因为这些选项的每个选项值依次对应如下数字：1,2,4,8,16,32，.... 最多64个

> 虽然可以这样去设置，但是不建议这样做，因为阅读起来不方便