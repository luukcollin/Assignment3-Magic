import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Merger {

    public Merger(){}

    /**
     * Merge two arrays that sorted in ascending order to create one array that is sorted in ascending order. Source
     * https://www.geeksforgeeks.org/merge-two-sorted-arrays/
     * @param sortedArray1 an array in ascending order
     * @param sortedArray2 an array in ascending order
     * @return
     */
    public List<Coin> mergeInOrder(List<Coin> sortedArray1, List<Coin> sortedArray2) {
        List<Coin> sorted = new ArrayList<>();
        int i = 0, j = 0, k = 0;

        // Traverse both array
        while (i < sortedArray1.size() && j < sortedArray2.size()) {
            // Check if current element of first
            // array is smaller than current element
            // of second array. If yes, store first
            // array element and increment first array
            // index. Otherwise do same with second array
            if (sortedArray1.get(i).compareTo(sortedArray2.get(j)) < 0) {
                sorted.add(k++, sortedArray1.get(i++));
            } else {
                sorted.add(k++, sortedArray2.get(j++));
            }
        }

        // Store remaining elements of first array
        while (i < sortedArray1.size()){
            sorted.add(k++, sortedArray1.get(i++));
        }
        // Store remaining elements of second array
        while (j < sortedArray2.size()){
            sorted.add(k++, sortedArray2.get(j++));
        }

        return sorted;
//        return Collections.synchronizedList(sorted);
    }
}


