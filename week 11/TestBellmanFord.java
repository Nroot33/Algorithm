import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TestBellmanFord {
    private static int[] vertex;
    private static int[] d;
    private static int[][] edge;

    public static void main(String[] args) {
        readFile("data11_bellman_ford_1.txt");
        bellmanFord(0);
    }

    public static boolean bellmanFord(int start) {
        initSingleSource(start);

        for (int i = 0; i < vertex.length; i++) {
            System.out.println("---------------iteration " + i + "---------------");
            for (int[] j : edge)
                relax(j[0], j[1], j[2]);
            System.out.println("iteration " + vertex[i] + " distance : " + Arrays.toString(d) + "\n");
        }
        for (int[] j : edge) {
            if (d[j[1]] > d[j[0]] + j[2]) {
                System.out.println("The graph has nagative cycle");
                return false;
            }
        }
        System.out.println("Final distance " + Arrays.toString(d));
        return true;
    }

    public static void initSingleSource(int start) {
        d = new int[vertex.length];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[vertex[start]] = 0;
    }

    public static void relax(int u, int v, int w) {
        if (d[u] != Integer.MAX_VALUE && d[v] > d[u] + w) {
            String oldState = (d[v] == Integer.MAX_VALUE) ? "inf" : String.valueOf(d[v]);
            d[v] = d[u] + w;
            String newState = (d[v] == Integer.MAX_VALUE) ? "inf" : String.valueOf(d[v]);

            System.out.println("Update distance of " + vertex[v] + " from " + oldState + " to " + newState);
        }
    }

    public static void readFile(String filename) {
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        ArrayList<int[]> arrayList2 = new ArrayList<>();
        String input;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            input = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(input, ",");

            // vertex
            while (stringTokenizer.hasMoreTokens()) {
                arrayList1.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
            vertex = Arrays.stream(arrayList1.toArray()).mapToInt(i -> (int) i).toArray();

            // edge
            while ((input = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList2.add(new int[]{Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken())});
                }
            }
            edge = arrayList2.toArray(new int[0][]);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}