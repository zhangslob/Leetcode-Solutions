# 括号生成

[原题链接](https://leetcode-cn.com/problems/generate-parentheses/)

## 题目

数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。

示例：

输入：n = 3
输出：
```text
[
       "((()))",
       "(()())",
       "(())()",
       "()(())",
       "()()()"
     ]
```

## 思路

DFS + 递归，思考下过程：
1. 记录左括号与右括号出现的次数，left、right = n
2. 判断left左括号次数是否大于0，如果是添加"("
3. 判断left与right大小，如果left小于right，添加")" 

递归终止条件: left = 0 or right = 0

## 代码

```java
package problem;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        dfs("", n, n, ans);
        return ans;
    }

    private void dfs(String str, int left, int right, List<String> ans) {
        // 左右括号都不剩余了，递归终止
        if (left == 0 && right == 0) {
            ans.add(str);
            return;
        }
        // 如果左括号还剩余的话，可以拼接左括号
        if (left > 0) {
            dfs(str + "(", left - 1, right, ans);
        }
        // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
        if (left < right) {
            dfs(str + ")", left, right - 1, ans);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().generateParenthesis(3));
        // [
        //       "((()))",
        //       "(()())",
        //       "(())()",
        //       "()(())",
        //       "()()()"
        //     ]
    }
}
```

- 执行用时 : 2 ms , 在所有 Java 提交中击败了 55.37% 的用户 
- 内存消耗 : 40.1 MB , 在所有 Java 提交中击败了 5.01% 的用户

### Python

```python
class Solution:
    def generateParenthesis(self, n):
        ans = []

        def dfs(string, left, right):
            # 如果左右括号都用完了
            if left == 0 and right == 0:
                ans.append(string)
                return
            # 如果还剩下左括号
            if left > 0:
                dfs(string + "(", left - 1, right)
            # 如果左括号小于右括号
            if left < right:
                dfs(string + ")", left, right - 1)
        dfs('', n, n)
        return ans


if __name__ == '__main__':
    print(Solution().generateParenthesis(0))
```

- 执行用时 : 40 ms , 在所有 Python3 提交中击败了 66.09% 的用户 
- 内存消耗 : 13.8 MB , 在所有 Python3 提交中击败了 5.03% 的用户

### Go

```go
package main

import "fmt"

func generateParenthesis(n int) []string {
	var ans []string
	dfs("", n, n, &ans)
	return ans
}

func dfs(s string, left int, right int, ans *[]string) {
	if left == 0 && right == 0 {
		*ans = append(*ans, s)
		return
	}
	if left > 0 {
		dfs(s+"(", left-1, right, ans)
	}
	if left < right {
		dfs(s+")", left, right-1, ans)
	}
}

func main() {
	fmt.Println(generateParenthesis(3))
}

```
- 执行用时 : 0 ms , 在所有 Go 提交中击败了 100.00% 的用户 
- 内存消耗 : 2.8 MB , 在所有 Go 提交中击败了 33.33% 的用户

不能使用全局变量，使用指针引用

