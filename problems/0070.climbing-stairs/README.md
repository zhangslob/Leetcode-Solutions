# 爬楼梯

[原题链接](https://leetcode-cn.com/problems/climbing-stairs/)

## 题目

假设你正在爬楼梯。需要 n 阶你才能到达楼顶。

每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

注意：给定 n 是一个正整数。

示例 1：
```text
输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。
1.  1 阶 + 1 阶
2.  2 阶
```

示例 2：
```text
输入： 3
输出： 3
解释： 有三种方法可以爬到楼顶。
1.  1 阶 + 1 阶 + 1 阶
2.  1 阶 + 2 阶
3.  2 阶 + 1 阶
```

## 题解

我没思路，想不出来，虽然我也明白这种题目是要用动态规划去做，而且看了下提交记录，我在上个月前曾经做过。

如果用 f(n) 表示在第n阶有多少种方法，只有两分钟可能
1. 在 n-2 阶时走两步
2. 在 n-1 阶时走一步

所以状态转移方程就是： f(n) = f(n-1) + f(n-2)

## 代码

### Java 动态规划

```text
class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n + 2];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n + 1];
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 36.3 MB , 在所有 Java 提交中击败了 5.66% 的用户

我看了答案才知道，这道题本质上是斐波那契数列，ε=(´ο｀*)))唉

### Java 空间优化版

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int i = 1, j = 2;
        for (int m = 2; m < n; m++) {
            int tmp = i + j;
            i = j;
            j = tmp;
        }
        return j;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 36.7 MB , 在所有 Java 提交中击败了 5.66% 的用户

