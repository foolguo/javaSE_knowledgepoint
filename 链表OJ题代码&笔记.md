反转链表:
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        if(head == null || head.next == null) {
            return head;
        }else {
            // 第一节点
            ListNode f = dummyHead.next;
            // 第二节点
            ListNode s = f.next;
            // 保存第三节点，以便下次反转使用
            while(s!=null) {
                f.next = s.next;
                s.next = dummyHead.next;
                dummyHead.next = s;
                s = f.next;
            }
            return dummyHead.next;
        }
    }
}
判断回文:
import java.util.*;

/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class PalindromeList {
    public boolean chkPalindrome(ListNode A) {
        // write code here
        if(A == null || A.next == null) {
            return true;
        }
        // 当前链表至少有两个节点
        ListNode fast = A;
        ListNode low = A;
        while(fast!=null&&low!=null&&fast.next!=null) {
            fast = fast.next.next;
            low = low.next;
        }
        // 找到链表的中间位置
        ListNode mid = low;
        // 反转后半段链表
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = mid;
        if(mid.val == A.val) {
            return true;
        }else {
            if(mid.next == null) {
                return false;
            }else {
                ListNode f = dummyHead.next;
                ListNode s = f.next;
                while(s!=null) {
                    f.next = s.next;
                    s.next = dummyHead.next;
                    dummyHead.next = s;
                    s = f.next;
                }
                // 两个链表开始从前向后遍历
                fast = A;
                while(A!=null&&dummyHead.next!=null) {
                    if(A.val != dummyHead.next.val) {
                        return false;
                    }
                    A = A.next;
                    dummyHead.next = dummyHead.next.next;
                }
                return true;
            }
        }
    }
}
实现递归链表的插入

```java
   public void add(Object newData,int index,Node head) {
       if(index==1){
           head.next=new Node(head.next,newData);
           return ;
       }
       add(newData,index-1,head.next);
    
    }
```









递归实现链表的删除

public ListNode removeElements(ListNode head, int val) {
    if(head == null) {
            return head;
    }
    ListNode res = removeElements(head.next,val);
    if(head.val == val) {
            return res;
        }else {
            head.next = res;
            return head;
        }
    }

反转链表2:https://leetcode-cn.com/problems/reverse-linked-list-ii/
k个一组反转链表:https://leetcode-cn.com/problems/reverse-nodes-in-k-group/

递归的微观语义(递归调用是有代价的:函数调用+系统栈空间),简化逻辑，二分搜素树等
更多与链表相关的问题
	-与链表的所有操作，都可以使用递归完成，CRUD都递归完成一下
	-双链表
	-循环链表

循环链表:约瑟夫环
特点:尾节点不再指向空，而指向头结点。
优点:方便找到头尾节点

双向链表:以空间换时间
节点内部含有前驱与后继节点指向其相邻的前节点与后节点。

优点:
插入:
1.对于在指定节点处插入新节点而言:
单链表复杂度O(n),需要找到其前驱节点
双向链表复杂度O(1)
2.插入特定内容的元素(要么尾插，要么头插)
单链表，O(1)或O(n)
双向链表均是O(1)
随机位置插入，复杂度均为O(n)，均需要走到index位置。

删除:
1.对于在指定节点位置的删除
单链表复杂度O(n),双向链表O(1)
2.删除指定元素，单链表与双向链表复杂度均为O(n)

查找:
12345678 3 
对于有序链表而言，上一次遍历到的位置为e,则下一次再查找时，
单链表复杂度O(n)
双向链表复杂度O(n)会减少多次的遍历

缺点:
比单链表多了一些存储空间

头结点前驱节点为null，尾节点后继节点为null

​	



