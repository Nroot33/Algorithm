import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class KnapsackTest {
    private static ArrayList<Integer> itemNum = new ArrayList<Integer>();
    private static ArrayList<Integer> value = new ArrayList<Integer>();
    private static ArrayList<Integer> weight = new ArrayList<Integer>();

    public static void main(String[] var0) {
        Scanner scanner = new Scanner(System.in);
        readFile("data10_knapsack.txt");
        System.out.print("배낭의 사이즈를 입력하세요 (0~50) : ");
        knapsack(scanner.nextInt());
    }

    public static void knapsack(int knapsackSize) {
        int itemSize = itemNum.size();
        int cmp;
        int[][] opt = new int[itemSize + 1][knapsackSize + 1];
        int[][] p = new int[itemSize + 1][knapsackSize + 1];

        for (int i = 1; i < itemSize + 1; i++) {
            for (int j = 0; j < knapsackSize + 1; j++) {
                opt[i][j] = opt[i - 1][j];
                if (weight.get(i - 1) <= j && opt[i][j] < (cmp = value.get(i - 1) + opt[i - 1][j - weight.get(i - 1)])) {
                    opt[i][j] = cmp;
                    p[i][j] = i - 1;
                }
            }
        }

        printKnapsack(opt, p, weight, itemSize, knapsackSize);
    }

    public static void printKnapsack(int[][] opt, int[][] p, ArrayList<Integer> weight, int itemSize, int knapsackSize){
        for (int[] i : opt) {
            for (int j : i)
                System.out.print(String.format("%" + (opt[itemSize][knapsackSize] / 10 + 1) + "d", j));
            System.out.println();
        }
        System.out.print("max : " + opt[itemSize][knapsackSize]+"\nitem : ");
        printWeight(p, weight, itemSize, knapsackSize);
    }

    public static void printWeight(int[][] p, ArrayList<Integer> weight, int i, int j) {
        if(i < 0 || j < 0)
            return;
        else if(j == 1)
            System.out.print(i + " ");
        else if (p[i][j] == 0)
            printWeight(p, weight, i - 1, j);
        else {
            printWeight(p, weight, i - 1, j - weight.get(i - 1));
            System.out.print(i + " ");
        }
    }

    public static void readFile(String filename) {
        String input;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    itemNum.add(Integer.parseInt(stringTokenizer.nextToken()));
                    value.add(Integer.parseInt(stringTokenizer.nextToken()));
                    weight.add(Integer.parseInt(stringTokenizer.nextToken()));
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}