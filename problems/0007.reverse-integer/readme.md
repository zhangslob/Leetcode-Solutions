# [7.反转整数](https://leetcode-cn.com/problems/reverse-integer/description/)

# 题目

给定一个 32 位有符号整数，将整数中的数字进行反转。

示例 1:

	输入: 123
	输出: 321
	
 示例 2:

	输入: -123
	输出: -321
	
示例 3:

	输入: 120
	输出: 21
注意:

假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。根据这个假设，如果反转后的整数溢出，则返回 0。

# 解题思路

取绝对值之后 除10 对10取余 反转过来 注意题目的限制范围

## Python

```python
class problem.Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        y = x if x>=0 else -x
        z = 0
        while y>0:
            z = z*10 + y%10
            y /= 10
            
        z = z if x>=0 else -z
        if z>0x7fffffff or z<-0x80000000:
            return 0
        return z

```

利用Python的字符串反转操作来实现对整数的反转，反转后的字符串要重新转换为整数。同上面一样，要注意正负和溢出情况。

```python
class problem.Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        x = int(str(x)[::-1]) if x >= 0 else - int(str(-x)[::-1])
        return x if x < 2147483648 and x >= -2147483648 else 0
```

## Golang

```golang
func reverse(x int) int {
    sign := 1
    if x < 0 {
        sign = -1
        x = -1 * x
    }
    
    res := 0
    for x > 0 {
        temp := x % 10
        res = res * 10 + temp
        x = x / 10
    }
    
    res = sign * res
    
    if res > math.MaxInt32 || res < math.MinInt32 {
        res = 0
    }
    
    return res
}
```
