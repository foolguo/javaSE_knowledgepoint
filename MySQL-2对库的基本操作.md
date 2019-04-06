#MySQL对库的基本操作：

##1.库的创建

CREATE DATABASE db_name CHARACTER SET    字符集  (或者CHARSET=字符集)  COLLATE  字符集校验规则 

例子

```mysql
create database test  charset=utf8 collate utf8_general_bin;
```

**库的字符集**

**mysql里默认的字符集是UTF8，**

```mysql
show variables like 'character_set_database';
```

**查看数据库支持的字符集**

```sql
show charset
```



**库的校验规则**

**mysql默认的校验集是utf8_general_ci;**  不区分大小写  还有一种区分大小写的校验规则  **general_bin;**

```mysql
show variables like 'collation_database';
```

##**2.库的删除**

删库是一项危险的操作，慎用，并且只可以单个库删除

```mysql
drop database 库名
```

## 3数据库的操作

1.看当前存在的个库

```mysql
show databases;
```

2.看库的创建语句

```mysql
 show create database 数据库;
```

3.修改数据库的字符集校验规则

可以改  ，但是也不要改

```mysql
LTER DATABASE db_name
[alter_spacification [,alter_spacification]...]
alter_spacification:
[DEFAULT] CHARACTER SET charset_name
[DEFAULT] COLLATE collation_name
```

## 4数据库的备份和恢复

1.数据库的备份:在不进入mysql的情况下进行备份

```mysql
//完整
mysqldump -P3306 -uroot -p 密码  -B  数据库1，数据库2   要备份的路径
//如果是3306的端口号，可以省略
mysqldump -uroot -p -B test C:\Users\admin\Desktop\bit;
```

2.数据库表的备份

```mysql
mysqldump -uroot -p 密码  数据库名  表1，表2  要备份的路径
```



2.恢复，在进入mysql的情况下进行恢复

对于数据库的恢复

```mysql
source 路径
```

对于数据库表的恢复:先创建一个数据库，在在数据库里进行恢复



3.查看当前有哪些用户在访问数据库

```mysql
show processlist;
```



