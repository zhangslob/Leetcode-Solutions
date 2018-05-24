# 15. [3Sum](https://leetcode-cn.com/problems/3sum/description/)

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

## Python

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

## Python ||


```python
class Solution:
    def threeSum(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        if len(nums) < 3:
            return []
        nums.sort()
        res = set()
        for i, v in enumerate(nums[:-2]):
            if i >= 1 and v == nums[i-1]:
                continue
            d = {}
            for x in nums[i+1:]:
                if x not in d:
                    d[-v-x] = 1
                else:
                    res.add((v, -v-x, x))
        return list(res)

```

## Golang

```golang
func threeSum(nums []int) [][]int {
	sort.Ints(nums)
	var solus = make([][]int, 0)
	for lo := 0; lo < len(nums)-2; lo++ {
		mid, hi, target := lo+1, len(nums)-1, -nums[lo]
		for mid < hi {
			sum := nums[mid] + nums[hi]
			switch {
			case sum < target:
				mid++
			case sum > target:
				hi--
			default: // find one
				solus = append(solus, []int{nums[lo], nums[mid], nums[hi]})
				for mid+1 < hi && nums[mid] == nums[mid+1] { // move next to the right-most same number
					mid++
				}
				for hi-1 > mid && nums[hi] == nums[hi-1] { // move back to the left-most same number
					hi--
				}
				mid++
				hi--
			}
		}
		for lo+1 < len(nums) && nums[lo] == nums[lo+1] { // move next to the right-most same number
			lo++
		}
	}
	return solus
}
```

目前还是看不懂 golang ，┭┮﹏┭┮