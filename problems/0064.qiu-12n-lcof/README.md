# 求1+2+…+n

[原题链接](https://leetcode-cn.com/problems/qiu-12n-lcof/)

## 题目

求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。

示例 1：
```text
输入: n = 3
输出: 6
```

示例 2：
```text
输入: n = 9
输出: 45
```

限制：1 <= n <= 10000

## 题解

初步想法是位运算。然后发现错了，哈哈。

正常递归写法应该是 
```java
    public int sumNums(int n) {
        if (n == 1) {
            return 1;
        }
        n += sumNums(n - 1);
        return n;
    }
```

但是使用了if关键字，用**逻辑运算符**可以达到同样效果。

## 代码

### Java

```java
class Solution {
    public int sumNums(int n) {
        boolean flag = n > 0 && (n += sumNums(n - 1)) > 0;
        return n;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 36.9 MB , 在所有 Java 提交中击败了 100.00% 的用户

看不懂来个变形吧

```java
    public int sumNums(int n) {
        boolean flag;
        if (n > 0) {
            flag = (n += sumNums(n - 1)) > 0;
        } else {
            flag = false;
        }
        return n;
    }
```

如果 n <= 0 就直接return了，不会继续递归，这种题很没意思

### Go

```go
func sumNums(n int) int {
	_ = n > 0 && func() bool { n += sumNums(n - 1); return true }()
	return n
}
```

> Go 语言中整数和 bool 类型是不能隐式转换的，所以使用匿名函数封装一下

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户
- 内存消耗 : 2.6 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python
```python
class Solution:
    def sumNums(self, n: int) -> int:
        return sum(range(1, n + 1))
```

- 执行用时 : 40 ms , 在所有 Python3 提交中击败了 87.19% 的用户 
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 100.00% 的用户

