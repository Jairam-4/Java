import java.util.*;
class Jai{
    public static void main(String[]args){
        Scanner scanner=new Scanner(System.in);
        int a=scanner.nextInt();
        int b[]=new int [a];
        for(int i=0;i<a;i++){
            b[i]=scanner.nextInt();
        }
        int count=0;
        int c=scanner.nextInt();
     for(int i=0;i<a;i++){
         if(c==b[i]){
             count=count+1;
        
         }
     }
     if(count!=0){
         System.out.println("Element Found");
     }else{
         System.out.println("Not Found");
     }
         scanner.close();
     
    }
}
