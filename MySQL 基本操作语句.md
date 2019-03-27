# MySQL基础

1.MySQL的存储引擎(可插拔的存储引擎)

InnoDB(默认存储引擎)  ，Memory，MyISAM

2.SQL分类

DDL数据定义语言，用来维护存储数据结构

creat  drop  alter

DML数据库操纵语言，用来对数据进行操作

insert  delete  update

DCL数据控制语言，主要负责权限管理和事物

grant  revoke  commit



# MySQL 基本操作语句

##创建数据库及其数据库表

1.连接服务器

```mysql
mysql -h 127.0.0.1 -P3306 -uroot -p
```

-h 127.0.0.1如果连接的是本地的话可以省略

MySQL默认端口号是3306；如果你的端口号就是3306的话直接省略可以写成

```mysql
mysql -uroot -p
```

-p 直接敲回车 输入密码时可以隐藏   在-p后直接输密码不隐藏

2.创建数据库：

```mysql
create database if not exists db2;
```

其中if not exists是可选字段

上面创建数据库的方式没有字符集和校验规则  **默认的字符集 utf8  校验规则是  utf8_general_ci**  

```mysql
create database if not exists db3 charset=utf8 collate utf8_general_ci;
create database if not exists students character set utf8 collate utf8_bin;//区分大小写
```

待字符集和校验规则

修改数据库的字符集

```mysql
alter database  db3 charset=gbk;
```



3.使用数据库

```mysql
use db2
```

4.创建数据库表

```mysql
mysql> create table student(
     id int,
     name varchar(10),
     num varchar(10)
     );
```

5.查看表

```mysql
show tables;
```



6.向表中插入元素

```mysql
insert into student(id,name,num)values (1,'张三','001');
insert into student(id,name,num)values(2,'李四','002');
insert into student(id,name,num)values(3,'王麻子','003');
```

7.查询表中数据

```sql
 select * from student;
```

对表中数据进行排序；

```mysql
select * from student order by id;
```

取出指定表中数据

```mysql
select *from student where id='1';
```



查看数据库的创建方式:

```mysql
show create database 数据库名；
```

 查看建表语句

```mysql
show create table 表名
```



## 查询系统默认的字符集和校验规则

1.查看系统默认的字符集

```mysql
show variables like 'character_set_database';
```

2.查看系统默认的校验规则

```mysql
show variables like 'collaet_database';
```

3.查看字符串支持的校验规则

```mysql
show collation;
```

## 数据库删除

```mysql
drop database [if exists] 数据库名;
```



## 校验规则对数据库的影响

1.区分大小写的校验规则；

```mysql
create database test1 collate utf8_general_ci;
```

2.不区分大小写的校验；

```mysql
create database test2 collate utf8_bin;
```



## 数据备份和还原

**备份：备份时不连接数据库**

1.备份数据库；

```mysql
mysqldump -uroot -p -B db2>d:/db2.sql
```

如果>.\表示当前路径

还原数据库；

```mysql
source d:/db2.sql
```



2.备份多个数据库；

```mysql
mysqldump -uroot -p -B db2 db3>d:/data.sql
```



3.备份数据库中的某个表

```mysql
mysqldump -uroot -p db2 student>D:/student.sql
```

**还原：  还原连接数据库,要先创建一个数据库才可以还原表**

```mysql
source d:/student.sql;
```

## 查看数据库连接

```mysql
show processlist
```



