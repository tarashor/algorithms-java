import java.util.LinkedList;

public class BST<Key extends Comparable<Key>, Value> {
    private TreeNode root;

    private class TreeNode {
        private Key key;
        private Value value;
        private int count;

        private TreeNode left;
        private TreeNode right;
        TreeNode(Key key, Value value) {
            this.key = key;
            this.value = value;
            count = 1;
            left = null;
            right = null;
        }

    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private TreeNode put(TreeNode node, Key key, Value val) {
        if (node == null) return new TreeNode(key, val);

        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            node.left = put(node.left, key, val);
        } else if (cmp > 0){
            node.right = put(node.right, key, val);
        } else {
            node.value = val;
        }

        node.count = 1 + size(node.left) + size(node.right);

        return node;
    }

    private int size(TreeNode node) {
        return node != null ? node.count : 0;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(TreeNode node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            return get(node.left, key);
        } else if (cmp > 0){
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private TreeNode delete(TreeNode node, Key key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left != null && node.right != null) {
                TreeNode min = deleteMin(node.right);
                min.left = node.left;
                min.right = node.right;
                return min;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
        }

        return node;

    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private TreeNode deleteMin(TreeNode node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.count = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Iterable<Key> keys() {
        LinkedList<Key> keys = new LinkedList<Key>();
        inorder(keys, root);
        return keys;
    }

    private void inorder(LinkedList<Key> keys, TreeNode root) {
        if (root == null) return;

        inorder(keys, root.left);
        keys.add(root.key);
        inorder(keys, root.right);
    }

    public int size() {
        return size(root);
    }
}
