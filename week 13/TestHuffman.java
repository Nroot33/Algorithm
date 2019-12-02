import java.io.*;
import java.util.*;

class TestHuffman {
    private static String[] character;

    private static String encodingInput;
    private static int[] frequency;
    private static String[] encoded;

    private static String decodingInput;
    private static HashMap<String, String> binary;


    public static void main(String[] args) {
        init();
        encoding("data13_huffman.txt");
        decoding("data13_encoded.txt", "data13_table.txt");
    }

    public static void init(){
        character = new String[27];
        character[0] = " ";
        for (int i = 1; i < 27; i++)
            character[i] = String.format("%c", i + 96);
    }

    public static void encoding(String fileName) {
        HashMap<Character, String> huffmanTable = new HashMap<>();
        StringBuilder encodedStr = new StringBuilder();
        StringBuilder encodeOutputStr = new StringBuilder();
        readHuffmanFile(fileName);
        BinaryTreeNode HuffmanTree = huffmanForEncode();
        encoded = new String[27];
        recuTable(HuffmanTree.left, "0");
        recuTable(HuffmanTree.right, "1");

        for (int i = 0; i < 27; i++) {
            huffmanTable.put(character[i].charAt(0), encoded[i]);
            encodedStr.append(character[i]).append(",").append(encoded[i]).append("\n");
        }
        saveFile("201502025_table.txt", encodedStr.toString());

        for (int i = 0; i < encodingInput.length(); i++) {
            encodeOutputStr.append(huffmanTable.get(encodingInput.charAt(i)));
        }
        saveFile("201502025_encoded.txt", encodeOutputStr.toString());
    }

    public static void recuTable(BinaryTreeNode tree, String result) {
        if (tree == null)
            return;
        if (tree.value != null) {
            if (tree.value.equals(" "))
                encoded[0] = result;
            else
                encoded[tree.value.charAt(0) - 96] = result;
            return;
        }
        recuTable(tree.left, "0" + result);
        recuTable(tree.right, "1" + result);
    }

    public static BinaryTreeNode huffmanForEncode() {
        int n = character.length;
        PriorityQueue<BinaryTreeNode> Q = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            Q.add(new BinaryTreeNode(frequency[i], character[i]));
        }
        for (int i = 0; i < n - 1; i++) {
            BinaryTreeNode x = Q.poll();
            BinaryTreeNode y = Q.poll();
            BinaryTreeNode z = new BinaryTreeNode(x.freq + y.freq, null);
            z.addLeft(x);
            z.addRight(y);
            Q.add(z);
        }
        return Q.poll();
    }

    public static void readHuffmanFile(String fileName) {
        String input;
        frequency = new int[27];
        encodingInput = "";

        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null) {
                encodingInput += input;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) == ' ')
                        frequency[0]++;
                    else
                        frequency[input.charAt(i) - 96]++;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void decoding(String encodedFileName, String tableFileName) {
        StringBuilder tempBinary = new StringBuilder();
        StringBuilder decodingOutput = new StringBuilder();
        String mapStr;
        readTableFile(tableFileName);
        readEncodedFile(encodedFileName);

        for(int i = 0; i < decodingInput.length(); i++){
            tempBinary.append(decodingInput.charAt(i));
            if((mapStr = binary.get(tempBinary.toString())) != null){
                decodingOutput.append(mapStr);
                tempBinary = new StringBuilder(new StringBuilder());
            }
        }

        saveFile("201502025_decoded.txt", decodingOutput.toString());
    }

    public static void readEncodedFile(String fileName) {
        String input;
        decodingInput = "";
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null)
                decodingInput += input;
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void readTableFile(String fileName) {
        StringTokenizer stringTokenizer;
        String input, c, b;
        binary = new HashMap<>();

        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((input = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(input, ",");
                c = stringTokenizer.nextToken();
                b = stringTokenizer.nextToken();
                binary.put(b, c);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void saveFile(String fileName, String data) {
        try {
            OutputStream outputStream = new FileOutputStream(fileName);
            byte[] by = data.getBytes();
            outputStream.write(by);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class BinaryTreeNode implements Comparable<BinaryTreeNode> {
        private int freq;
        private String value;
        private BinaryTreeNode parent;
        private BinaryTreeNode left, right;

        public BinaryTreeNode(int freq, String value) {
            this.freq = freq;
            this.value = value;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        public void addLeft(BinaryTreeNode left) {
            this.left = left;
            this.left.parent = this;
        }

        public void addRight(BinaryTreeNode right) {
            this.right = right;
            this.right.parent = this;
        }

        @Override
        public int compareTo(BinaryTreeNode o) {
            return Integer.compare(this.freq, o.freq);
        }
    }
}