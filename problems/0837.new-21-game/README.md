# 新21点

[原题链接](https://leetcode-cn.com/problems/new-21-game/)

## 题目

爱丽丝参与一个大致基于纸牌游戏 “21点” 规则的游戏，描述如下：

爱丽丝以 0 分开始，并在她的得分少于 K 分时抽取数字。 

抽取时，她从 [1, W] 的范围中随机获得一个整数作为分数进行累计，其中 W 是整数。 每次抽取都是独立的，其结果具有相同的概率。

当爱丽丝获得不少于 K 分时，她就停止抽取数字。 爱丽丝的分数不超过 N 的概率是多少？

示例 1：
```text
输入：N = 10, K = 1, W = 10
输出：1.00000
说明：爱丽丝得到一张卡，然后停止。
```

示例 2：
```text
输入：N = 6, K = 1, W = 10
输出：0.60000
说明：爱丽丝得到一张卡，然后停止。在 W = 10 的 6 种可能下，她的得分不超过 N = 6 分。
```

示例 3：
```text
输入：N = 21, K = 17, W = 10
输出：0.73278
```

提示：
1. 0 <= K <= N <= 10000
2. 1 <= W <= 10000
3. 如果答案与正确答案的误差不超过 $10^-5$，则该答案将被视为正确答案通过。
4. 此问题的判断限制时间已经减少。

## 题解

直接去网上找题解

---

动态规划题目。

dp[i]表示当目标为i时的概率，由于最后一次的选择的排数的范围为[i-w,i-1]，这时可选的牌有[1,w]，所以最后的点数和是[i,w+i-1]，对应我们是要求大于等于K的数，所以最后的累加范围是[K,K+W-1]，所以我们的结果便是返回dp[i]（K<=i<=K+W-1）的和。

最后还有一个小技巧，由于最后的范围是[K,K+W-1]：

如果N>=K+W（意味着在最后一次累加和[K-W,K-1]选择时，无论选择[1,W]中的任何数，最后的累加和一定在[K,N]范围内，在这种条件下[K,K+W-1]是[K,N]的子集，所以直接返回1；

如果N<K+W，那么我们最后的答案就只用累加[K,N]的dp值即可，所以申请长度为N+1的数组即可。

## 代码

### Java

```java
class Solution {
    public double new21Game(int N, int K, int W) {
        if (K == 0 || N >= K + W) {
            return 1.0;
        }
        double[] dp = new double[N + 1];
        double res = 0.0, sum = 0.0;
        for (int i = 1; i <= N; i++) {
            if (i <= W) {
                dp[i] = sum / W + 1.0 / W;
            } else {
                dp[i] = sum / W;
            }
            if (i < K) {
                sum += dp[i];
            }
            if (i > W) {
                sum -= dp[i - W];
            }
            if (i >= K) {
                res += dp[i];
            }
        }
        return res;
    }
}
```

- 执行用时 : 6 ms , 在所有 Java 提交中击败了 20.75% 的用户 
- 内存消耗 : 38.9 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go

```go
func new21Game(N int, K int, W int) float64 {
	if K == 0 || N >= K+W {
		return 1
	}
	dp := make([]float64, N+1)
	res, sum := 0.0, 0.0
	for i := 1; i <= N; i++ {
		if i <= W {
			dp[i] = sum/float64(W) + 1.0/float64(W)
		} else {
			dp[i] = sum / float64(W)
		}
		if i < K {
			sum += dp[i]
		}
		if i > W {
			sum -= dp[i-W]
		}
		if i >= K {
			res += dp[i]
		}
	}
	return res
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 71.43% 的用户 
- 内存消耗 : 4.5 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def new21Game(self, N: int, K: int, W: int) -> float:
        if K == 0 or N >= K + W:
            return 1
        dp = [0.0 for _ in range(N + 1)]
        res, cnt = 0.0, 0.0
        for i in range(1, N + 1):
            dp[i] = cnt / W + 1.0 / W if i <= W else cnt / W
            if i < K:
                cnt += dp[i]
            if i > W:
                cnt -= dp[i - W]
            if i >= K:
                res += dp[i]
        return res
```

- 执行用时 : 168 ms , 在所有 Python3 提交中击败了 16.91% 的用户 
- ``内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 100.00% 的用户
