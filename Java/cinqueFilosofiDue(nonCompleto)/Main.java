package cinqueFilosofiDue;


public class Main {
    public static void main(String args[]){
        int numeroFilosofi = 5;
        Filosofo filosofi[] = new Filosofo[numeroFilosofi];
        Bacchetta bacchette[] = new Bacchetta[numeroFilosofi];

        for(int i=0; i<numeroFilosofi; i++){
            bacchette[i] = new Bacchetta(i);
        }
        
    }
}
