# 统计「优美子数组」

[原题链接](https://leetcode-cn.com/problems/count-number-of-nice-subarrays/)

## 题目

给你一个整数数组 nums 和一个整数 k。

如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。

请返回这个数组中「优美子数组」的数目。

示例 1：

输入：nums = [1,1,2,1,1], k = 3
输出：2
解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
示例 2：

输入：nums = [2,4,6], k = 1
输出：0
解释：数列中不包含任何奇数，所以不存在优美子数组。
示例 3：

输入：nums = [2,2,2,1,2,2,1,2,2,2], k = 2
输出：16

提示：

- 1 <= nums.length <= 50000
- 1 <= nums[i] <= 10^5
- 1 <= k <= nums.length

## 题解

看到大佬写的一篇：[前缀和技巧](https://zhuanlan.zhihu.com/p/107778275)，优秀。

提出了前缀和的观点，前缀和的思路是这样的，对于一个给定的数组 `nums`，我们额外开辟一个前缀和数组进行预处理：

```java
int n = nums.length;
// 前缀和数组
int[] preSum = new int[n + 1];
preSum[0] = 0;
for (int i = 0; i < n; i++)
    preSum[i + 1] = preSum[i] + nums[i];
```

![preview](https://pic1.zhimg.com/v2-0bef2c46daeca964e2933c6a5bea03d4_r.jpg)

这个前缀和数组 `preSum` 的含义也很好理解，`preSum[i]` 就是 `nums[0..i-1]` 的和。那么如果我们想求 `nums[i..j]` 的和，只需要一步操作 `preSum[j+1]-preSum[i]` 即可，而不需要重新去遍历数组了。

回到这个子数组问题，我们想求有多少个子数组的和为 k，借助前缀和技巧很容易写出一个解法：

```java
int subarraySum(int[] nums, int k) {
    int n = nums.length;
    // 构造前缀和
    int[] sum = new int[n + 1];
    sum[0] = 0; 
    for (int i = 0; i < n; i++)
        sum[i + 1] = sum[i] + nums[i];

    int ans = 0;
    // 穷举所有子数组
    for (int i = 1; i <= n; i++)
        for (int j = 0; j < i; j++)
            // sum of nums[j..i-1]
            if (sum[i] - sum[j] == k)
                ans++;

    return ans;
}
```

这个解法的时间复杂度 *O*(*N^*2) 空间复杂度 *O*(*N*)，并不是最优的解法。不过通过这个解法理解了前缀和数组的工作原理之后，可以使用一些巧妙的办法把时间复杂度进一步降低。

进一步优化

前面的解法有嵌套的 for 循环：

```java
for (int i = 1; i <= n; i++)
    for (int j = 0; j < i; j++)
        if (sum[i] - sum[j] == k)
            ans++;
```

第二层 for 循环在干嘛呢？翻译一下就是，**在计算，有几个 `j` 能够使得 `sum[i]` 和 `sum[j]` 的差为 k。**毎找到一个这样的 `j`，就把结果加一。

我们可以把 if 语句里的条件判断移项，这样写：

```java
if (sum[j] == sum[i] - k)
    ans++;
```

> 这不就是第一题两数之和的思想吗
>
> a + b = k ==> a = k - b



## 代码

### Java

#### 滑动窗口，双指针

这种解法一定要注意左右两边的情况，i,j都是奇数，还要把i左右、j右边的偶数加起来，不然就会少很多

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0, ans = 0, oddCount = 0;
        while (right < nums.length) {
            if ((nums[right++] & 1) == 1) {
                oddCount++;
            }
            if (oddCount == k) {
                int tmp = right;
                while (right < nums.length && (nums[right] & 1) == 0) {
                    right++;
                }
                int rightCount = right - tmp;
                int leftCount = 0;
                while ((nums[left] & 1) == 0) {
                    leftCount++;
                    left++;
                }
                ans += (leftCount + 1) * (rightCount + 1);
                left++;
                oddCount--;
            }
        }
        return ans;
    }
}
```

- 太难了，自己根本写不出来

- 执行用时 : 8 ms , 在所有 Java 提交中击败了 74.71% 的用户 
- 内存消耗 : 47.9 MB , 在所有 Java 提交中击败了 100.00% 的用户

#### 使用map

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int sum = 0, ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += (nums[i] & 1);
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
```

- 执行用时 : 90 ms , 在所有 Java 提交中击败了 5.17% 的用户 
- 内存消耗 : 48.2 MB , 在所有 Java 提交中击败了 100.00% 的用户

#### 使用array

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int ans = 0, sum = 0;
        int[] arr = new int[nums.length + 1];
        arr[0] = 1;
        for (int num : nums) {
            sum += num & 1;
            arr[sum]++;
            if (sum >= k) {
                ans += arr[sum - k];
            }
        }
        return ans;
    }
}
```

- 执行用时 : 3 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 48.4 MB , 在所有 Java 提交中击败了 100.00% 的用户



### Python

```python
class Solution:
    def numberOfSubarrays(self, nums: List[int], k: int) -> int:
        # use array to store exists value
        arr = [0 for i in range(len(nums)+1)]
        arr[0] = 1
        ans = sum = 0
        for num in nums:
            sum += num & 1
            # sum + x = k
            arr[sum] += 1
            if sum >= k:
                ans += arr[sum - k]
        return ans
```

- 执行用时 : 1020 ms , 在所有 Python3 提交中击败了 69.68% 的用户 
- 内存消耗 : 20.4 MB , 在所有 Python3 提交中击败了 25.00% 的用户

## Go

```go
func numberOfSubarrays(nums []int, k int) int {
	arr := make([]int, len(nums)+1)
	arr[0] = 1
	ans, sum := 0, 0
	for i := 0; i < len(nums); i++ {
		sum += nums[i] & 1
		arr[sum]++
		if sum >= k {
			ans += arr[sum-k]
		}
	}
	return ans
}
```

- 执行用时 : 148 ms , 在所有 Go 提交中击败了 60.87% 的用户 
- 内存消耗 : 7.6 MB , 在所有 Go 提交中击败了 100.00% 的用户