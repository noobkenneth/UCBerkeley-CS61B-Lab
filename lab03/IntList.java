/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 */
public class IntList {
    public int first;
    public IntList rest;


    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Returns an IntList consisting of the given values. */
    public static IntList of(int... values) {
        if (values.length == 0) {
            return null;
        }
        IntList p = new IntList(values[0], null);
        IntList front = p;
        for (int i = 1; i < values.length; i++) {
            p.rest = new IntList(values[i], null);
            p = p.rest;
        }
        return front;
    }

    /** Returns the size of the list. */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + rest.size();
    }

    /** Returns [position]th value in this list. */
    public int get(int position) {
        if (position == 0) {
            return first;

        }
        return rest.get(position-1);
    }
        // TODO: YOUR CODE HERE

    

    /** Returns the string representation of the list. */
    public String toString() {
        if (rest == null) {
            return Integer.toString(first);
        }
        return Integer.toString(first) + " " + rest.toString();
        // TODO: YOUR CODE HERE
        
    }

    /** Returns whether this and the given list or object are equal. */
    public boolean equals(Object o) {
        IntList other = (IntList) o;
        int x = this.size();
        int y = other.size();
        if(y != x){
            return false;
        }
        else{

        

            for (int i = 0; i <  x ; i++) {
                if (other.get(i) != this.get(i)) {
                    return false;
                }
            }
        }  
        return true;
        // TODO: YOUR CODE HERE
        
    }

    public static void main(String[] args) {
        IntList L = new IntList(5,
                        new IntList(10,
                            new IntList(15, null)));
        IntList M = new IntList(5,
                        new IntList(10,
                            new IntList(15, null)));

        System.out.println(L.equals(M));
    }
}
