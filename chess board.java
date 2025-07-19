import java.util.Scanner;

public class Main {
    public static String determineColor(String s) {
        // Extract column (character) and row (digit)
        char columnChar = s.charAt(0);
        char rowChar = s.charAt(1);

        // Convert column ('a' -> 1, 'b' -> 2, etc.)
        int column = columnChar - 'a' + 1;

        // Convert row character to integer
        int row = rowChar - '0';

        // Sum column + row
        int sum = column + row;

        // If sum is even => Black, else White
        if (sum % 2 == 0) {
            return "Black";
        } else {
            return "White";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine().trim();
        System.out.println(determineColor(s));
    }
}
