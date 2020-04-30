import java.util.Arrays;

/*
AVL_Tree implementation

@author RASIM GARIPOV
@author LYSENKOV DMITRIY
april 2020
Higher School of ITIS, Kazan Federal University, Kazan, Russia
 */
public class AVL_Tree<T extends Comparable<T>> {

    public AVL_Node<T> root;

    public AVL_Tree(AVL_Node<T> root) {
        this.root = root;
    }

    public AVL_Tree() {
        this(null);
    }

    public void insert(T key) {
        if (root == null) {
            root = new AVL_Node<>(key);
        } else {
            this.root = root.insert(this.root, key);
        }
    }

    public void delete(T key) {
        if (root != null) {
            this.root = root.delete(this.root, key);
        }
    }

    public boolean contains(T key) {
        if (root != null) {
            return root.search(key) != null;
        }
        return false;
    }

    public void traverse() {
        root.traverse(root);
    }

    public String toString() {
        return Arrays.deepToString(this.toArray());
    }

    public int size() {
        return root.treeSize(root);
    }

    private T[] toArray() {
        return root.treeToArray(root);

    }

}