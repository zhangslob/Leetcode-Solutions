# 1. [Two Sum](https://leetcode-cn.com/problems/two-sum/description/)

# 题目

给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。

你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

示例:
```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

# 解题思路

## 1 Go
`a + b = target`

也可以看成是

`a = target - b`

在map[整数]整数的序号中，可以查询到a的序号。这样就不用嵌套两个for循环了。

```go
func twoSum(nums []int, target int) []int {
	m := make(map[int]int, len(nums))

	for i, b := range nums {
		if j, ok := m[target-b]; ok {
			return []int{j, i}
		}

		m[nums[i]] = i
	}

	return nil
}
```

## 2 Python

1. 由于要找到符合题意的数组元素的下标，所以先要将原来的数组深拷贝一份，然后排序。
2. 然后在排序后的数组中找两个数使它们相加为target。这个思路比较明显：使用两个指针，一个指向头，一个指向尾，两个指针向中间移动并检查两个指针指向的数的和是否为target。如果找到了这两个数，再将这两个数在原数组中的位置找出来就可以了。
3. 要注意的一点是：在原来数组中找下标时，需要一个从头找，一个从尾找，要不无法通过。如这个例子：numbers=[0,1,2,0]; target=0。如果都从头开始找，就会有问题。

```python
class Solution:
    def twoSum(self, nums, target):
        if len(nums) <= 1:
            return False
        d = dict()
        for i in range(len(nums)):
            if nums[i] in d:
                return [d[nums[i]], i]
            else:
                d[target - nums[i]] = i
```
