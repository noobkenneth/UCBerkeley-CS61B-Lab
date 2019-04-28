/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * This is a dummy implementation to allow IntListTest to compile. Replace this
 * file with your own IntList class.
 */
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /**
     * Returns an IntList consisting of the given values.
     */
    public static IntList of(int... values) {
        if (values.length == 0) {
            return null;
        }
        IntList p = new IntList(values[0], null);
        IntList front = p;
        for (int i = 1; i < values.length; i += 1) {
            p.rest = new IntList(values[i], null);
            p = p.rest;
        }
        return front;
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + rest.size();
    }

    /**
     * Returns [position]th value in this list.
     */
    public int get(int position) {
        if (position == 0) {
            return first;
        } else {
            return rest.get(position - 1);
        }
    }

    public void add(int value) {
        if (rest == null) {
            rest = new IntList(value, null);
        } else {
            rest.add(value);

        }
    }

    public int smallest() {
        int min;
        min = 99999999;
        for (int i = 0; i < this.size(); i++) {
            int f = this.get(i);
            if (f < min) {
                min = f;
            }
        }

        return min;

    }


    public int squaredSum() {
        int sqsum;
        sqsum = 0;
        for (int i = 0; i < this.size(); i++) {
            int g = this.get(i);
            sqsum += g * g;
        }
        return sqsum;
    }

    public static void dSquareList(IntList L) {
        while (L != null) {
            L.first = L.first * L.first;
            L = L.rest;
        }
    }

    public static IntList catenate(IntList A, IntList B) {
        if (A == null) {
            return B;
        } else {

            if (A == null) {
                return B;
            } else {
                return new IntList(A.first, catenate(A.rest, B));
            }
        }


    }

    public static IntList dcatenate(IntList A, IntList B) {
        if (A == null) {
            return B;
        } else {

            if (A.rest == null) {
                A.rest = B;
            } else {
                A.rest.dcatenate(A.rest, B);
            }
            return A;
        }
    }

    public boolean equals(Object o) {
        IntList other = (IntList) o;
        int x = this.size();
        int y = other.size();
        if (y != x) {
            return false;
        } else {


            for (int i = 0; i < x; i++) {
                if (other.get(i) != this.get(i)) {
                    return false;
                }
            }
        }
        return true;

    }
}
