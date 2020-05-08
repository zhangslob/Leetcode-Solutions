# 最大正方形

[原题链接](https://leetcode-cn.com/problems/maximal-square/)

## 题目

在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。

示例:
~~~~
输入: 
```text
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```

输出: 4

## 题解

二维数组`dp[i][j]`表示在`[i][j]`时最大的正方形，如何判断正方形？

1. `dp[0][0]` = 0
2. 如果`dp[i-1][j]`,`dp[i][j-1]`,`dp[i-1][j-1]` 中存在一个等于0，那么就等于`dp[i][j]`
3. 如果`dp[i-1][j]`,`dp[i][j-1]`,`dp[i-1][j-1]` 中全部等于1，那么就等于`dp[i-1][j-1] + 1`

这道题理解上有误区，`[['1']]`返回答案是1，因为1也是正方形，我擦。

`dp[i][j]`表示以第i行第j列为右下角所能构成的最大正方形边长, 则递推式为: 
`dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`

## 代码

### Java

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        int row = matrix.length;
        if (row < 1) {
            return 0;
        }
        int high = matrix[0].length;
        int[][] dp = new int[row + 1][high + 1];
        dp[0][0] = 0;
        int max = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= high; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                    }
                }
            }
        }
        return max * max;
    }
}
```

- 执行用时 : 6 ms , 在所有 Java 提交中击败了 87.05% 的用户 
- 内存消耗 : 42.3 MB , 在所有 Java 提交中击败了 68.75% 的用户

### Go

```go
func maximalSquare(matrix [][]byte) int {
	row := len(matrix)
	if row < 1 {
		return 0
	}
	high := len(matrix[0])
	dp := make([][]int, row+1)
	for i := range dp {
		dp[i] = make([]int, high+1)
	}
	max := 0
	for i := 1; i <= row; i++ {
		for j := 1; j <= high; j++ {
			if matrix[i-1][j-1] == '1' {
				dp[i][j] = 1 + min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1])
				if dp[i][j] > max {
					max = dp[i][j]
				}
			}
		}
	}
	return max * max
}

func min(a, b, c int) int {
	if a > b {
		if b > c {
			return c
		} else {
			return b
		}
	} else {
		if a > c {
			return c
		} else {
			return a
		}
	}
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 4.3 MB , 在所有 Go 提交中击败了 11.11% 的用户

### Python

```python
class Solution:
    def maximalSquare(self, matrix: List[List[str]]) -> int:
        m = len(matrix)
        if m == 0:
            return 0
        n = len(matrix[0])
        dp = [[0 for _ in range(n + 1)] for _ in range(m + 1)]
        num = 0
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if matrix[i - 1][j - 1] == '1':
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1
                    num = max(num, dp[i][j])
        return num * num
```

- 执行用时 : 100 ms , 在所有 Python3 提交中击败了 65.58% 的用户 
- 内存消耗 : 14.4 MB , 在所有 Python3 提交中击败了 14.29% 的用户
