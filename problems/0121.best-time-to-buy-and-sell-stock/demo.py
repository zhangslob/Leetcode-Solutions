class Solution(object):
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


print(Solution().maxProfit([7,1,5,3,6,4]))

print(Solution().maxProfit([7,6,4,3,1]))
