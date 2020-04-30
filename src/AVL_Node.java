/*
AVL_Tree implementation

@author RASIM GARIPOV
@author LYSENKOV DMITRIY
april 2020
Higher School of ITIS, Kazan Federal University, Kazan, Russia
 */

public class AVL_Node<T extends Comparable<T>> {
    T key;
    int height;
    AVL_Node<T> left;
    AVL_Node<T> right;

    public AVL_Node(T key) {
        this(key, 0, null, null);
    }

    public AVL_Node(T key, int height, AVL_Node<T> left, AVL_Node<T> right) {
        this.key = key;
        this.height = height;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return key.toString();
    }

    private void updateHeight(AVL_Node<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(AVL_Node<T> node) {
        if (node == null) {
            return  -1;
        }
        return node.height;
    }

    private int getBalance(AVL_Node<T> node) {
        if (node == null) {
            return  0;
        }
        return height(node.right) - height(node.left);
    }

    private AVL_Node<T> rightRotation(AVL_Node<T> node) {//AVL Tree classic balancing operation
        AVL_Node<T> x = node.left;
        AVL_Node<T> z = x.right;
        x.right = node;
        node.left = z;
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    private AVL_Node<T> leftRotation(AVL_Node<T> node) {//AVL Tree classic balancing operation
        AVL_Node<T> x = node.right;
        AVL_Node<T> z = x.left;
        x.left = node;
        node.right = z;
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    private AVL_Node<T> rebalance(AVL_Node<T> node) {//Returns root of balanced AVL Tree
        updateHeight(node);                          //(root_of_the_tree)
        int balance = getBalance(node);
        if (balance > 1) {
            if (height(node.right.right) <= height(node.right.left)) {
                node.right = rightRotation(node.right);
            }
            node = leftRotation(node);
        } else if (balance < -1) {
            if (height(node.left.left) <= height(node.left.right)) {
                node.left = leftRotation(node.left);
            }
            node = rightRotation(node);
        }
        return node;
    }

    public AVL_Node<T> insert(AVL_Node<T> node, T key) {//Returns root of tree with inserted new key
        if (node == null) {                             //(root_of_the_tree, key)
            return new AVL_Node<>(key);
        } else if (node.key.compareTo(key) > 0) {
            node.left = insert(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }
        return rebalance(node);
    }

    private AVL_Node<T> mostLeftChild(AVL_Node<T> node) {//Returns the most left child of tree, where root = node
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public AVL_Node<T> delete(AVL_Node<T> node, T key) {//Returns root of tree with deleted key
        if (node == null) {                             //(root_of_the_tree, key)
            return null;
        } else if (node.key.compareTo(key) > 0) {
            node.left = delete(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                AVL_Node<T> mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    public AVL_Node<T> search(T key) {//Returns the element if it was found, or sends the null if was not
        AVL_Node<T> current = this;
        while (current != null) {
            if (current.key.equals(key)) {
                break;
            }
            if(current.key.compareTo(key) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return current;
    }

    public void traverse(AVL_Node<T> node) {//Array traversal
        if(node != null) {
            traverse(node.left);
            System.out.println(node);
            traverse(node.right);
        }
    }

    public int treeSize(AVL_Node<T> node) {// Size of tree where root = node
        if(node != null) {
            return 1 + treeSize(node.right) + treeSize(node.left);
        }
        return 0;
    }

    public T[] treeToArray(AVL_Node<T> node) {// [left_array, key_of_this_node, right_array]
        if(node != null) {
            T [] elements = (T[]) new Comparable[node.treeSize(node)];
            T [] left = node.treeToArray(node.left);
            T [] right = node.treeToArray(node.right);
            for (int i = 0; i < left.length; i++) {
                elements[i] = left[i];
            }
            elements[left.length] = node.key;
            for (int i = 0; i < right.length; i++) {
                elements[i + 1 + left.length] = right[i];
            }
            return elements;
        }
        return (T[]) new Comparable[0];
    }
}