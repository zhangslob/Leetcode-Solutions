# 乘积最大子数组

[原题链接](https://leetcode-cn.com/problems/maximum-product-subarray/)

## 题目

给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

示例 1:

输入: [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。

示例 2:

输入: [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。

## 题解

本题是 [最大子序列](https://leetcode-cn.com/problems/maximum-subarray) 的加强版本，之前一题是求最大区间和，而本题是求最大区间乘积。

两题最本质的区别是在对负数的处理上，求和时，加上一个负数，总和一定变小，而乘上一个负数，结果可能变大也可能变小（负数乘以负数结果会变大）。

因此我们需要新建2个变量，一个代表区间内最大乘积max（可能为负），另一个代表区间内最小乘积min（可能为负），首元素区间的最大乘积与最小乘积均为该数字本身。我们从数组第二个数字开始循环，当前数字与前区间能形成的最大乘积，一定在下面三个元素中产生：

1. 当前数字num自身
2. 当前数字num * max
3. 当前数字num * min

用三个数的最大值更新max，最小值更新min，用max更新全局最大区间乘积，然后继续向后循环，找到一个全局最大区间乘积即可。

## 代码

### Java

```java
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) {
        	return 0;
        }
        int max = nums[0], min = nums[0];
        int ans = max;
        for (int i = 1; i < n; i++) {
        	if (nums[i] < 0) {
        		int tmp = max;
        		max = min;
        		min = tmp;
        	}
        	max = Math.max(max * nums[i], nums[i]);
        	min = Math.min(min * nums[i], nums[i]);
        	ans = Math.max(ans, max);
        }
        return ans;
    }
}
```

- 执行用时 : 2 ms , 在所有 Java 提交中击败了 92.56% 的用户 
- 内存消耗 : 39.9 MB , 在所有 Java 提交中击败了 6.67% 的用户

### Go

```go
func maxProduct(nums []int) int {
	n := len(nums)
	if n == 0 {
		return 0
	}
    ans, tmp_max, tmp_min := nums[0], nums[0], nums[0]
	for i := 1; i < n; i++ {
		if nums[i] < 0 {
			tmp_max, tmp_min = tmp_min, tmp_max
		}
		tmp_max = max(tmp_max * nums[i], nums[i])
		tmp_min = min(tmp_min * nums[i], nums[i])
		ans = max(ans, tmp_max)
	}
	return ans
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func min(a, b int) int {
	if a > b {
		return b
	}
	return a
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 88.22% 的用户 
- 内存消耗 : 2.7 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def maxProduct(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return 0
        ans, tmp_max, tmp_min = nums[0], nums[0], nums[0]
        for i in range(1, n):
            # 如果为负数、调换位置
            if nums[i] < 0:
                tmp_max, tmp_min = tmp_min, tmp_max
            tmp_max = max(tmp_max * nums[i], nums[i])
            tmp_min = min(tmp_min * nums[i], nums[i])
            ans = max(ans, tmp_max)
        return ans
```

- 执行用时 : 48 ms , 在所有 Python3 提交中击败了 83.97% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 25.00% 的用户

