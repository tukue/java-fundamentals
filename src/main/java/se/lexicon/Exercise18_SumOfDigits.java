package se.lexicon;

public class Exercise18_SumOfDigits {
    public static int sumOfDigits(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("sumOfDigits(1234) = " + sumOfDigits(1234));
        System.out.println("sumOfDigits(9) = " + sumOfDigits(9));
        System.out.println("sumOfDigits(305) = " + sumOfDigits(305));
    }
}