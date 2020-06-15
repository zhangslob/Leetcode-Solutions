# 最长公共前缀

[原题链接](https://leetcode-cn.com/problems/longest-common-prefix/)

## 题目

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 `""`。

**示例 1:**

```
输入: ["flower","flow","flight"]
输出: "fl"
```

**示例 2:**

```
输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
```

**说明:**

所有输入只包含小写字母 `a-z` 。

## 题解

我把这题分为两步：
1. 需要知道列表中最短的字符串长度是多少
2. 需要判断每个字符串指定位置的字符是否相等

仔细想想后，第一步其实不是必要的，数组中任意一个字符串都可以当做长度遍历对象

## 代码

### Java I

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        if (strs.length == 0) {
            return sb.toString();
        }
        int i = 0;
        int max = strs[0].length();
        while (i < max) {
            if (!same(strs, i)) {
                break;
            }
            sb.append(strs[0].charAt(i));
            i++;
        }
        return sb.toString();
    }

    private boolean same(String[] strs, int index) {
        char c = strs[0].charAt(index);
        for (String str : strs) {
            if (index >= str.length()) {
                return false;
            }
            if (str.charAt(index) != c) {
                return false;
            }
        }
        return true;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 78.40% 的用户 
- 内存消耗 : 37.9 MB , 在所有 Java 提交中击败了 10.00% 的用户

看了解答，时间复杂度可以优化

### Java II

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 37.7 MB , 在所有 Java 提交中击败了 25.84% 的用户

### Go I

```go
func longestCommonPrefix(strs []string) string {
	if len(strs) == 0 {
		return ""
	}
	ans := ""
	i, max := 0, len(strs[0])
	for i < max {
		if !same(strs, i) {
			break
		}
		ans += string(strs[0][i])
		i++
	}
	return ans
}

func same(strs []string, index int) bool {
	tmp := strs[0][index]
	for i := 1; i < len(strs); i++ {
		if len(strs[i]) <= index {
			return false
		}
		if strs[i][index] != tmp {
			return false
		}
	}
	return true
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.4 MB , 在所有 Go 提交中击败了 11.76% 的用户

### Go II 

```go
func longestCommonPrefix(strs []string) string {
	if len(strs) == 0 {
		return ""
	}
	for index, char := range strs[0] {
		for _, v := range strs {
			if index >= len(v) {
				return v
			}
			if v[index] != byte(char) {
				return v[:index]
			}
		}
	}
	return strs[0]
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.4 MB , 在所有 Go 提交中击败了 82.35% 的用户

### Python I

```python
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        if len(strs) == 0:
            return ""

        def same(index):
            tmp = strs[0][index]
            for s in strs:
                if index >= len(s):
                    return False
                if s[index] != tmp:
                    return False
            return True

        index, first = 0, len(strs[0])
        ans = ""
        while index < first:
            if not same(index):
                break
            ans += strs[0][index]
            index += 1
        return ans
```

- 执行用时 : 44 ms , 在所有 Python3 提交中击败了 59.27% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 6.15% 的用户

### Python II

```python
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        if len(strs) == 0:
            return ""
        for i in range(len(strs[0])):
            for j in range(1, len(strs)):
                if i == len(strs[j]) or strs[j][i] != strs[0][i]:
                    return strs[0][:i]
        return strs[0]
```

- 执行用时 : 48 ms , 在所有 Python3 提交中击败了 37.08% 的用户 
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 6.15% 的用户