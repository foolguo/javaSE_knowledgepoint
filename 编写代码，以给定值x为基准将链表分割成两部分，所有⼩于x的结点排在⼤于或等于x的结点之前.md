# 编写代码，以给定值x为基准将链表分割成两部分，所有⼩于x的结点排在⼤于或等于x的结点之前

两个链表  ，将小于x的值插入链表1中 将大于等于x值插入链表2 中   ，将链表2连到链表1后

```java
public ListNode partition(ListNode pHead, int x) {
       if(pHead.next==null){
           return pHead;
       }
       ListNode dummyHead=new ListNode(-1);
       dummyHead.next=pHead;
       ListNode j=dummyHead,i=dummyHead.next,i1=dummyHead;
       for(;i!=null;i=i.next,i1=i1.next){
           if(i.val<x&&j.next.val>=x){
            int temp=j.next.val;
               j.next.val=i.val;
               i.val=temp;
               j=j.next;
           }
       }
       return dummyHead.next;

    }
```

