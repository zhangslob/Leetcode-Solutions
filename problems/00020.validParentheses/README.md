# 20.ValidParentheses

# 题目

给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串，判断字符串是否有效。

有效字符串需满足：

1. 左括号必须用相同类型的右括号闭合。
2. 左括号必须以正确的顺序闭合。

注意空字符串可被认为是有效字符串。

**示例 2:**

```
输入: "()[]{}"
输出: true
```

**示例 3:**

```
输入: "(]"
输出: false
```

**示例 4:**

```
输入: "([)]"
输出: false
```

**示例 5:**

```
输入: "{[]}"
输出: true
```



# 解题思路

可以利用栈——LIFO（先进先出）的特点，对于传入的字符串，从第一个符号开始，往栈中push值，当遇到一个右括号，则直接与栈中最顶层的括号对比是否为“一对”，如果是则将顶层的值出栈，如果不是则程序返回false。大致思路是这样，再进行一些细节处理就可以完成啦。下面是java代码：

Java

```
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    if (s.length() % 2 != 0) {
        return false;
    }
    for (int i = 0; i < s.length(); i++) {

        char nowChar = s.charAt(i);

        if (isLeftCouple(nowChar)) {
            stack.push(nowChar);
            continue;
        }

        if (stack.empty()) {
            return false;
        }

        char preChar = stack.peek();

        if (!isCouple(preChar, nowChar)) {
            return false;
        }
        stack.pop();
    }
    return stack.empty();
}

private static boolean isLeftCouple(char a) {
    return a == '(' || a == '{' || a == '[';
}

private static boolean isCouple(char a, char b) {
    switch (a) {
        case '(':
            return b == ')';
        case '[':
            return b == ']';
        case '{':
            return b == '}';
        default:
            return false;
    }
}
```
[问题链接](https://leetcode-cn.com/problems/valid-parentheses/description/)