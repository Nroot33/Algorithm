import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class TestCountingInversions {
    public static void main(String[] args) {
        String input;
        int[] arr;
        List<Integer> arrayList = new ArrayList<>();

        try {
            File file = new File("data04_inversion.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            input = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        arr = Arrays.stream(arrayList.toArray()).mapToInt(i -> (int) i).toArray();
        System.out.print("Input Data : ");
        printArray(arr);
        System.out.println("Output Data : " + sortAndCount(arr, 0, arr.length - 1));
    }

    public static int sortAndCount(int[] arr, int left, int right) {
        int mid = (left + right) / 2, sum;
        if (left >= right)
            return 0;
        sum = sortAndCount(arr, left, mid);
        sum += sortAndCount(arr, mid + 1, right);
        sum += mergeAndCount(arr, left, right);
        return sum;
    }

    public static int mergeAndCount(int[] arr, int left, int right) {
        int mid = (right + left) / 2, tP = 0, i, j, sum = 0;
        int[] temp = new int[right - left + 1];

        for (i = left, j = mid + 1; (i < mid + 1) && (j < right + 1); ) {
            if (arr[i] < arr[j]) {
                temp[tP++] = arr[i++];
            } else {
                sum += mid - i + 1;
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

        return sum;
    }

    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
