# MySQL表的增删改查

##6.1创建生表

```mysql
create table student (id int unsigned primary key Auto_increment,sn int unsigned not null unique,name varchar(6));
```

1.全列插入

单行插入：

```mysql
insert into student values('100','10000','唐三藏');
```

多行插入：

```mysql
insert into student(id,sn,name) values('102','20001','曹孟德'),('103','20002','孙中某');
```



2.插入否则更新：

**由于插入主键或者唯一值已经存在而导致的错误**

语法 

```mysql
insert into ... values.. 插入的数据 ..on duplicate key update 要改变的值; 
```

例子

```mysql
insert into student values ('100','10010','糖大师')
  on duplicate key update sn=10010,name='糖大师';
```

3.替换：

主要功能和   on duplicate key update...一样

不同的是，如果插入的数据主键或者唯一键已经存在，那么删除再插入；

如果不存在，则直接插入

```mysql
replace into 表名[要插入的字段] values(插入值列表);//可以不写要插入的字段，默认全部赋值
```

例子：

```mysql
replace into student values(103,20020,'孙尚香');
```

结果

```mysql
+-----+-------+--------+
| id  | sn    | name   |
+-----+-------+--------+
| 100 | 10010 | 糖大师 |
| 101 | 10001 | 孙悟空 |
| 102 | 20001 | 曹孟德 |
| 103 | 20020 | 孙尚香 |
+-----+-------+--------+
```



## retrieve（筛选）

语法：

```mysql
SELECT
[DISTINCT] {* | {column [, column] ...}
[FROM table_name]
[WHERE ...]
[ORDER BY column [ASC | DESC], ...]
LIMIT ...

```

先创建一个数据库表

```mysql
CREATE TABLE exam_result (
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL COMMENT '同学姓名',
yuwen float DEFAULT 0.0 COMMENT '语文成绩',
shuxue float DEFAULT 0.0 COMMENT '数学成绩',
yingyu float DEFAULT 0.0 COMMENT '英语成绩'
);
```

向数据库表中插入数据

```mysql
INSERT INTO exam_result (name, yuwen, shuxue, yingyu) VALUES
('唐三藏', 67, 98, 56),
('孙悟空', 87, 78, 77),
('猪悟能', 88, 98, 90),
('曹孟德', 82, 84, 67),
('刘玄德', 55, 85, 45),
('孙权', 70, 73, 78),
('宋公明', 75, 65, 30);
```



**1.全列查询**

```mysql
select *from 表名
```

例如

```mysql
select *from exam_result;
+----+--------+-------+--------+--------+
| id | name   | yuwen | shuxue | yingyu |
+----+--------+-------+--------+--------+
|  1 | 唐三藏 |    67 |     98 |     56 |
|  2 | 孙悟空 |    87 |     78 |     77 |
|  3 | 猪悟能 |    88 |     98 |     90 |
|  4 | 曹孟德 |    82 |     84 |     67 |
|  5 | 刘玄德 |    55 |     85 |     45 |
|  6 | 孙权   |    70 |     73 |     78 |
|  7 | 宋公明 |    75 |     65 |     30 |
+----+--------+-------+--------+--------+
```

**2.指定列查询  查询所有学生的语文成绩**

```mysql
select 列名1，列名2...列名n from 表；
```



```mysql
select name,yuwen from exam_result;
+--------+-------+
| name   | yuwen |
+--------+-------+
| 唐三藏 |    67 |
| 孙悟空 |    87 |
| 猪悟能 |    88 |
| 曹孟德 |    82 |
| 刘玄德 |    55 |
| 孙权   |    70 |
| 宋公明 |    75 |
+--------+-------+
```

**3.查询字段为表达式   给所有学生的数学成绩+10**

```mysql
select name,shuxue+10 from exam_result;
+--------+-----------+
| name   | shuxue+10 |
+--------+-----------+
| 唐三藏 |       108 |
| 孙悟空 |        88 |
| 猪悟能 |       108 |
| 曹孟德 |        94 |
| 刘玄德 |        95 |
| 孙权   |        83 |
| 宋公明 |        75 |
+--------+-----------+
```

**4.给查询的字段重命名  关键字as**

```mysql
select name,yuwen+shuxue+yingyu as total from exam_result;
+--------+-------+
| name   | total |
+--------+-------+
| 唐三藏 |   221 |
| 孙悟空 |   242 |
| 猪悟能 |   276 |
| 曹孟德 |   233 |
| 刘玄德 |   185 |
| 孙权   |   221 |
| 宋公明 |   170 |
+--------+-------+
```

**5.给查询的字段去重  用distinct关键字**

```mysql
select distinct  列名 from  表
```

列子：

```mysql
select distinct shuxue from exam_result;
+--------+
| shuxue |
+--------+
|     98 |
|     78 |
|     84 |
|     85 |
|     73 |
|     65 |
+--------+
```

## **where  条件**

**1.mysql运算符**

![](E:\javaSE_knowledgepoint\mysql 运算符.PNG)

需要注意的是：=   和<=>

  前者不安全  null=null 结果还是null  

```mysql
-- NULL 和 NULL 的比较，= 和 <=> 的区别
SELECT NULL = NULL, NULL = 1, NULL = 0;
+-------------+----------+----------+
| NULL = NULL | NULL = 1 | NULL = 0 |
+-------------+----------+----------+
| NULL | NULL | NULL |
+-------------+----------+----------+
1 row in set (0.00 sec)
SELECT NULL <=> NULL, NULL <=> 1, NULL <=> 0;
+---------------+------------+------------+
| NULL <=> NULL | NULL <=> 1 | NULL <=> 0 |
+---------------+------------+------------+
| 1 | 0 | 0 |
+---------------+------------+------------+
1 row in set (0.00 sec)
```



后者null<=>null 安全 结果为true   否则结果为 false

还需要注意的是 ：mysql的不等于有两个   一个是c和java里都用到的 !=   另一个是 <>;

2. **语文成绩在 [80, 90] 分的同学及语文成绩**

   ​	a.用between  ...and...查询

   ```mysql
   select name,yuwen from exam_result where yuwen between 80 and 90;
   +--------+-------+
   | name   | yuwen |
   +--------+-------+
   | 孙悟空 |    87 |
   | 猪悟能 |    88 |
   | 曹孟德 |    82 |
   +--------+-------+
   ```

   ​	**b.用 AND查询**

   ```mysql
    select name,yuwen from exam_result where yuwen>=80 AND yuwen<=90;
   +--------+-------+
   | name   | yuwen |
   +--------+-------+
   | 孙悟空 |    87 |
   | 猪悟能 |    88 |
   | 曹孟德 |    82 |
   +--------+-------+
   ```

   

3. **数学成绩是 58 或者 59 或者 98 或者 99 分的同学及数学成绩**

   ```mysql
    select name,shuxue from exam_result where shuxue=58 or shuxue=59 or shuxue=98 or shuxue=99;
   +--------+--------+
   | name   | shuxue |
   +--------+--------+
   | 唐三藏 |     98 |
   | 猪悟能 |     98 |
   +--------+--------+
   ```

   

4. **姓孙的同学 及 孙某同学**

   ```mysql
   select name from exam_result where name like '孙%'or'孙_';
   +--------+
   | name   |
   +--------+
   | 孙悟空 |
   | 孙权   |
   +--------+
   ```

5. **语文成绩好于英语成绩的同学**

   ```mysql
   select name,yuwen,yingyu from exam_result where yuwen>yingyu;
   +--------+-------+--------+
   | name   | yuwen | yingyu |
   +--------+-------+--------+
   | 唐三藏 |    67 |     56 |
   | 孙悟空 |    87 |     77 |
   | 曹孟德 |    82 |     67 |
   | 刘玄德 |    55 |     45 |
   | 宋公明 |    75 |     30 |
   +--------+-------+--------+
   ```

6. **总分在 200 分以下的同学**

   ```mysql
   select name,yuwen+shuxue+yingyu as total from exam_result where yuwen+shuxue+yingyu<200;
   +--------+-------+
   | name   | total |
   +--------+-------+
   | 刘玄德 |   185 |
   | 宋公明 |   170 |
   +--------+-------+
   ```

   

7. **语文成绩 > 80 并且不姓孙的**

   ```mysql
    select name,yuwen from exam_result where yuwen>80 and name not like '孙%';
   +--------+-------+
   | name   | yuwen |
   +--------+-------+
   | 猪悟能 |    88 |
   | 曹孟德 |    82 |
   +--------+-------+
   ```

   

8. **孙某同学，否则要求总成绩 > 200 并且 语文成绩 < 数学成绩 并且 英语成绩 > 80**

   ```mysql
    select name,yuwen,shuxue,yingyu from exam_result where name like '孙%'or(yuwen<yingyu and yingyu>80);
   +--------+-------+--------+--------+
   | name   | yuwen | shuxue | yingyu |
   +--------+-------+--------+--------+
   | 孙悟空 |    87 |     78 |     77 |
   | 猪悟能 |    88 |     98 |     90 |
   | 孙权   |    70 |     73 |     78 |
   +--------+-------+--------+--------+
   ```

9. **NULL 的查询**

   ```mysql
    select name,yuwen from exam_result where yuwen is not null;
   +--------+-------+
   | name   | yuwen |
   +--------+-------+
   | 唐三藏 |    67 |
   | 孙悟空 |    87 |
   | 猪悟能 |    88 |
   | 曹孟德 |    82 |
   | 刘玄德 |    55 |
   | 孙权   |    70 |
   | 宋公明 |    75 |
   +--------+-------+
   ```

   ## 排序结果  ---order by

   ```mysql
   -- ASC 为升序（从小到大）
   -- DESC 为降序（从大到小）
   -- 默认为 ASC
   SELECT ... FROM table_name [WHERE ...]
   ORDER BY column [ASC|DESC], [...];
   ```

   现在，新转学来一位蜡笔小新同学没有赶上考试 所以

   ```mysql
   insert into exam_result(name,yuwen,shuxue,yingyu) values('蜡笔小新',null,null,null);
   ```

   

   **1.同学及数学成绩，按数学成绩升序显示**

   ```mysql
   SELECT name, shuxue FROM exam_result ORDER BY shuxue;
   +----------+--------+
   | name     | shuxue |
   +----------+--------+
   | 蜡笔小新 |   NULL |
   | 宋公明   |     65 |
   | 孙权     |     73 |
   | 孙悟空   |     78 |
   | 曹孟德   |     84 |
   | 刘玄德   |     85 |
   | 唐三藏   |     98 |
   | 猪悟能   |     98 |
   +----------+--------+
   ```

   

   **2.按一个同学语文成绩的降序显示**

```mysql
select name,yuwen from exam_result order by yuwen desc;
+----------+-------+
| name     | yuwen |
+----------+-------+
| 猪悟能   |    88 |
| 孙悟空   |    87 |
| 曹孟德   |    82 |
| 宋公明   |    75 |
| 孙权     |    70 |
| 唐三藏   |    67 |
| 刘玄德   |    55 |
| 蜡笔小新 |  NULL |
+----------+-------+
```

**由此我们可以知道的是，null在mysql中时最小值**



3. **查询同学各门成绩，依次按 数学降序，英语升序，语文升序的方式显示**

```mysql
 select name,shuxue,yuwen,yingyu from exam_result order by shuxue desc,yingyu,yuwen;
+----------+--------+-------+--------+
| name     | shuxue | yuwen | yingyu |
+----------+--------+-------+--------+
| 唐三藏   |     98 |    67 |     56 |
| 猪悟能   |     98 |    88 |     90 |
| 刘玄德   |     85 |    55 |     45 |
| 曹孟德   |     84 |    82 |     67 |
| 孙悟空   |     78 |    87 |     77 |
| 孙权     |     73 |    70 |     78 |
| 宋公明   |     65 |    75 |     30 |
| 蜡笔小新 |   NULL |  NULL |   NULL |
+----------+--------+-------+--------+
```

4. **查询同学及总分，由高到低**

```mysql
 select name,shuxue+yuwen+yingyu as total from exam_result order by total desc;
+----------+-------+
| name     | total |
+----------+-------+
| 猪悟能   |   276 |
| 孙悟空   |   242 |
| 曹孟德   |   233 |
| 唐三藏   |   221 |
| 孙权     |   221 |
| 刘玄德   |   185 |
| 宋公明   |   170 |
| 蜡笔小新 |  NULL |
+----------+-------+
```



5. **查询姓孙的同学或者姓曹的同学数学成绩，结果按数学成绩由高到低显示**

```mysql
select name,shuxue from exam_result where name like '孙%'or name like'曹%'order by shuxue desc;
+--------+--------+
| name   | shuxue |
+--------+--------+
| 曹孟德 |     84 |
| 孙悟空 |     78 |
| 孙权   |     73 |
+--------+--------+
```

 **6.筛选分页**

```mysql
-- 起始下标为 0
-- 从 0 开始，筛选 n 条结果
SELECT ... FROM table_name [WHERE ...] [ORDER BY ...] LIMIT n;
-- 从 s 开始，筛选 n 条结果
SELECT ... FROM table_name [WHERE ...] [ORDER BY ...] LIMIT s, n;
-- 从 s 开始，筛选 n 条结果，比第二种用法更明确，建议使用
SELECT ... FROM table_name [WHERE ...] [ORDER BY ...] LIMIT n OFFSET s;
```

这个offset表示偏移量

方法一：

```mysql
select *from exam_result limit 0,3;
+----+--------+-------+--------+--------+
| id | name   | yuwen | shuxue | yingyu |
+----+--------+-------+--------+--------+
|  1 | 唐三藏 |    67 |     98 |     56 |
|  2 | 孙悟空 |    87 |     78 |     77 |
|  3 | 猪悟能 |    88 |     98 |     90 |
+----+--------+-------+--------+--------+
```

方法二：

```mysql
 select *from exam_result limit 3 offset 0;
+----+--------+-------+--------+--------+
| id | name   | yuwen | shuxue | yingyu |
+----+--------+-------+--------+--------+
|  1 | 唐三藏 |    67 |     98 |     56 |
|  2 | 孙悟空 |    87 |     78 |     77 |
|  3 | 猪悟能 |    88 |     98 |     90 |
+----+--------+-------+--------+--------+
```



## update......set...更新数据

```mysql
UPDATE table_name SET column = expr [, column = expr ...]
[WHERE ...] [ORDER BY ...] [LIMIT ...]
```

1. 将孙悟空同学的数学成绩变更为 80 分

   ```mysql
   select name,shuxue from exam_result;
   +----------+--------+
   | name     | shuxue |
   +----------+--------+
   | 唐三藏   |     98 |
   | 孙悟空   |     80 |
   | 猪悟能   |     98 |
   | 曹孟德   |     84 |
   | 刘玄德   |     85 |
   | 孙权     |     73 |
   | 宋公明   |     65 |
   | 蜡笔小新 |   NULL |
   +----------+--------+
   ```

   

2. 将曹孟德同学的数学成绩变更为 60 分，语文成绩变更为 70 分

   ```mysql
   update exam_result set shuxue=60,yuwen=70 where name='曹孟德';
   ```

   ```mysql
   select name,shuxue ,yuwen from exam_result;
   +----------+--------+-------+
   | name     | shuxue | yuwen |
   +----------+--------+-------+
   | 唐三藏   |     98 |    67 |
   | 孙悟空   |     80 |    87 |
   | 猪悟能   |     98 |    88 |
   | 曹孟德   |     60 |    70 |
   | 刘玄德   |     85 |    55 |
   | 孙权     |     73 |    70 |
   | 宋公明   |     65 |    75 |
   | 蜡笔小新 |   NULL |  NULL |
   +----------+--------+-------+
   ```

   

3. 将总成绩倒数前三的 3 位同学的数学成绩加上 30 分

   ```mysql
   update exam_result set shuxue=shuxue+30  order by yuwen+shuxue+yingyu limit 3;
   ```

   

4. 将所有同学的语文成绩更新为原来的 2 倍

   ```mysql
   select name,yuwen from exam_result;
   +----------+-------+
   | name     | yuwen |
   +----------+-------+
   | 唐三藏   |   134 |
   | 孙悟空   |   174 |
   | 猪悟能   |   176 |
   | 曹孟德   |   140 |
   | 刘玄德   |   110 |
   | 孙权     |   140 |
   | 宋公明   |   150 |
   | 蜡笔小新 |  NULL |
   +----------+-------+
   ```

   

## 删除表的数据--delete

```mysql
DELETE FROM table_name [WHERE ...] [ORDER BY ...] [LIMIT ...]
```

和drop 删除整张表区分和 alter table  drop 删除标的字段要区分

1.删除孙悟空同学的考试成绩

```mysql
delete from exam_result where name='孙悟空';
```

2.删除所有同学的考试成绩

```
delete from exam_result;
```



## 截断表--truancate

其实也是删除表中所有数据，不过与delete不同的是 这里的删除是指告诉系统这个表为空，实际上没有删除，下次插入数据时，覆盖原有的数据；

```mysql
TRUNCATE [TABLE] table_name
```



```
CREATE TABLE for_truncate (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20)
);
Query OK, 0 rows affected (0.16 sec)
-- 插入测试数据
INSERT INTO for_truncate (name) VALUES ('A'), ('B'), ('C');
Query OK, 3 rows affected (1.05 sec)
Records: 3 Duplicates: 0 Warnings: 0
-- 查看测试数据
SELECT * FROM for_truncate;
+----+------+
| id | name |
+----+------+
| 1 | A |
| 2 | B |
| 3 | C |
+----+------+
3 rows in set (0.00 sec)
-- 截断整表数据，注意影响行数是 0，所以实际上没有对数据真正操作
TRUNCATE for_truncate;
Query OK, 0 rows affected (0.10 sec)
-- 查看删除结果
SELECT * FROM for_truncate;
Empty set (0.00 sec)
-- 再插入一条数据，自增 id 在重新增长
INSERT INTO for_truncate (name) VALUES ('D');
Query 
```

