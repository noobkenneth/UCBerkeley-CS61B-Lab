public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }
        if (r.getItemCount() == 1) {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(1));

            return new RBTreeNode<T>(true, r.getItemAt(0), left, right);
        } else if (r.getItemCount() == 2) {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> rightleft = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode<T> rightright = buildRedBlackTree(r.getChildAt(2));

            RBTreeNode<T> right = new RBTreeNode<T>(false, r.getItemAt(1), rightleft, rightright);

            return new RBTreeNode<T>(true, r.getItemAt(0), left, right);
        } else if (r.getItemCount() == 3) {
            RBTreeNode<T> leftleft = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> leftright = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode<T> rightleft = buildRedBlackTree(r.getChildAt(2));
            RBTreeNode<T> rightright = buildRedBlackTree(r.getChildAt(3));

            RBTreeNode<T> left = new RBTreeNode<>(false, r.getItemAt(0), leftleft, leftright);
            RBTreeNode<T> right = new RBTreeNode<>(false, r.getItemAt(2), rightleft, rightright);

            return new RBTreeNode<T>(true, r.getItemAt(1), left, right);
        }

        return null;
    }


    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        if (node.left == null) {
            return null;
        }

        RBTreeNode<T> left = node.left.left;
        RBTreeNode<T> right = new RBTreeNode<>(false, node.item, node.left.right, node.right);

        return new RBTreeNode<>(node.isBlack, node.left.item, left, right);

    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        if (node.right == null) {
            return null;
        }

        RBTreeNode<T> right = node.right.right;
        RBTreeNode<T> left = new RBTreeNode<>(false, node.item, node.left, node.right.left);

        return new RBTreeNode<>(node.isBlack, node.right.item, left, right);
    }

    /* Insert ITEM into the red black tree, rotating
       it accordingly afterwards. */
    void insert(T item) {
        if (root == null) {
            root = new RBTreeNode<>(true, item);
        }
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    RBTreeNode<T> insertHelper(RBTreeNode<T> x, T item) {
        if (x == null) {
            x = new RBTreeNode<T>(false, item);
        } else if (item.compareTo(x.item) < 0) {
            x.left = insertHelper(x.left, item);
        } else if (item.compareTo(x.item) > 0) {
            x.right = insertHelper(x.right, item);
        }
        x = update(x);
        return x;
    }

    RBTreeNode<T> update(RBTreeNode<T> x) {
        if (x == null) {
            return null;
        }
        if (x.left == null && x.right != null && x.right.left == null & x.right.right == null) {
            x = rotateLeft(x); //CASE 1
        } else if (x.isBlack && x.right != null && x.left != null
                && isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        } else if (x.isBlack && x.left != null && isRed(x.left)) {
            if (x.left.left != null && x.left.right == null) {
                if (isRed(x.left.left)) {
                    if (x.left.left.left == null && x.left.left.right == null) {
                        x = rotateRight(x);
                        x = update(x);
                    }
                }
            } else if (x.left.right != null && x.left.left == null) {
                if (isRed(x.left.right)) {
                    if (x.left.right.left == null && x.left.right.right == null) {
                        x.left = rotateLeft(x.left);
                        x = update(x);
                    }
                }
            }
        }
        return x;

    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

}
