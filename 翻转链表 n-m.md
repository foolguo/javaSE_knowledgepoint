# 翻转m-n索引的链表

思路：1.找到子链表的前驱结点（m-1个节点）；

​	    2.翻转m-n个元素；

特殊情况：1.链表为空或者链表只有一个元素；

​		    2.m=n



![](E:\javaSE_knowledgepoint\翻转链表的m-n.png)

```java
 public ListNode reverseBetween(ListNode head, int m, int n) {//m和n指的是链表第m—n的元素
        if(head==null||head.next==null){
            return head;
        }
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        ListNode prev=dummyHead;
        for(int i=0;i<m-1;i++){//找到要翻转的子链表之前的链表
            prev=prev.next; 
        }
        ListNode f=prev.next;
        ListNode s=f.next;
        int k=n-m;//要翻转的元素个数
        while(k>0){//翻转部分链表
            f.next=s.next;
            s.next=prev.next;
            prev.next=s;
            s=f.next;
            k--;
        }
        return dummyHead.next;
 }
```

