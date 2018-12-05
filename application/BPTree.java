package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        this.root = null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        //root is null, start out with a leaf node
        if(root == null) {
           root = new LeafNode();
        }
        root.insert(key, value);
        //see if the root is too big
        if(root.isOverflow()) {
            //create a new root, propagate the middle key up, and slit the old root
            InternalNode newRoot = new InternalNode();
            K middleKey = root.keys.get(branchingFactor / 2);
            newRoot.keys.add(middleKey);
            Node sibling = root.split();
            newRoot.children.add(root);
            newRoot.children.add(sibling);
            root = newRoot;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        // TODO : Complete
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            //create list of keys
            this.keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            this.children = new ArrayList<Node>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            //get the first child and then the first leaf key of that node
            if(this.children.isEmpty()) {
                //return null if there are no children
                return null;
            }
            return this.children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(this.keys.size() >= branchingFactor) {
                return true;
            }
            return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            //if this is the first key in the node
            if(this.keys.isEmpty()) {
                this.keys.add(key);
                this.children.add(new LeafNode());
                this.children.get(0).insert(key, value);
            }
            //normal case
            //loop over keys in node to see where to insert key
            int index;
            for(index = 0; index < this.keys.size(); index++) {
                int comp = key.compareTo(this.keys.get(index));
                //insert to the left of this node, equals go to the right
                if(comp < 0) {
                    this.children.get(index).insert(key, value);
                    //see if we need to split the child
                    if(this.children.get(index).isOverflow()) {
                        //get the middle key from the child and add it to the current node
                        K middleKey = this.children.get(index).keys.get(branchingFactor / 2);
                        this.keys.add(index + 1, middleKey);
                        //split the child and add it as a child to the current node
                        Node sibling = this.children.get(index).split();
                        this.children.add(index + 1, sibling);
                    }
                    return;
                }
            }
            //reached the end of the node, so insert to the right
            if(this.children.size() <= index) {
                // create leaf node if there was not one already there
                this.children.add(index, new LeafNode());
            }
            this.children.get(index).insert(key, value);
            //see if we need to split the child
            if(this.children.get(index).isOverflow()) {
                //get the middle key from the child and add it to the current node
                K middleKey = this.children.get(index).keys.get(branchingFactor / 2);
                this.keys.add(index, middleKey);
                //split the child and add it as a child to the current node
                Node sibling = this.children.get(index).split();
                this.children.add(index, sibling);
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            int middleIndex = this.keys.size() / 2;
            //middle key has already been propagated up so remove it from the internal node
            this.keys.remove(middleIndex);
            //split the child of the middle index
            Node newChild = this.children.get(middleIndex).split();
            this.children.add((middleIndex + 1), newChild);
            InternalNode sibling = new InternalNode();
            for(int index = middleIndex; index < this.keys.size(); index++) {
                //remove keys and children from current node and add to sibling
                sibling.keys.add(this.keys.remove(middleIndex));
                sibling.children.add(this.children.remove(middleIndex + 1));
            }
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            this.values = new ArrayList<V>();
            this.next = null;
            this.previous = null;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            if(this.keys.size() == 0) {
                return null;
            }
            return this.keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(this.values.size() >= branchingFactor) {
                return true;
            }
            return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            //look through keys in leaf to see where to insert the key value pair
            int index;
            for(index = 0; index < this.keys.size(); index++) {
                //stop looking if the key to insert is less than the current key in the leaf
                int comp = key.compareTo(this.keys.get(index));
                if(comp <= 0) {
                    break;
                }
            }
            //insert key and value into lists
            this.keys.add(index, key);
            this.values.add(index, value);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode sibling = new LeafNode();
            int middleIndex = this.keys.size() / 2;
            for(int index = middleIndex; index < this.keys.size(); index++) {
                //take key from leaf and add to sibling
                sibling.keys.add(this.keys.remove(middleIndex));
                sibling.values.add(this.values.remove(middleIndex));
            }
            //fix previous and next pointers
            sibling.next = this.next;
            sibling.previous = this;
            this.next = sibling;
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        //List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        //System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
