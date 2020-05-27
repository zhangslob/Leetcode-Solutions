# 和可被 K 整除的子数组

[原题链接](https://leetcode-cn.com/problems/subarray-sums-divisible-by-k/)

## 题目

给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。

示例：
```text
输入：A = [4,5,0,-2,-3,1], K = 5
输出：7
```

解释：
有 7 个子数组满足其元素之和可被 K = 5 整除：
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 

提示：
1. 1 <= A.length <= 30000
2. -10000 <= A[i] <= 10000
3. 2 <= K <= 10000

## 题解

首先暴力法

```java
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int n = A.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int sum = A[i];
            if (sum % K == 0) {
                ans++;
            }
            for (int j = i + 1; j < n; j++) {
                sum += A[j];
                if (sum % K == 0) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
```

毫无疑问的超时了.

如果任意两个数字除以K得到的余数相同，那么两数之差一定可以被K整除。举个例子：
```
int a = 7;
int b = 12;
int K = 5;
 
// 因为
a % K = 7 % 5 = 2;
b % K = 12 % 5 = 2;
 
// 所以
(b - a) % K = (12 - 7) % 5 = 0;

// weima

if (a % k == b % k) {
    (a - b) % k = 0
} 

```

这是一个简单的数学定理，在这就不必展开讨论。那么如果来运用这个定理解开本题呢？

`preSum[i, j](下标i到j之和)`
`preSum[i, j] = preSum[0, j] - preSum[0, i];`

也就是说，只要我们求出下列子数组的和，

```text
preSum[0, 0];
preSum[0, 1];
preSum[0, 2];
...
preSum[0, n];
```

那么对于数组中任意一个区间的和，都可以由上面相应的2个sum之差来取得。

另外，根据上面提到的余数定理，所有sum分别除以K，余数相同的sum之差是可以被K整除的，所以问题就转化为，寻找余数相同的个数。

我们定义一个Map来存储每个余数对应的个数：
```java
Map<Integer, Integer> map = new HashMap<>();
```

举个例子，比如当前map中余数为1的个数是5，当我们又找到一个sum%K的余数为1时，由于当前sum与之前5个的差都可以被K整除，所以结果会增加5。此外还要注意一个问题，当余数为0时，当前sum本身也是一个解，别忘了加入结果中。

最后还有一个小坑需要注意一下，在java语言中，负数与正整数的余为负数，所以需要特别转换下。比如：

`-3 % 5 = -3;`

我们应该将其转换为正数：

`-3 % 5 + 5 = 2;`

## 代码

### Java I

使用map

```java
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int count = 0, preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int num : A) {
            preSum = (preSum + num) % K;
            if (preSum < 0) {
                preSum += K;
            }
            if (map.containsKey(preSum)) {
                count += map.get(preSum);
            }
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}
```

- 执行用时 : 32 ms , 在所有 Java 提交中击败了 22.20% 的用户 
- 内存消耗 : 43.6 MB , 在所有 Java 提交中击败了 100.00% 的用

### Java II

优化版，使用数组

```java
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int count = 0, preSum = 0;
        int[] nums = new int[K];
        nums[0] = 1;
        for (int a : A) {
            preSum = (preSum + a) % K;
            if (preSum < 0) {
                preSum += K;
            }
            count += nums[preSum];
            nums[preSum]++;
        }
        return count;
    }
}
```

- 执行用时 : 6 ms , 在所有 Java 提交中击败了 50.47% 的用户 
- 内存消耗 : 42.7 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go I

```go
func subarraysDivByK(A []int, K int) int {
	sumMap := map[int]int{}
	sumMap[0] = 1
	sum, cnt := 0, 0
	for _, v := range A {
		sum = (v + sum) % K
		if sum < 0 {
			sum += K
		}
		if k, found := sumMap[sum]; found {
			cnt += k
		}
		sumMap[sum]++
	}
	return cnt
}
```

- 执行用时 : 60 ms , 在所有 Go 提交中击败了 82.22% 的用户 
- 内存消耗 : 6.7 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Go II

```go
func subarraysDivByK(A []int, K int) int {
	nums := make([]int, K)
	nums[0] = 1
	sum, cnt := 0, 0
	for _, v := range A {
		sum = (v + sum) % K
		if sum < 0 {
			sum += K
		}
		cnt += nums[sum]
		nums[sum]++
	}
	return cnt
}
```

- 执行用时 : 48 ms , 在所有 Go 提交中击败了 97.78% 的用户 
- 内存消耗 : 6.6 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python I

```python
class Solution:
    def subarraysDivByK(self, A: List[int], K: int) -> int:
        m = dict()
        m[0] = 1
        pre_sum, cnt = 0, 0
        for i in A:
            pre_sum = (pre_sum + i) % K
            if pre_sum < 0:
                pre_sum += K
            cnt += m.get(pre_sum, 0)
            m[pre_sum] = m.get(pre_sum, 0) + 1
        return cnt
```

- 执行用时 : 388 ms , 在所有 Python3 提交中击败了 53.41% 的用户 
- 内存消耗 : 17.6 MB , 在所有 Python3 提交中击败了 100.00% 的用户

### Python II

```python
class Solution:
    def subarraysDivByK(self, A: List[int], K: int) -> int:
        nums = [0 for _ in range(K)]
        nums[0] = 1
        pre_sum, cnt = 0, 0
        for i in A:
            pre_sum = (pre_sum + i) % K
            if pre_sum < 0:
                pre_sum += K
            cnt += nums[pre_sum]
            nums[pre_sum] += 1
        return cnt
```

- 执行用时 : 348 ms , 在所有 Python3 提交中击败了 97.63% 的用户 
- 内存消耗 : 17.6 MB , 在所有 Python3 提交中击败了 100.00% 的用户

