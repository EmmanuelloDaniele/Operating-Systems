/*
Con riferimento ai processi creati nell'esercizio 2, si implementi il seguente comportamento:
    ° Il processo F1 genera un numero intero tra 0 e 100 e lo invia al processo P.
    ° Il processo P riceve il numero generato da F1 e lo invia al processo F2.
    ° Il processo F2 riceve il numero da P e lo stampa.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <time.h>

int main(){
    srand(time(NULL));
    int stato1, stato2;
    int array[2];
    pipe(array);
    pid_t P = fork();
    if(P == 0){
        close(array[0]);
        int buffer = rand() % 101;
        write(array[1], &buffer, sizeof(buffer));
        printf("F1-> Numero generato: %d\n", buffer);
        exit(0);
    }
    else{
        int array2[2];
        pipe(array2);
        pid_t P1 = fork();
        if(P1 == 0){
            close(array2[1]);
            int buffer3;
            read(array2[0], &buffer3, sizeof(buffer3));
            printf("F2-> Valore ricevuto da P: %d\n", buffer3);
            exit(0);
        }
        else{
            close(array[1]);
            int buffer2;
            read(array[0], &buffer2, sizeof(buffer2));
            printf("P-> Valore ricevuto da F1: %d\n", buffer2);
            close(array[0]);
            write(array2[1], &buffer2, sizeof(buffer2));
            exit(0);
        }
    }
    wait(&stato1);
    wait(&stato2);
    return 0;   
}