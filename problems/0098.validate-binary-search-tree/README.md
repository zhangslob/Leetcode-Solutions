# 验证二叉搜索树

[原题链接](https://leetcode-cn.com/problems/validate-binary-search-tree/)

## 题目

给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：
1. 节点的左子树只包含小于当前节点的数。
2. 节点的右子树只包含大于当前节点的数。
3. 所有左子树和右子树自身必须也是二叉搜索树。

示例 1:
```text
输入:
    2
   / \
  1   3
输出: true
```

示例 2:
```text
输入:
    5
   / \
  1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
```

## 题解

### 解法一

可以利用它本身的性质来做，即 左 < 根 < 右，初始化时带入系统最大值和最小值，在递归过程中换成它们自己的节点值。
用 long 代替 int 就是为了包括 int 的边界条件。

### 解法二

中序遍历+数组

### 解法三

栈+中序遍历

## 代码

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean valid(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        return valid(root.left, min, root.val) && valid(root.right, root.val, max);
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
内存消耗 : 39.7 MB , 在所有 Java 提交中击败了 5.80% 的用户

### Go

```go
func isValidBST(root *TreeNode) bool {
	var list []int
	helper(root, &list)
	for i := 1; i < len(list); i++ {
		if list[i] <= list[i-1] {
			return false
		}
	}
	return true
}

func helper(root *TreeNode, list *[]int) {
	if root == nil {
		return 
	}
	helper(root.Left, list)
	*list = append(*list, root.Val)
	helper(root.Right, list)
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 98.53% 的用户 
- 内存消耗 : 5.9 MB , 在所有 Go 提交中击败了 40.00% 的用户

### Python

```python
class Solution:
    def isValidBST(self, root: TreeNode) -> bool:
        if not root:
            return True
        pre = None
        stack = list()
        while root or stack:
            if root:
                stack.append(root)
                root = root.left
            else:
                root = stack.pop()
                if pre and pre.val >= root.val:
                    return False
                pre = root
                root = root.right
        return True
```

- 执行用时 : 52 ms , 在所有 Python3 提交中击败了 80.27% 的用户 
- 内存消耗 : 16.2 MB , 在所有 Python3 提交中击败了 9.52% 的用户
