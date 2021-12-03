import java.util.ArrayList;
import java.util.List;

public class BSTTester {
    public static void main(String[] args)
    {
        //testPutAndGet1();
        //testPutAndGet2();
        //testPutAndGet3();
        //testContainsKey();;
        //testRemove();
        //testSize();
        testTraversals1();
        testTraversals2();
        testTraversals3();
    }

    // One put and one get per map (tree won't be deeper than the root).
    private static void testPutAndGet1()
    {
        System.out.println("Testing put/get 1");
        // Simulate a bank account map.
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 50.23);
        System.out.println("Get: " + accounts.get(123)); // get the balance for account 123

        // Simulate an address book.
        BSTMap<String, String> emails = new BSTMap<String, String>();
        emails.put("Elvis Presley", "elvis@graceland.com");
        System.out.println("Get: " + emails.get("Elvis Presley")); // get Elvis's email address
    }

    // Multiple puts and gets, testing making deeper trees.
    private static void testPutAndGet2()
    {
        System.out.println("Testing put/get 2");
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 50.23);
        accounts.put(456, 100.81);
        accounts.put(78, 72.11);
        System.out.println("Get: " + accounts.get(123));
        System.out.println("Get: " + accounts.get(456));
        System.out.println("Get: " + accounts.get(78));

        BSTMap<String, String> emails = new BSTMap<String, String>();
        emails.put("Elvis Presley", "elvis@graceland.com");
        emails.put("Albus Dumbledore", "dumbledore@hogwarts.edu");
        emails.put("Dorothy Gale", "dorothy@oz.org");
        emails.put("Grace Hopper", "hopper@navy.mil");  // look her up!
        System.out.println("Get: " + emails.get("Elvis Presley"));
        System.out.println("Get: " + emails.get("Albus Dumbledore"));
        System.out.println("Get: " + emails.get("Dorothy Gale"));
        System.out.println("Get: " + emails.get("Grace Hopper"));
    }

    // Testing adding a new value to a key that already exists
    private static void testPutAndGet3()
    {
        System.out.println("Testing put/get 3");
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 20.5);
        accounts.put(456, 20.5);
        accounts.put(789, 10.8);
        System.out.println("Testing put/get 3 original accounts");
        System.out.println("Get: " + accounts.get(123));
        System.out.println("Get: " + accounts.get(456));
        System.out.println("Get: " + accounts.get(789));
        accounts.put(123, 22.1);
        accounts.put(456, 28.3);
        System.out.println("Testing put/get 3 new accounts");
        System.out.println("Get: " + accounts.get(123));
        System.out.println("Get: " + accounts.get(456));
        System.out.println("Get: " + accounts.get(789));
    }

    // Testing adding a new value to a key that already exists
    private static void testContainsKey()
    {
        System.out.println("Testing containsKey");
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 18.3);
        accounts.put(456, 20.5);
        accounts.put(789, 10.8);
        System.out.println("Contains: " + accounts.containsKey(123));
        System.out.println("Contains: " + accounts.containsKey(456));
        System.out.println("Contains: " + accounts.containsKey(789));
        System.out.println("Contains: " + accounts.containsKey(765));
        System.out.println("Contains: " + accounts.containsKey(1));
    }

    // Testing removing a key-value pair from a tree
    private static void testRemove() {
        System.out.println("Testing removes");
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 18.3);
        accounts.put(456, 20.5);
        accounts.put(789, 10.8);
        System.out.println("Contains: " + accounts.containsKey(123));
        System.out.println("Contains: " + accounts.containsKey(456));
        System.out.println("Contains: " + accounts.containsKey(789));

        accounts.remove(456);
        System.out.println("Contains: " + accounts.containsKey(456));
        System.out.println("Get: " + accounts.get(456));

        accounts.remove(123);
        System.out.println("Contains: " + accounts.containsKey(123));
        System.out.println("Get: " + accounts.get(123));

        System.out.println("Contains: " + accounts.containsKey(789));
        System.out.println("Get: " + accounts.get(789));
    }

    // Testing the size of the map
    private static void testSize() {
        System.out.println("Testing size");
        BSTMap<Integer, Double> accounts = new BSTMap<Integer, Double>();
        accounts.put(123, 18.3);
        accounts.put(456, 20.5);
        accounts.put(789, 10.8);
        System.out.println("Size: " + accounts.size()); // should be 3

        accounts.remove(123);
        System.out.println("Size: " + accounts.size()); // should be 2

        accounts.put(987, 13.4);
        accounts.put(654, 11.9);
        accounts.put(321, 123.7);
        accounts.remove(789);
        System.out.println("Size: " + accounts.size()); // should be 4
    }

    private static void testTraversals1()
    {
        System.out.println("Testing traversals 1");
        BSTMap<Integer, Integer> map = createBst(List.of(4, 2, 6, 1, 3, 5, 7));
        System.out.println("Preorder:  " + map.preorderKeys());
        System.out.println("Inorder:   " + map.inorderKeys());
        System.out.println("Postorder: " + map.postorderKeys());
        System.out.println("size=" + map.size() + " height=" + map.height());
    }

    private static void testTraversals2()
    {
        System.out.println("Testing traversals 2");
        BSTMap<Integer, Integer> map = createBst(List.of(1, 2, 3, 4, 5, 6, 7));
        System.out.println("Preorder:  " + map.preorderKeys());
        System.out.println("Inorder:   " + map.inorderKeys());
        System.out.println("Postorder: " + map.postorderKeys());
        System.out.println("size=" + map.size() + " height=" + map.height());
    }

    // Testing traversals for a more complex tree
    private static void testTraversals3() {
        System.out.println("Testing traversals 3");
        BSTMap<Integer, Integer> map = createBst(List.of(50, 25, 75, 22, 47, 70, 88, 15, 24, 44, 49, 60 ,73, 76, 99));
        System.out.println("Preorder:  " + map.preorderKeys());
        System.out.println("Inorder:   " + map.inorderKeys());
        System.out.println("Postorder: " + map.postorderKeys());
        System.out.println("size=" + map.size() + " height=" + map.height());

        System.out.println("Testing traversals 3.0");
        BSTMap<Integer, Integer> map1 = createBst(List.of(1, 2, 3, 4, 5, 6, 7));
        map1.remove(5);
        map1.remove(1);
        map1.remove(7);
        System.out.println("Inorder:   " + map1.inorderKeys());
        System.out.println("size=" + map1.size() + " height=" + map1.height());
    }

    /**
     * Helper method to create a BST map from the numbers in a list.  It just uses zero
     * for every value, since this is mostly used to test the traversal methods.
     */
    private static BSTMap<Integer, Integer> createBst(List<Integer> list)
    {
        BSTMap<Integer, Integer> map = new BSTMap<>();
        for (int x = 0; x < list.size(); x++)
        {
            map.put(list.get(x), 0);
        }
        return map;
    }
}
