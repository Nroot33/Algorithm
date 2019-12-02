import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestPrim {
    private static ArrayList<String> vertex;
    private static int[] key;
    private static String[] parent;
    private static int[][] edge;

    public static void main(String[] args) {
        readFile("data13_prim.txt");
        prim(0);
    }

    public static void prim(int s) {
        PriorityQueue<Integer> Q = new PriorityQueue<>(vertex.size(), new PQComp());
        ArrayList<Integer> S = new ArrayList<>();
        int mst = 0;

        key = new int[vertex.size()];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[s] = 0;
        parent = new String[vertex.size()];
        parent[s] = " ";

        for (int i = 0; i < vertex.size(); i++)
            Q.add(i);

        while (!Q.isEmpty()) {
            int u = Q.poll();
            S.add(u);

            for (int v : Q) {
                if (edge[u][v] != Integer.MAX_VALUE && key[v] > edge[u][v]) {
                    key[v] = edge[u][v];
                    parent[v] = vertex.get(u);
                }
            }
            System.out.println("w(" + parent[u] + "," + vertex.get(u) + ") = " + key[u]);

            mst += key[u];

            if (!Q.isEmpty())
                Q.add(Q.poll()); // Priority Queue Resort
        }
        System.out.println("\nw(MST) = " + mst);
    }

    public static void readFile(String filename) {
        String input;

        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            input = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(input, ",");

            // vertex
            vertex = new ArrayList<>();
            while (stringTokenizer.hasMoreTokens()) {
                vertex.add(stringTokenizer.nextToken());
            }

            // edge
            edge = new int[vertex.size()][vertex.size()];
            for (int[] i : edge)
                Arrays.fill(i, Integer.MAX_VALUE);
            while ((input = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    int u = vertex.indexOf(stringTokenizer.nextToken());
                    int v = vertex.indexOf(stringTokenizer.nextToken());
                    int w = Integer.parseInt(stringTokenizer.nextToken());
                    edge[u][v] = w;
                    edge[v][u] = w;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    static class PQComp implements Comparator<Integer> {
        @Override
        public int compare(Integer p1, Integer p2) {
            return Integer.compare(key[p1], key[p2]);
        }
    }
}
