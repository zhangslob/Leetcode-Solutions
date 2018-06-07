# 20.LongestCommonPrefix


[问题链接](https://leetcode-cn.com/problems/longest-common-prefix/description/)

# 题目

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 `""`。

**示例 1:**

```
输入: ["flower","flow","flight"]
输出: "fl"
```

**示例 2:**

```
输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
```

**说明:**

所有输入只包含小写字母 `a-z` 。

# 解题思路

需要一个字符串作为模板，判断后面的字符串是否以这个模板为前缀，如果判断为false，则将模板字符串去掉最后一位再比较，如果任然为false，则再删去一位，递归调用至判断为true。这里使用的是第一个字符串为模板。



```java
public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0){
            return "";
        }
        if (strs[0].isEmpty() && strs.length == 1) {
            return "";
        }
        String shortest = strs[0];//以第一个字符串为准做对比
        for (int i = 0; i < strs.length; i++) {
            shortest = this.getShortest(strs[i], shortest);
        }
        return shortest;

    }

 private String getShortest(String s, String shortest) {
        if (s.indexOf(shortest) != 0) {
            shortest = shortest.substring(0, shortest.length() - 1);
            if (shortest.isEmpty()) {
                return "";
            }
            shortest = getShortest(s, shortest);//递归调用直到符合标准
        }
        return shortest;
    }
```

