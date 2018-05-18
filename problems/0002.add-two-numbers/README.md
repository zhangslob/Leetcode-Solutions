# 2. [两数相加](https://leetcode-cn.com/problems/add-two-numbers/description/)

# 题目
给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。

你可以假设除了数字 0 之外，这两个数字都不会以零开头。

示例：
```
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807
```

# 解题思路

## Python



```
1.因为存储是反过来的，即数字342存成2->4->3，所以要注意进位是向后的；
2.链表l1或l2为空时，直接返回，这是边界条件，省掉多余的操作；
3.链表l1和l2长度可能不同，因此要注意处理某个链表剩余的高位；
4.2个数相加，可能会产生最高位的进位，因此要注意在完成以上1－3的操作后，判断进位是否为0，不为0则需要增加结点存储最高位的进位。
```

* 全部为null时，返回进位值
* 有一个为null时，返回不为null的那个ListNode和进位相加的值
* 都不为null时，返回 两个ListNode和进位相加的值

```python
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None
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
```



## Golang




>(2 -> 4 -> 3)是 342
>
>(5 -> 6 -> 4)是 465
>
>(7 -> 0 -> 8)是 807
>
>342 + 465 = 807

所以，题目的本意是，把整数换了一种表达方式后，实现其加法。

设计程序时候，需要处理的点有

1. 位上的加法，需要处理进位问题
2. 如何进入下一位运算
3. 按位相加结束后，也还需要处理进位问题。

```go
/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	result := &ListNode{}
	temp := result
	v, n := 0, 0

	for {
		// 在当前位上进行加法运算
		v, n = add(l1, l2, n)
		temp.Val = v

		// 进入下一位
		l1 = next(l1)
		l2 = next(l2)
		// 如果两个数的下一位都为nil，则结束按位相加的运算
		if l1 == nil && l2 == nil {
			break
		}

		// 为下一位运算准备节点
		temp.Next = &ListNode{}
		temp = temp.Next
	}

	// n == 1 说明，最后一次加运算进位了，需要再添加一个节点。
	if n == 1 {
		temp.Next = &ListNode{Val: n}
	}

	return result
}

// next 进入l的下一位。
func next(l *ListNode) *ListNode {
	if l != nil {
		return l.Next
	}
	return nil
}

func add(n1, n2 *ListNode, i int) (v, n int) {
	if n1 != nil {
		v += n1.Val
	}

	if n2 != nil {
		v += n2.Val
	}

	v += i

	if v > 9 {
		v -= 10
		n = 1
	}

	return
}
```
