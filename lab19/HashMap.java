import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;



public class HashMap<K, V> implements Map61BL<K, V> {

    /* Instance variables here? */

    public LinkedList<Entry<K, V>>[] entry;
    //public LinkedList<LinkedList<Entry<K, V>>> entries;
    int size;
    float loadFactor;

    public HashMap() {
        this.entry = new LinkedList[16];
        this.size = 0;
        this.loadFactor = (float) 0.75;
    }

    public HashMap(int initialCapacity) {
        this.entry = new LinkedList[initialCapacity];
        this.size = 0;
        loadFactor = (float) 0.75;
    }

    public HashMap(int initialCapacity, float loadFactor) {
        this.entry = new LinkedList[initialCapacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public int hash(K key) {
        return (int) Math.floorMod((key.hashCode()), entry.length);

    }

    public int capacity() {
        return entry.length;
    }

    public int size() {
        return this.size;
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */


    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        if (entry[hash(key)] != null) {
            for (int i = 0; i < entry[hash(key)].size(); i++) {
                if (entry[hash(key)].get(i).key.equals(key)) {
                    return true;
                }
            }

        }
        return false;
    }



    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */


    public V get(K key) {
        for (int i = 0; i < entry[hash(key)].size(); i++) {
            if (entry[hash(key)].get(i).key.equals(key)) {
                return entry[hash(key)].get(i).value;
            }
        }

        return null;
    }


    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */

    public void put(K key, V value) {

        if (containsKey(key)) {
            for (int i = 0; i < entry[hash(key)].size(); i++) {
                if (entry[hash(key)].get(i).key.equals(key)) {
                    entry[hash(key)].get(i).value = value;

                }
            }
        } else {
            if (entry[hash(key)] == null) {
                entry[hash(key)] = new LinkedList<>();
                // entry[hash(key)].add(new Entry(key, value));

            }
            entry[hash(key)].add(new Entry(key, value));
            size += 1;
        }



        float loadfactor = (float) size /(float) entry.length;
        if (loadfactor > loadFactor) {
            resize((entry.length * 2));
        }
    }

    public void resize(int capacity) {
        LinkedList<Entry<K,V>>[] oldEntry = entry;
        entry = new LinkedList[capacity];
        this.size = 0;
        // LinkedList<Entry<K, V>>[]
        //HashMap newHashMap = new HashMap(capacity);
        for (int i = 0; i < oldEntry.length; i++ ) {
            if (oldEntry[i] == null) {
                // don't have to copy over
                continue;
            } else {
                for (int j = 0; j < oldEntry[i].size(); j++) {
                    this.put(oldEntry[i].get(j).key, oldEntry[i].get(j).value);
                    // this.size += 1;
                }
            }
        }

    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */

    public V remove(K key) {
        if (!containsKey(key)) {
            System.out.println("sdklfjasgjidsa");
            return null;
        } else {
            for (int i = 0; i < entry[hash(key)].size(); i++) {
                if (entry[hash(key)].get(i).key.equals(key)) {
                    V toReturn = entry[hash(key)].get(i).value;
                    entry[hash(key)].remove();
                    size -= 1;
                    return toReturn;

                }
            }
        }

        return null;
    }

    public void clear() {
        for (int i = 0; i < entry.length; i++) {
            entry[i] = null;

        }
        size = 0;
        return;
    }

    public boolean remove(K key, V value) {
        for (int i = 0; i < entry.length; i++) {
            for (int j = 0; j < entry[i].size(); j++) {
                if (entry[i].get(j).key.equals(key) && entry[i].get(j).value.equals(value)) {
                    entry[i].get(j).key = null;
                    size -= 1;
                    return true;
                }
            }
        }

        return false;
    }

    public Iterator<K> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<K> {
        LinkedList<Entry<K,V>>[] x = entry;
        int checkIndex = 0;
        int sz = 0;
        int checkIndexInside = 0;

        public K next() {
            for (int i = checkIndex; i < entry.length; i++) {
                if (entry[i] != null) {
                    for (int j = checkIndexInside; j < entry[i].size(); j++) {
                        checkIndex = i;
                        checkIndexInside = j + 1;
                        sz += 1;
                        return entry[i].get(j).key;
                    }
                }
                checkIndexInside = 0;
            }
            return null;
        }

        public boolean hasNext() {
            return sz < size();
        }
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
