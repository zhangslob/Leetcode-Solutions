## 28.实现strStr()

### 题目

实现 strStr() 函数。

给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。

示例 1:

	输入: haystack = "hello", needle = "ll"
	输出: 2
	
示例 2:

	输入: haystack = "aaaaa", needle = "bba"
	输出: -1
	
说明:

当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。

对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。


### 解题思路

对 python 内置字符串函数 find 的实现

#### Python

```
class problem.Solution(object):
    def strStr(self, haystack, needle):
        """
        :type haystack: str
        :type needle: str
        :rtype: int
        """
        # return haystack.find(needle)
        for i in range(len(haystack) + 1):
            for j in range(len(needle) + 1):
                if j == len(needle):
                    return i
                if i + j == len(haystack):
                    return -1
                if needle[j] != haystack[i + j]:
                    break

```