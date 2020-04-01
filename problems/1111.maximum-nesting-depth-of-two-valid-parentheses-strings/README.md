# 有效括号的嵌套深度

[原题链接](https://leetcode-cn.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/)

## 题目

有效括号字符串 仅由 "(" 和 ")" 构成，并符合下述几个条件之一：

- 空字符串
- 连接，可以记作 AB（A 与 B 连接），其中 A 和 B 都是有效括号字符串
- 嵌套，可以记作 (A)，其中 A 是有效括号字符串

类似地，我们可以定义任意有效括号字符串 s 的 嵌套深度 depth(S)：

- s 为空时，depth("") = 0
- s 为 A 与 B 连接时，depth(A + B) = max(depth(A), depth(B))，其中 A 和 B 都是有效括号字符串
- s 为嵌套情况，depth("(" + A + ")") = 1 + depth(A)，其中 A 是有效括号字符串

例如：""，"()()"，和 "()(()())" 都是有效括号字符串，嵌套深度分别为 0，1，2，而 ")(" 和 "(()" 都不是有效括号字符串。 

给你一个有效括号字符串 seq，将其分成两个不相交的子序列 A 和 B，且 A 和 B 满足有效括号字符串的定义（注意：A.length + B.length = seq.length）。

现在，你需要从中选出 任意 一组有效括号字符串 A 和 B，使 max(depth(A), depth(B)) 的可能取值最小。

返回长度为 seq.length 答案数组 answer ，选择 A 还是 B 的编码规则是：如果 seq[i] 是 A 的一部分，那么 answer[i] = 0。否则，answer[i] = 1。即便有多个满足要求的答案存在，你也只需返回 一个。

示例 1：

输入：seq = "(()())"
输出：[0,1,1,1,1,0]

示例 2：

输入：seq = "()(())()"
输出：[0,0,0,1,1,0,1,1]

提示：1 <= text.size <= 10000

## 题解

看到这么长的题目，根本就不想做，直接翻评论区，发现都在吐槽，题目看不懂。专门去英文版的看了下，85个👍，417个踩，国内版就让不开放踩的功能，可太真实了。

但是在评论区，还是看到有热心人在解释：

```text
尝试解释一下，抛砖引玉：
Seq = ( ( ) ( ( ) ) ( ) )

嵌套深度 = [ 1, 2, 2, 2, 3, 3, 2, 2, 2, 1]

分组情况 = [ A, B, B, B, A, A, B, B, B, A]

输出 = [ 0, 1, 1, 1, 0, 0, 1, 1, 1, 0]

其中把A或B下标的括号单独抽出来，均为合法括号

按深度奇偶分组，原理请看题解：）
```


## 代码

### JAVA

```java
package problem;

import java.util.Arrays;

public class Solution {

    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        int d = 0;
        for (char c : seq.toCharArray()) {
            if (c == '(') {
                ans[d++] = d & 1;
            } else {
                ans[d++] = (d + 1) & 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution().maxDepthAfterSplit("()(())()")));
    }
}
```

- 执行用时 : 1 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 40 MB , 在所有 Java 提交中击败了 7.14% 的用户

### Python

```python
class Solution:
    def maxDepthAfterSplit(self, seq: str):
        ans = []
        d = 0
        for c in seq:
            if c == '(':
                d += 1
                ans.append(d % 2)
            if c == ')':
                ans.append(d % 2)
                d -= 1
        return ans


if __name__ == '__main__':
    print(Solution().maxDepthAfterSplit("()(())()"))
```

- 执行用时 : 56 ms , 在所有 Python3 提交中击败了 52.83% 的用户 
- 内存消耗 : 14 MB , 在所有 Python3 提交中击败了 16.67% 的用户

### go

```go
package main

import "fmt"

func maxDepthAfterSplit(seq string) []int {
	var ans []int
	d := 0
	for _, i := range seq {
		if i == '(' {
			d++
			ans = append(ans, d%2)
		}
		if i == ')' {
			ans = append(ans, d%2)
			d--
		}
	}
	return ans
}

func main() {
	fmt.Println(maxDepthAfterSplit("()(())()"))
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 3.7 MB , 在所有 Go 提交中击败了 25.00% 的用户


---

失败、失败中的失败
