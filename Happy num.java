import java.util.*;
calss Jai{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int c=n;
        while(c!=1 && c!=4)
        {
            int s=0;
            int t=c;
            while(t>0)
            {
                int r=t%10;
                s+=r*r;
                t/=10;
            }
            c=s;
        }
        if(s==1)
        System.out.println("1");
        else
        System.out.println("0");
    }
}
