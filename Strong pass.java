import java.util.*;
class Main{
 public static void main(String args[]){
     Scanner sc=new Scanner(System.in);
     String str=sc.nextLine();
    int B=8;
         boolean hasLower=false;
         boolean hasUpper=false;
         boolean hasDigit=false;
         boolean hasSpecial=false;
         for(char ch:str.toCharArray()){
             if(Character.isLowerCase(ch)) hasLower=true;
             else if(Character.isUpperCase(ch)) hasUpper=true;
             else if(Character.isDigit(ch)) hasDigit=true;
             else if(ch=='@'||ch=='a'||ch=='s') hasSpecial=true;
         }
         int length=str.length();
         if(hasLower && hasUpper && hasDigit && hasSpecial && length>=B){
             System.out.println("Strong");
         }else if (hasLower && hasUpper && hasSpecial && length>=6){
             System.out.println("Moderate");
         }else {
             System.out.println("Weak");
         }
     }
 }
