
# [4. 两个排序数组的中位数](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/description/)

给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。

请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。

示例 1:
```
nums1 = [1, 3]
nums2 = [2]
```
中位数是 2.0
示例 2:
```
nums1 = [1, 2]
nums2 = [3, 4]
```
中位数是 (2 + 3)/2 = 2.5


# 思路

## Python

普通方法：

对列表进行排序，针对列表长度是奇数还是偶数的不同情况，计算中位数。这显然不够有趣

最佳方法：

利用了取反数和为 1 的特性，通过列表负索引来获得列表中位数。如下：

```python
class Solution:
    def findMedianSortedArrays(self, nums1, nums2):
        data = nums1 + nums2
        data.sort()
        half = len(data) // 2
        return (data[half] + data[~half]) / 2
```

## Golang

```golang
func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	nums := combine(nums1, nums2)
	return medianOf(nums)
}

func combine(mis, njs []int) []int {
	lenMis, i := len(mis), 0
	lenNjs, j := len(njs), 0
	res := make([]int, lenMis+lenNjs)

	for k := 0; k < lenMis+lenNjs; k++ {
		if i == lenMis ||
			(i < lenMis && j < lenNjs && mis[i] > njs[j]) {
			res[k] = njs[j]
			j++
			continue
		}

		if j == lenNjs ||
			(i < lenMis && j < lenNjs && mis[i] <= njs[j]) {
			res[k] = mis[i]
			i++
		}
	}

	return res
}

func medianOf(nums []int) float64 {
	l := len(nums)

	if l == 0 {
		panic("切片的长度为0，无法求解中位数。")
	}

	if l%2 == 0 {
		return float64(nums[l/2]+nums[l/2-1]) / 2.0
	}

	return float64(nums[l/2])
}

```
