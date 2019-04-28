public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
<<<<<<< HEAD
        root = null;

=======
        // TODO: YOUR CODE HERE
>>>>>>> 6c64b13b3a52aec83562b6216be68a1f9191c1df
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
<<<<<<< HEAD
        this.root = root;

=======
        // TODO: YOUR CODE HERE
>>>>>>> 6c64b13b3a52aec83562b6216be68a1f9191c1df
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
<<<<<<< HEAD
        return containsHelper(root, key);
    }

    public boolean containsHelper(TreeNode t, T key) {
        if (t == null) {
            return false;
        }
        if (key.compareTo(t.item) == 0) {
            return true;
        } else if (key.compareTo(t.item) < 0) {
            return containsHelper(t.left, key);
        } else {
            return containsHelper(t.right, key);
        }
=======
        // TODO: YOUR CODE HERE
        return false;
>>>>>>> 6c64b13b3a52aec83562b6216be68a1f9191c1df
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
<<<<<<< HEAD
        if (!contains(key)) {
            TreeNode x = new TreeNode(key);
            if (root == null) {
                root = x;
                return;
            }
            TreeNode curr = root;
            TreeNode parent = null;
            while (true) {
                parent = curr;
                if (key.compareTo(curr.item) < 0) {
                    curr = curr.left;
                    if (curr == null) {
                        parent.left = x;
                        return;
                    }
                } else {
                    curr = curr.right;
                    if (curr == null) {
                        parent.right = x;
                        return;
                    }
                }
            }
        }

=======
        // TODO: YOUR CODE HERE
>>>>>>> 6c64b13b3a52aec83562b6216be68a1f9191c1df
    }

    /* Deletes a node from the BST. */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (((Comparable<T>) curr.item).compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
