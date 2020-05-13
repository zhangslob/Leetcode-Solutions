# 二叉树的层序遍历

[原题链接](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)

## 题目

给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。

示例：
二叉树：[3,9,20,null,null,15,7],
```text
    3
   / \
  9  20
    /  \
   15   7
```

返回其层次遍历结果：
```text
[
  [3],
  [9,20],
  [15,7]
]
```

## 题解

这两天正好在复习二叉树专题，这题很简单，用队列即可实现。

## 代码

### Java

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode cur;
        int size;

        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                cur = queue.poll();
                tmp.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            ans.add(tmp);
        }
        return ans;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 91.36% 的用户 
- 内存消耗 : 40.1 MB , 在所有 Java 提交中击败了 5.71% 的用户

### Go
```go

func levelOrder(root *TreeNode) [][]int {
	var res [][]int
	if root == nil {
		return res
	}
	queue := []*TreeNode{root}
	for len(queue) != 0 {
		size := len(queue)
		var tmp []int
		for i := 0; i < size; i++ {
			cur := queue[i]
			tmp = append(tmp, cur.Val)
			if cur.Left != nil {
				queue = append(queue, cur.Left)
			}
			if cur.Right != nil {
				queue = append(queue, cur.Right)
			}
		}
		res = append(res, tmp)
		queue = queue[size:]
	}
	return res
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.8 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python

class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        ans = []
        if root is None:
            return ans
        queue = [root]
        while len(queue) > 0:
            size = len(queue)
            tmp = []
            for i in range(size):
                cur = queue[i]
                tmp.append(cur.val)
                if cur.left is not None:
                    queue.append(cur.left)
                if cur.right is not None:
                    queue.append(cur.right)
            ans.append(tmp)
            queue = queue[size:]
        return ans
```

- 执行用时 : 40 ms , 在所有 Python3 提交中击败了 75.19% 的用户 
- 内存消耗 : 14 MB , 在所有 Python3 提交中击败了 7.14% 的用户

