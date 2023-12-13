/*
Con riferimento ai processi creati nell’esercizio 2, si implementi il seguente comportamento:
    • Il processo F1 chiede all’utente di inserire un valore intero tra 0 e 31 e lo invia al processo P;
    • Il processo P inoltra tale valore al processo F2;
    • Il processo F2 chiede all’utente di inserire un PID (si esegua manualmente il comando ps in un’altra finestra di terminale per scegliere un PID valido);
    • Il processo F2 riceve l’intero inviato da P ed esegue il comando di sistema necessario per inviare il signal specificato da F1 (valore tra 0-31) al PID specificato dall’utente. 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <signal.h>

int main(){
    int stato, stato1;
    int array[2];
    pipe(array);
    int array2[2];
    pipe(array2);
    pid_t P = fork();

    if(P == 0){
        int valore;
        printf("Inserisci valore intero tra 0 e 31\n");
        scanf("%d", &valore);

        if(valore>=0 && valore<31){
            close(array[0]);
            write(array[1], &valore, sizeof(valore));
            printf("F1 manda il valore digitato: %d a P\n", valore);
            exit(0);
        }
    }
    else{
        pid_t P1 = fork();
        
        if(P1 == 0){
            int buffer2;
            int pIDvalido;

            close(array2[1]);
            read(array2[0], &buffer2, sizeof(buffer2));
            printf("F2-> Valore ricevuto da P: %d\n", buffer2);
            printf("F2-> PID: %d\n", (int)getpid());
            printf("Inserire un pID valido\n");
            scanf("%d", &pIDvalido);
            
            if(kill(pIDvalido, (int)buffer2)){
                printf("Singal eseguito su pID %d\n", pIDvalido);
            }
            exit(0); 
        }
        else{
            int buffer;
            close(array[1]);
            read(array[0], &buffer, sizeof(buffer));
            close(array2[0]);
            write(array2[1], &buffer, sizeof(buffer));
            printf("P riceve il valore da F1 e lo manda a F2\n");
        }
    }
    wait(&stato);
    wait(&stato1);
    return 0;
}