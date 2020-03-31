## 35.搜索插入位置

### 题目

给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。

你可以假设数组中无重复元素。

示例 1:

	输入: [1,3,5,6], 5
	输出: 2
	
示例 2:

	输入: [1,3,5,6], 2
	输出: 1
	
示例 3:

	输入: [1,3,5,6], 7
	输出: 4
	
示例 4:

	输入: [1,3,5,6], 0
	输出: 0
	
### 解题思路

常用二分查找来实现


```python
class problem.Solution(object):
    def helper(self, nums, target):
        length = len(nums)
        if length <= 2:
            for i in range(length):
                if nums[i] < target:
                    self.sum += 1
        else:
            mid = length / 2
            if nums[mid] == target:
                self.sum += mid
            elif nums[mid] > target:
                self.helper(nums[0:mid], target)
            else:
                self.helper(nums[mid + 1:], target)
                self.sum += mid + 1

    def searchInsert(self, nums, target):
        self.length = len(nums)
        if nums[0] > target:
            return 0
        elif nums[-1] < target:
            return self.length
        else:
            self.sum = 0
            self.helper(nums, target)
            return self.sum
				

```
	