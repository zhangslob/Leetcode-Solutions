# 编辑举例

[原题链接](https://leetcode-cn.com/problems/edit-distance/)

## 题目

给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：
1. 插入一个字符
2. 删除一个字符
3. 替换一个字符

示例 1：

输入：word1 = "horse", word2 = "ros"
输出：3

解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')

示例 2：

输入：word1 = "intention", word2 = "execution"
输出：5

解释：
intention -> inention (删除 't')
inention -> enention (将 'i' 替换为 'e')
enention -> exention (将 'n' 替换为 'x')
exention -> exection (将 'n' 替换为 'c')
exection -> execution (插入 'u')

## 题解

刚开始拿到题目，我是懵逼的，没有想法，后来我想到一种方法，分为两步：先删减、后排序。想法是先操作 word1，经过一系列添加删除，转换为 word2，然后再排序。可是实际上没法操作。

我也想过使用动态规划，可就是想不出状态转移方程，直到看到了答案。

> 其实我陷入了误区，没有把题目等价的能力。因为操作 word1 和操作 word2 的步骤是一样的，word1 添加元素 = word2 删除元素。

状态定义：
`dp[i][j]`表示word1的前i个字母转换成word2的前j个字母所使用的最少操作。

状态转移：
- i指向word1，j指向word2
- 若当前字母相同，则`dp[i][j] = dp[i - 1][j - 1]`;
- 否则取增删替三个操作的最小值 + 1， 即:
    `dp[i][j] = min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1`

## 代码

### JAVA

```java
package problem;

public class Solution {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[len1][len2];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minDistance("", "a"));
    }
}
```

- 执行用时 : 6 ms , 在所有 Java 提交中击败了 92.48% 的用户 
- 内存消耗 : 39.3 MB , 在所有 Java 提交中击败了 5.06% 的用户

### Python

```python
class Solution:
    def minDistance(self, word1: str, word2: str) -> int:
        n1, n2 = len(word1), len(word2)
        dp = [[0] * (n2 + 1) for _ in range(n1 + 1)]
        dp[0][0] = 0
        for i in range(1, n1 + 1):
            dp[i][0] = i
        for j in range(1, n2 + 1):
            dp[0][j] = j
        for i in range(1, n1 + 1):
            for j in range(1, n2 + 1):
                if word1[i - 1] == word2[j - 1]:
                    dp[i][j] = dp[i - 1][j - 1]
                else:
                    dp[i][j] = min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1
        return dp[n1][n2]


if __name__ == '__main__':
    print(Solution().minDistance("intention", "execution"))
```

- 执行用时 : 196 ms , 在所有 Python3 提交中击败了 68.32% 的用户
- 内存消耗 : 17.3 MB , 在所有 Python3 提交中击败了 12.88% 的用户

### go

```go
package main

import "fmt"

func minDistance(word1 string, word2 string) int {
	l1, l2 := len(word1), len(word2)
	dp := make([][]int, l1+1)
	for i := 0; i <= l1; i++ {
		dp[i] = make([]int, l2+1)
		dp[i][0] = i
	}
	for j := 0; j <= l2; j++ {
		dp[0][j] = j
	}
	dp[0][0] = 0
	for i := 1; i <= l1; i++ {
		for j := 1; j <= l2; j++ {
			if word1[i-1] == word2[j-1] {
				dp[i][j] = dp[i-1][j-1]
			} else {
				tmp := dp[i-1][j]
				if dp[i][j-1] < dp[i-1][j] {
					tmp = dp[i][j-1]
				}
				if tmp > dp[i-1][j-1] {
					tmp = dp[i-1][j-1]
				}
				dp[i][j] = tmp + 1
			}
		}
	}
	return dp[l1][l2]
}

func main() {
	fmt.Println(minDistance("", "a"))
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 84.65% 的用户
- 内存消耗 : 5.7 MB , 在所有 Go 提交中击败了 50.00% 的用户

## 进阶

使用一位数组优化空间

```python
class Solution(object):
    def minDistance(self, word1, word2):
        """
        :type word1: str
        :type word2: str
        :rtype: int
        """
        n1, n2 = len(word1), len(word2)
        dp = [0] * (n2 + 1) #保留一行
        dp[0] = 0
        for j in range(1, n2 + 1):
            dp[j] = j
        for i in range(1, n1 + 1):
            old_dp_j = dp[0]
            dp[0] = i
            for j in range(1, n2 + 1):
                old_dp_j_1, old_dp_j = old_dp_j, dp[j]
                if word1[i - 1] == word2[j - 1]:
                    dp[j] = old_dp_j_1
                else:
                    dp[j] = min(dp[j], dp[j - 1], old_dp_j_1) + 1
        return dp[n2]
```

