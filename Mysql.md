# Mysql

**数据库**：使用数据库有利于文件的管理和安全，数据的 查找和管理  ，有利于存放海量数据



SOL语句的分类：

DDL（data description language）:数据库定义语言（维护数据的结构） create    drop   alter

DML（data message language）：数据库管理语言   （对数据进行管理）  update  insert  delete

​		在DML中有一个分支叫做  DQL（data  question language）：数据库查询语言   select

DCL（data control language）：数据库控制语言（负责权限和事物管理）： grant，revoke ， commit



存储引擎（mysql是可插拔式索引）：

​				INNODB（mysql默认的存储引擎，  支持ACID事物，支持行级锁定）

​				MyISAM  (在mysql 5.1以前是默认的存储引擎)

​				Mernomy

