# 斐波那契数

[原题链接](https://leetcode-cn.com/problems/fibonacci-number/)

## 题目

斐波那契数，通常用 F(n) 表示，形成的序列称为斐波那契数列。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：

- F(0) = 0,   F(1) = 1
- F(N) = F(N - 1) + F(N - 2), 其中 N > 1.

给定 N，计算 F(N)。


示例 1：
```text
输入：2
输出：1
解释：F(2) = F(1) + F(0) = 1 + 0 = 1.
```

示例 2：
```text
输入：3
输出：2
解释：F(3) = F(2) + F(1) = 1 + 1 = 2.
```

示例 3：
```text
输入：4
输出：3
解释：F(4) = F(3) + F(2) = 2 + 1 = 3.
```


提示：
- 0 ≤ N ≤ 30

# 题解

刚开始拿到这题时，我竟然想不出方法，首先递归肯定是不会去考虑的，然后想到了动态规划，写完了，发现可以在动态规划的基础上进行优化，即不需要数组，只需要常量即可，这样空间会有优化。

## 代码

### Java 方法一

```java
class Solution {
    public int fib(int N) {
        if (N == 0) {
            return 0;
        }
        int[] dp = new int[N + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[N];
    }
}
```

- 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗： 36.6 MB , 在所有 Java 提交中击败了 16.64% 的用户

### Java 方法一

可以看到上面一种方法用了数组去存储了所有值，但是我们真的需要吗？

答案是不，数组可以改为常量。一个还是两个需要自己琢磨。

重要理论：**所有能使用动态规划数组去解题的，可以考虑用常量去优化空间复杂度**

```java
class Solution {
    public int fib(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1 || N == 2) {
            return 1;
        }
        int res = 0;
        int pre = 1, last = 1;
        for (int i = 3; i <= N; i++) {
            res = pre + last;
            pre = last;
            last = res;
        }
        return last;
    }
}
```

- 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗： 36.6 MB , 在所有 Java 提交中击败了 12.63% 的用户




