# 两数相加

[原题链接](https://leetcode-cn.com/problems/add-two-numbers/description/)

## 题目

给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。

你可以假设除了数字 0 之外，这两个数字都不会以零开头。

示例：

```bash
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807
```

## 解题思路

![add_two_numbers](https://pic.leetcode-cn.com/Figures/2/2_add_two_numbers.svg)

```bash
1.因为存储是反过来的，即数字342存成2->4->3，所以要注意进位是向后的；
2.链表l1或l2为空时，直接返回，这是边界条件，省掉多余的操作；
3.链表l1和l2长度可能不同，因此要注意处理某个链表剩余的高位；
4.2个数相加，可能会产生最高位的进位，因此要注意在完成以上1－3的操作后，判断进位是否为0，不为0则需要增加结点存储最高位的进位。
```

* 全部为null时，返回进位值
* 有一个为null时，返回不为null的那个ListNode和进位相加的值
* 都不为null时，返回 两个ListNode和进位相加的值

## 代码

### JAVA

```java
package problem;

public class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummy;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummy.next;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        ListNode ans = new Solution().addTwoNumbers(l1, l2);
        while (ans != null) {
            System.out.println(ans.val);
            ans = ans.next;
        }
    }
    
}
```

### Python

```Python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    def addTwoNumbers(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        c = 0
        dummy = cur = ListNode(0)
        while l1 or l2 or c:
            num1 = l1.val if l1 else 0
            num2 = l2.val if l2 else 0
            (c, val) = divmod(num1 + num2 + c, 10)
            cur.next = ListNode(val)
            l1 = l1.next if l1 else None
            l2 = l2.next if l2 else None
            cur = cur.next
        return dummy.next


if __name__ == "__main__":
    l1 = ListNode(2)
    l1.next = ListNode(4)
    l1.next.next = ListNode(3)
    l2 = ListNode(5)
    l2.next = ListNode(6)
    l2.next.next = ListNode(4)
    ans = Solution().addTwoNumbers(l1, l2)
    while ans is not None:
        print(ans.val)
        ans = ans.next
```


### Golang

```go
package main

import "fmt"

type ListNode struct {
	Val  int
	Next *ListNode
}

func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	dummy := &ListNode{Val: 0}
	p, q, curr := l1, l2, dummy
	carry := 0
	for p != nil || q != nil {
		x, y := 0, 0
		if p != nil {
			x = p.Val
		}
		if q != nil {
			y = q.Val
		}
		sum := carry + x + y
		carry = sum / 10
		curr.Next = &ListNode{Val: sum % 10}
		curr = curr.Next
		if p != nil {
			p = p.Next
		}
		if q != nil {
			q = q.Next
		}
	}
	if carry > 0 {
		curr.Next = &ListNode{Val: carry}
	}
	return dummy.Next
}

func main() {
	l1 := &ListNode{
		Val: 2,
		Next: &ListNode{
			Val: 4,
			Next: &ListNode{
				Val:  3,
				Next: nil,
			},
		},
	}
	l2 := &ListNode{
		Val: 5,
		Next: &ListNode{
			Val: 6,
			Next: &ListNode{
				Val:  4,
				Next: nil,
			},
		},
	}
	ans := addTwoNumbers(l1, l2)
	for ans != nil {
		fmt.Println(ans.Val)
		ans = ans.Next
	}
}
```