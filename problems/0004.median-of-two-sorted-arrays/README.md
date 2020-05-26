# 两个排序数组的中位数

[原题链接](https://leetcode-cn.com/problems/median-of-two-sorted-arrays)

## 题目

给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。

请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。

示例 1:
```
nums1 = [1, 3]
nums2 = [2]
```
中位数是 2.0

示例 2:
```
nums1 = [1, 2]
nums2 = [3, 4]
```
中位数是 (2 + 3)/2 = 2.5


## 思路

https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/

中位数的定义：将一个集合划分为两个长度相等的子集，其中一个子集中的元素总是大于另一个子集中的元素。此时元素较小的子集的最大值和元素较大的子集的最小值的平均值就是中位数

提到时间复杂度为O(log(m+n))的算法，很容易想到的就是二分查找，所以现在要做的就是在两个排序数组中进行二分查找。

具体思路如下，可以将问题转化为在两个数组中找第K个大的数，先在两个数组中分别找出第k/2大的数，再比较这两个第k/2大的数，这里假设两个数组为A,B。那么比较结果会有下面几种情况：
1. A[k/2]=B[k/2],那么第k大的数就是A[k/2]
2. A[k/2]>B[k/2],那么第k大的数肯定在A[0:k/2+1]和B[k/2:]中，这样就将原来的所有数的总和减少到一半了，再在这个范围里面找第k/2大的数即可，这样也达到了二分查找的区别了。
3. A[k/2] < B[k/2]，那么第k大的数肯定在B[0:k/2+1]和A[k/2:]中,同理在这个范围找第k/2大的数就可以了。

## 代码

### Python
```java
public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        // 让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1 
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }

        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }
}
```

