public class StringFunctions {
    public static void main(String[] args) {
        String st = "Jai";
        System.out.println("Original String: '" + st + "'\n");

        // Length
        System.out.println("Length: " + st.length());

        // Lowercase
        System.out.println("Lowercase: " + st.toLowerCase());

        // Uppercase
        System.out.println("Uppercase: " + st.toUpperCase());

        // Character at index
        System.out.println("Character at index 1: " + st.charAt(1));

        // Substring
        System.out.println("Substring from index 1: " + st.substring(1));

        // Replace
        System.out.println("Replace 'J' with 'B': " + st.replace("J", "B"));

        // Starts with
        System.out.println("Starts with 'Ja': " + st.startsWith("Ja"));

        // Ends with
        System.out.println("Ends with 'ai': " + st.endsWith("ai"));

        // Index of character
        System.out.println("Index of 'i': " + st.indexOf('i'));

        // Equals
        System.out.println("Equals 'Jai': " + st.equals("Jai"));

        // Equals ignore case
        System.out.println("EqualsIgnoreCase 'jai': " + st.equalsIgnoreCase("jai"));

        // Concatenation
        System.out.println("Concatenation with ' Hind': " + st.concat(" Hind"));

        // Is empty
        System.out.println("Is empty: " + st.isEmpty());

        // Trim
        String stWithSpaces = "  Jai  ";
        System.out.println("Trimmed: '" + stWithSpaces.trim() + "'");

        // To char array
        char[] chars = st.toCharArray();
        System.out.print("To Char Array: ");
        for (char c : chars) {
            System.out.print(c + " ");
        }

        // Reverse using StringBuilder
        String reversed = new StringBuilder(st).reverse().toString();
        System.out.println("\nReversed: " + reversed);
    }
}
