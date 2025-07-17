import java.util.*;
class Jai{
    public static void main(String []args){
        Scanner scanner= new Scanner(System.in);
        int a=scanner.nextInt();
        int b[]=new int[a];
        for(int i=0;i<a;i++){
            b[i]=scanner.nextInt();
        }
        int sum=0;
        for(int i=0;i<a;i++){
            sum=sum+b[i];
        }
        System.out.println(sum);
    }
}
