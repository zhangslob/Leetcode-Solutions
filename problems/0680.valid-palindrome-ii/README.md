# 验证回文字符串 Ⅱ

[原题链接](https://leetcode-cn.com/problems/valid-palindrome-ii/)

## 题目

给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。

示例 1:

- 输入: "aba"
- 输出: True

示例 2:

- 输入: "abca"
- 输出: True

解释: 你可以删除c字符。

注意: 字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。

## 题解

最开始想到的是遍历每一个字符，去掉该字符，再判断是否回文串。代码

```python
class Solution:
    def validPalindrome(self, s: str) -> bool:
        if self.isPalindrome(s):
            return True
        for i in range(len(s)):
            tmp = s[:i] + s[i + 1:]
            if self.isPalindrome(tmp):
                return True
        return False

    def isPalindrome(self, s):
        if len(s) == 0:
            return True
        i, j = 0, len(s) - 1
        while i < j:
            if s[i] != s[j]:
                return False
            i += 1
            j -= 1
        return True
```

毫无疑问的，超时了。

1. 双指针，用子函数判断当前字符串是否符合回文串。
2. 两个指针从两头向中间聚拢，如果遇到字母不相同，判断左指针右移一位或者右指针左移一位是否构成回文串。

## 代码

### Java

```java
class Solution {
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return valid(s, i + 1, j) || valid(s, i, j - 1);
            }
            i++;
            j--;
        }
        return true;
    }

    private boolean valid(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
```

- 执行用时 : 10 ms , 在所有 Java 提交中击败了 50.20% 的用户 
- 内存消耗 : 40.5 MB , 在所有 Java 提交中击败了 6.67% 的用户

### Go

```go

func validPalindrome(s string) bool {
	i, j := 0, len(s)-1
	for i < j {
		if s[i] != s[j] {
			return valid(s, i, j-1) || valid(s, i+1, j)
		}
		i++
		j--
	}
	return true
}

func valid(s string, i, j int) bool {
	for i < j {
		if s[i] != s[j] {
			return false
		}
		i++
		j--
	}
	return true
}
```

- 执行用时 : 20 ms , 在所有 Go 提交中击败了 72.02% 的用户 
- 内存消耗 : 6.3 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def validPalindrome(self, s: str) -> bool:
        if s == s[::-1]:
            return True
        i, j = 0, len(s) - 1
        while i < j:
            if s[i] == s[j]:
                i += 1
                j -= 1
            else:
                return self.valid(s, i + 1, j) or self.valid(s, i, j - 1)

    def valid(self, s, i, j):
        while i < j:
            if s[i] != s[j]:
                return False
            i += 1
            j -= 1
        return True
```

- 执行用时 : 164 ms , 在所有 Python3 提交中击败了 49.12% 的用户 
- 内存消耗 : 14.1 MB , 在所有 Python3 提交中击败了 36.36% 的用户

