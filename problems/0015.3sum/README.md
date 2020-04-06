# 三数之和

[原题链接](https://leetcode-cn.com/problems/3sum/description/)

## 题目

给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]

## 解题思路

1. 数组排序 
2. 定义三个指针，i，j，k
3. 遍历i，那么这个问题就可以转化为在i之后的数组中寻找`nums[j]+nums[k]=-nums[i]`
4. 这个问题，也就将三数之和问题转变为二数之和---（可以使用双指针）

## 代码

### JAVA

```java
package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                int l = i + 1, r = nums.length - 1, sum = -nums[i];
                while (l < r) {
                    if (nums[l] + nums[r] == sum) {
                        ans.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] < sum) {
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        l++;
                    } else {
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        r--;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().threeSum(new int[]{0, 0, 0}));
    }
}
```

- 执行用时 : 28 ms , 在所有 Java 提交中击败了 73.80% 的用户 
- 内存消耗 : 43.5 MB , 在所有 Java 提交中击败了 99.77% 的用户

### Python

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
            j = i + 1
            k = length - 1
            if nums[i] > 0 or j > k:
                break
            while j < k:
                if nums[i] + nums[j] + nums[k] == 0:
                    s.add((nums[i], nums[j], nums[k]))
                    while j < k and nums[j] == nums[j + 1]:
                        j += 1
                    while j < k and nums[k] == nums[k - 1]:
                        k -= 1
                    j += 1
                    k -= 1
                    continue
                if nums[i] + nums[j] + nums[k] > 0:
                    while j < k and nums[k] == nums[k - 1]:
                        k -= 1
                    k -= 1
                    continue
                if nums[i] + nums[j] + nums[k] < 0:
                    while j < k and nums[j] == nums[j + 1]:
                        j += 1
                    j += 1
                    continue
        return sorted(list(s), key=lambda x: (x[0], x[1], x[2]))


if __name__ == '__main__':
    print(Solution().threeSum([-1, 0, 1, 2, -1, -4]))
```
- 执行用时 : 2504 ms , 在所有 Python3 提交中击败了 5.04% 的用户 
- 内存消耗 : 16.7 MB , 在所有 Python3 提交中击败了 8.30% 的用户

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


if __name__ == '__main__':
    print(Solution().threeSum([-1, 0, 1, 2, -1, -4]))
```

这种方法真的太难想到了

- 执行用时 : 764 ms , 在所有 Python3 提交中击败了 88.34% 的用户 
- 内存消耗 : 16.4 MB , 在所有 Python3 提交中击败了 12.37% 的用户

### Golang

```go
package main

import (
	"fmt"
	"sort"
)

func threeSum(nums []int) [][]int {
	sort.Ints(nums)
	var ans = make([][]int, 0)
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
				ans = append(ans, []int{nums[lo], nums[mid], nums[hi]})
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
	return ans
}

func main() {
	fmt.Println(threeSum([]int{-1, 0, 1, 2, -1, -4}))
}
```

- 执行用时 : 36 ms , 在所有 Go 提交中击败了 78.02% 的用户 
- 内存消耗 : 6.9 MB , 在所有 Go 提交中击败了 30.29% 的用户
