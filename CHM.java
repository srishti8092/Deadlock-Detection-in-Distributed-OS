import java.util.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

class Probe
{
    int i;
    int j;
    int k;
    Probe(int i,int j,int k)
    {
        this.i=i;
        this.j=j;
        this.k=k;
    }
    int getI()
    {
        return i;
    }
    int getJ()
    {
        return j;
    }
    int getK()
    {
        return k;
    }
}

class Site
{
    int nm;
    int no_of_process=0;
    Vector<Proc> Process_list=new Vector<Proc>();
    Site(int n,Resource r[],int nm,int no,BlockingQueue<Probe> q)
    {
        no_of_process=n;
        this.nm=nm;
        int k=no;
        for(int i=0;i<no_of_process;i++)
        {
            Process_list.add(new Proc("P"+String.valueOf(k),r,nm,q));
            k++;
        }
    }
    void displayProcessesList()
    {
        for(Proc p:Process_list)
        {
            p.displayProcessDetail();
        }
    }
    void useResource()
    {
        for(Proc p:Process_list)
        {
            p.start();
        }
    }
}
class Resource
{
    String name;
    int no;
    int HoldedBy;
    //boolean free;
    Resource(String nm,int n)
    {
        //free=true;
        name=nm;
        no=n;
        HoldedBy=0;
    }
    /*void changeStatus()
    {
        if(free)
        {
            free=false;
        }
        else
        {
            free=true;
        }
    }*/
}
class Proc extends Thread
{
    String name;
    Resource r[];
    int site_n;
    BlockingQueue<Probe> q;
    static Probe probe=new Probe(0,0,0);
    Proc(String n,Resource res[],int sitno,BlockingQueue<Probe> q)
    {
        name=n;
        r=res;
        site_n=sitno;
        this.q=q;
    }
    public void run()
    {
         Random rand = new Random();
        int rand_int = rand.nextInt(r.length);
        int rand_int1 = rand.nextInt(r.length);
        if(rand_int==rand_int1)
        {
            if(rand_int!=0)
            {
                rand_int1=0;
            }
            else
            {
                rand_int=0;
            }
        }
        //System.out.println("site:"+site_n);
        //msg = queue.take()).getMsg() 
        System.out.println("site:"+site_n+" "+name + " waiting for lock on "+r[rand_int1].no);
        if(r[rand_int1].HoldedBy!=0)
        {
            probe.i =1;
            probe.j=Integer.parseInt(String.valueOf(name.charAt(1)));
            probe.k=r[rand_int1].HoldedBy;
        }
        if(Integer.parseInt(String.valueOf(name.charAt(1)))==probe.k)
        {
            System.out.println("DEADLOCK DETECTED!!!!");
        }
        /*try
        {
        if(r[rand_int1].HoldedBy!=0)
        {
            Probe probe =new Probe("P1",name,"P"+String.valueOf(r[rand_int1].HoldedBy));
            q.put(probe);
        }
        String pr="P"+(q.take()).getK();
        if(name==pr)
        {
            System.out.println("DEADLOCK DETECTED!!!!");
        }
        }catch(Exception e)
        {
            System.out.println(e);
        }*/    
        synchronized (r[rand_int1]) {
            //System.out.println("site:"+site_n);
         System.out.println("site:"+site_n+" "+name + " acquired lock on "+r[rand_int1].no);
         r[rand_int1].HoldedBy=Integer.parseInt(String.valueOf(name.charAt(1)));
         work(rand_int1);
         //System.out.println("site:"+site_n);
         System.out.println("site:"+site_n+" "+name + " waiting for lock on "+r[rand_int].no);
         if(r[rand_int].HoldedBy!=0)
        {
            probe.i =1;
            probe.j=Integer.parseInt(String.valueOf(name.charAt(1)));
            probe.k=r[rand_int].HoldedBy;
        }
        if(Integer.parseInt(String.valueOf(name.charAt(1)))==probe.k)
        {
            System.out.println("DEADLOCK DETECTED!!!!");
        }
         /*try
         {
          if(r[rand_int1].HoldedBy!=0)
        {
            Probe probe =new Probe("P1",name,"P"+String.valueOf(r[rand_int].HoldedBy));
            q.put(probe);
        }
        String pr2="P"+(q.take()).getK();
        if(name==pr2)
        {
            System.out.println("DEADLOCK DETECTED!!!!");
        }
         }catch(Exception e)
         {
             System.out.println(e);
         }*/
         synchronized (r[rand_int]) {
             //System.out.println("site:"+site_n);
            System.out.println("site:"+site_n+" "+name + " acquired lock on "+r[rand_int].no);
            r[rand_int1].HoldedBy=Integer.parseInt(String.valueOf(name.charAt(1)));
            work(rand_int);
        }
        //System.out.println("site:"+site_n);
         System.out.println("site:"+site_n+" "+name + " released lock on "+r[rand_int].no);
        }
        //System.out.println("site:"+site_n);
        System.out.println("site:"+site_n+" "+name + " released lock on "+r[rand_int1].no);
        //System.out.println("site:"+site_n);
        System.out.println("site:"+site_n+" "+name + " finished execution.");
    }
    private void work(int rl) {
        try {
            Thread.sleep(3000);
             r[rl].HoldedBy=0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void displayProcessDetail()
    {
        System.out.println(name);
    }
}
class CMH2
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter number of resource");
        int n=sc.nextInt();
        Resource res[]=new Resource[n];
        for(int i=0;i<n;i++)
        {
            res[i]=new Resource(String.valueOf(i+1),i+1);
        }
        System.out.println("Resources are");
        for(int i=0;i<n;i++)
        {
            System.out.println(res[i].name);
        }
        Site sites[]=new Site[4];
        int no=0;
        BlockingQueue<Probe> queue = new ArrayBlockingQueue<>(1);
        for(int i=0;i<4;i++)
        {
            //no=no+1;
            int num=no;
            System.out.println("Enter number of process for site:"+(i+1));
            int sn=sc.nextInt();
            num=num+1;
            sites[i]=new Site(sn,res,i+1,num,queue);
            no=no+sn;
        }
        System.out.println("Site and process details are as follows");
        for(int i=0;i<4;i++)
        {
            System.out.println("SITE"+(i+1));
            System.out.println("Process are");
            sites[i].displayProcessesList();
        }
        sites[0].useResource();
        sites[1].useResource();
        sites[2].useResource();
        sites[3].useResource();
  
