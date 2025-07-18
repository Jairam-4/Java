import java.util.*;
class Jai{
    public static void main(String[]args){
        Scanner scanner= new Scanner(System.in);
        int a=scanner.nextInt();
        while(a%4==0){
            a=a/4;
        }
        if(a==1){
            System.out.println("true");
        }
        else{
            System.out.println("false");
        }
    }
}
