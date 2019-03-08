#找到倒数第K个节点



倒数节点：

让第一个节点先走k(倒数步数)

第二个结点从头结点开始走；

```java
public ListNode FindKthToTail(ListNode head,int k) {
         //1.看看一共有多少个结点，
        //2.看这个倒数的结点在整数的第几个
        
        if(head==null){
            return null;
        }
        
        ListNode first=head;
        ListNode second=head;
       for(int i=0;i<k;i++){
           if(first!=null){
               first=first.next;
           }else{
               return null;
           }
       }
        while(first!=null){
            first=first.next;
            second=second.next;
        }
        return second;
```

方法二： 1.看看一共有多少个结点，
        	2.看这个倒数的结点在整数的第几个

​		3.总个数-倒数步数就是  倒数节点在的位置

```java
  public ListNode FindKthToTail(ListNode head,int k) {
        
        int size=0;
        for(ListNode temp=head;temp!=null;temp=temp.next){
            size++;
        }
        /* size=6
        k:1->6
        k:2->5
        k:3->4
        k:4->3
        k:5->2
        k:6->1*/
        ListNode temp=head;
        if(k<0||k>size){
            return null;
        }
        for(int i=0;i<size-k;i++){//走size-k步得到结点
            temp=temp.next;
        }
        return temp;
    }
```



