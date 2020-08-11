# 寻找数组的中心索引

[原题链接](https://leetcode-cn.com/problems/find-pivot-index/)

## 题目

给定一个整数类型的数组 nums，请编写一个能够返回数组 “中心索引” 的方法。

我们是这样定义数组 中心索引 的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。

如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。


示例 1：
```text
输入：
nums = [1, 7, 3, 6, 5, 6]
输出：3
解释：
索引 3 (nums[3] = 6) 的左侧数之和 (1 + 7 + 3 = 11)，与右侧数之和 (5 + 6 = 11) 相等。
同时, 3 也是第一个符合要求的中心索引。
```

示例 2：
```text
输入：
nums = [1, 2, 3]
输出：-1
解释：
数组中不存在满足此条件的中心索引。
```


说明：
- nums 的长度范围为 [0, 10000]。
- 任何一个 nums[i] 将会是一个范围在 [-1000, 1000]的整数。

# 题解

一个多月没刷题了，以后要坚持下来。

自己想不出来方法，看的答案，发现核心的方法是"前缀和"。以后但凡是需要计算数组和的都要考虑"前缀和"这个知识点。

首先需要计算出数组元素相加总和`sum`，然后再次遍历数组，并记录从0到现在的总和`leftSum`

如果有 `sum - leftSum = n + leftSum` 那么就存在。一定要从左往右遍历

## 代码

### Java

```java
class Solution {
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int sumLeft = 0;
        for (int i = 0; i < n; i++) {
            if (sum - sumLeft * 2 == nums[i]) {
                return i;
            }
            sumLeft += nums[i];
        }
        return -1;
    }
}
```

- 执行用时： 1 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗： 40.9 MB , 在所有 Java 提交中击败了 5.03% 的用户

### Go

```go
func pivotIndex(nums []int) int {
	n := len(nums)
	if n == 0 {
		return -1
	}
	sum := 0
	for i := 0; i < n; i++ {
		sum += nums[i]
	}
	leftSum := 0
	for i := 0; i < n; i++ {
		if sum-leftSum*2 == nums[i] {
			return i
		}
		leftSum += nums[i]
	}
	return -1
}
```

- 执行用时： 20 ms , 在所有 Go 提交中击败了 95.95% 的用户 
- 内存消耗： 6 MB , 在所有 Go 提交中击败了 47.44% 的用户

### Python

别使用`sum()`函数

```python
class Solution:
    def pivotIndex(self, nums: List[int]) -> int:
        n = len(nums)
        if n == 0:
            return -1
        sum = 0
        for num in nums:
            sum += num
        left_sum = 0
        for i in range(n):
            if sum - left_sum * 2 == nums[i]:
                return i
            left_sum += nums[i]
        return -1
```

- 执行用时： 72 ms , 在所有 Python3 提交中击败了 57.97% 的用户 
- 内存消耗： 14.5 MB , 在所有 Python3 提交中击败了 90.35% 的用户
