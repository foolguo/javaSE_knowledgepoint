# MySQL对表的修改

1.创建表：

```mysql
//创建一个字符集是utf8 校验规则是utf8_bin区分大小写
create database school character set utf8 collate utf8_bin;

//进入该数据库
use school； 

//创建数据库表；
create tables student（
ID int，
name varchar(10);
birthday varchar(10);
age varchar(3);
)character set utf8 collate utf8_general_bin;

//查看数据库里有多少个表
select database();
show tables;

//查看数据库表的结构
desc student；

//插入数据
insert into student (ID,name birthday,age) values(1,'xxxx','1998-4-17',20);//一次插入一行
insert into student (ID,name birthday,age) values(1,'xxxx','1996-4-17',20),(2,'**','1990-1-2',28);

//查看数据库表内容
select *from student；

//修改数据库表名字
//to可以省略
alter table student rename to school;
//修改字符串表中的一个字段名字
alter table student change name xingming varchar(10);

//增加数据库表的字段
alter table student add sex varchar(2) after name;

//修改数据库表的字段长度或者类型
alter table student modify name varchar(4);
alter table student modify ID varchar(10);

//删除字符串表中的一个子段
alter table student drop sex；


//删除数据表
drop table student;

```

