# 回文数

[问题链接](https://leetcode-cn.com/problems/palindrome-number/description/)

## 题目

判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

**示例 1:**

```
输入: 121
输出: true
```

**示例 2:**

```
输入: -121
输出: false
解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
```

**示例 3:**

```
输入: 10
输出: false
解释: 从右向左读, 为 01 。因此它不是一个回文数。
```

**进阶:**

你能不将整数转为字符串来解决这个问题吗？

## 题解

最简单常规的思路当然是转化为字符串，然后双指针判断是否相等，这肯定不是一种好方法。

可以新建一个变量去表示数字转换的结果

## 代码

### Java

```java
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int y = 0, tmp = x;
        while (tmp != 0) {
            y = y * 10 + tmp % 10;
            tmp = tmp / 10;
        }
        return x == y;
    }
}
```

- 执行用时 : 9 ms , 在所有 Java 提交中击败了 99.06% 的用户 
- 内存消耗 : 39.3 MB , 在所有 Java 提交中击败了 5.14% 的用户

提交后发现一种方法，简化了计算步骤

```java
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || x % 10 == 0 && x != 0) {
            return false;
        }
        int reversed = 0;
        while (x > reversed) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        return x == reversed || x == reversed / 10;
    }
}
```

- 执行用时 : 9 ms , 在所有 Java 提交中击败了 99.06% 的用户 
- 内存消耗 : 39.3 MB , 在所有 Java 提交中击败了 5.14% 的用户 炫耀一下:

## Go

```go
func isPalindrome(x int) bool {
	if x < 0 || x%10 == 0 && x != 0 {
		return false
	}
	reversed := 0
	for x > reversed {
		reversed = reversed*10 + x%10
		x /= 10
	}
	return x == reversed || x == reversed/10
}
```

- 执行用时 : 8 ms , 在所有 Go 提交中击败了 97.86% 的用户 
- 内存消耗 : 5.2 MB , 在所有 Go 提交中击败了 88.00% 的用户

### Python

```python
class Solution:
    def isPalindrome(self, x: int) -> bool:
        if x < 0 or x % 10 == 0 and x != 0:
            return False
        reversed_num = 0
        while x > reversed_num:
            reversed_num = reversed_num * 10 + x % 10
            x //= 10
        return x == reversed_num or x == reversed_num // 10
```

- 执行用时 : 84 ms , 在所有 Python3 提交中击败了 70.72% 的用户
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 5.88% 的用户

看看这种解法
```python
class Solution:
    def isPalindrome(self, x: int) -> bool:
        return str(x) == str(x)[::-1]
```

- 执行用时 : 80 ms , 在所有 Python3 提交中击败了 80.17% 的用户 
- 内存消耗 : 13.5 MB , 在所有 Python3 提交中击败了 5.88% 的用户

> 我不服
