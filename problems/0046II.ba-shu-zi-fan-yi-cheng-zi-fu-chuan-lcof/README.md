# 螺旋矩阵

[原题链接](https://leetcode-cn.com/problems/spiral-matrix/)

## 题目

给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。

示例 1:
```text
输入: 12258
输出: 5
解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
```

提示：0 <= num < 231

## 题解

### 动态规划

首先我们来通过一个例子理解一下这里「翻译」的过程：我们来尝试翻译「1402」。

分成两种情况：
1. 首先我们可以把每一位单独翻译，即 [1, 4, 0, 2][1,4,0,2]，翻译的结果是 beac
2. 然后我们考虑组合某些连续的两位：
    1. [14, 0, 2][14,0,2]，翻译的结果是 oac。
    2. [1, 40, 2][1,40,2]，这种情况是不合法的，因为 4040 不能翻译成任何字母。
    3. [1, 4, 02][1,4,02]，这种情况也是不合法的，含有前导零的两位数不在题目规定的翻译规则中，那么 [14, 02][14,02] 显然也是不合法的。
    
那么我们可以归纳出翻译的规则，字符串的第 ii 位置：
1. 可以单独作为一位来翻译
2. 如果第 i - 1i−1 位和第 ii 位组成的数字在 1010 到 2525 之间，可以把这两位连起来翻译

到这里，我们发现它和「198. 打家劫舍」非常相似。我们可以用 f(i) 表示以第 i 位结尾的前缀串翻译的方案数，考虑第 i 位单独翻译和与前一位连接起来再翻译对 f(i) 的贡献。
单独翻译对 f(i) 的贡献为 f(i - 1)；如果第 i−1 位存在，并且第 i−1 位和第 i 位形成的数字 x 满足 10≤x≤25，那么就可以把第 i−1 位和第 i 位连起来一起翻译，对 f(i) 的贡献为 f(i−2)，否则为 0。

我们可以列出这样的动态规划转移方程：

`f(i)=f(i−1)+f(i−2)[i−1≥0,10≤x≤25]`


## 代码

### Java 动态规划

```java
class Solution {
    public int translateNum(int num) {
        String src = String.valueOf(num);
        int p = 0, q = 0, r = 1;
        for (int i = 0; i < src.length(); i++) {
            p = q;
            q = r;
            r = 0;
            r += q;
            if (i == 0) {
                continue;
            }
            String pre = src.substring(i - 1, i + 1);
            if (pre.compareTo("25") <= 0 && pre.compareTo("10") >= 0) {
                r += p;
            }
        }
        return r;
    }
}
```

- 执行用时 : 0 ms , 在所有 Java 提交中击败了 100.00% 的用户 
- 内存消耗 : 36.5 MB , 在所有 Java 提交中击败了 100.00% 的用户

### Go 动态规划
```go
func translateNum(num int) int {
	stc := strconv.Itoa(num)
	p, q, r := 0, 0, 1
	for i := 0; i < len(stc); i++ {
		p, q, r = q, r, 0
		r += q
		if i == 0 {
			continue
		}
		pre := stc[i-1 : i+1]
		if pre <= "25" && pre >= "10" {
			r += p
		}
	}
	return r
}
```

- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 1.9 MB , 在所有 Go 提交中击败了 100.00% 的用户


### Python 动态规划

```python
class Solution:
    def translateNum(self, num: int) -> int:
        string = str(num)
        p, q, r = 0, 0, 1
        for i in range(len(string)):
            p, q, r = q, r, 0
            r += q
            if i == 0:
                continue
            pre = string[i - 1:i + 1]
            if '10' <= pre <= '25':
                r += p
        return r
```

- 执行用时 : 36 ms , 在所有 Python3 提交中击败了 83.43% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 100.00% 的用户
