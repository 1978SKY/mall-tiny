import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Deep
 * @date 2022/4/1
 */
public class Test {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private static ArrayList<String> res = new ArrayList<>();


    public static String serialize(TreeNode root) {
        ArrayList<String> list = new ArrayList<>();
        if (root == null) {
            list.add(null);
            return list.toString();
        }
        LinkedList<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode first = deque.removeFirst();
                list.add(String.valueOf(first.val));
                if (first.left != null) {
                    deque.addLast(first.left);
                } else {
                    list.add("#");
                }
                if (first.right != null) {
                    deque.addLast(first.right);
                } else {
                    list.add("#");
                }
            }
        }
        res.addAll(list);
        return list.toString();
    }

    public TreeNode deserialize(String data) {
        for (int i = 0; i < 10; i++) {

        }
        return null;
    }

    public static void main(String[] args) {
    }
}
