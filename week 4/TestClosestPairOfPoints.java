import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestClosestPairOfPoints {
    public static void main(String[] args) {
        String input;
        double[][] arr;
        int n = 0;
        ArrayList<double[]> arrayList = new ArrayList<>();

        try {
            File file = new File("data04_closest.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                double[] temp = {Double.parseDouble(stringTokenizer.nextToken()), Double.parseDouble(stringTokenizer.nextToken())};
                arrayList.add(temp);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        arr = new double[arrayList.size()][2];

        System.out.print("Input Data : ");
        for (int i = 0; i < arrayList.size(); i++) {
            arr[i][0] = arrayList.get(i)[0];
            arr[i][1] = arrayList.get(i)[1];
            System.out.println(arr[i][0] + ", " + arr[i][1]);
        }

        System.out.println(closestPair(arr, 0, arr.length - 1));
    }


    public static double closestPair(double[][] arr, int l, int r) {
        Arrays.sort(arr, new CustomComparatorX());
        return calClosestPair(arr, l, r, new CustomComparatorY());
    }

    public static double calClosestPair(double[][] arr, int l, int r, CustomComparatorY cpt) {
        if (r - l < 3){
            double result = Double.MAX_VALUE;
            for (int i = l; i < r; i++)
                for (int j = i + 1; j <= r; j++)
                    result = Math.min(result, dist(arr[i], arr[j]));
            return result;
        }

        int mid = (l + r) / 2;

        double leftHalf = calClosestPair(arr, l, mid, cpt);
        double rightHalf = calClosestPair(arr, mid + 1, r, cpt);
        double result = Math.min(leftHalf, rightHalf);

        ArrayList<double[]> tempArr = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if ((arr[i][0] - arr[mid][0]) < result)
                tempArr.add(arr[i]);
        }

        tempArr.sort(cpt);

        for (int i = 0; i < tempArr.size() - 1; i++)
            for (int j = i + 1; j < tempArr.size() && (tempArr.get(j)[1] - tempArr.get(i)[1]) < result; j++)
                result = Math.min(result, dist(tempArr.get(i), tempArr.get(j)));

        return result;
    }

    static class CustomComparatorX implements Comparator<double[]> {
        @Override
        public int compare(double[] a, double[] b) {
            return Double.compare(a[0], b[0]);
        }
    }

    static class CustomComparatorY implements Comparator<double[]> {
        @Override
        public int compare(double[] a, double[] b) {
            return Double.compare(a[1], b[1]);
        }
    }

    public static double dist(double[] a, double[] b) {
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
    }
}



