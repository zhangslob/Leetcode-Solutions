计算一句话中最后一个单词的长度 处理一些边界情况


#### Python


```python
class Solution(object):

    # @param s, a string
    # @return an integer
    def lengthOfLastWord(self, s):

        len_s = len(s)
        if 0 == len_s:
            return 0

        index = len_s - 1
        while index >= 0 and ' ' == s[index]:
            index -= 1
        len_last_word = 0
        while index >= 0 and ' ' != s[index]:
            len_last_word += 1
            index -= 1
        return len_last_word
```