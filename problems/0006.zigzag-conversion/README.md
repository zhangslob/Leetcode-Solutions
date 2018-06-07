# [6. Z字形变换](https://leetcode-cn.com/problems/zigzag-conversion/description/)

将字符串 "PAYPALISHIRING" 以Z字形排列成给定的行数：
```
P   A   H   N
A P L S I I G
Y   I   R
```
之后从左往右，逐行读取字符："PAHNAPLSIIGYIR"

实现一个将字符串进行指定行数变换的函数:

`string convert(string s, int numRows);`

示例 1:
```
输入: s = "PAYPALISHIRING", numRows = 3
输出: "PAHNAPLSIIGYIR"
```
示例 2:
```
输入: s = "PAYPALISHIRING", numRows = 4
输出: "PINALSIGYAHRPI"
解释:

P     I    N
A   L S  I G
Y A   H R
P     I
```

# 思路

发现其实新的字符串的顺序在原字符串中是有规律可循的。

重新组合后的形状其实就是一个个N，相邻N对应位置之间的字符个数是固定的，如例子中的P-A-H-N之间的字符都是3个。现在假设行数为n，则对应位置字符间的间距都为2n-3，现在第一排和最后一排的字符顺序就可以确定了.

但中间排的字符就比较特殊，在对应位置之间还会夹着一个字符。以例子中第一列的A和第二列的P来说，假设A的下标为i（从0开始），则A和P的间距为2(n-i-1)-1=2n-2i-3，而A和L的间距为2n-3，同时去掉P自己，所以P和L的间距为(2n-3)-(2n-2i-3)-1=2i-1。这样只需遍历第一列的字符，并依次加上对应的间距来获取后面的字符即可。上文的间距是指中间隔了几个字符。


## Python
```python
class Solution(object):
    def convert(self, s, numRows):
        """
        :type s: str
        :type numRows: int
        :rtype: str
        """
        if numRows<=1:
            return s
        result = ''
        index = 0
        n = len(s)
        for i in range(0, numRows):
            if i == 0 or i == numRows - 1:
                while index < n:
                    result += s[index]
                    index += 2 * numRows - 2
                index = i + 1
            else:
                while index < n:
                    result += s[index]
                    index += 2 * numRows - 2 * i - 2
                    if index >= n:
                        break
                    result += s[index]
                    index += 2 * i
                index = i + 1
        return result

```


```python
class Solution:
    # @return a string
    def convert(self, s, nRows):
        if nRows==1: return s
        tmp=['' for i in range(nRows)]
        index=-1; step=1
        for i in range(len(s)):
            index+=step
            if index==nRows:
                index-=2; step=-1
            elif index==-1:
                index=1; step=1
            tmp[index]+=s[i]
        return ''.join(tmp)

```

## Golang

输入"ABCDEFGHIJKLMNOPQRSTUVWXYZ"和参数5后，得到答案"AGMSYBFHLNRTXZCEIKOQUWDJPV"， 按照题目的摆放方法，可得：
```
A   I   Q   Y
B  HJ  PR  XZ
C G K O S W
DF  LN  TV
E   M   U
```
可以看到，各行字符在原字符串中的索引号为
```
0行，0, 8, 16, 24
1行，1, 7, 9, 15, 17, 23, 25
2行，2, 6, 10, 14, 18, 22
3行，3, 5, 11, 13, 19, 21
4行，4, 12, 20
```
令p=numRows×2-2，可以总结出以下规律
```
0行， 0×p，1×p，...
r行， r，1×p-r，1×p+r，2×p-r，2×p+r，...
最后一行， numRow-1, numRow-1+1×p，numRow-1+2×p，...
```
只需编程依次处理各行即可。

```golang
func convert(s string, numRows int) string {
	if numRows == 1 || len(s) <= numRows {
		return s
	}

	res := bytes.Buffer{}
	// p pace 步距
	p := numRows*2 - 2

	// 处理第一行
	for i := 0; i < len(s); i += p {
		res.WriteByte(s[i])
	}

	// 处理中间的行
	for r := 1; r <= numRows-2; r++ {
		// 添加r行的第一个字符
		res.WriteByte(s[r])

		for k := p; k-r < len(s); k += p {
			res.WriteByte(s[k-r])
			if k+r < len(s) {
				res.WriteByte(s[k+r])
			}
		}
	}

	// 处理最后一行
	for i := numRows - 1; i < len(s); i += p {
		res.WriteByte(s[i])
	}

	return res.String()
}
```
