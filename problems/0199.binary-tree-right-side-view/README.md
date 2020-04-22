# 二叉树的右视图

[原题链接](https://leetcode-cn.com/problems/binary-tree-right-side-view/)

## 题目

给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。

示例:

输入: [1,2,3,null,5,null,4]

输出: [1, 3, 4]

解释:
```text
   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
```

## 题解

好好记录下我这个菜鸡是怎么一步步写（cao）出来的。

首先拿到题目，我就想到了二叉树的遍历：
```java
void traverse(TreeNode root) {
    if (root == null) {
        return;
    }
    traverse(root.left);
    traverse(root.right);
}
```

然后我就想，这不是很简单吗，我只需要通过BFS，每次拿右边的节点即可，代码如下：

```java
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            ans.add(node.val);
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return ans;
    }
```

我还暗自得意，哎，这题真简单，然而很苦逼的是，我并没有考虑左变存在，右边不存在的情况，如图：

```text
       1            <---
     /   \
    2     3         <---
  /      
 4                  <---
```

这种答案是[1,3,5]，我计算的是[1,3]。那我再现，当如果左边存在，右边不存在时入队，然后我就开心的写出来了：

```java
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            ans.add(node.val);
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (node.right == null && node.left != null) {
                queue.offer(node.left);
            }
        }
        return ans;
    }
```

但是我没有考虑下面这种情况：

```text
   1            <---
 /   \
2     3         <---
 \     
  5            <---
```

哎，我真的菜。看了大神的解法，才知道我离答案就差一步。遍历到每一层时，把所有的节点全部入队，取最后一个。特别注意的是，怎么保证最后一个就是右边的呢，所以需要在入队的时候保证，先入左边的，再入右边的。

## 代码

### Java

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                // 先入左边的，再入右边的
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                // 如果到了最后一位
                if (i == size - 1) {
                    ans.add(node.val);
                }
            }
        }
        return ans;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 97.36% 的用户 
- 内存消耗 : 38.2 MB , 在所有 Java 提交中击败了 5.00% 的用户

### Python

```python
class Solution:
    def rightSideView(self, root: TreeNode) -> List[int]:
        ans = []
        if root is None:
            return ans
        queue = [root]
        while len(queue) != 0:
            for i in range(len(queue)):
                node = queue.pop(0)
                if i == 0:
                    ans.append(node.val)
                if node.right is not None:
                    queue.append(node.right)
                if node.left is not None:
                    queue.append(node.left)
        return ans
```

- 执行用时 : 32 ms , 在所有 Python3 提交中击败了 91.93% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 14.29% 的用户

也可以把入队顺序改下，这样每次就从第一个取

### Go

```go
func rightSideView(root *TreeNode) []int {
	var ans []int
	if root == nil {
		return ans
	}
	queue := []*TreeNode{root}
	for len(queue) != 0 {
		sz := len(queue)
		for i := 0; i < sz; i++ {
			node := queue[0]
			queue = queue[1:]
			if i == 0 {
				ans = append(ans, node.Val)
			}
			if node.Right != nil {
				queue = append(queue, node.Right)
			}
			if node.Left != nil {
				queue = append(queue, node.Left)
			}
		}
	}
	return ans
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.3 MB , 在所有 Go 提交中击败了 100.00% 的用户
