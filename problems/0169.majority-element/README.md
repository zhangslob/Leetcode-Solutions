## 169.求众数

### 题目

给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组总是存在众数。

示例 1:

	输入: [3,2,3]
	输出: 3

示例 2:

	输入: [2,2,1,1,1,2,2]
	输出: 2
	
	
### 解题思路

#### 2 python

利用python当中的collections 对数组进行统计 里面出现次数最多的就是我们需要的

```
class problem.Solution(object):
    def majorityElement(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        counts = collections.Counter(nums)
        return max(counts.keys(), key=counts.get)
```	