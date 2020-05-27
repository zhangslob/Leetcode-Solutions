# 寻找重复数

[原题链接](https://leetcode-cn.com/problems/find-the-duplicate-number/)

## 题目

给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。

示例 1:
```text
输入: [1,3,4,2,2]
输出: 2
```

示例 2:
```text
输入: [3,1,3,4,2]
输出: 3
```

说明：
1. 不能更改原数组（假设数组是只读的）。
2. 只能使用额外的 O(1) 的空间。
3. 时间复杂度小于 O(n2) 。
4. 数组中只有一个重复的数字，但它可能不止重复出现一次。

## 题解

这道题给了我们 n+1 个数，所有的数都在 [1, n] 区域内，首先让证明必定会有一个重复数。

这不禁让博主想起了小学华罗庚奥数中的抽屉原理(又叫鸽巢原理)，即如果有十个苹果放到九个抽屉里，如果苹果全在抽屉里，则至少有一个抽屉里有两个苹果，这里就不证明了，直接来做题吧。

题目要求不能改变原数组，即不能给原数组排序，又不能用多余空间，那么哈希表神马的也就不用考虑了，又说时间小于 $O(n^2)$，也就不能用 brute force 的方法，那也就只能考虑用二分搜索法了

1. 在区间 [1, n] 中搜索，首先求出中点 mid，然后遍历整个数组，统计所有小于等于 mid 的数的个数
2. 如果个数小于等于 mid，则说明重复值在 [mid+1, n] 之间，反之，重复值应在 [1, mid-1] 之间，
3. 然后依次类推，直到搜索完成，此时的 low 就是我们要求的重复值

还有另一种很牛的解法，巧用快慢指针。
1. 数组的索引与存储的数值之间形成了特殊链表。
2. 如果存在重复的数，因为数组大小是 n+1，数字范围是1~n，所以该链表存在环。
3. 环的入口即为结果。

> 链表有环可以使用快慢指针

## 代码

### Java I

```java
class Solution {
    public int findDuplicate(int[] nums) {
        int left = 1, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2, cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }
            if (cnt <= mid) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
```

- 执行用时 : 3 ms , 在所有 Java 提交中击败了 60.11% 的用户 
- 内存消耗 : 39.7 MB , 在所有 Java 提交中击败了 6.67% 的用户

### java II

```java
class Solution {
    public int findDuplicate(int[] nums) {
        // 快慢指针
        int fast = nums[0];
        int low = nums[0];
        do{
            low = nums[low];
            fast = nums[nums[fast]];
        }while(fast != low);
        int step = nums[0];
        // 寻找环链表的入口，即为结果
        while(step != low){
            step = nums[step];
            low = nums[low];
        }
        return low;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 39.4 MB , 在所有 Java 提交中击败了 6.67% 的用户

### Python I

```python
class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        left, right = 1, len(nums) - 1
        while left < right:
            mid = (left + right) // 2
            count = sum(i <= mid for i in nums)
            if count > mid:
                right = mid
            else:
                left = mid + 1
        return right
```

- 执行用时 : 112 ms , 在所有 Python3 提交中击败了 33.26% 的用户 
- 内存消耗 : 16 MB , 在所有 Python3 提交中击败了 35.71% 的用户

### Python II

```python
class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        fast, slow = 0, 0
        while True:
            fast = nums[nums[fast]]
            slow = nums[slow]
            if slow == fast:
                slow = 0
                while nums[fast] != nums[slow]:
                    slow = nums[slow]
                    fast = nums[fast]
                return nums[fast]
```

- 执行用时 : 88 ms , 在所有 Python3 提交中击败了 68.54% 的用户 
- 内存消耗 : 16.1 MB , 在所有 Python3 提交中击败了 35.71% 的用户

### Go I

```go
func findDuplicate(nums []int) int {
	left, right := 1, len(nums)
	cnt := 0
	for left < right {
		mid := left + (right - left) / 2
		cnt = 0
		for _, v := range nums {
			if v <= mid {
				cnt++
			}
		}
		if cnt <= mid {
			left = mid + 1
		} else {
			right = mid
		}
	}
	return left
}
```

- 执行用时 : 8 ms , 在所有 Go 提交中击败了 90.36% 的用户 
- 内存消耗 : 3.8 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Go II
```go
func findDuplicate(nums []int) int {
	slow := nums[0]
	fast := nums[slow]
	for slow != fast {
		slow = nums[slow]
		fast = nums[nums[fast]]
	}
	fast = nums[slow]
	slow = nums[0]
	for slow != fast {
		slow = nums[slow]
		fast = nums[fast]
	}
	return slow
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 99.60% 的用户 
- 内存消耗 : 3.8 MB , 在所有 Go 提交中击败了 100.00% 的用户
