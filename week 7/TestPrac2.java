import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TestPrac2 {
    public static void main(String[] args) {
        int[] arrA = readFile("data07_a.txt");
        int[] arrB = readFile("data07_b.txt");
        System.out.println("result : " + solve(arrA, arrB));
    }

    public static int solve(int[] arrA, int[] arrB) {
        int n = arrA.length;
        int l = 0, r = n, midA = (l + r) / 2, midB = n - midA;

        while (l < r) {
            midA = (l + r) / 2;
            midB = n - midA;

            if (arrA[midA] < arrB[midB])
                l = midA + 1;

            else
                r = midA;
        }

        return Math.min(arrA[midA], arrB[midB]);
    }

    public static int[] readFile(String filename) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String input;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ", ");
                while (stringTokenizer.hasMoreTokens())
                    arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return Arrays.stream(arrayList.toArray()).mapToInt(i -> (int) i).toArray();
    }
}
