package cinqueFilosofi;

public class Main {
    public static void main(string args[]){
        int numero_filosofi = 5;
        Filosofo filosofo[] = new Filosofo[numero_filosofi];
        Bacchetta bacchetta[] = new Bacchetta[numero_filosofi];

        for(int i = 0; i< numero_filosofi; i++){
            bacchetta[i] = new Bacchetta(i);
        }

        for(int i=0; i<numero_filosofi; i++){
            int sinistra,destra;
            
            sinistra = i - 1;
            if(sinistra<0){
                sinistra = numero_filosofi - 1;
            }
            destra = i;

            filosofo[i] = new Filosofo(i, sinistra, destra);
            filosofo[i].start();
        }


    }
}
