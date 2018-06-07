## 38.报数


### 题目

报数序列是指一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：

    1 --    1
    2 --   11
    3 --    21
    4 --   1211
    5 --     111221
  
1 被读作  "one 1"  ("一个一") , 即 11。  
11 被读作 "two 1s" ("两个一"）, 即 21。  
21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。  

给定一个正整数 n ，输出报数序列的第 n 项。

注意：整数顺序将表示为一个字符串。

示例 1:

  输入: 1
  输出: "1"
  
示例 2:

  输入: 4
  输出: "1211"
  
  
### 解题思路

依次查询每一个字符出现的次数，并统计下来，然后在结果中存入即可，题目数据量比较小。

#### Python

```
class Solution(object):
    # @param {integer} n
    # @return {string}
    def countAndSay(self, n):
        seq=['1'];top=1;
        while n-1>0:
            n-=1;index=0;bak=[]
            i=0
            while i<top:
                num=1
                while i+1<top and seq[i+1]==seq[i]:i+=1;num+=1
                bak.append(chr(num+ord('0')))
                bak.append(seq[i])
                i+=1
            seq=bak;top=len(bak)
        return ''.join(seq)

```

