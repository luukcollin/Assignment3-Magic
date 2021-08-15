import java.util.List;

public class Divider {
    private List<Coin> values;

    public Divider(List<Coin> values){
        this.values = values;
    }


    /**
     * Define how large each separate array will be after dividing the big array into smaller parts for each thread
     * @param amountOfArrays amount of Arrays is same as amount of threads that are being used
     * @return array with the sizes of each separateArray
     */
    public int[] getSeparateArraySizes(int amountOfArrays){
        int leftOvers = this.values.size() % amountOfArrays;
        int normalArraySize = (this.values.size() - leftOvers) / amountOfArrays;
        int[] arraySizes = new int[amountOfArrays];

        for(int i = 0; i < arraySizes.length -1; i++){
            arraySizes[i] = normalArraySize;
        }
        arraySizes[arraySizes.length -1] = normalArraySize + leftOvers;

        return arraySizes;
    }
}
