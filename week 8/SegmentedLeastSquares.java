import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SegmentedLeastSquares {
    private static int n, c;
    private static double[] dotX, dotY;

    public static void main(String[] var0) {
        readFile("data08.txt");
        segmentedLeastSquares();
    }

    public static void segmentedLeastSquares() {
        int size;
        double sX, sXX, sY, sXY;
        double[] m = new double[n];
        double[][] a = new double[n][n];
        double[][] b = new double[n][n];
        double[][] e = new double[n][n];
        int[] p = new int[n];

        m[0] = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                size = j - i + 1;
                sX = 0;
                sXX = 0;
                sY = 0;
                sXY = 0;
                for (int k = i; k <= j; k++) {
                    sX += dotX[k];
                    sXX += dotX[k] * dotX[k];
                    sY += dotY[k];
                    sXY += dotX[k] * dotY[k];
                }
                a[i][j] = (size * sXY - sX * sY) / (size * sXX - Math.pow(sX, 2));
                b[i][j] = (sY - a[i][j] * sX) / size;
                for (int k = i; k <= j; k++)
                    e[i][j] += Math.pow((dotY[k] - a[i][j] * dotX[k] - b[i][j]), 2);
                if (Double.isNaN(e[i][j]))
                    e[i][j] = 0;
            }
        }

        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                if (i == 0) {
                    m[j] = e[i][j] + c;
                    p[j] = 0;
                } else if ((e[i][j] + c + m[i - 1]) < m[j]) {
                    m[j] = e[i][j] + c + m[i - 1];
                    p[j] = i;
                }
            }
        }
        System.out.println("Cost of the optimal solution : " + String.format("%.6f", m[n - 1]) + "\n\nAn optimal solution :");
        segmentPrint(p, a, b, e, p[n - 1], n - 1);
    }

    public static void segmentPrint(int[] p, double[][] a, double[][] b, double[][] e, int start, int end) {
        if (start != 0)
            segmentPrint(p, a, b, e, p[start], start - 1);
        System.out.println("[Segment " + (start + 1) + " - " + (end + 1) + "] : y = " + String.format("%.6f", a[start][end]) + " * x + " + String.format("%.6f", b[start][end]) + " // square error : " + String.format("%.6f", e[start][end]));
    }

    public static void readFile(String filename) {
        ArrayList<Double> arrayList1 = new ArrayList<Double>();
        ArrayList<Double> arrayList2 = new ArrayList<Double>();
        String input;
        double temp;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                n = Integer.parseInt(stringTokenizer.nextToken());
                while (stringTokenizer.hasMoreTokens()) {
                    temp = Double.parseDouble(stringTokenizer.nextToken());
                    if (!stringTokenizer.hasMoreTokens()) {
                        c = (int) temp;
                    } else {
                        arrayList1.add(temp);
                        arrayList2.add(Double.parseDouble(stringTokenizer.nextToken()));
                    }
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        dotX = Arrays.stream(arrayList1.toArray()).mapToDouble(i -> (double) i).toArray();
        dotY = Arrays.stream(arrayList2.toArray()).mapToDouble(i -> (double) i).toArray();
    }
}