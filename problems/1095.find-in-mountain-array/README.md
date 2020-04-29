# 山脉数组中查找目标值

[原题链接](https://leetcode-cn.com/problems/find-in-mountain-array/)

## 题目

（这是一个 交互式问题 ）

给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index 值。

如果不存在这样的下标 index，就请返回 -1。

何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：

首先，A.length >= 3

其次，在 0 < i < A.length - 1 条件下，存在 i 使得：

```text
A[0] < A[1] < ... A[i-1] < A[i]
A[i] > A[i+1] > ... > A[A.length - 1]
```

你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：

1. MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
2. MountainArray.length() - 会返回该数组的长度
 
注意：

对 MountainArray.get 发起超过 100 次调用的提交将被视为错误答案。此外，任何试图规避判题系统的解决方案都将会导致比赛资格被取消。

为了帮助大家更好地理解交互式问题，我们准备了一个样例 “答案”：https://leetcode-cn.com/playground/RKhe3ave，请注意这 不是一个正确答案。

示例 1：

```text
输入：array = [1,2,3,4,5,3,1], target = 3
输出：2
解释：3 在数组中出现了两次，下标分别为 2 和 5，我们返回最小的下标 2。
```

示例 2：
```text
输入：array = [0,1,2,4,2,1], target = 3
输出：-1
解释：3 在数组中没有出现，返回 -1。
```

提示：

- 3 <= mountain_arr.length() <= 10000
- 0 <= target <= 10^9
- 0 <= mountain_arr.get(index) <= 10^9

## 题解

思路是一定要找到最高点：top，题目说了top一定存在。后来我以为只需要去左边查找就行了，但是还有下面这个case： [1,5,2]。

算法：
1. 找到最高点top
2. 在[0, top]寻找，如果找到了target，直接返回
3. 最后在[top, n]寻找 

## 代码

### Java

```java
/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int size = mountainArr.length();
        // 找到山顶
        int topIndex = findTop(mountainArr, 0, size - 1);
        // 查看左边有没有
        int left = search(mountainArr, 0, topIndex, target, true);
        if (left != -1) {
            return left;
        }
        // 左边没有，去查询右边
        return search(mountainArr, topIndex, size-1, target, false);
    }

    private int search(MountainArray m, int left, int right, int target, boolean isDesc) {
        // 1,2,3,4,5   0   4   5
        while (left < right) {
            int mid = (left + right) >> 1;
            if (isDesc) {
                if (m.get(mid) < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            } else {
                if (m.get(mid) > target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        // 因为不确定区间收缩成 1个数以后，这个数是不是要找的数，因此单独做一次判断
        if (m.get(left) == target) {
            return left;
        }
        return -1;
    }

    private int findTop(MountainArray m, int left, int right) {
        while (left < right) {
            int mid = (left + right) >> 1;
            // 数组一定至少有 2 个元素 如果中间的数比右边的数小，说明top在右边
            if (m.get(mid) < m.get(mid + 1)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // top一定存在
        return left;
    }

}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 39.6 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go

```go
/**
 * // This is the MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * type MountainArray struct {
 * }
 *
 * func (this *MountainArray) get(index int) int {}
 * func (this *MountainArray) length() int {}
 */

func findInMountainArray(target int, mountainArr *MountainArray) int {
	size := mountainArr.length()
	top := findTop(mountainArr, size)
	left := search(mountainArr, 0, top, target, true)
	if left != -1 {
		return left
	}
	right := search(mountainArr, top, size-1, target, false)
	if right != -1 {
		return right
	}
	return -1
}

func search(arr *MountainArray, left int, right int, target int, b bool) int {
	for left < right {
		mid := (left + right) >> 1
		if b {
			if arr.get(mid) < target {
				left = mid + 1
			} else {
				right = mid
			}
		} else {
			if arr.get(mid) > target {
				left = mid + 1
			} else {
				right = mid
			}
		}
	}
	// 特殊情况，[1, 3, 2], 0, 1, 3, true
	if arr.get(left) == target {
		return left
	}
	return -1
}

func findTop(arr *MountainArray, size int) int {
	left, right := 0, size-1
	for left < right {
		mid := (left + right) >> 1
		if arr.get(mid) < arr.get(mid+1) {
			left = mid + 1
		} else {
			right = mid
		}
	}
	return left
}
```

- 执行用时 : 8 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 3.4 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def findInMountainArray(self, target: int, mountain_arr: 'MountainArray') -> int:
        size = mountain_arr.length()
        top = self.findTop(mountain_arr, 0, size - 1)
        left = self.search(mountain_arr, 0, top, target, True)
        if left != -1:
            return left
        return self.search(mountain_arr, top, size - 1, target, False)

    def search(self, m, left, right, target, isDesk):
        while left < right:
            mid = left + (right - left) // 2
            if m.get(mid) == target:
                return mid
            if isDesk:
                if m.get(mid) < target:
                    left = mid + 1
                else:
                    right = mid - 1
            else:
                if m.get(mid) > target:
                    left = mid + 1
                else:
                    right = mid - 1
        if m.get(left) == target:
            return left
        return -1

    def findTop(self, m, left, right):
        while left < right:
            mid = left + (right - left) // 2
            if m.get(mid) < m.get(mid + 1):
                left = mid + 1
            else:
                right = mid
        return left
```

- 执行用时 : 40 ms , 在所有 Python3 提交中击败了 56.96% 的用户 
- 内存消耗 : 14.3 MB , 在所有 Python3 提交中击败了 100.00% 的用户
