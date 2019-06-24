package src.twosum001;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author zhangslob
 * https://leetcode.com/problems/two-sum/submissions/
 */
public class Solution {
    /**
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        if (nums == null || nums.length < 2) {
            return res;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
            }
            map.put(nums[i], i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        Solution twoSum = new Solution();
        int[] res = twoSum.twoSum(nums, target);
        System.out.println(res);
        System.out.println(Arrays.toString(res));
    }
}
