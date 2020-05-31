# 对称二叉树

[原题链接](https://leetcode-cn.com/problems/symmetric-tree/)

## 题目

给定一个二叉树，检查它是否是镜像对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
```text
    1
   / \
  2   2
 / \ / \
3  4 4  3
```

但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
```text
    1
   / \
  2   2
   \   \
   3    3
```

进阶：你可以运用递归和迭代两种方法解决这个问题吗？

## 题解

虽然是一道简单题，但是没做过，就真的想不出来。看了评论里的高赞，自己竟然写出来了

---

递归的难点在于：找到可以递归的点 为什么很多人觉得递归一看就会，一写就废。 或者说是自己写无法写出来，关键就是你对递归理解的深不深。

对于此题： 递归的点怎么找？从拿到题的第一时间开始，思路如下：
1. 怎么判断一棵树是不是对称二叉树？ 答案：如果所给根节点，为空，那么是对称。如果不为空的话，当他的左子树与右子树对称时，他对称
2. 那么怎么知道左子树与右子树对不对称呢？在这我直接叫为左树和右树 答案：如果左树的左孩子与右树的右孩子对称，左树的右孩子与右树的左孩子对称，那么这个左树和右树就对称。

仔细读这句话，是不是有点绕？怎么感觉有一个功能A我想实现，但我去实现A的时候又要用到A实现后的功能呢？

当你思考到这里的时候，递归点已经出现了： 递归点：我在尝试判断左树与右树对称的条件时，发现其跟两树的孩子的对称情况有关系。

想到这里，你不必有太多疑问，上手去按思路写代码，函数A（左树，右树）功能是返回是否对称

def 函数A（左树，右树）： 左树节点值等于右树节点值 且 函数A（左树的左子树，右树的右子树），函数A（左树的右子树，右树的左子树）均为真 才返回真

实现完毕。。。

写着写着。。。你就发现你写出来了。。。。。。 

---

那么迭代的算法怎么写呢，感觉这个和之前做的二叉树层序遍历很像，队列辅助，遍历这一层的节点，并把下一层所有节点入队。但是怎么判断对称呢，这是个问题。

看答案了，发现非常巧妙，初始化入队左右两个节点，每次从队列中去除两个节点进行判断。具体实现看代码

## 代码

### Java  递归

```java
public class Solution {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return helper(root.left, root.right);
    }

    public boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        return helper(left.left, right.right) && helper(left.right, right.left);
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 38.3 MB , 在所有 Java 提交中击败了 8.75% 的用户

### Java 迭代

```java
public class Solution {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            // 每次出队两个节点 node1 和 node2
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            if (node1 == null && node2 == null) {
                continue;
            }
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }
            queue.add(node1.left);
            queue.add(node2.right);
            queue.add(node1.right);
            queue.add(node2.left);
        }
        return true;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 37.93% 的用户 
- 内存消耗 : 39.5 MB , 在所有 Java 提交中击败了 5.00% 的用户

### Go 递归

```go
func isSymmetric(root *TreeNode) bool {
	if root == nil {
		return true
    }
	return helper(root.Left, root.Right)
}

func helper(left *TreeNode, right *TreeNode) bool {
	if left == nil && right == nil {
		return true
	}
	if left == nil || right == nil || left.Val != right.Val {
		return false
	}
	return helper(left.Left, right.Right) && helper(left.Right, right.Left)
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 73.35% 的用户 
- 内存消耗 : 2.9 MB , 在所有 Go 提交中击败了 57.14% 的用户

### Go 迭代

```go
func isSymmetric(root *TreeNode) bool {
	if root == nil {
		return true
	}
	stack := []*TreeNode{}
	stack = append(stack, root.Left)
	stack = append(stack, root.Right)
	for len(stack) > 0 {
		node1 := stack[0]
		stack = append(stack[:0], stack[1:]...)
		node2 := stack[0]
		stack = append(stack[:0], stack[1:]...)
		if node1 == nil && node2 == nil {
			continue
		}
		if node1 == nil || node2 == nil || node1.Val != node2.Val {
			return false
		}
		stack = append(stack, node1.Left)
		stack = append(stack, node2.Right)
		stack = append(stack, node1.Right)
		stack = append(stack, node2.Left)
	}
	return true
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 73.35% 的用户 
- 内存消耗 : 2.9 MB , 在所有 Go 提交中击败了 28.57% 的用户

### Python 递归

```python
class Solution:
    def isSymmetric(self, root: TreeNode) -> bool:
        def helper(left, right):
            if left is None and right is None:
                return True
            if left is None or right is None or left.val != right.val:
                return False
            return helper(left.left, right.right) and helper(left.right, right.left)

        if not root:
            return True
        return helper(root.left, root.right)
```

- 执行用时 : 36 ms , 在所有 Python3 提交中击败了 95.39% 的用户 
- 内存消耗 : 13.9 MB , 在所有 Python3 提交中击败了 6.06% 的用户

### Python 迭代

```python
class Solution:
    def isSymmetric(self, root: TreeNode) -> bool:
        if not root:
            return True
        stack = [root.left, root.right]
        while len(stack) > 0:
            node1, node2 = stack.pop(), stack.pop()
            if node1 is None and node2 is None:
                continue
            if node1 is None or node2 is None or node1.val != node2.val:
                return False
            stack.append(node1.left)
            stack.append(node2.right)
            stack.append(node1.right)
            stack.append(node2.left)
        return True
```

- 执行用时 : 48 ms , 在所有 Python3 提交中击败了 49.08% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 6.06% 的用户

