# 最长公共子序列

[原题链接](https://leetcode-cn.com/problems/longest-common-subsequence/)

## 题目

给定两个字符串 `text1` 和 `text2`，返回这两个字符串的最长公共子序列。

一个字符串的 `子序列` 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。

若这两个字符串没有公共子序列，则返回 0。

示例 1:
```text
输入：text1 = "abcde", text2 = "ace" 
输出：3  
解释：最长公共子序列是 "ace"，它的长度为 3。
```

示例 2:
```text
输入：text1 = "abc", text2 = "abc"
输出：3
解释：最长公共子序列是 "abc"，它的长度为 3。
```

示例 3:
```text
输入：text1 = "abc", text2 = "def"
输出：0
解释：两个字符串没有公共子序列，返回 0。
```

提示:
```text
1 <= text1.length <= 1000
1 <= text2.length <= 1000
输入的字符串只含有小写英文字符。
```

## 题解

### 思路1

递归思路。双指针，从后往前遍历，如果当前值相等，说明有一个相同的，两个指针同时-1，value+1；如果不相等，找大的那个

代码：
```python
class Solution:
    def longestCommonSubsequence(self, text1: str, text2: str) -> int:
        def back(i, j):
            if i < 0 or j < 0:
                return 0
            if text1[i] == text2[j]:
                return back(i - 1, j - 1) + 1
            else:
                return max(back(i - 1, j), back(i, j - 1))
        return back(len(text1) - 1, len(text2) - 1)
```

输入：`"ylqpejqbalahwr"，"yrkzavgdmdgtqpg"`，会超时，显然递归不行。

### 思路2

增加二维数组维护状态，避免重复计算。代码详见下方

## 代码

```java
package problem;

import java.util.Arrays;

public class Solution {

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().longestCommonSubsequence("abcde", "ace"));
    }
}
```

- 执行用时 : 13 ms , 在所有 Java 提交中击败了 62.11% 的用户
- 内存消耗 : 43.5 MB , 在所有 Java 提交中击败了 5.66% 的用户

### Python

```python
class Solution:
    def longestCommonSubsequence(self, text1, text2):
        m, n = len(text1), len(text2)
        dp = [[0] * (n + 1) for _ in range(m + 1)]
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if text1[i - 1] == text2[j - 1]:
                    dp[i][j] = dp[i - 1][j - 1] + 1
                else:
                    dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
        return dp[m][n]


if __name__ == '__main__':
    print(Solution().longestCommonSubsequence("abcde", "ace"))
```

- 执行用时 : 576 ms , 在所有 Python3 提交中击败了 36.38% 的用户 
- 内存消耗 : 21.4 MB , 在所有 Python3 提交中击败了 19.15% 的用户

### Go

```go
package main

import "fmt"

func longestCommonSubsequence(text1 string, text2 string) int {
	m, n := len(text1), len(text2)
	dp := make([][]int, m+1)
	for v := range dp {
		dp[v] = make([]int, n+1)
	}
	for i := 1; i < m+1; i++ {
		for j := 1; j < n+1; j++ {
			if text1[i-1] == text2[j-1] {
				dp[i][j] = dp[i-1][j-1] + 1
			} else {
				dp[i][j] = max(dp[i][j-1], dp[i-1][j])
			}
		}
	}
	return dp[m][n]
}

func max(i, j int) int {
	if i > j {
		return i
	}
	return j
}

func main() {
	fmt.Println(longestCommonSubsequence("abcde", "ace"))
}
```

- 执行用时 : 8 ms , 在所有 Go 提交中击败了 41.30% 的用户 
- 内存消耗 : 10.4 MB , 在所有 Go 提交中击败了 57.14% 的用户

## 改进

使用一维数组改进

```go
func longestCommonSubsequence(text1 string, text2 string) int {
	m, n := len(text1), len(text2)
	dp := make([]int, n+1)
	for i := 1; i <= m; i++ {
		last := 0
		for j := 1; j <= n; j++ {
			tmp := dp[j]
			if text1[i-1] == text2[j-1] {
				dp[j] = last + 1
			} else {
				dp[j] = max(tmp, dp[j-1])
			}
			last = tmp
		}
	}
	return dp[n]
}

func max(i, j int) int {
	if i > j {
		return i
	}
	return j
}
```

增加临时变量去储存，一般想不到

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.1 MB , 在所有 Go 提交中击败了 100.00% 的用户

> Amazing  双百
