# 最长回文子串

[原题链接](https://leetcode-cn.com/problems/longest-palindromic-substring)

## 题目

给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为1000。

示例 1：
```
输入: "babad"
输出: "bab"
注意: "aba"也是一个有效答案。
```

示例 2：

```
输入: "cbbd"
输出: "bb"
```

## 题解

学习大佬巨作：[LeetCode 第 5 题：最长回文子串（超详细的解法！！！）](https://www.cxyxiaowu.com/2869.html)

### 方法一：暴力匹配 （Brute Force）

根据回文子串的定义，枚举所有长度大于等于 2 的子串，依次判断它们是否是回文。

在具体实现时，可以只针对大于“当前得到的最长回文子串长度”的子串进行“回文验证”。

```java
public class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        String res = s.substring(0, 1);
        // 枚举所有长度大于等于 2 的子串
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && valid(s, i, j)) {
                    maxLen = j - i + 1;
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    private boolean valid(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
```

暴力解法时间复杂度高，但是思路清晰、编写简单，因为编写的正确性的可能性很大，可以使用暴力匹配算法检验我们编写的其它算法是否正确。

复杂度分析：
- 时间复杂度：$O(N^3)$，这里 $N$ 是字符串的长度，枚举字符串的左边界、右边界，然后继续验证子串是否是回文子串，这三种操作都与 $N$ 相关
- 空间复杂度：$O(1)$，只使用到常数个临时变量，与字符串长度无关。

### 方法二：中心扩散法

暴力法采用双指针两边夹，验证是否是回文子串，时间复杂度比较高。

除了枚举字符串的左右边界以外，比较容易想到的是枚举可能出现的回文子串的“中心位置”，从“中心位置”尝试尽可能扩散出去，得到一个回文串。

因此，中心扩散法的思路是：遍历每一个索引，以这个索引为中心，利用“回文串”中心对称的特点，往两边扩散，看最多能扩散多远。

枚举“中心位置”时间复杂度为 $O(N)$，从“中心位置”扩散得到“回文子串”的时间复杂度为 $O(N)$ ，因此时间复杂度可以降到 $O(N^2)$。

在这里要注意一个细节：回文串在长度为奇数和偶数的时候，“回文中心”的形式是不一样的。
- 奇数回文串的“中心”是一个具体的字符，例如：回文串 "aba" 的中心是字符 "a"；
- 偶数回文串的“中心”是位于中间的两个字符的“空隙”，例如：回文串串 "abba" 的中心是两个 "b" 中间的那个“空隙”。

![](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/WpHK3Q.jpg)

我们看一下一个字符串可能的回文子串的中心在哪里？

![FZAQoC](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/FZAQoC.jpg)

我们可以设计一个方法，兼容以上两种情况：
1. 如果传入重合的索引编码，进行中心扩散，此时得到的回文子串的长度是奇数；
2. 如果传入相邻的索引编码，进行中心扩散，此时得到的回文子串的长度是偶数。

具体编码细节在以下的代码的注释中体现。

```java
public class Solution {

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        String res = s.substring(0, 1);
        for (int i = 0; i < len - 1; i++) {
            String oddStr = centerSpread(s, i, i);
            String evenStr = centerSpread(s, i, i + 1);
            String maxLenSt = evenStr;
            if (oddStr.length() > evenStr.length()) {
                maxLenSt = oddStr;
            }
            if (maxLenSt.length() > maxLen) {
                maxLen = maxLenSt.length();
                res = maxLenSt;
            }
        }
        return res;
    }

    private String centerSpread(String s, int left, int right) {
        // left = right 的时候，此时回文中心是一个空隙，回文串的长度是奇数
        // right = left + 1 的时候，此时回文中心是任意一个字符，回文串的长度是偶数
        int len = s.length();
        int i = left, j = right;
        while (i >= 0 && j < len) {
            // 从中心往两边扩散
            if (s.charAt(i) == s.charAt(j)) {
                i--;
                j++;
            } else {
                break;
            }
        }
        // 这里要小心，跳出 while 循环时，恰好满足 s.charAt(i) != s.charAt(j)，因此不能取 i，不能取 j
        return s.substring(i + 1, j);
    }
}
```

- 执行用时 : 34 ms , 在所有 Java 提交中击败了 72.00% 的用户 
- 内存消耗 : 40 MB , 在所有 Java 提交中击败了 15.18% 的用户

# Manacher 算法

Manacher 算法，被中国程序员戏称为“马拉车”算法。它专门用于解决“最长回文子串”问题，时间复杂度为 $O(N)$。

Manacher 算法本质上还是中心扩散法，只不过它使用了类似 KMP 算法的技巧，充分挖掘了已经进行回文判定的子串的特点，在遍历的过程中，记录了已经遍历过的子串的信息，也是典型的以空间换时间思想的体现。

下面介绍 Manacher 算法的具体流程。

## 对原始字符串进行预处理（添加分隔符）

首先在字符串的首尾、相邻的字符中插入分隔符，例如 "babad" 添加分隔符 "#" 以后得到 "#b#a#b#a#d#"。

对这一点有如下说明：
1. 分隔符是一个字符，种类也只有一个，并且这个字符一定不能是原始字符串中出现过的字符；
2. 加入了分隔符以后，使得“间隙”有了具体的位置，方便后续的讨论，并且新字符串中的任意一个回文子串在原始字符串中的一定能找到唯一的一个回文子串与之对应，因此对新字符串的回文子串的研究就能得到原始字符串的回文子串；
3. 新字符串的回文子串的长度一定是奇数；
4. 新字符串的回文子串一定以分隔符作为两边的边界，因此分隔符起到“哨兵”的作用。

![IFkvwG](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/IFkvwG.jpg)

## 计算辅助数组 p

辅助数组 p 记录了新字符串中以每个字符为中心的回文子串的信息。

手动的计算方法仍然是“中心扩散法”，此时记录以当前字符为中心，向左右两边同时扩散，记录能够扩散的最大步数。

以字符串 "abbabb" 为例，说明如何手动计算得到辅助数组 p ，我们要填的就是下面这张表。

![NDbWVF](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/NDbWVF.png)

第 1 行数组 char ：原始字符串加上分隔符以后的每个字符。

第 2 行数组 index ：这个数组是新字符串的索引数组，它的值是从 0 开始的索引编号。

我们首先填 p[0]。以 char[0] = '#' 为中心，同时向左边向右扩散，走 1 步就碰到边界了，因此能扩散的步数为 0 ，因此 p[0] = 0；

![gbC8OG](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/gbC8OG.png)

以 char[1] = 'a' 为中心，同时向左边向右扩散，走 1 步，左右都是 "#"，构成回文子串，于是再继续同时向左边向右边扩散，左边就碰到边界了，最多能扩散的步数”为1，因此 p[1] = 1；

![F7hIXE](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/F7hIXE.png)

以 char[2] = '#' 为中心，同时向左边向右扩散，走 1 步，左边是 "a"，右边是 "b"，不匹配，最多能扩散的步数为0，因此 p[2] = 0；

![CBhVBu](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/CBhVBu.png)

以 char[3] = 'b' 为中心，同时向左边向右扩散，走 1 步，左右两边都是 “#”，构成回文子串，继续同时向左边向右扩散，左边是 "a"，右边是 "b"，不匹配，最多能扩散的步数为 1 ，因此 p[3] = 1；

![tObjTt](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/tObjTt.png)

以 char[4] = '#' 为中心，同时向左边向右扩散，最多可以走 4 步，左边到达左边界，因此 p[4] = 4。

![6jbi3c](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/6jbi3c.png)

分析到这里，后面的数字不难填出，最后写成如下表格：

![xPU8Wj](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/xPU8Wj.png)

说明：有些资料将辅助数组 p 定义为回文半径数组，即 p[i] 记录了以新字符串第 i 个字符为中心的回文字符串的半径（包括第 i 个字符），与我们这里定义的辅助数组 p 有一个字符的偏差，本质上是一样的。

下面是辅助数组 p 的结论：辅助数组 p 的最大值是 ，对应了原字符串 "abbabb" 的 “最长回文子串” ："bbabb"。这个结论具有一般性，即：

> 辅助数组 p 的最大值就是“最长回文子串”的长度。

因此，我们可以在计算辅助数组 p 的过程中记录这个最大值，并且记录最长回文子串。

简单说明一下这是为什么：

1. 如果新回文子串的中心是一个字符，那么原始回文子串的中心也是一个字符，在新回文子串中，向两边扩散的特点是：“先分隔符，后字符”，同样扩散的步数因为有分隔符 # 的作用，在新字符串中每扩散两步，虽然实际上只扫到一个有效字符，但是相当于在原始字符串中相当于计算了两个字符。因为最后一定以分隔符结尾，还要计算一个，正好这个就可以把原始回文子串的中心算进去；

![oii323](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/oii323.jpg)

2. 如果新回文子串的中心是 #，那么原始回文子串的中心就是一个“空隙”。在新回文子串中，向两边扩散的特点是：“先字符，后分隔符”，扩散的步数因为有分隔符 # 的作用，在新字符串中每扩散两步，虽然实际上只扫到一个有效字符，但是相当于在原始字符串中相当于计算了两个字符。

因此，“辅助数组 p 的最大值就是“最长回文子串”的长度”这个结论是成立的，可以看下面的图理解上面说的2点。

![xj5EFl](https://raw.githubusercontent.com/zhangslob/oss/master/uPic/xj5EFl.jpg)

写到这里，其实已经能写出一版代码，把这一版代码提交到 LeetCode 是可以通过的，这同样也可以验证我们上面的结论是正确的。

```java
class Solution {

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        String str = addBoundaries(s, '#');
        int sLen = 2 * len + 1;
        int maxLen = 1;
        int start = 0;
        for (int i = 0; i < sLen; i++) {
            int curLen = centerSpread(str, i);
            if (curLen > maxLen) {
                maxLen = curLen;
                start = (i - maxLen) / 2;
            }
        }
        return s.substring(start, start + maxLen);
    }

    private int centerSpread(String str, int center) {
        int len = str.length();
        int i = center - 1;
        int j = center + 1;
        int step = 0;
        while (i >= 0 && j < len && str.charAt(i) == str.charAt(j)) {
            i--;
            j++;
            step++;
        }
        return step;
    }

    private String addBoundaries(String s, char divide) {
        int len = s.length();
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(divide);
            sb.append(s.charAt(i));
        }
        sb.append(divide);
        return sb.toString();
    }
}
```

- 执行用时 : 61 ms , 在所有 Java 提交中击败了 54.23% 的用户 
- 内存消耗 : 39.7 MB , 在所有 Java 提交中击败了 15.18% 的用户

复杂度分析：
1. 时间复杂度：$O(N^2)$，这里 N 是原始字符串的长度。新字符串的长度是 `2 * N + 1`，不计系数与常数项，因此时间复杂度仍为 $O(N^2)$。
2. 空间复杂度：$O(N)$。

Manacher 算法我个人觉得没有必要记住，如果真有遇到，查资料就可以了。

“最长回文子串”问题最通用的做法是动态规划，它的时间复杂度为 $O(N^2)$ ，大家可以自己动手试试，或者查阅相关资料，把它做出来。

## Golang

```go

func longestPalindrome(s string) string {
	if len(s) < 2 {
		return s
	}
	start, end := 0, 0
	for i := 0; i < len(s); i++ {
		len1 := expandAroudCenter(s, i, i)
		len2 := expandAroudCenter(s, i, i+1)
		len3 := len1
		if len2 > len1 {
			len3 = len2
		}
		if len3 > end-start {
			start = i - (len3-1)/2
			end = i + len3/2
		}
	}
	return s[start : end+1]
}

func expandAroudCenter(s string, left int, right int) int {
	for left >= 0 && right < len(s) && s[left] == s[right] {
		left--
		right++
	}
	return right - left - 1
}
```

- 执行用时 : 4 ms , 在所有 Go 提交中击败了 93.94% 的用户 
- 内存消耗 : 2.2 MB , 在所有 Go 提交中击败了 44.83% 的用户
