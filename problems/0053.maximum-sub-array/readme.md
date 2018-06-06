## 53.最大子序列

### 题目

给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

示例:

	输入: [-2,1,-3,4,-1,2,1,-5,4],
	输出: 6
	解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
	
进阶:

如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。

### 解题思路

**Kadane's algorithm**

max_sum 必然是以A[i](取值范围为A[0] ~ A[n-1])结尾的某段构成的，也就是说max_sum的candidate必然是以A[i]结果的。如果遍历每个candidate，然后进行比较，那么就能找到最大的max_sum了。


假设把A[i]之前的连续段叫做sum。可以很容易想到:


1. 如果sum>=0，就可以和A[i]拼接在一起构成新的sum'。因为不管A[i]多大，加上一个正数总会更大，这样形成一个新的candidate。


2. 反之，如果sum<0，就没必要和A[I]拼接在一起了。因为不管A[i]多小，加上一个负数总会更小。此时由于题目要求数组连续，所以没法保留原sum，所以只能让sum等于从A[i]开始的新的一段数了，这一段数字形成新的candidate。


3. 如果每次得到新的candidate都和全局的max_sum进行比较，那么必然能找到最大的max sum subarray.

在循环过程中，用max_sum记录历史最大的值。从A[0]到A[n-1]一步一步地进行。


#### Python

```
class Solution(object):
    def maxSubArray(self, array):
        """
        :type array: List[int]
        :rtype: int
        """
        if len(array) == 1:
            return array[0]
        cur = pos = array[0]
        for i in range(1, len(array)):
            pos = max(pos + array[i], array[i])
            cur = max(cur, pos)
        return cur
```

以上是时间复杂度为n的算法

由于题目提示还有时间复杂度更少的办法 所以做后续的尝试

采用分治法 进行处理 使


```
class Solution(object):
    def maxSubArray(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        if len(nums) == 1:
            return nums[0]
        left_max = self.maxSubArray(nums[:len(nums)/2])
        right_max = self.maxSubArray(nums[len(nums)/2:])
        
        right_tmp = nums[len(nums)/2]
        left_tmp = nums[len(nums)/2-1]
        
        sum = 0
        for num in reversed(nums[:len(nums)/2]):
            sum = sum + num
            if sum > left_tmp:
                left_tmp = sum
       
                
        sum = 0
        for num in nums[len(nums)/2:]:
            sum = sum + num
            if sum > right_tmp:
                right_tmp = sum
                
        if right_tmp < 0 and left_tmp < 0:
            return max(left_max, right_max, right_tmp, left_tmp)
        else:
            return max(left_max, right_max, right_tmp+left_tmp)
```



