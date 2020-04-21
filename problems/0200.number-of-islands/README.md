# 岛屿数量

[原题链接](https://leetcode-cn.com/problems/number-of-islands/)

## 题目

给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。

岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

此外，你可以假设该网格的四条边均被水包围。

示例 1:

输入:
```text
11110
11010
11000
00000
```

输出: `1`

示例 2:

输入:
```text
11000
11000
00100
00011
```

输出: `3`

解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。



## 题解

牢记二叉树的遍历方法：

```java
void traverse(TreeNode root) {
    // 判断 bad case
    if (root == null) {
        return;
    }
    // 访问两个相邻结点：左子结点、右子结点
    traverse(root.left);
    traverse(root.right);
}
```

在岛屿数量这里，左右节点就是上下左右四个节点，bad case就是越界问题。

要明白，一个点和一片点是一样的

为了防止重复，可以把已经遍历过的点置为——0

## 代码

### Java

```java
public class Solution {
    public int numIslands(char[][] grid) {
        // empty?
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }
                ans++;
                dfs(grid, i, j);
            }
        }
        return ans;
    }

    private void dfs(char[][] grid, int i, int j) {
        // 越界
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
    }

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'},
        };
        char[][] grid2 = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'},
        };
        System.out.println(new Solution().numIslands(grid2));
    }
}
```

- 执行用时 : 2 ms , 在所有 Java 提交中击败了 96.16% 的用户 
- 内存消耗 : 41.9 MB , 在所有 Java 提交中击败了 6.25% 的用户

### Python

```python
class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        def dfs(grid, i, j):
            if i < 0 or j < 0 or i >= len(grid) or j >= len(grid[0]):
                return
            if grid[i][j] == '0':
                return
            grid[i][j] = '0'
            dfs(grid, i, j - 1)
            dfs(grid, i, j + 1)
            dfs(grid, i + 1, j)
            dfs(grid, i - 1, j)

        ans = 0
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == '0':
                    continue
                ans += 1
                dfs(grid, i, j)
        return ans
```

- 执行用时 : 80 ms , 在所有 Python3 提交中击败了 79.19% 的用户 
- 内存消耗 : 14.3 MB , 在所有 Python3 提交中击败了 6.67% 的用户



### Go

```go
func numIslands(grid [][]byte) int {
	ans := 0
	if len(grid) == 0 {
		return ans
	}
	m, n := len(grid), len(grid[0])
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
			if grid[i][j] == '0' {
				continue
			}
			ans++
			dfs(grid, i, j)
		}
	}
	return ans
}

func dfs(grid [][]byte, i int, j int) {
	if i < 0 || j < 0 || i >= len(grid) || j >= len(grid[0]) || grid[i][j] == '0' {
		return
	}
	grid[i][j] = '0'
	dfs(grid, i, j+1)
	dfs(grid, i, j-1)
	dfs(grid, i+1, j)
	dfs(grid, i-1, j)
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.9 MB , 在所有 Go 提交中击败了 100.00% 的用户





看到一个很好的教程：[岛屿类问题的通用解法、DFS 遍历框架](https://leetcode-cn.com/problems/number-of-islands/solution/dao-yu-lei-wen-ti-de-tong-yong-jie-fa-dfs-bian-li-/)

