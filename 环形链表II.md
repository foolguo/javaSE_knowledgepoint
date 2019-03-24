环形链表：

给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 `null`。

为了表示给定链表中的环，我们使用整数 `pos` 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 `pos` 是 `-1`，则在该链表中没有环。

**说明：**不允许修改给定的链表。

思路：1.快慢指针判断是否有换，

2.定义一个指向头结点的指针，指针一次走一步，上一步定义的慢指针向下继续向下走，两个指针交汇处就是入环的结点；

 ```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head==null||head.next==null){
            return null;
        }
        ListNode fast=head,slow=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
            if(fast==slow){
                break;
            }
        }
        
        if(fast==slow){
            ListNode q=head;
            while(q!=slow){
                q=q.next;
                slow=slow.next;
            }
            return q;
        }else{
            return null;
        }
        
    }
}
 ```

