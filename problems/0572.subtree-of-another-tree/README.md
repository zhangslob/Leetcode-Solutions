# 另一个树的子树

[原题链接](https://leetcode-cn.com/problems/subtree-of-another-tree/)

## 题目

给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。

示例 1:
给定的树 s:
```text
     3
    / \
   4   5
  / \
 1   2
```

给定的树 t：
```text
   4 
  / \
 1   2
```

返回 true，因为 t 与 s 的一个子树拥有相同的结构和节点值。

示例 2:

给定的树 s：
```text
     3
    / \
   4   5
  / \
 1   2
    /
   0
```

给定的树 t：
```text
   4
  / \
 1   2
```

返回 false。

## 题解

这题肯定与二叉树的遍历有关，正好去学习下二叉树的遍历方法。

二叉树定义

```java
static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int x) {
        val = x;
    }
}
```

![a1rbGN](https://cdn.jsdelivr.net/gh/zhangslob/oss@master/uPic/a1rbGN.jpg)

递归先序遍历

```java
public static void recursionPreorderTraversal(TreeNode root) {
    if (root != null) {
        System.out.print(root.val + " ");
        recursionPreorderTraversal(root.left);
        recursionPreorderTraversal(root.right);
    }
}
```

递归中序遍历

```java
public static void recursionMiddleorderTraversal(TreeNode root) {
    if (root != null) {
        recursionMiddleorderTraversal(root.left);
        System.out.print(root.val + " ");
        recursionMiddleorderTraversal(root.right);
    }
}
```

递归后序遍历

```java
public static void recursionPostorderTraversal(TreeNode root) {
    if (root != null) {
        recursionPostorderTraversal(root.left);
        recursionPostorderTraversal(root.right);
        System.out.print(root.val + " ");
    }
}
```

递归方法比较简单，也好理解。还有非递归办法，利用栈去储存，这种方法以后再说。

## 代码

### Java

```java
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null || t == null) {
            return false;
        }
        return hasSubtree(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    public boolean hasSubtree(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }
        if (s.val == t.val) {
            return hasSubtree(s.left, t.left) && hasSubtree(s.right, t.right);
        }
        return false;
    }
}
```

- 执行用时 : 7 ms , 在所有 Java 提交中击败了 93.76% 的用户 
- 内存消耗 : 40.3 MB , 在所有 Java 提交中击败了 40.00% 的用户

https://juejin.im/post/5b8d64346fb9a01a1d4f99fa


> 重新定义简单
