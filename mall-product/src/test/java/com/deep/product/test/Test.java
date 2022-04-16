package com.deep.product.test;

import java.util.*;

/**
 * @author Deep
 * @date 2022/3/18
 */
public class Test {
    public static void main(String[] args) {
//        PAYPALISHIRING
        String str = "PAYPALISHIRING";
        System.out.println(convert(str, 3));
    }

    public static String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        int count = 0;
        int col = s.length();
        String[][] str = new String[numRows][col];

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int cur = numRows - 1;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0 || cur == 0) {
                    List<Integer> list = map.get(j);
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(i);
                    map.put(j, list);
                    cur = numRows - 2;
                } else {
                    if ((numRows - 1 - i) == j % (numRows - 1)) {
                        List<Integer> list = map.get(j);
                        if (list == null) {
                            list = new LinkedList<>();
                        }
                        list.add(i);
                        map.put(j, list);
                    }
                    cur--;
                }
                count++;
            }
        }
        int len = s.length();
        int k = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if (k == len)
                break;
            Integer j = entry.getKey();
            List<Integer> iList = entry.getValue();
            for (int i = 0; i < iList.size(); i++) {
                if (k == len)
                    break;
                str[iList.get(i)][j] = s.charAt(k) + "";
                k++;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < col; j++) {
                if (str[i][j] != null) {
                    builder.append(str[i][j]);
                }
            }
        }
        return builder.toString();
    }

    public static String convert1(String s, int numRows) {
        String[][] str = new String[numRows][20];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < 20; j++) {
                str[i][j] = "  ";
            }
        }
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        int cur = numRows - 1;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < 20; j++) {
                if (j == 0 || cur == 0) {
                    str[i][j] = "# ";
                    List<Integer> list = map.get(j);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(i);
                    map.put(j, list);
                    cur = numRows - 2;
                } else {
                    if ((numRows - 1 - i) == j % (numRows - 1)) {
                        str[i][j] = "# ";
                        List<Integer> list = map.get(j);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(i);
                        map.put(j, list);
                    }
                    cur--;
                }
            }
        }
        int k = 0;
        int len = s.length();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if (k == len)
                break;
            Integer j = entry.getKey();
            List<Integer> iList = entry.getValue();
            for (int i = 0; i < iList.size(); i++) {
                if (k == len)
                    break;
                str[iList.get(i)][j] = s.charAt(k) + " ";
                k++;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < 20; j++) {
//                builder.append(str[i][j]);
                System.out.print(str[i][j]);
            }
            System.out.println();
        }
        System.out.println(builder);

        return "";
    }
}
