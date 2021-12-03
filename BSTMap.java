import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A map implemented with a binary search tree.
 */
public class BSTMap<K extends Comparable<K>, V> {

    private Node<K, V> root;    // points to the root of the BST.

    /**
     * Create a new, empty BST.
     */
    public BSTMap() {
        root = null;
    }

    /**
     * Put (add a key-value pair) into this BST.  If the key already exists, the old
     * value will be overwritten with the new one.
     */
    public void put(K newKey, V newValue) {
        // If the tree is empty, create a new node as the root
        if (root == null) {
            // Creating the newNode to add
            Node<K, V> newNode = new Node<>();
            newNode.key = newKey;
            newNode.value = newValue;
            root = newNode;
        }
        else {
            put(root, newKey, newValue);
        }
    }

    /**
     * Helper function for put.
     */
    private void put(Node<K, V> curr, K newKey, V newValue) {
        // newKey already exists, so overwrite the old value with the new value
        if (curr.key.compareTo(newKey) == 0) {
            curr.value = newValue;
        }
        // The key should be inserted in the left branch
        else if (newKey.compareTo(curr.key) < 0) {
            // If the left branch is empty, add the newNode
            if (curr.left == null) {
                // Creating the newNode to add
                Node<K, V> newNode = new Node<>();
                newNode.key = newKey;
                newNode.value = newValue;

                curr.left = newNode;
            }
            else {
                put(curr.left, newKey, newValue);
            }
        }
        // The key should be inserted in the right branch
        else {
            // If the right branch is empty, all the newNode
            if (curr.right == null) {
                // Creating the newNode to add
                Node<K, V> newNode = new Node<>();
                newNode.key = newKey;
                newNode.value = newValue;

                curr.right = newNode;
            }
            else {
                put(curr.right, newKey, newValue);
            }
        }
    }

    /**
     * Get a value from this BST, based on its key.  If the key doesn't already exist in the BST,
     * this method returns null.
     */
    public V get(K searchKey) {
        return get(root, searchKey);
    }

    /**
     * Helper function for get.
     */
    private V get(Node<K, V> curr, K searchKey) {
        // We have reached a leaf node, so the key is not in tree
        if (curr == null)
            return null;
        // The key has been found
        else if (searchKey.compareTo(curr.key) == 0) {
            return curr.value;
        }
        // The searchKey is too small, so we search left
        else if (searchKey.compareTo(curr.key) < 0) {
            return get(curr.left, searchKey);
        }
        // The searchKey is too big, so we search right
        else {
            return get(curr.right, searchKey);
        }
    }

    /**
     * Test if a key is present in this BST.  Returns true if the key is found, false if not.
     */
    public boolean containsKey(K searchKey) {
        V searchValue = get(root, searchKey);
        return searchValue != null;
    }

    /**
     * Given a key, remove the corresponding key-value pair from this BST.  Returns true
     * for a successful deletion, or false if the key wasn't found in the tree.
     */
    public boolean remove(K removeKey) {
        // Will point to the node to be deleted.
        Node<K, V> curr = root;
        // Will point to the parent of the node to be deleted.
        Node<K, V> parent = null;
        // Descend through the tree, looking for the node that contains removekey.
        // Stop when we find it, or when we encounter a null pointer.
        while (curr != null && !(curr.key.compareTo(removeKey) == 0)) {
            parent = curr;
            if (removeKey.compareTo(curr.key) < 0){
                curr = curr.left;
            }
            else {
                curr = curr.right;
            }
        }
        // At this point, curr is null, or we’ve successfully found removeKey in the tree.
        // If curr is null, removeKey was not in the tree
        if (curr == null)
            return false;
        // We’ve found removeKey at the "curr" node, so remaining code will delete curr from the tree.
        // Handling 2−child situation first.
        if (curr.left != null && curr.right != null) {
            // Find inorder successor of curr.
            Node<K, V> successor = curr.right;
            Node<K, V> successorParent = curr;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            // Copy the inorder successor’s key and value into curr.
            curr.key = successor.key;
            curr.value = successor.value;
            // Code below will delete the successor node, which is guaranteed to have 0 children or 1 child.
            curr = successor;
            parent = successorParent;
        }
        // Handling 0−child or 1−child situation.
        // Will point to the subtree of curr that exists, if there is one, or null if it has 0 children.
        Node<K, V> subtree;
        if (curr.left == null && curr.right == null) {
            subtree = null;
        }
        else if (curr.left != null) {
            subtree = curr.left;
        }
        else {
            subtree = curr.right;
        }
        // Attach subtree to the correct child pointer of the parent node, if it exists.
        // If there is no parent, then we are deleting the root node, and the subtree becomes the new root.
        if (parent == null) {
            root = subtree;
        }
        // Deleting parent’s left child.
        else if (parent.left == curr) {
            parent.left = subtree;
        }
        // Deleting parent’s left child.
        else {
            parent.right = subtree;
        }
        // Return true as it was a successful deletion.
        return true;
    }

    /**
     * Return the number of key-value pairs in this BST.
     */
    public int size() {
        return size(root);
    }

    /**
     * Helper function for size.
     */
    private int size(Node<K, V> curr) {
        // There are no more pairs to count
        if (curr == null) {
            return 0;
        }
        // Descend through the left and right halves of the tree and count the number of key-value pairs
        else {
            int sizeLeft = size(curr.left);
            int sizeRight = size(curr.right);
            return( sizeLeft + sizeRight + 1);
        }
    }

    /**
     * Return the height of this BST.
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper function for height.
     */
    private int height(Node<K, V> curr) {
        // The tree is empty or it's a leaf node
        if (curr == null) {
            return -1;
        }
        // Calculating the height of the left half and right half, and returning the max height
        else {
            int leftHeight = height(curr.left);
            int rightHeight = height(curr.right);
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            }
            else {
                return rightHeight + 1;
            }
        }
    }

    /**
     * Return a List of the keys in this BST, ordered by a preorder traversal.
     */
    public List<K> preorderKeys() {
        ArrayList<K> preorderList = new ArrayList<>();
        return preorderKeys(root, preorderList);
    }

    /**
     * Helper function for preorderKeys.
     */
    private List<K> preorderKeys(Node<K, V> curr, ArrayList<K> preorderList) {
        if (curr != null) {
            preorderList.add(curr.key);
            preorderKeys(curr.left, preorderList);
            preorderKeys(curr.right, preorderList);
        }
        return preorderList;
    }

    /**
     * Return a List of the keys in this BST, ordered by a inorder traversal.
     */
    public List<K> inorderKeys() {
        ArrayList<K> inorderList = new ArrayList<>();
        return inorderKeys(root, inorderList);
    }

    /**
     * Helper function for inorderKeys.
     */
    private List<K> inorderKeys(Node<K, V> curr, ArrayList<K> inorderList) {
        if (curr != null) {
            inorderKeys(curr.left, inorderList);
            inorderList.add(curr.key);
            inorderKeys(curr.right, inorderList);
        }
        return inorderList;
    }

    /**
     * Return a List of the keys in this BST, ordered by a postorder traversal.
     */
    public List<K> postorderKeys() {
        ArrayList<K> postorderList = new ArrayList<>();
        return postorderKeys(root, postorderList);
    }

    /**
     * Helper function for inorderKeys.
     */
    private List<K> postorderKeys(Node<K, V> curr, ArrayList<K> postorderList) {
        if (curr != null) {
            postorderKeys(curr.left, postorderList);
            postorderKeys(curr.right, postorderList);
            postorderList.add(curr.key);
        }
        return postorderList;
    }

    /**
     * It is very common to have private classes nested inside other classes.  This is most commonly used when
     * the nested class has no meaning apart from being a helper class or utility class for the outside class.
     * In this case, this Node class has no meaning outside of this BSTMap class, so we nest it inside here
     * so as to not prevent another class from declaring a Node class as well.
     *
     * Note that even though the members of node are public, because the class itself is private
     */
    private static class Node<K extends Comparable<K>, V> {
        public K key = null;
        public V value = null;
        public Node<K, V> left = null;     // you may initialize member variables of a class when they are defined;
        public Node<K, V> right = null;    // this behaves as if they were initialized in a constructor.
    }
}
