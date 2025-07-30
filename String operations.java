public class StringBuilderBufferExample {
    public static void main(String[] args) {
        // StringBuilder example
        StringBuilder sb = new StringBuilder("Jai");
        System.out.println("=== StringBuilder Example ===");
        System.out.println("Original: " + sb);

        sb.append(" Hind");
        System.out.println("After append: " + sb);

        sb.insert(4, "Shree ");
        System.out.println("After insert: " + sb);

        sb.replace(4, 10, "Sri");
        System.out.println("After replace: " + sb);

        sb.delete(4, 7);
        System.out.println("After delete: " + sb);

        sb.reverse();
        System.out.println("After reverse: " + sb);

        // StringBuffer example
        StringBuffer sf = new StringBuffer("Jai");
        System.out.println("\n=== StringBuffer Example ===");
        System.out.println("Original: " + sf);

        sf.append(" Hind");
        System.out.println("After append: " + sf);

        sf.insert(4, "Shree ");
        System.out.println("After insert: " + sf);

        sf.replace(4, 10, "Sri");
        System.out.println("After replace: " + sf);

        sf.delete(4, 7);
        System.out.println("After delete: " + sf);

        sf.reverse();
        System.out.println("After reverse: " + sf);
    }
}
