import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TestMatrixChainOrder {
    public static void main(String[] args) {
        int[][] p = readFile("data11_matrix_chain.txt");
        printMatrixChainOrder(matrixChainOrder(p));
    }

    public static int[][] matrixChainOrder(int[][] p) {
        int n = (p.length) - 1;
        int[][] m = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int l = 2; l <= n + 1; l++) {
            for (int i = 0; i <= n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i][0] * p[k][1] * p[j][1];
                    if (q < m[i][j])
                        m[i][j] = q;
                }
            }
        }
        return m;
    }

    public static void printMatrixChainOrder(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (i > j)
                    System.out.print(String.format("%11c", '-') + " ");
                else {
                    System.out.print(String.format("%11d", m[i][j]) + " ");
                }
            }
            System.out.println();
        }
    }

    public static int[][] readFile(String filename) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        int[][] r;
        String input;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList.add(new int[]{Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken())});
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        r = new int[arrayList.size()][2];
        for (int i = 0; i < r.length; i++)
            r[i] = arrayList.remove(0);

        return r;
    }
}