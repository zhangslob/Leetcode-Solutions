# 对称二叉树

[原题链接](https://leetcode-cn.com/problems/symmetric-tree/)

## 题目

给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。

对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。

示例 1：
```text
输入：candies = [2,3,5,1,3], extraCandies = 3
输出：[true,true,true,false,true] 
解释：
孩子 1 有 2 个糖果，如果他得到所有额外的糖果（3个），那么他总共有 5 个糖果，他将成为拥有最多糖果的孩子。
孩子 2 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
孩子 3 有 5 个糖果，他已经是拥有最多糖果的孩子。
孩子 4 有 1 个糖果，即使他得到所有额外的糖果，他也只有 4 个糖果，无法成为拥有糖果最多的孩子。
孩子 5 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
```

示例 2：

```text
输入：candies = [4,2,1,1,2], extraCandies = 1
输出：[true,false,false,false,false] 
解释：只有 1 个额外糖果，所以不管额外糖果给谁，只有孩子 1 可以成为拥有糖果最多的孩子。
示例 3：

输入：candies = [12,1,12], extraCandies = 10
输出：[true,false,true]
``` 

提示：
1. 2 <= candies.length <= 100
2. 1 <= candies[i] <= 100
3. 1 <= extraCandies <= 50

## 题解

把题目读三遍，就知道怎么写了。

估计因为今天是儿童节所以才来这么简单的题目。

## 代码

### Java

```java
class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> ans = new ArrayList<>();
        int max = candies[0];
        for (int candy : candies) {
            if (candy > max) {
                max = candy;
            }
        }
        for (int candy : candies) {
            ans.add(candy + extraCandies >= max);
        }
        return ans;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 99.84% 的用户 
- 内存消耗 : 40 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go

```go
func kidsWithCandies(candies []int, extraCandies int) []bool {
	var ans []bool
	max := candies[0]
	for _, v := range candies {
		if v > max {
			max = v
		}
	}
	for _, v := range candies {
		if v + extraCandies >= max {
			ans = append(ans, true)
		} else {
			ans = append(ans, false)
		}
	}
	return ans
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.3 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def kidsWithCandies(self, candies: List[int], extraCandies: int) -> List[bool]:
        ans = []
        max_num = max(candies)
        for candy in candies:
            if candy + extraCandies >= max_num:
                ans.append(True)
            else:
                ans.append(False)
        return ans
```

- 执行用时 : 40 ms , 在所有 Python3 提交中击败了 76.94% 的用户 
- 内存消耗 : 13.7 MB , 在所有 Python3 提交中击败了 100.00% 的用户

