# 快乐数

[原题链接](https://leetcode-cn.com/problems/happy-number/)

## 题目

编写一个算法来判断一个数 n 是不是快乐数。

「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。

如果 n 是快乐数就返回 True ；不是，则返回 False 。

示例：
```text
输入：19
输出：true
解释：
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
```

## 题解

看到这个题目，没有想多了，递归计算就行了，用map存储已经出现过的数字，如果有重复就不是，然后代码就写出来了

```java
class Solution {
    public boolean isHappy(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(n, n);
        return isHappy(n, map);
    }

    private boolean isHappy(int n, Map<Integer, Integer> map) {
        String[] strings = String.valueOf(n).split("");
        int ans = 0;
        for (String string : strings) {
            ans += Integer.parseInt(string) * Integer.parseInt(string);
        }
        if (ans == 1) {
            return true;
        }
        if (map.containsKey(ans)) {
            return false;
        }
        map.put(ans, ans);
        return isHappy(ans, map);
    }
}
```

- 执行用时 : 11 ms , 在所有 Java 提交中击败了 5.01% 的用户 
- 内存消耗 : 39.9 MB , 在所有 Java 提交中击败了 8.33% 的用户

懵逼了，啥情况，然后去群里看，别人都在讨论双指针、环，我就知道这道题最优解法肯定不会这么暴力。难点就在于如何转为一个环，想不出来啊

1. 利用快慢指针思想, 慢指针每次做一次转换, 快指针每次做两次转换
2. 如果出现无限循环, 那么快慢指针一定相遇

## 代码

### Java

```java
class Solution {
    public boolean isHappy(int n) {
        // 利用快慢指针思想, 慢指针每次做一次转换, 快指针每次做两次转换
        // 如果出现无限循环, 那么快慢指针一定相遇
        int slow = trans(n), fast = trans(trans(n));
        while (slow != fast) {
            slow = trans(slow);
            fast = trans(trans(fast));
        }
        return slow == 1;
    }

    private int trans(int n) {
        int res = 0;
        while (n != 0) {
            int tmp = n % 10;
            res += tmp * tmp;
            n /= 10;
        }
        return res;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 99.89% 的用户 
- 内存消耗 : 36.2 MB , 在所有 Java 提交中击败了 8.33% 的用户

最快乐的算法如下，数学发现只有出现数字1或者4会循环（不推荐）

```java
class Solution {
    public boolean isHappy(int n) {
        while (n != 1 && n != 4) {
            int sum = 0;
            while (n != 0) {
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 99.89% 的用户 
- 内存消耗 : 36.4 MB , 在所有 Java 提交中击败了 8.33% 的用户

还有看到用这种方法的

```java
func isHappy(n int) bool {
	for i := 0; i < 100; i++ {
		ans := 0
		for n > 0 {
			ans += (n % 10) * (n % 10)
			n /= 10
		}
		n = ans
		if n == 1 {
			return true
		}
	}
	return false
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2 MB , 在所有 Go 提交中击败了 100.00% 的用户

果然很快乐

