# 排序数组

[原题链接](https://leetcode-cn.com/problems/sort-an-array/)

## 题目

给你一个整数数组 nums，将该数组升序排列。

示例 1：
输入：nums = [5,2,3,1]
输出：[1,2,3,5]

示例 2：
输入：nums = [5,1,1,2,0,0]
输出：[0,0,1,1,2,5]

提示：
1 <= nums.length <= 50000
-50000 <= nums[i] <= 50000

## 解题思路

这不就是排序吗，所有排序算法中表现效果最好的就是快排了，快排最重要的就是partition函数。看看自己能不能手写出来。

## 代码

### JAVA

```java
package problem;

import java.util.Arrays;

public class Solution {

    public int[] sortArray(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        sort(nums, 0, nums.length - 1);
        return nums;
    }

    private void sort(int[] nums, int i, int j) {
        if (i < j) {
            int index = partition(nums, i, j);
            sort(nums, i, index - 1);
            sort(nums, index + 1, j);
        }
    }

    private int partition(int[] nums, int i, int j) {
        int p = nums[i];
        int left = i;
        int right = j;
        while (left < right) {
            while (left < right && nums[right] >= p) {
                right--;
            }
            while (left < right && nums[left] <= p) {
                left++;
            }
            swap(nums, right, left);
        }
        swap(nums, i, left);
        return left;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 1, 1, 2, 0, 0};
        System.out.println(Arrays.toString(new Solution().sortArray(nums)));
    }

}
```

- 执行用时 : 5 ms , 在所有 Java 提交中击败了 97.87% 的用户 
- 内存消耗 : 47.5 MB , 在所有 Java 提交中击败了 7.38% 的用户

### Python

```python
class Solution:
    def sortArray(self, nums):
        self.sort(nums, 0, len(nums) - 1)
        return nums

    def sort(self, nums, left, right):
        if left < right:
            posit = self.partition(nums, left, right)
            self.sort(nums, left, posit - 1)
            self.sort(nums, posit + 1, right)

    def partition(self, nums, left, right):
        p = nums[left]
        i, j = left, right
        while i < j:
            while i < j and nums[j] >= p:
                j -= 1
            while i < j and nums[i] <= p:
                i += 1
            nums[i], nums[j] = nums[j], nums[i]
        nums[left], nums[i] = nums[i], nums[left]
        return i


if __name__ == '__main__':
    nums = [5, 2, 3, 1]
    Solution().sortArray(nums)
    print(nums)
```

- 执行用时 : 296 ms , 在所有 Python3 提交中击败了 62.07% 的用户 
- 内存消耗 : 19.7 MB , 在所有 Python3 提交中击败了 8.57% 的用户

执行用时为 36 ms 的范例

```python
class Solution:
    def sortArray(self, nums: List[int]) -> List[int]:
        return sorted(nums)
```

这样写有什么意思呢，刷题还是蒙骗自己？

### go

```go

func sortArray(nums []int) []int {
	if len(nums) <= 1 {
		return nums
	}
	sort(nums, 0, len(nums)-1)
	return nums
}

func sort(nums []int, i int, j int) {
	if i < j {
		index := partition(nums, i, j)
		sort(nums, i, index-1)
		sort(nums, index+1, j)
	}
}

func partition(nums []int, i int, j int) int {
	p := nums[i]
	left, right := i, j
	for left < right {
		for left < right && nums[right] >= p {
			right--
		}
		for left < right && nums[left] <= p {
			left++
		}
		nums[left], nums[right] = nums[right], nums[left]
	}
	nums[left], nums[i] = nums[i], nums[left]
	return left
}

```

- 执行用时 : 24 ms , 在所有 Go 提交中击败了 90.44% 的用户 
- 内存消耗 : 6.5 MB , 在所有 Go 提交中击败了 5.13% 的用户

在维基百科上看到另一种方法，学习下

```go
package main

import "fmt"

func sortArray(nums []int) []int {
	if len(nums) <= 1 {
		return nums
	}
	mid := nums[0]
	head, tail := 0, len(nums)-1
	for i := 1; i <= tail; {
		if nums[i] > mid {
			nums[i], nums[tail] = nums[tail], nums[i]
			tail--
		} else {
			nums[i], nums[head] = nums[head], nums[i]
			head++
			i++
		}
	}
	sortArray(nums[:head])
	sortArray(nums[head+1:])
	return nums
}

func main() {
	nums := []int{5, 1, 1, 2, 0, 0}
	fmt.Println(sortArray(nums))
}
```

- 执行用时 : 40 ms , 在所有 Go 提交中击败了 20.75% 的用户 
- 内存消耗 : 6.5 MB , 在所有 Go 提交中击败了 5.13% 的用户

## 新思路

参考：[当我谈排序时，我在谈些什么🤔](https://leetcode-cn.com/problems/sort-an-array/solution/dang-wo-tan-pai-xu-shi-wo-zai-tan-xie-shi-yao-by-s/)
 
 ```java
package problem;

import java.util.Arrays;

public class Solution {

    public int[] sortArray(int[] nums) {
        int max = -50001, min = 50001;
        for (int num : nums) {
            max = Math.max(num, max);
            min = Math.min(num, min);
        }
        int[] counter = new int[max - min + 1];
        for (int num : nums) {
            counter[num - min]++;
        }
        int idx = 0;
        for (int num = min; num <= max; num++) {
            int cnt = counter[num - min];
            while (cnt-- > 0) {
                nums[idx++] = num;
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 1, 1, 2, 0, 0};
        System.out.println(Arrays.toString(new Solution().sortArray(nums)));
    }
}
```
 
- 执行用时 : 2 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 47.2 MB , 在所有 Java 提交中击败了 7.79% 的用户

计数排序法，感觉和之前做的找出一个数组中出现次数最多的，摩尔投票法

[169. 多数元素](https://leetcode-cn.com/problems/majority-element/)
