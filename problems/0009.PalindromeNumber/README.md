# 9.PalindromeNumber

[问题链接](https://leetcode-cn.com/problems/palindrome-number/description/)

# 题目

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

# 解题思路

解决问题的思路是将传入的数据进行反转，当然如果是字符串的话，反转会比较方便。但是一味地用字符串或者list解题未免太不程序员了，那么我们就用计算的方法吧。要想将数字反转，按照思路我们要将数字最右边的数据一个一个拿出来，并且放在最左边。最主要的“拿出来”这一步，可以用取模运算（这里不考虑负数，所以不讨论是取模还是求余），”拿出来“后将原始数据除以10，就可以得到下一次需要被操作的数据了。下面是java代码：

## java

```java
public boolean isPalindrome(int x) {
        boolean b = false;
        int y = 0;
        int z = x;
        while (z > 0) {
            y = y * 10 + z % 10;
            z = z / 10;
        }
        if (y == x) {
            b = true;
        }
        return b;

    }
```

## Python

用Python比较简单，直接使用字符串的切片来倒序

```python
class Solution:
    def isPalindrome(self, x):
        """
        :type x: int
        :rtype: bool
        """
        n = str(x)  
        m = n[::-1]  
        return n == m  
```

## Golang

检查一个整数是否是回文。

先把整数转换成字符串，再检查字符串是否是回文。

```go 
package main

import "strconv"

func isPalindrome(x int) bool {
    if x < 0 {
		return false
	}

	s := strconv.Itoa(x)

	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		if s[i] != s[j] {
			return false
		}
	}

	return true
}
```
