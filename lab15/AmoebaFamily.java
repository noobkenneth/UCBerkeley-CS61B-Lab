import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/* An AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {

    /* ROOT is the root amoeba of this AmoebaFamily */
    private Amoeba root = null;


    /* Creates an AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Adds a new Amoeba with CHILDNAME to this AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Prints the name of all Amoebas in this AmoebaFamily in preorder, with
       the ROOT Amoeba printed first. Each Amoeba should be indented four spaces
       more than its parent. */
    public void print() {
        int i = 0;
        if (root != null) {
            AmoebaFamily.printHelper(i, root);
        }
    }

    public static void printHelper(int i, Amoeba newAmoeba) {
        if (newAmoeba.children == null) {
            System.out.println(newAmoeba.name);
        } else {
            String s = "";
            for (int j = 0; j <  4 * i; j++) {
                s += " ";
            }
            System.out.println(s + newAmoeba.name);
            Iterator<Amoeba> toIterate = newAmoeba.children.iterator();
            while (toIterate.hasNext()) {
                AmoebaFamily.printHelper(i + 1, toIterate.next());
            }
        }
    }


    /* Returns the length of the longest name in this AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this AmoebaFamily. */
    public String longestName() {

        return "";
    }

    /* Returns an Iterator for this AmoebaFamily. */
    public Iterator<Amoeba> iterator() {
        return new AmoebaDFSIterator();
    }

    /* Creates a new AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        System.out.println("Here's the family:");
        family.print();
    }

    /* An Amoeba is a node of an AmoebaFamily. */
    public static class Amoeba {

        private String name;
        private Amoeba parent;
        private ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen,
                                         a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }



    }

    /* An Iterator class for the AmoebaFamily, running a DFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaDFSIterator implements Iterator<Amoeba> {

        private Stack<Amoeba> x = new Stack<Amoeba>();

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */
        public AmoebaDFSIterator() {
            if (root != null) {
                x.push(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !x.empty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there are no elements");
            }

            Amoeba newAmoeba = x.pop();
            if (newAmoeba.children != null) {
                int sizeOfChild = newAmoeba.children.size();
                while (sizeOfChild > 0) {
                    x.push(newAmoeba.children.get(sizeOfChild - 1));
                    sizeOfChild -= 1;
                }
            }
            return newAmoeba;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* An Iterator class for the AmoebaFamily, running a BFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {

        private Queue<Amoeba> x = new LinkedList<>();


        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */
        public AmoebaBFSIterator() {
            if (root != null) {
                x.add(root);
            }
        }


        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !x.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there are no elements");
            }

            Amoeba newAmoeba = x.poll();
            if (newAmoeba.children != null) {
                int sizeOfChild = newAmoeba.children.size();
                int a = 0;
                while (a < sizeOfChild) {
                    x.add(newAmoeba.children.get(a));
                    a += 1;
                }
            }
            return newAmoeba;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
