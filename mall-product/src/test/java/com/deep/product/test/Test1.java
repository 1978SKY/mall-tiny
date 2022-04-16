package com.deep.product.test;

public class Test1 {
    public int maxProduct(String[] words) {
        int len = words.length;
        if (len < 2) {
            return len;
        }

        int max = 0;
        for (int i = 0; i < len; i++) {
            String word = words[i];
            int[] set = new int[26];
            for (int k = 0; k < word.length(); k++) {
                set[word.charAt(k) - 'a'] = 1;
            }

            for (int j = i + 1; j < len; j++) {
                String word1 = words[j];
                int res = word1.length() * word.length();
                if (res > max) {
                    int k;
                    for (k = 0; k < word1.length(); k++) {
                        if (set[word1.charAt(k) - 'a'] == 1) {
                            break;
                        }
                    }
                    if (k >= word1.length()) {
                        max = res;
                    }
                }
            }
        }
        return max;
    }
}
