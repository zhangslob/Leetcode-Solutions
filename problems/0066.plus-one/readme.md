

### 题目


### 解题思路

用一个int数组代表一个数值，最左边是最高位，现要求加上1，同样地返回一个int数组代表计算结果。
非常简单的模拟加法，注意进位问题，如果是99，999这种，最后加上1后还要在数组最左边插入一个元素1。

#### Python

```python
class Solution(object):
    # @param digits, a list of integer digits
    # @return a list of integer digits
    def plusOne(self, digits):
        len_s = len(digits)
        carry = 1
        for i in range(len_s - 1, -1, -1):
            total = digits[i] + carry
            digit = int(total % 10)
            carry = int(total / 10)
            digits[i] = digit
        if carry == 1:
            digits.insert(0, 1)
        return digits
        
```
