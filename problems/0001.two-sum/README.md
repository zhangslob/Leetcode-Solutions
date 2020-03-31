# 两数之和

[原题链接](https://leetcode-cn.com/problems/two-sum/)

## 题目

给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。

你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

示例:

```bash
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

## 解题思路

字典储存之前的状态，避免重复。

时间复杂度：O(n)
空间复杂度：O(1)

## 代码

### JAVA

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(final int[] nums, final int target) {
        if (nums.length <= 1) {
            return new int[] { -1, -1 };
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int res = target - nums[i];
            if (map.containsKey(res)) {
                return new int[] { map.get(res), i };
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 2, 7, 11, 15 };
        int[] ans = new problem.Solution().twoSum(nums, 9);
        System.out.println(ans[0]);
        System.out.println(ans[1]);
        // 0, 1
    }
}
```

- 执行用时 : 3 ms , 在所有 Java 提交中击败了 91.28% 的用户
- 内存消耗 : 41.4 MB , 在所有 Java 提交中击败了 5.02% 的用户

### Go

```go
package main

import "fmt"

func twoSum(nums []int, target int) []int {
	maps := make(map[int]int, len(nums))
	for i, j := range nums {
		if index, ok := maps[target-j]; ok {
			return []int{index, i}
		}
		maps[j] = i
	}
	return nil
}
func main() {
	nums := []int{2, 7, 11, 15}
	fmt.Println(twoSum(nums, 9))
}

```

- 执行用时 : 12 ms , 在所有 Go 提交中击败了 40.21% 的用户
- 内存消耗 : 3.4 MB , 在所有 Go 提交中击败了 58.79% 的用户

### Python

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

- 执行用时 : 28 ms , 在所有 Python3 提交中击败了 99.63% 的用户
- 内存消耗 : 15.1 MB , 在所有 Python3 提交中击败了 5.00% 的用户
