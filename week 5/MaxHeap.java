import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MaxHeap {
    private static Node[] arr;
    private static int size;

    public static void main(String[] args) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean endChk = false;
        int n;

        try {
            File file = new File("data05.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((input = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(input, ",");
                arrayList.add(new Node(Integer.parseInt(stringTokenizer.nextToken()), stringTokenizer.nextToken()));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        size = arrayList.size();
        arr = new Node[size];
        for (int i = 0; i < size; i++)
            arr[i] = arrayList.get(i);
        buildMaxHeap(); // 힙 생성
        while (!endChk) {
            Node tNode;
            int tKey;
            String tValue;
            orderPrint();
            n = scanner.nextInt();
            System.out.println(n + "번 기능.");
            switch (n) {
                case 1: // 추가
                    System.out.println("key와 value를 입력해주세요");
                    System.out.print("key : ");
                    tKey = scanner.nextInt();
                    System.out.print("value : ");
                    tValue = scanner.next();
                    insert(new Node(tKey, tValue));
                    System.out.println("원소를 추가했습니다.");
                    break;
                case 2: // 최대값 리턴
                    tNode = max();
                    System.out.println("최대값 : key = " + tNode.key + ", value = " + tNode.value);
                    break;
                case 3: // 최대값 원소 제거
                    tNode = extractMax();
                    if(tNode == null){
                        System.out.println("heap에 원소가 없습니다.");
                        break;
                    }
                    System.out.println("최대값 : key = " + tNode.key + ", value = " + tNode.value + "원소를 제거했습니다.");
                    break;
                case 4: // 원소 x의 키값을 k로 증가 (k는 x의 현재 키값보다 작아지지 않음)
                    System.out.print("key를 입력해주세요 : ");
                    tKey = scanner.nextInt();
                    System.out.println("새로운 key를 입력해주세요 (원래 키 보다 커야함)");
                    System.out.print("key : ");
                    int newKey = scanner.nextInt();
                    if (newKey <= tKey) {
                        System.out.println("새로운 key가 기존의 key 이하입니다.");
                        break;
                    }
                    if (!increase_key(tKey, newKey)) {
                        System.out.println("해당 원소를 찾지 못했습니다.");
                        break;
                    }
                    System.out.println("key값을 " + newKey + "로 증가시켰습니다.");
                    break;
                case 5: // 노드 x 제거
                    System.out.print("key를 입력해주세요 : ");
                    tKey = scanner.nextInt();
                    if (delete(tKey) == 0) {
                        System.out.println("해당 원소를 찾지 못했습니다.");
                        break;
                    }
                    if (delete(tKey) == -1) {
                        System.out.println("heap에 원소가 없습니다.");
                        break;
                    }
                    System.out.println("원소를 삭제했습니다.");
                    break;
                case 6: // 종료
                    endChk = true;
                    break;
                default:
                    System.out.println("올바르지 않은 입력값입니다.");
            }
            System.out.println("\n");
        }
    }

    public static void orderPrint() {
        System.out.println("**** 현재 우선 순위 큐에 저장되어 있는 작업 대기 목록은 다음과 같습니다 ****");
        for (int i = 0; i < size; i++) {
            System.out.println(arr[i].getKey() + ", " + arr[i].getValue());
        }
        System.out.println("\n--------------------------------------------");
        System.out.println("1. 작업 추가\t2. 최대값\t\t3.최대 우선순위 작업 처리");
        System.out.println("4. 원소 키값 증가\t\t\t5. 작업 제거\t6. 종료");
        System.out.println("--------------------------------------------");
    }

    private static class Node {
        private int key;
        private String value;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static void maxHeapify(int i) {
        int leftIdx = 2 * i + 1;
        int rightIdx = 2 * i + 2;
        int largest;

        if (leftIdx < size && arr[leftIdx].key > arr[i].key)
            largest = leftIdx;
        else
            largest = i;

        if (rightIdx < size && arr[rightIdx].key > arr[largest].key)
            largest = rightIdx;

        if (largest != i) {
            Node temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            maxHeapify(largest);
        }
    }

    public static void buildMaxHeap() {
        for (int i = (size - 1) / 2; i >= 0; i--)
            maxHeapify(i);
    }

    public static void insert(Node x) {
        if (size == arr.length) {
            Node[] newArr = new Node[arr.length * 2];
            System.arraycopy(arr, 0, newArr, 0, size);
            arr = newArr;
        }
        arr[size++] = x;
        for (int i = size; i != 0; i = (i - 1) / 2)
            maxHeapify(i);
        maxHeapify(0);
    }

    public static Node max() {
        return arr[0];
    }

    public static Node extractMax() {
        if (size == 0)
            return null;
        Node oldNode = arr[0];
        arr[0] = arr[--size];
        arr[size] = null;
        maxHeapify(0);
        return oldNode;
    }

    public static boolean increase_key(int inputKey, int newKey) {
        int p;
        for (p = 0; p < size; p++) {
            if (arr[p].getKey() == inputKey)
                break;
        }
        if (p == size)
            return false;

        arr[p].setKey(newKey);
        while (p != 0) {
            maxHeapify(p);
            p = (p - 1) / 2;
        }
        maxHeapify(p);
        return true;
    }

    public static int delete(int inputKey) {
        int p;
        if (size == 0)
            return -1;

        for (p = 0; p < size; p++) {
            if (arr[p].getKey() == inputKey)
                break;
        }
        if (p == size)
            return 0;

        arr[p] = arr[--size];
        arr[size] = null;
        while (p != 0) {
            maxHeapify(p);
            p = (p - 1) / 2;
        }
        maxHeapify(p);
        return 1;
    }
}



