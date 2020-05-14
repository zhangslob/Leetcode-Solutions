# 只出现一次的数字

[原题链接](https://leetcode-cn.com/problems/single-number/)

## 题目

给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

示例 1:

输入: [2,2,1]
输出: 1

示例 2:

输入: [4,1,2,1,2]
输出: 4

## 题解

用异或解决，没难度

## 代码

### Java

```java
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 99.75% 的用户 
- 内存消耗 : 41 MB , 在所有 Java 提交中击败了 5.71% 的用户

### Go

```go
func singleNumber(nums []int) int {
	res := 0
	for _, num := range nums {
		res ^= num
	}
	return res
}
```

- 执行用时 : 12 ms , 在所有 Go 提交中击败了 80.06% 的用户 
- 内存消耗 : 4.7 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def singleNumber(self, nums: List[int]) -> int:
        ans = 0
        for num in nums:
            ans = ans ^ num
        return ans
```

- 执行用时 : 48 ms , 在所有 Python3 提交中击败了 70.69% 的用户 
- 内存消耗 : 15.3 MB , 在所有 Python3 提交中击败了 5.26% 的用户
