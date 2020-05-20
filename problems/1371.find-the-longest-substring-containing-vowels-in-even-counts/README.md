# 每个元音包含偶数次的最长子字符串

[原题链接](https://leetcode-cn.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/)

## 题目

给你一个字符串 s ，请你返回满足以下条件的最长子字符串的长度：每个元音字母，即 'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。

示例 1：
- 输入：s = "eleetminicoworoep"
- 输出：13
- 解释：最长子字符串是 "leetminicowor" ，它包含 e，i，o 各 2 个，以及 0 个 a，u 。

示例 2：
- 输入：s = "leetcodeisgreat"
- 输出：5
- 解释：最长子字符串是 "leetc" ，其中包含 2 个 e 。

示例 3：
- 输入：s = "bcbcbc"
- 输出：6
- 解释：这个示例中，字符串 "bcbcbc" 本身就是最长的，因为所有的元音 a，e，i，o，u 都出现了 0 次。
 
提示：
- 1 <= s.length <= 5 x 10^5
- s 只包含小写英文字母。

## 题解

这虽然是一道中等难度的题目，但是如果你之前没有接触过该类型题目的话，可能对你来说会有一些困难。
1. 首先，本题的数据规模很大，因此暴力解（n平方）肯定行不通。我们应该寻找时间复杂度在O(n)级别的解题思路。
2. 其实本题的核心是如何记录子串的状态，题目要求求出最长的子串中，所有元音字符的个数都是偶数
3. 那么我们可以使用一个状态位来表示当前子串中每种元音的状态，其中1代表该元音字符有奇数个，0代表个数为偶数，五个元音我们可以使用5个状态位来分别表示，举几个例子：

```text
00000 // a:偶数 e:偶数 i:偶数 o:偶数 u:偶数
01000 // a:偶数 e:奇数 i:偶数 o:偶数 u:偶数
01011 // a:偶数 e:奇数 i:偶数 o:奇数 u:奇数
00100 // a:偶数 e:偶数 i:奇数 o:偶数 u:偶数
```

1. 我们从开头开始遍历字符串，查看每个[0, i]区间子串的状态，并将每个状态第一次出现的位置存储到Map中
2. 接下来是重点，当我们再次遇到相同状态出现时，当前位置与首次出现该状态时的位置后一位所组成的区间即是一个合理区间
3. 我们举个例子来说明一下。比如状态01000在第x位首次出现，该状态表示字符e出现了奇数次，其他元音字符都是偶数次。
4. 接下来，我们在位置y再次发现了该状态01000，这能说明在x到y之间可能发生了以下2种情况：
    - x到y之间没有任何元音字符，因此，他们之间的状态没有发生改变
    - x到y之间有1个或多个元音出现，但是每种元音出现的次数都是偶数个，因此，他们的转态最终又还原为x时的状态。

我们再举一个生动的例子，比如我们遍历到字符串的第3位（下标为2），当前子串为：

```java
String sub = "bba" // 状态为10000，a是奇数个，其他元音都是偶数个（0个）
```
接下来我们遍历到了第8位（下标为7）

```java
String sub = "bbaaaiib" // 状态为10000，a是奇数个，其他元音都是偶数个
```

从第4位开始到第8位过程中，出现了2个a和2个i，因为出现的次数都是偶数，因此a和i的状态实际都没有发生改变，a依旧是奇数个，i依旧还是偶数个。进而转态也还是10000，没有发生改变。

因此我们可以得到结论，当两个点之间的状态相同时，他们之间的区间一定是一个合理区间，即所有元音元素都出现了偶数次（包括0次）。

1. 解题时，我们依次循环字符串中每个字符，统计下标0位到当前字符间元音状态，如果该状态第一次出现，我们将它存入Map中，记录下该状态第一次出现的位置。
2. 如果该状态不是首次出现，我们计算出当前位置与首次出现位置之间的长度，该长度为一个合理区间长度，并用该长度更新全局最大长度。

另外还有一种情况，如果当前的状态是0，说明当前位到首位间的子串也是合理区间。

## 代码

### Java

```java
class Solution {
    public int findTheLongestSubstring(String s) {
        // 记录每种状态首次出现的位置
        Map<Integer, Integer> map = new HashMap<>();
        // 返回结果
        int res = 0;
        // 当前状态
        int state = 0;
        // 循环
        for (int i = 0; i < s.length(); i++) {
            // 当前字符
            char c = s.charAt(i);
            switch (c) {
                case 'a':
                    // 更新a的状态
                    state = state ^ 16;
                    break;
                case 'e':
                    // 更新e的状态
                    state = state ^ 8;
                    break;
                case 'i':
                    // 更新i的状态
                    state = state ^ 4;
                    break;
                case 'o':
                    // 更新o的状态
                    state = state ^ 2;
                    break;
                case 'u':
                    // 更新u的状态
                    state = state ^ 1;
                    break;
                default:
                    break;
            }
            // 如果当前状态是0，代表所有元音都是偶数，当前区间是合理解
            if (state == 0) {
                res = i + 1;
            }
            // 如果该状态不是首次出现
            if (map.containsKey(state)) {
                // 首次出现位置到当前位置的区间是一个合理解
                int length = i - map.get(state);
                // 更新全局最大区间
                res = Math.max(res, length);
            } else {
                // 该状态首次出现时，将当前位置记录到map中
                map.put(state, i);
            }
        }
        return res;
    }
}
```

- 执行用时 : 47 ms , 在所有 Java 提交中击败了 50.00% 的用户 
- 内存消耗 : 44.1 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go

```go
func findTheLongestSubstring(s string) int {
	m := map[int]int{}
	res, state := 0, 0
	for i := 0; i < len(s); i++ {
		if s[i] == 'a' {
			state ^= 16
		} else if s[i] == 'e' {
			state ^= 8
		} else if s[i] == 'i' {
			state ^= 4
		} else if s[i] == 'o' {
			state ^= 2
		} else if s[i] == 'u' {
			state ^= 1
		}
		if state == 0 {
			res = i + 1
		}
		if v, f := m[state]; f {
			length := i - v
			if length > res {
				res = length
			}
		} else {
			m[state] = i
		}
	}
	return res
}
```

- 执行用时 : 32 ms , 在所有 Go 提交中击败了 48.48% 的用户 
- 内存消耗 : 6.2 MB , 在所有 Go 提交中击败了 100.00% 的用户

### Python

```python
class Solution:
    def findTheLongestSubstring(self, s: str) -> int:
        map = dict()
        res, state = 0, 0
        for i in range(len(s)):
            if s[i] == 'a':
                state ^= 16
            elif s[i] == 'e':
                state ^= 8
            elif s[i] == 'i':
                state ^= 4
            elif s[i] == 'o':
                state ^= 2
            elif s[i] == 'u':
                state ^= 1
            if state == 0:
                res = i + 1
            if state in map:
                length = i - map[state]
                res = max(res, length)
            else:
                map[state] = i
        return res
```

- 执行用时 : 836 ms , 在所有 Python3 提交中击败了 43.75% 的用户 
- 内存消耗 : 19.8 MB , 在所有 Python3 提交中击败了 100.00% 的用户
