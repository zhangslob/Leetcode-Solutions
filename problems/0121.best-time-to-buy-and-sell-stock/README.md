## 121.买卖股票的最佳时机

### 题目

给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。

如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。

注意你不能在买入股票前卖出股票。

示例 1:

	输入: [7,1,5,3,6,4]
	输出: 5
	
解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     
示例 2:

	输入: [7,6,4,3,1]
	输出: 0

解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。


### 解题思路

根据题目的条件 先将买入的价格设置为第一个值 如果后续有比它小的 则替换为小的
最大的利润差初值设置为0 如果后面的值大于买入值 则取差值 否则取0 顺势找到最大的利润差值。
注意边界条件 比如数组的长度为0或者1这种情况。


```
class problem.Solution(object):
    def maxProfit(self, prices):
        """
        :type prices: List[int]
        :rtype: int
        """
        maxprofit = 0
        if len(prices) <= 1:
            return 0
        minprice = prices[0]
        for i in range(1, len(prices)):
            if prices[i] < minprice:
                minprice = prices[i]
            maxprofit = max(maxprofit, prices[i] - minprice)
        return maxprofit
```