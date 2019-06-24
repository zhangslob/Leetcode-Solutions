package src.longestsubstring002;

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        int start = 0, end = 0, len = 0;
        while (end < s.length()) {
            if (map[s.charAt(end++)]++ == 0) {
                len = Math.max(len, end-start);
            } else {
                while (map[s.charAt(end-1)] > 1) {
                    map[s.charAt(start++)]--;
                }
            }
        }
        return len;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        Solution solution = new Solution();
        System.out.println(solution.lengthOfLongestSubstring(s));
    }
}
