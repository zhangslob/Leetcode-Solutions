# 和为K的子数组

[原题链接](https://leetcode-cn.com/problems/subarray-sum-equals-k/)

## 题目

给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。

示例 1 :
- 输入:nums = [1,1,1], k = 2
- 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。

说明 :
- 数组的长度为 [1, 20,000]。
- 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。

## 题解

能想到的只有两次遍历，类似

```python
class Solution:
    def subarraySum(self, nums: List[int], k: int) -> int:
        n = len(nums)
        if n == 0:
            return 0
        ans = 0
        for i in range(n):
            tmp = nums[i]
            if tmp == k:
                ans += 1
            for j in range(i + 1, n):
                tmp += nums[j]
                if tmp == k:
                    ans += 1
        return ans
```

但是肯定会超时的，怎么也想不出来，去看答案。


1. 建立map表用于存储每个连续子数组sum求和出现的次数，初始化为（0,1），表示和为0的连续子数组出现1次
2. sum的值是在对nums数组的循环中不断累加当前元素的，res的值则需要查找map中是否已存在`sum-k`的元素，也就是在查找此前所有从0项开始累加的连续子项和中有没有`sum-k`
3. 如果有的话，则说明从该项到当前项的连续子数组和必定为k，那么res则可以和这个sum的对应值，即这个sum出现的次数，相加得到新的res
4. 对于当前sum如果已存在与map中则其对应值+1，不存在则添加新项，初始值为1

算法思想：前缀和+哈希表

## 代码

### Java

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, ans = 0;
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
```

- 执行用时 : 22 ms , 在所有 Java 提交中击败了 68.49% 的用户 
- 内存消耗 : 40.2 MB , 在所有 Java 提交中击败了 11.54% 的用户


### Go
```go
func subarraySum(nums []int, k int) int {
	count := 0
	sumMap := map[int]int{}
	sum := 0
	sumMap[0] = 1
	for _, v := range nums {
		sum += v
		if sumMap[sum - k] > 0 {
			count += sumMap[sum - k]
		}
		sumMap[sum]++
	}
	return count
}
```

- 执行用时 : 28 ms , 在所有 Go 提交中击败了 45.19% 的用户 
- 内存消耗 : 6.3 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def subarraySum(self, nums: List[int], k: int) -> int:
        d = dict()
        d[0] = 1
        count, ans = 0, 0
        for i in range(len(nums)):
            count += nums[i]
            if count - k in d:
                ans += d[count - k]
            d[count] = d.get(count, 0) + 1
        return ans
```

- 执行用时 : 72 ms , 在所有 Python3 提交中击败了 96.89% 的用户 
- 内存消耗 : 16.1 MB , 在所有 Python3 提交中击败了 11.11% 的用户

