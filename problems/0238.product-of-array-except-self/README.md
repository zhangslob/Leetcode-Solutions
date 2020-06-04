# 除自身以外数组的乘积

[原题链接](https://leetcode-cn.com/problems/product-of-array-except-self/)

## 题目

给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。

示例:
```text
输入: [1,2,3,4]
输出: [24,12,8,6]
```

提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。

说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。

进阶：你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）

## 题解

正常人的思路

```java
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int count = 1;
        for (int num : nums) {
            count *= num;
        }
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = count / nums[i];
        }
        return res;
    }
}
```

肯定是不符合要求的。

看了答案才知道可以使用`左右乘积列表`，对于给定索引 i，我们将使用它左边所有数字的乘积乘以右边所有数字的乘积，就是我们想要的答案。

算法
1. 初始化 answer 数组，对于给定索引 i，answer[i] 代表的是 i 左侧所有数字的乘积。
2. 构造 answer 作为方法一数组。满足题目要求，空间复杂度 $O(1)$
3. 首先遍历nums数组来更新左边乘积
4. 再次遍历nums数组来更新右边乘积

## 题解

### Java

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        int left = 1, right = 1;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            // 此时数组存储的是除去当前元素左边的元素乘积
            res[i] = left;
            left *= nums[i];
        }
        for (int j = len - 1; j >= 0; j--) {
            // right为该数右边的乘积。
            res[j] *= right;
            right *= nums[j];
        }
        return res;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 48.3 MB , 在所有 Java 提交中击败了 11.76% 的用户

### Go

```go
func productExceptSelf(nums []int) []int {
	left, right := 1, 1
	length := len(nums)
	ans := make([]int, length)
	for i := 0; i < length; i++ {
		ans[i] = left
		left *= nums[i]
	}
	for j := length - 1; j >= 0; j-- {
		ans[j] *= right
		right *= nums[j]
	}
	return ans
}
```

- 执行用时 : 12 ms , 在所有 Go 提交中击败了 93.26% 的用户 
- 内存消耗 : 6.3 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def productExceptSelf(self, nums: List[int]) -> List[int]:
        left, right = 1, 1
        length = len(nums)
        ans = [0 for _ in range(length)]
        for i in range(length):
            ans[i] = left
            left *= nums[i]
        for j in range(length - 1, -1, -1):
            ans[j] *= right
            right *= nums[j]
        return ans
```

- 执行用时 : 48 ms , 在所有 Python3 提交中击败了 96.80% 的用户
- 内存消耗 : 17.9 MB , 在所有 Python3 提交中击败了 100.00% 的用户
