import java.util.Arrays;
public class CodingChallenges {

    /**
     * Return the missing number from an array of length N - 1 containing all
     * the values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        Arrays.sort(values);
        int number = 0;
        for (int i = 0; i < values.length ; i++) {
            if (values[i] == number) {
                number += 1;
            } else {
                return number;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] x = {0,1,2,3,4,5,6,7,9};
        int[] y = {5,7,3,12,2,6};
        System.out.println(missingNumber(x));
        System.out.println(sumTo(y, 23));
        System.out.println(isPermutation("racecar","carrace"));
    }

    /** Returns true if and only if two integers in the array sum up to n. */
    public static boolean sumTo(int[] values, int n) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] + values[j] == n) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        char tempArrays1[] = s1.toCharArray();
        char tempArrays2[] = s2.toCharArray();
        Arrays.sort(tempArrays1);
        Arrays.sort(tempArrays2);
        String t1 = new String(tempArrays1);
        String t2 = new String(tempArrays2);
        System.out.println(tempArrays1);
        System.out.println(tempArrays2);
        System.out.println(t1.equals(t2));
        if (t1.equals(t2)) {
            return true;
        }
        return false;
    }
}
