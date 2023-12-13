/*
Con riferimento ai processi creati nell'esercizio 1, si implementi il seguente comportamento:
    ° Il processo F1 genera un numero intero tra 0 e 100 e lo invia al processo P.
    ° Il processo P riceve il numero generato da F1 e lo stampa.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <time.h>

int main(){
    int stato;
    int array[2];
    pipe(array);
    pid_t P = fork();
    srand(time(NULL));
    if(P == 0){
        close(array[0]);
        int buffer = rand() % 101;
        write(array[1], &buffer, sizeof(buffer));
        printf("Numero generato da F1: %d\n", buffer);
        exit(0);
    }
    else{
        close(array[1]);
        int buffer2;
        read(array[0], &buffer2, sizeof(buffer2));
        printf("Numero ricevuto: %d\n", buffer2);
        exit(0);
    }
    wait(&stato);
    return 0;
}