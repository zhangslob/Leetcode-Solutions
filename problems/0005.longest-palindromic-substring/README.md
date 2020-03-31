
# [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/description/)

给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为1000。

示例 1：
```
输入: "babad"
输出: "bab"
注意: "aba"也是一个有效答案。
```

示例 2：

```
输入: "cbbd"
输出: "bb"
```

# 思路

## Python

这道题有很多解法，如暴力法、动态规划、中心扩展法，最经典的是 `Manacher算法` （中文名：马拉车算法）

由于回文分为偶回文（比如 `bccb`）和奇回文（比如 `bcacb`），而在处理奇偶问题上会比较繁琐，所以这里我们使用一个技巧，具体做法是：在字符串首尾，及各字符间各插入一个字符（前提这个字符未出现在串里）。

举个例子：`s="abbahopxpo"`，转换为`s_new="$#a#b#b#a#h#o#p#x#p#o#"`（这里的字符 `$` 只是为了防止越界，下面代码会有说明），如此，s 里起初有一个偶回文`abba`和一个奇回文`opxpo`，被转换为`#a#b#b#a#`和`#o#p#x#p#o#`，长度都转换成了奇数。

定义一个辅助数组int p[]，其中p[i]表示以 i 为中心的最长回文的半径，例如：

![](http://p8eyj0cpn.bkt.clouddn.com/%E6%B7%B1%E5%BA%A6%E6%88%AA%E5%9B%BE_%E9%80%89%E6%8B%A9%E5%8C%BA%E5%9F%9F_20180531144658.png)

可以看出，`p[i] - 1`正好是原字符串中最长回文串的长度。

参考: [最长回文子串——Manacher 算法](https://segmentfault.com/a/1190000003914228)

```python

class problem.Solution:
    def longestPalindrome(self, s):
        s_list = [str1 for str1 in s]
        string = '#' + '#'.join(s_list) + '#'
        lens = len(string)
        f = []  # 辅助列表：f[i]表示i作中心的最长回文子串的长度
        maxj = 0  # 记录对i右边影响最大的字符位置j
        maxl = 0  # 记录j影响范围的右边界
        maxd = 0  # 记录最长的回文子串长度
        for i in range(lens):  # 遍历字符串
            if maxl > i:
                count = min(maxl - i, int(f[2 * maxj - i] / 2) + 1)  # 这里为了方便后续计算使用count，其表示当前字符到其影响范围的右边界的距离
            else:
                count = 1
            while i - count >= 0 and i + count < lens and string[i - count] == string[i + count]:  # 两边扩展,这是一个循环，切记
                count += 1
            if (i - 1 + count) > maxl:  # 更新影响范围最大的字符j及其右边界
                maxl, maxj = i - 1 + count, i
            f.append(count * 2 - 1)
            maxd = max(maxd, f[i])# 更新回文子串最长长度
        centerindex=f.index(maxd) 
        length=int((maxd + 1) / 2) - 1
        finalstring=string[centerindex-length:centerindex+length]
        returnstring=finalstring.replace("#","")
        return returnstring
```


## Golang

```golang
func longestPalindrome(s string) string {
	if len(s) < 2 { 
		return s
	}

	begin, maxLen := 0, 1

	for i := 0; i < len(s); { 
		if len(s)-i <= maxLen/2 {
			break
		}

		b, e := i, i
		for e < len(s)-1 && s[e+1] == s[e] {
			e++
		}
		i = e + 1

		for e < len(s)-1 && b > 0 && s[e+1] == s[b-1] {
			e++
			b--
		}

		newLen := e + 1 - b
		if newLen > maxLen {
			begin = b
			maxLen = newLen
		}
	}

	return s[begin : begin+maxLen]
}
```

执行用时：4 ms，啧啧啧
