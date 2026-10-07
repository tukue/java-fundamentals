package se.lexicon;

public class Exercise10_SwapWithoutTemp {
    public static void main(String[] args) {
        int a = 15;
        int b = 42;

        System.out.println("Before: a = " + a + ", b = " + b);

        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println("After: a = " + a + ", b = " + b);
    }
}