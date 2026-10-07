package se.lexicon;

public class Exercise19_CountVowels {
    public static int countVowels(String s) {
        String lower = s.toLowerCase();
        int count = 0;
        for (int i = 0; i < lower.length(); i++) {
            char ch = lower.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("countVowels(\"Hello World\") = " + countVowels("Hello World"));
        System.out.println("countVowels(\"Java\") = " + countVowels("Java"));
        System.out.println("countVowels(\"rhythm\") = " + countVowels("rhythm"));
    }
}