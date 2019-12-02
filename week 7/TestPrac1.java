import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TestPrac1 {
    public static void main(String[] args) {
        int[] arr = readFile("data07_a.txt");
        Scanner scan = new Scanner(System.in);
        System.out.print("input x : ");
        int x = scan.nextInt();
        System.out.println("result : " + solve(arr, x));
    }

    public static int solve(int[] arr, int x){
        int l = 0, r = arr.length, mid = (r + l - 1) / 2;

        while (x != arr[mid]) {
            if (x < arr[mid])
                r = mid;

            else
                l = mid + 1;

            mid = (r + l - 1) / 2;
        }

       return arr[mid];
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