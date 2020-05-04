# 跳跃游戏 II

[原题链接](https://leetcode-cn.com/problems/jump-game-ii/)

## 题目

给定一个非负整数数组，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。你的目标是使用最少的跳跃次数到达数组的最后一个位置。

示例:
```text
输入: [2,3,1,1,4]
输出: 2
解释: 跳到最后一个位置的最小跳跃数是 2。
     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
```

说明: 假设你总是可以到达数组的最后一个位置。

## 题解

绝对的贪心是鼠目寸光，但是如果加上下一次的贪心就是最佳解法。每次跳跃的时候考虑这次跳跃后和下一次跳跃能到达的最远距离

第一次跳跃，nums[i] = 2，有两种选择，1或2（所有的跳跃都不考虑0，0没有任何意义）

![](https://cdn.jsdelivr.net/gh/zhangslob/oss@master/uPic/lmgCuw.png)

如果选择1

![](https://cdn.jsdelivr.net/gh/zhangslob/oss@master/uPic/5GIraw.png)

如果选择2

![](https://cdn.jsdelivr.net/gh/zhangslob/oss@master/uPic/19RoZ8.png)

如何在当前节点选择最远的跳跃，`i + nums[i]`，刚开始没想明白，`i`表示的是前面，`nums[i]`表示的是后面

## 代码

### Java

```java
class Solution {
    public int jump(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        // 已跳跃次数
        int times = 0;
        // 已到达的索引位置
        int reached = 0;
        // 最大能跳跃到的索引位置
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            // 已到达的索引位置小于遍历到的位置
            if (reached < i) {
                times++;
                reached = max;
            }
            // 当前可跳范围内，可以达到的的更远的位置
            max = Math.max(max, i + nums[i]);
        }
        return times;
    }
}
```

- 执行用时 : 2 ms , 在所有 Java 提交中击败了 94.93% 的用户 
- 内存消耗 : 41.8 MB , 在所有 Java 提交中击败了 5.00% 的用户

### Go

```go
func jump(nums []int) int {
	n := len(nums)
	max := 0
	step := 0
	reached := 0
	for i := 0; i < n-1; i++ {
		max = maxNum(max, i+nums[i])
		if reached == i {
			step++
			reached = max
		}
	}
	return step
}

func maxNum(a, b int) int {
	if a > b {
		return a
	}
	return b
}
```

- 执行用时 : 8 ms , 在所有 Go 提交中击败了 95.88% 的用户 
- 内存消耗 : 4.3 MB , 在所有 Go 提交中击败了 100.00% 的用户

> 如果先更新最远距离，则无需遍历到最后一位。只需要到最后第二位即可

### Python

```python
class Solution:
    def jump(self, nums: List[int]) -> int:
        step, max_num, reached = 0, 0, 0
        for i in range(len(nums) - 1):
            max_num = max(max_num, i + nums[i])
            if reached == i:
                reached = max_num
                step += 1
        return step
```

- 执行用时 : 52 ms , 在所有 Python3 提交中击败了 88.09% 的用户 
- 内存消耗 : 15.3 MB , 在所有 Python3 提交中击败了 12.50% 的用户

---

这道题还是有难度的，刚开始我想的是用动态规划，但是找不到状态转移方程。最后看到答案用的贪心法，刷新了自己对贪心的理解。

