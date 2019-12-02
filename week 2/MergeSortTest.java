import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MergeSortTest {
    public static void main(String[] args) {
        String input;
        int[] arr;
        List<Integer> arrayList = new ArrayList<>();

        try {
            File file = new File("data02.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            input = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
            while(stringTokenizer.hasMoreTokens()){
                arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        arr = Arrays.stream(arrayList.toArray()).mapToInt(i -> (int)i).toArray();
        MergeSortTest sort = new MergeSortTest();
        System.out.print("Init : ");
        sort.printArray(arr);

        sort.mergeSort(arr, 0, arr.length - 1);
        System.out.print("Merge Sort : ");
        sort.printArray(arr);
    }

    public void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void mergeSort(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (left >= right)
            return;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, right);
    }

    public void merge(int[] arr, int left, int right) {
        int mid = (right + left) / 2, tP = 0, i, j;
        int[] temp = new int[right - left + 1];

        for (i = left, j = mid + 1; (i < mid + 1) && (j < right + 1); ) {
            if (arr[i] < arr[j]) {
                temp[tP++] = arr[i++];
            } else {
                temp[tP++] = arr[j++];
            }
        }

        while (i < mid + 1) {
            temp[tP++] = arr[i++];
        }

        while ((j < right + 1)) {
            temp[tP++] = arr[j++];
        }

        for (i = left, j = 0; i <= right && j < tP; i++, j++) {
            arr[i] = temp[j];
        }
    }
}
