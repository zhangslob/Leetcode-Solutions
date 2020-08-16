# 零钱兑换

[原题链接](https://leetcode-cn.com/problems/coin-change/)

## 题目

给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。

示例 1:
```text
输入: coins = [1, 2, 5], amount = 11
输出: 3 
解释: 11 = 5 + 5 + 1
```

示例 2:
```text
输入: coins = [2], amount = 3
输出: -1
```

说明:
- 你可以认为每种硬币的数量是无限的。

# 题解

1. **确定 base case**，这个很简单，显然目标金额 `amount` 为 `0` 时算法返回 `0`，因为不需要任何硬币就已经凑出目标金额了。
2. **确定「状态」**，也就是原问题和子问题中会变化的变量。由于硬币数量无限，硬币的面额也是题目给定的，只有目标金额会不断地向 `base case` 靠近，所以唯一的「状态」就是目标金额 `amount`。
3. **确定「选择」**，也就是导致「状态」产生变化的行为。目标金额为什么变化呢，因为你在选择硬币，你每选择一枚硬币，就相当于减少了目标金额。所以说所有硬币的面值，就是你的「选择」。
4. **明确 dp 函数/数组的定义**。我们这里讲的是自顶向下的解法，所以会有一个递归的 `dp` 函数，一般来说函数的参数就是状态转移中会变化的量，也就是上面说到的「状态」；函数的返回值就是题目要求我们计算的量。就本题来说，状态只有一个，即「目标金额」，题目要求我们计算凑出目标金额所需的最少硬币数量。所以我们可以这样定义 dp 函数：

dp(n) 的定义：输入一个目标金额 n，返回凑出目标金额 n 的最少硬币数量。

搞清楚上面这几个关键点，解法的伪码就可以写出来了：

```python
# 伪码框架
def coinChange(coins: List[int], amount: int):

    # 定义：要凑出金额 n，至少要 dp(n) 个硬币
    def dp(n):
        # 做选择，选择需要硬币最少的那个结果
        for coin in coins:
            res = min(res, 1 + dp(n - coin))
        return res

    # 题目要求的最终结果是 dp(amount)
    return dp(amount)
```

状态转义方程：

![](https://gblobscdn.gitbook.com/assets%2F-LrtQOWSnDdXhp3kYN4k%2Fsync%2F5381a5e30482682c1c6f111e882991113b8661f7.png?alt=media)

这里有个地方我不是很理解，就是 `i - coin` 

举个例子，coins = [1, 2, 5], amount = 11

如果 i = 11

1. `dp[11] = min(dp[11], dp[11 - 1] + 1)` = `dp[11] = min(dp[11], dp[10] + 1)`
1. `dp[11] = min(dp[11], dp[11 - 2] + 1)` = `dp[11] = min(dp[11], dp[9] + 1)`
1. `dp[11] = min(dp[11], dp[11 - 5] + 1)` = `dp[11] = min(dp[11], dp[6] + 1)`

很显然，dp[11] 不是最小的，所以我们需要求dp[10]、dp[9]、dp[6]的值，可以快速算出分别是：2、4、2，最终答案是3。

再来形象一点的理解，蛋糕有11块，每次只能拿走 [1, 2, 5] 块，最少几次能拿完。每次拿走的选择有很多，但是也有限制，就是剩下的块数必须大于本次拿走的快速


# 代码

### Java

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        // 当目标金额为 i 时，至少需要 dp[i] 枚硬币凑出。
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1; i < amount + 1; i++) {
            dp[i] = amount + 1;
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
```

- 执行用时： 20 ms , 在所有 Java 提交中击败了 30.06% 的用户
- 内存消耗： 39.2 MB , 在所有 Java 提交中击败了 77.92% 的用户
