import java.util.*;
class Jai{
    public static void main(String[]args){
        Scanner scanner= new Scanner(System.in);
        String s=scanner.next();
        StringBuilder J =new StringBuilder();
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(Character.isLowerCase(ch)){
            J.append(Character.toUpperCase(ch));
            }else{
                J.append(Character.toLowerCase(ch));
            }
        }
        System.out.println(J.toString());
    }
}
