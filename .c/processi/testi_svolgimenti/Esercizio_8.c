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
    int stato1, stato2;
    int array[2], array2[2];
    pipe(array);
    pipe(array2);
    pid_t P = fork();
    if(P == 0){
        int valoreDigitato;
        printf("Digita un numero compreso da 0 e 31\n");
        scanf("%d", &valoreDigitato);
        if(valoreDigitato>=0 && valoreDigitato<=31){
            close(array[0]);
            write(array[1], &valoreDigitato, sizeof(valoreDigitato));
            printf("F1-> Ho inviato il valore '%d' a P\n", valoreDigitato);
        }
        else
            exit(0);
    }
    else{
        pid_t P1 = fork();
        if(P1 == 0){
            int buffer2;
            int valorePID;
            close(array2[1]);
            read(array2[0], &buffer2, sizeof(buffer2));
            printf("F2-> Valore ricevuto da P: %d\n", buffer2);
            printf("F2-> PID: %d\n", (int)getpid());
            printf("F2-> Inserisci un PID a tuo piacimento\n");
            scanf("%d", &valorePID);
            if(kill(valorePID, SIGINT))
                printf("Signal eseguito");
            exit(0);
        }
        else{
            int buffer;
            close(array[1]);
            read(array[0], &buffer, sizeof(buffer));
            printf("P-> Valore ricevuto da F1: %d\n", buffer);
            close(array[0]);
            write(array2[1], &buffer, sizeof(buffer));
        }
    }
    wait(&stato1);
    wait(&stato2);
    return 0;
}