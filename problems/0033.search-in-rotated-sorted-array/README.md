# 搜索旋转排序数组

[原题链接](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)

## 题目

假设按照升序排序的数组在预先未知的某个点上进行了旋转。

( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。

搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。

你可以假设数组中不存在重复的元素。

你的算法时间复杂度必须是 O(log n) 级别。

示例 1:
```text
输入: nums = [4,5,6,7,0,1,2], target = 0
输出: 4
```

示例 2:
```text
输入: nums = [4,5,6,7,0,1,2], target = 3
输出: -1
```

## 题解

数组其实是有序的，比如数组长度是n，前k个数是有序的；从n-k到n也是有序的。

我初步的想法是根据双指针往中间缩，具体如下：
1. 如果target比第一个数大，如果target在这个数组中，那么一定在前k个。不停的从前往后去找
2. 如果target小于第一个数，那么target一定存在与从n-k到n。不停的从后往前找


```java
class Solution {
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        if (target < nums[0] && target > nums[nums.length - 1]) {
            return -1;
        }
        int first = nums[0], len = nums.length;
        if (target == first) {
            return 0;
        } else if (target > first) {
            int left = 1;
            while (left < len) {
                if (nums[left] == target) {
                    return left;
                }
                if (nums[left] < nums[left - 1]) {
                    break;
                }
                left++;
            }
        } else {
            int right = nums.length - 1;
            while (right > 0) {
                if (nums[right] == target) {
                    return right;
                }
                if (nums[right] < nums[right - 1]) {
                    break;
                }
                right--;
            }
        }
        return -1;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 39.5 MB , 在所有 Java 提交中击败了 17.74% 的用户

虽然从结果来说还不错，但是这道题要求的算法时间复杂度必须是 O(log n) 级别，我这个做法并没有达到，最坏情况下依然是O(n)。这道题必须使用二分去解决


## 代码

### Java

```java
class Solution {
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        if (target < nums[0] && target > nums[nums.length - 1]) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] >= nums[0]) {
                // 在左边
                if (nums[0] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 在右边
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 39.4 MB , 在所有 Java 提交中击败了 17.74% 的用户


## Go

```go
package main

import "fmt"

func search(nums []int, target int) int {
	if len(nums) == 0 {
		return -1
	}
	left, right := 0, len(nums)-1
	for left <= right {
		mid := left + (right - left) / 2
		if nums[mid] == target {
			return mid
		} else if nums[mid] >= nums[0] {
			if nums[0] <= target && target < nums[mid] {
				right = mid - 1
			} else {
				left = mid + 1
			}
		} else {
			if nums[right] >= target && target > nums[mid] {
				left = mid + 1
			} else {
				right = mid - 1
			}
		}
	}
	return -1
}

func main() {
	nums := []int{4, 5, 6, 7, 0, 1, 2}
	fmt.Println(search(nums, 2))
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.6 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        if len(nums) == 0:
            return -1
        left, right = 0, len(nums)-1
        while left <= right:
            mid = left + (right - left) // 2
            if nums[mid] == target:
                return mid
            elif nums[mid] >= nums[0]:
                if nums[0] <= target < nums[mid]:
                    right = mid - 1
                else:
                    left = mid + 1
            else:
                if nums[mid] < target <= nums[right]:
                    left = mid + 1
                else:
                    right = mid - 1
        return -1
```

- 执行用时 : 44 ms , 在所有 Python3 提交中击败了 56.57% 的用户 
- 内存消耗 : 13.6 MB , 在所有 Python3 提交中击败了 7.69% 的用户


