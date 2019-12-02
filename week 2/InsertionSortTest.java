import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class InsertionSortTest {
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
        InsertionSortTest sort = new InsertionSortTest();
        System.out.print("Init : ");
        sort.printArray(arr);
        
        sort.insertionSort(arr);
        System.out.print("Insertion Sort : ");
        sort.printArray(arr);
    }

    public void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void insertionSort(int[] arr) {
        for (int j = 1; j < arr.length; j++) {
            int key = arr[j];
            int i = j - 1;
            for (; i >= 0 && arr[i] > key; i--) {
                arr[i + 1] = arr[i];
            }
            arr[i + 1] = key;
        }
    }
}
