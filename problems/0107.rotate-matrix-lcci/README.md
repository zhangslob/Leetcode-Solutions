# 旋转矩阵

[原题链接](https://leetcode-cn.com/problems/rotate-matrix-lcci/)

## 题目

给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。

不占用额外内存空间能否做到？

示例 1:

给定 matrix = 
```text
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
]
```

原地旋转输入矩阵，使其变为:
```text
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
```

示例 2:

给定 matrix =
```text
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
]
```

原地旋转输入矩阵，使其变为:
```text
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
```

## 题解

拿到题目，想了会，没思路。我发现了一些规律，但是不知道怎么用代码去实现。

第一行
- `[0][0]` ==>`[0][N]`
- `[0][1]` ==> `[1][N]`
- `[0][2]` ==> `[2][N]`

第二行
- `[1][0]` ==> `[0][N-1]`
- `[1][1]` ==> `[1][N-1]`
- `[1][2]` ==> `[2][N-1]`

第三行
- `[2][0]` ==> `[0][N-2]`
- `[2][1]` ==> `[1][N-2]`
- `[2][2]` ==> `[2][N-2]`

好像发现规律了，试试如何用代码实现。题目要求不占用额外内存空间，应该就是： `l[a], l[b] = l[b], l[a]` 这种了

尝试了，还是失败告终，看题解：用翻转代替旋转

规律:
> 对于矩阵中第 ii 行的第 jj 个元素，在旋转后，它出现在倒数第 ii 列的第 jj 个位置。
 
如下图，先由对角线 `[1, 5, 9]` 为轴进行翻转：

```text
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
]
```

于是数组变成了:
```text
[1,4,7]
[2,5,8]
[3,6,9]
```

再对每一行以中点进行翻转，就得到了
```text
[7,4,1]
[8,5,2]
[9,6,3]
```

两次翻转，完成目标，时间复杂度O(n^2)，空间复杂度O(1)

## 代码

### JAVA

```java
package problem;

import java.util.Arrays;

public class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        new Solution().rotate(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 39.6 MB , 在所有 Java 提交中击败了 100.00% 的用户

> Amazing

### Python

```python
class Solution:
    def rotate(self, matrix) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        n = len(matrix[0])
        for i in range(n):
            for j in range(i, n, 1):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
        for i in range(n):
            matrix[i].reverse()


if __name__ == '__main__':
    matrix = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
    Solution().rotate(matrix)
    print(matrix)
```
- 执行用时 : 36 ms , 在所有 Python3 提交中击败了 80.33% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 100.00% 的用户

看到有这种方法：
```python
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        matrix[::] = zip(*matrix[::-1])
```

### Go

```go
package main

import "fmt"

func rotate(matrix [][]int) {
	n := len(matrix)
	for i := 0; i < n; i++ {
		for j := i; j < n; j++ {
			matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
		}
		for j := 0; j < n/2; j++ {
			matrix[i][j], matrix[i][n-j-1] = matrix[i][n-j-1], matrix[i][j]
		}
	}
}

func main() {
	matrix := [][]int{
		{1, 2, 3},
		{4, 5, 6},
		{7, 8, 9},
	}
	rotate(matrix)
	fmt.Println(matrix)
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.2 MB , 在所有 Go 提交中击败了 100.00% 的用户
