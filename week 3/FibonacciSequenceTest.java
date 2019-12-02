import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class FibonacciSequenceTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("방법\n1 : Recursion\n2 : Array\n3 : Recursive squaring");
        int select, n;
        long startTime, endTime;
        BigInteger answer;
        select = scanner.nextInt();
        System.out.println("N의 크기");
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            if (i % 10 == 0)
                System.out.println("------------------------------------------------------------------");
            startTime = System.nanoTime();

            if (select == 1)
                answer = recursionFibo(i);
            else if (select == 2)
                answer = arrayFibo(i);
            else if (select == 3)
                answer = recuSquaringFibo(i);
            else
                return;
            endTime = System.nanoTime();
            System.out.printf("f<%d> = %s\t\t\t\t\t\t%.12f sec\n", i, answer.toString(), (endTime / 1000000000.0 - startTime / 1000000000.0));
        }
    }

    public static BigInteger recursionFibo(int n) {
        if (n < 2)
            return BigInteger.valueOf(n);
        return recursionFibo(n - 1).add(recursionFibo(n - 2));
    }

    public static BigInteger arrayFibo(int n) {
        ArrayList<BigInteger> arr = new ArrayList<BigInteger>();

        for (int i = 0; i <= n; i++) {
            if (i < 2)
                arr.add(BigInteger.valueOf(i));
            else {
                arr.add((arr.get(i - 2)).add(arr.get(i - 1)));
            }
        }
        return arr.get(arr.size() - 1);
    }

    public static BigInteger recuSquaringFibo(int n) {
        if (n < 2)
            return BigInteger.valueOf(n);
        BigInteger[][] a = {{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};
        a = matrixPow(a, n- 1);
        return a[0][0];
    }

    public static BigInteger[][] matrixPow(BigInteger[][] a, int n) {
        if (n == 1)
            return a;
        BigInteger[][] b = {{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};
        BigInteger[][] c = matrixPow(a, n / 2);
        c = matrixMul(c, c);
        if (n % 2 != 0) {
            c = matrixMul(c, b);
        }
        return c;
    }

    public static BigInteger[][] matrixMul(BigInteger[][] a, BigInteger[][] b) {
        BigInteger[][] c = new BigInteger[2][2];
        c[0][0] = a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0]));
        c[0][1] = a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]));
        c[1][0] = a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0]));
        c[1][1] = a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]));

        return c;
    }
}
