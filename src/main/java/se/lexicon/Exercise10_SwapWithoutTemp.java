package se.lexicon;

public class Exercise10_SwapWithoutTemp {
    public static void main(String[] args) {
        var a = 15;
        var b = 42;

        System.out.println("Before: a = " + a + ", b = " + b);

        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println("After: a = " + a + ", b = " + b);
    }
}