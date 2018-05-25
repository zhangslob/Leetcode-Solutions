
# [3. 无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/description/)


给定一个字符串，找出不含有重复字符的最长子串的长度。

示例：

给定 `"abcabcbb"` ，没有重复字符的最长子串是 `"abc"` ，那么长度就是3。

给定 `"bbbbb"` ，最长的子串就是 `"b"` ，长度是1。

给定 `"pwwkew"` ，最长子串是 `"wke"` ，长度是3。请注意答案必须是一个子串，`"pwke"` 是 子序列  而不是子串。


# 解法


## 思路(时间复杂度为O(n))

1. 遍历字符串，过程中将出现过的字符存入字典，key为字符，value为字符下标
2. 用maxLength保存遍历过程中找到的最大不重复子串的长度
3. 用start保存最长子串的开始下标
4. 如果字符已经出现在字典中，更新start的值
5. 如果字符不在字典中，更新maxLength的值
6. return maxLength

## Python

```python
class Solution(object):
    def lengthOfLongestSubstring(self, s):
        """
        :type s: str
        :rtype: int
        """
        start = maxLength = 0
        usedChar = {}

        for i in range(len(s)):
            if s[i] in usedChar and start <= usedChar[s[i]]:
                start = usedChar[s[i]] + 1
            else:
                maxLength = max(maxLength, i - start + 1)
            usedChar[s[i]] = i

        return maxLength

```

## Golang

```golang
func lengthOfLongestSubstring(s string) int {
	// location[s[i]] == j 表示：
	// s中第i个字符串，上次出现在s的j位置，所以，在s[j+1:i]中没有s[i]
	// location[s[i]] == -1 表示： s[i] 在s中第一次出现
	location := [256]int{} // 只有256长是因为，假定输入的字符串只有ASCII字符
	for i := range location {
		location[i] = -1 // 先设置所有的字符都没有见过
	}

	maxLen, left := 0, 0

	for i := 0; i < len(s); i++ {
		// 说明s[i]已经在s[left:i+1]中重复了
		// 并且s[i]上次出现的位置在location[s[i]]
		if location[s[i]] >= left {
			left = location[s[i]] + 1 // 在s[left:i+1]中去除s[i]字符及其之前的部分
		} else if i+1-left > maxLen {
			// fmt.Println(s[left:i+1])
			maxLen = i + 1 - left
		}
		location[s[i]] = i
	}

	return maxLen
}
```

提交之后发现更有意思的方法

```golang
func lengthOfLongestSubstring(s string) int {
      baseMap := make(map[byte]int)
    length, max, start := 0, 0, 0
    for k, v := range []byte(s) {
        if p, ok := baseMap[v]; ok && p >= start {
            start = p + 1
        }
        length = k - start + 1
        baseMap[v] = k
        if length > max {
            max = length
        }
    }
    return max
}
```
