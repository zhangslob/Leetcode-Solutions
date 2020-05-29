# 打家劫舍

[原题链接](https://leetcode-cn.com/problems/house-robber/)

## 题目

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。

示例 1:
```text
输入: [1,2,3,1]
输出: 4
解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     偷窃到的最高金额 = 1 + 3 = 4 。
```

示例 2:
```text
输入: [2,7,9,3,1]
输出: 12
解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     偷窃到的最高金额 = 2 + 9 + 1 = 12 。
```

## 题解

不能连续两个元素相加，有个坑：不能一位计算奇偶数就是答案，有这种情况`[8, 1, 2, 9, 1]`，正确解法是动态规划

dp 方程 dp[i] = max(dp[i-2] + nums[i], dp[i-1])


## 代码

### Java I

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 37.1 MB , 在所有 Java 提交中击败了 6.52% 的用户

### Java II

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int n1 = 0, n2 = 0;
        for (int num : nums) {
            int tmp = n1;
            n1 = Math.max(n1, n2 + num);
            n2 = tmp;
        }
        return n1;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 36.7 MB , 在所有 Java 提交中击败了 6.52% 的用户

### Go I

```go
func rob(nums []int) int {
	n := len(nums)
	if n == 0 {
		return 0
	}
	if n == 1 {
		return nums[0]
	}
	if n == 2 {
		return max(nums[0], nums[1])
	}
	dp := make([]int, n)
	dp[0] = nums[0]
	dp[1] = max(nums[0], nums[1])
	for i := 2; i < n; i++ {
		dp[i] = max(dp[i-2]+nums[i], dp[i-1])
	}
	return dp[n-1]
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2 MB , 在所有 Go 提交中击败了 33.33% 的用户


### Go II

```go
func rob(nums []int) int {
	preMax, curMax := 0, 0
	for _, v := range nums {
		temp := curMax
		if preMax+v > curMax {
			curMax = preMax + v
		}
		preMax = temp
	}
	return curMax
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2 MB , 在所有 Go 提交中击败了 33.33% 的用户

### Python I

```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return 0
        if n == 1:
            return nums[0]
        if n == 2:
            return max(nums[0], nums[1])
        dp = [0 for _ in range(n)]
        dp[0] = nums[0]
        dp[1] = max(nums[0], nums[1])
        for i in range(2, n):
            dp[i] = max(dp[i - 1], dp[i - 2] + nums[i])
        return dp[n - 1]
```

- 执行用时 : 36 ms , 在所有 Python3 提交中击败了 84.98% 的用户 
- 内存消耗 : 13.6 MB , 在所有 Python3 提交中击败了 9.09% 的用户

### Python II

其实我们并不需要数组来保存所有状态，可以优化空间为$O(N)$

```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return 0
        if n == 1:
            return nums[0]
        if n == 2:
            return max(nums[0], nums[1])
        n1, n2 = 0, 0
        for num in nums:
            n1, n2 = n2, max(n1 + num, n2)
        return n2
```

- 执行用时 : 32 ms , 在所有 Python3 提交中击败了 95.25% 的用户 
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 9.09% 的用户

