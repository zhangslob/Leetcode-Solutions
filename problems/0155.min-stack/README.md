# 最小栈

[原题链接](https://leetcode-cn.com/problems/min-stack/)

## 题目

设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。

- push(x) —— 将元素 x 推入栈中。
- pop() —— 删除栈顶的元素。
- top() —— 获取栈顶元素。
- getMin() —— 检索栈中的最小元素。
 

示例:

输入：
```text
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
```

输出：
```text
[null,null,null,null,-3,null,0,-2]
```

解释：
```text
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```

提示：
- pop、top 和 getMin 操作总是在 非空栈 上调用。

## 题解

直接看代码吧。注意栈是先进后出，栈顶是数组中最后一个

## 代码

### Go

```go
type MinStack struct {
	stack    []int
	minStack []int
}

func Constructor() MinStack {
	return MinStack{
		stack:    []int{},
		minStack: []int{math.MaxInt64},
	}
}

func (this *MinStack) Push(x int) {
	this.stack = append(this.stack, x)
	top := this.minStack[len(this.minStack)-1]
	this.minStack = append(this.minStack, min(x, top))
}

func (this *MinStack) Pop() {
	this.stack = this.stack[:len(this.stack)-1]
	this.minStack = this.minStack[:len(this.minStack)-1]
}

func (this *MinStack) Top() int {
	return this.stack[len(this.stack)-1]
}

func (this *MinStack) GetMin() int {
	return this.minStack[len(this.minStack)-1]
}

func min(x int, y int) int {
	if x > y {
		return y
	}
	return x
}
```

- 执行用时 : 12 ms , 在所有 Go 提交中击败了 99.70% 的用户 
- 内存消耗 : 7.8 MB , 在所有 Go 提交中击败了 16.67% 的用户

