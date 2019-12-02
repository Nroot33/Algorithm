import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class QuickSort {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String input, output;
        int[] quickArr, randomQuickArr;

        try {
            File file = new File("data06.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens())
                    arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        quickArr = Arrays.stream(arrayList.toArray()).mapToInt(i -> (int) i).toArray();
        randomQuickArr = Arrays.stream(arrayList.toArray()).mapToInt(i -> (int) i).toArray();
        quickSort(quickArr, 0, quickArr.length - 1);
        quickSort_withRandom(randomQuickArr, 0, randomQuickArr.length - 1);

        try {
            OutputStream outputStream = new FileOutputStream("data6_quick.txt");
            output = Arrays.toString(quickArr);
            byte[] by = output.getBytes();
            outputStream.write(by);

            outputStream = new FileOutputStream("data6_quickRandom.txt");
            output = Arrays.toString(randomQuickArr);
            by = output.getBytes();
            outputStream.write(by);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static int partition(int[] A, int p, int r) {
        int x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                i = i + 1;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        i = i + 1;
        int temp = A[i];
        A[i] = A[r];
        A[r] = temp;
        return i;
    }

    public static int randomizedPartition(int[] A, int p, int r) {
        Random random = new Random();
        int i = p + random.nextInt(r - p);
        int temp = A[i];
        A[i] = A[r];
        A[r] = temp;
        return partition(A, p, r);
    }

    public static void quickSort(int[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }

    public static void quickSort_withRandom(int[] A, int p, int r) {
        if (p < r) {
            int q = randomizedPartition(A, p, r);
            quickSort_withRandom(A, p, q - 1);
            quickSort_withRandom(A, q + 1, r);
        }
    }
}



