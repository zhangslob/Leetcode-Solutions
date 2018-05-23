#15. [3Sum](https://leetcode-cn.com/problems/3sum/description/)

# 题目

给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]

# 解题思路
双指针，第311个例子超时

```python

class Solution:
    def threeSum(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        s = set()
        nums.sort()
        length = len(nums)
        for i in range(length - 2):
            j = i+1
            k = length-1
            if nums[i] > 0 or j > k:
                break
            while j<k:
                if nums[i] + nums[j] + nums[k] == 0:
                    s.add((nums[i], nums[j], nums[k]))
                    j += 1
                    k -= 1
                    continue
                if nums[i] + nums[j] + nums[k] > 0:
                    k -= 1
                    continue
                if nums[i] + nums[j] + nums[k] < 0:
                    j += 1
                    continue
        return sorted(list(s), key = lambda x:(x[0], x[1], x[2]))
		
```