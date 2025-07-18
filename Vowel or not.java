import java.util.*;
class jai{
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        char a=scanner.next();
        if(a=='a'||a=='A'||a=='e'||a=='E'||a=='i'||a=='I'||a=='o'||a=='O'||a=='u'||a=='U'){
            System.out.println("YES");
        }
        else{
            System.out.println("NO");
        }
    }
}
