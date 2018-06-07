## 123.买卖股票的最佳时机 III

### 题目

给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。

注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

示例 1:

	输入: [3,3,5,0,0,3,1,4]
	输出: 6
解释: 在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。  
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。	
     	
示例 2:

	输入: [1,2,3,4,5]
	输出: 4
解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     
示例 3:

	输入: [7,6,4,3,1] 
	输出: 0 
解释: 在这个情况下, 没有交易完成, 所以最大利润为 0。


### 解题思路

由题目121演变而来 最多交易两次

将天数分开，前i天调用一次121的算法，后面的天调用一次121的算法，但是要注意如果外层循环i，里面再循环121的算法，会超时，这时我们考虑用两个数组来存储结果，pre\_profit和pro\_profit，其中pre\_profit[i]表示i天之前的最大利润，pro\_profit[i]表示i天之后的最大利润，前i天的很好理解和121一样的写法，后i天注意要从后往前动态规划。

```python
class Solution(object):
    def maxProfit(self, prices):
        n = len(prices)
        if n < 2:
            return 0
        pre_max_profit = [0 for i in range(n)]
        pro_max_profit = [0 for i in range(n)]
        max_profit = 0
        pre_min_buy = prices[0]
        for i in range(1,n):
            # pre i days
            pre_min_buy = min(pre_min_buy,prices[i])
            pre_max_profit[i]=max(pre_max_profit[i-1], prices[i]-pre_min_buy)

        pro_max_sell = prices[n-1]
        for k in range(n-2,-1,-1):
            pro_max_sell = max(pro_max_sell,prices[k])
            pro_max_profit[k]=max(pro_max_profit[k+1], pro_max_sell-prices[k])
        for j in range(0,n):
            max_profit = max(max_profit,pre_max_profit[j]+pro_max_profit[j])
        return max_profit


print(Solution().maxProfit([3,3,5,0,0,3,1,4]))

```