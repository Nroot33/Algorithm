import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TestDijkstra {
    private static int[] vertex;
    private static int[] d;
    private static int[][] edge;

    public static void main(String[] args){
        readFile("data12.txt");
        dijkstra(0);
    }

    public static void dijkstra(int s){
        ArrayList<Integer> S;
        PriorityQueue<Integer> Q;
        int u;
        int sCnt = 0, qCnt;

        d = new int[vertex.length];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[s] = 0;

        S = new ArrayList<>();
        Q = new PriorityQueue<>(vertex.length, new PQComp());
        for(int i = 0; i < vertex.length; i++)
            Q.add(i);

        while(!Q.isEmpty()){
            Q.add(Q.poll()); // Priority Queue Resort
            u = Q.poll();
            S.add(u);
            System.out.println("________________________________________________________");
            System.out.printf("\tS[%d] : d[%c] = %d\n", sCnt, (65+u), d[u]);
            System.out.println("--------------------------------------------------------");
            qCnt = 0;
            for(int v:Q){
                String qStr = String.format("\tQ[%d] : d[%c] = %d", qCnt, (65+v), d[v]);
                if(edge[u][v] != Integer.MAX_VALUE && d[v] > d[u] + edge[u][v]){
                    d[v] = d[u] + edge[u][v];
                    qStr += String.format(" -> d[%c] = %d", (65+v), d[v]);
                }
                qCnt++;
                System.out.println(qStr);
            }
            System.out.println();
            sCnt++;
        }
    }

    public static void readFile(String filename) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String input;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            input = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(input, ",");

            // vertex
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
            vertex = Arrays.stream(arrayList.toArray()).mapToInt(i -> (int) i).toArray();

            // edge
            edge = new int[vertex.length][vertex.length];
            for(int[] i:edge)
                Arrays.fill(i, Integer.MAX_VALUE);
            while ((input = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(input, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    int u = arrayList.indexOf(Integer.parseInt(stringTokenizer.nextToken()));
                    int v = arrayList.indexOf(Integer.parseInt(stringTokenizer.nextToken()));
                    int w = Integer.parseInt(stringTokenizer.nextToken());
                    edge[u][v] = w;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    static class PQComp implements Comparator <Integer> {
        @Override
        public int compare(Integer p1, Integer p2){
            return Integer.compare(d[p1], d[p2]);
        }
    }
}