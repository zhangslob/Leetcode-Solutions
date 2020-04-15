# 两数相加 II

[原题链接](https://leetcode-cn.com/problems/add-two-numbers-ii/)

## 题目

给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。

你可以假设除了数字 0 之外，这两个数字都不会以零开头。

进阶：

如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。

示例：
```text
输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 8 -> 0 -> 7
```

## 题解

这道题和之前的`两数相加`基本一样，有几种做法：
1. 递归
2. 用2个列表储存每个链表数据，在加起来
3. 将链表转为数字后相加，再转为链表 (后来证明这种方法并不好，如果输入20位数，用什么计算。WTF)

需要注意的就是相加超过10，进位情况

## 代码

### Java

方法2

```java
package problem;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        while (l1 != null) {
            list1.add(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            list2.add(l2.val);
            l2 = l2.next;
        }
        ListNode ans = null;
        Integer carry = 0;
        while (!list1.isEmpty() || !list2.isEmpty() || carry != 0) {
            Integer a = list1.isEmpty() ? 0 : list1.remove(list1.size() - 1);
            Integer b = list2.isEmpty() ? 0 : list2.remove(list2.size() - 1);
            int count = a + b + carry;
            carry = count / 10;
            ListNode tmp = new ListNode(count % 10);
            tmp.next = ans;
            ans = tmp;
        }
        return ans;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(7);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode res = new Solution().addTwoNumbers(l1, l2);
        List<Integer> ans = new ArrayList<>();
        while (res != null) {
            ans.add(res.val);
            res = res.next;
        }
        System.out.println(ans);
    }
}
```

- 执行用时 : 4 ms , 在所有 Java 提交中击败了 71.16% 的用户 
- 内存消耗 : 40.1 MB , 在所有 Java 提交中击败了 95.83% 的用户

方法3

```java
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int num1 = 0, num2 = 0;
        while (l1 != null) {
            num1 = num1 * 10 + l1.val;
            l1 = l1.next;
        }
        while (l2 != null) {
            num2 = num2 * 10 + l2.val;
            l2 = l2.next;
        }
        int res = num1 + num2;
        if (res == 0) {
            return new ListNode(0);
        }
        System.out.println(res);
        ListNode pre = null;
        while (res != 0) {
            ListNode cur = new ListNode(res % 10);
            cur.next = pre;
            pre = cur;
            res /= 10;
        }
        return pre;
    }
```

代码不通过，在这个输入报错

输入:
```text
[3,9,9,9,9,9,9,9,9,9]
[7]
```
就很奇怪，应该是整数越界了，后来换成Long还是报错

### Python

方法2

```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def addTwoNumbers(self, l1: ListNode, l2: ListNode) -> ListNode:
        list1, list2 = [], []
        while l1:
            list1.append(l1.val)
            l1 = l1.next
        while l2:
            list2.append(l2.val)
            l2 = l2.next
        ans = None
        carry = 0
        while list1 or list2 or carry != 0:
            s1 = list1.pop() if list1 else 0
            s2 = list2.pop() if list2 else 0
            cur = s1 + s2 + carry
            val = cur % 10
            carry = cur // 10
            tmp = ListNode(val)
            tmp.next = ans
            ans = tmp
        return ans


if __name__ == '__main__':
    l1 = ListNode(7)
    l1.next = ListNode(2)
    l1.next.next = ListNode(4)
    l1.next.next.next = ListNode(3)

    l2 = ListNode(5)
    l2.next = ListNode(6)
    l2.next.next = ListNode(4)

    ans = Solution().addTwoNumbers(l1, l2)
    l = []
    while ans:
        l.append(ans.val)
        ans = ans.next
    print(l)
```

- 执行用时 : 84 ms , 在所有 Python3 提交中击败了 57.14% 的用户 
- 内存消耗 : 13.6 MB , 在所有 Python3 提交中击败了 50.00% 的用户

方法3

```python
class Solution:
    def addTwoNumbers(self, l1: ListNode, l2: ListNode) -> ListNode:
        num1, num2 = 0, 0
        while l1:
            num1 = num1 * 10 + l1.val
            l1 = l1.next
        while l2:
            num2 = num2 * 10 + l2.val
            l2 = l2.next
        res = num1 + num2
        if res == 0:
            return ListNode(0)
        ans = None
        while res:
            tmp = ListNode(res % 10)
            res //= 10
            tmp.next = ans
            ans = tmp
        return ans
```

- 执行用时 : 76 ms , 在所有 Python3 提交中击败了 80.81% 的用户 
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 50.00% 的用户

Python竟然不越界

### Go

方法2

```go
package main

import "fmt"

type ListNode struct {
	Val  int
	Next *ListNode
}

func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	var slice1 []int
	var slice2 []int
	for l1 != nil {
		slice1 = append(slice1, l1.Val)
		l1 = l1.Next
	}
	for l2 != nil {
		slice2 = append(slice2, l2.Val)
		l2 = l2.Next
	}
	var ans *ListNode
	carry := 0
	for len(slice1) != 0 || len(slice2) != 0 || carry != 0 {
		a := 0
		if len(slice1) != 0 {
			a = slice1[len(slice1)-1]
			slice1 = slice1[:len(slice1)-1]
		}
		b := 0
		if len(slice2) != 0 {
			b = slice2[len(slice2)-1]
			slice2 = slice2[:len(slice2)-1]
		}
		cur := a + b + carry
		carry = cur / 10
		tmp := &ListNode{
			Val:  cur % 10,
			Next: ans,
		}
		ans = tmp
	}
	return ans
}

func main() {
	l1 := &ListNode{
		Val: 7,
		Next: &ListNode{
			Val: 2,
			Next: &ListNode{
				Val: 4,
				Next: &ListNode{
					Val: 3,
				},
			},
		},
	}
	l2 := &ListNode{
		Val: 5,
		Next: &ListNode{
			Val: 6,
			Next: &ListNode{
				Val: 4,
			},
		},
	}
	ans := addTwoNumbers(l1, l2)
	var slice []int
	for ans != nil {
		slice = append(slice, ans.Val)
		ans = ans.Next
	}
	fmt.Print(slice)
 }
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 99.62% 的用户 
- 内存消耗 : 5.7 MB , 在所有 Go 提交中击败了 100.00% 的用户

方法3

```go
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	num1, num2 := 0, 0
	for l1 != nil {
		num1 = num1 * 10 + l1.Val
		l1 = l1.Next
	}
	for l2 != nil {
		num2 = num2 * 10 + l2.Val
		l2 = l2.Next
	}
	cnt := num1 + num2
	if cnt==0 {
		return &ListNode{
			Val:  0,
			Next: nil,
		}
	}
	var ans *ListNode
	for cnt != 0 {
		tmp := &ListNode{
			Val:  cnt % 10,
			Next: ans,
		}
		cnt = cnt / 10
		ans = tmp
	}
	return ans
}
```

Go也有这个问题，头疼~
