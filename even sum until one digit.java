import java.util.*;
class Jai{
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        int a=scanner.nextInt();
        int b=a,c,d=0;
        while(b!=0){
            c=b%10;
            b=b/10;
            if(c%2==0){
                d+=c;
            }
        }int sum=0;
    while(d>9){
        int temp=0;
        while(d>0){
            temp+=d%10;
            d=d/10;
        }
      d=temp;  
    }System.out.println(d);
        
    }
}
