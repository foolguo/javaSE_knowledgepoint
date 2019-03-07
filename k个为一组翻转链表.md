# k个为一组翻转链表

思路：

1.获取链表长度  **size**；

2.将链表分成   **size/k**；

3.将每组内部分别翻转；

要注意几种特殊情况：

​	1.链表为空或者只有一个元素

​	2.链表个数小于k



![](E:\javaSE_knowledgepoint\K个一组翻转链表png.png)

```java

 Definition for singly-linked list.
 public class ListNode {
      int val;
     ListNode next;
     ListNode(int x) { val = x; }
  }
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        //链表为空或者只有一个元素直接返回
        if(head==null||head.next==null){
            return head;
        }
       ListNode dummyHead= new ListNode(-1);//创建虚拟头结点
        //将虚拟头结点与链表相连
       dummyHead.next=head;
       ListNode prev=dummyHead;
        
		//获取链表长度
        int size=0;
        for(ListNode temp=head;temp!=null;temp=temp.next){
            size++;
        }
        
        //链表长度小于k
        if(size<k){
            return dummyHead.next;
        }
       
        int n=size/k;//将链表分成K组
        while(n>0){
           ListNode f=prev.next;
            ListNode s=prev.next.next;
            for(int i=0;i<k-1;i++){//2个元素只用翻转一次，3元素翻转两次，k个元素翻转k-1次
                f.next=s.next;
                s.next=prev.next;
                prev.next=s;
                s=f.next;
            }
            //每组翻转后prev结点要向后移动k步，为了下一个组翻转
            for(int j=0;j<k;j++){
                prev=prev.next;
            }
            n--;
        }
       return dummyHead.next;
    }
}
```

