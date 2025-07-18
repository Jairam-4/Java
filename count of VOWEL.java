import java.util.*;

class Jai {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int v = 0; // vowel count
        String s = scanner.next();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isLetter(ch)) {
                // Convert to lowercase for easy checking
                ch = Character.toLowerCase(ch);

                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    v++;
                }
            }
        }

        System.out.println(v);

        scanner.close(); // good practice
    }
}
