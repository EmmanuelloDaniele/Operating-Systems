/*
Con riferimento ai processi creati nell'esercizio 1, si implementi il seguente comportamento:
    ° Il processo F1 invia il proprio PID al processo P;
    ° Il processo P riceve il PID generato da F1 e lo stampa.
*/

#include <stdlib.h>
#include <stdio.h>
#include <wait.h>
#include <unistd.h>

int main(){
    int stato;
    int array[2]; //Utilizzato per gestire il write o il read di un buffer.
    pipe(array);
    pid_t P = fork();
    if(P == 0){
        close(array[0]); //Termino la lettura.
        int buffer = (int)getpid();
        write(array[1], &buffer, sizeof(buffer));
        printf("F1: %d\n", buffer);
        exit(0);
    }
    else{
        close(array[1]); //Termino la scrittura.
        int buffer2;
        read(array[0], &buffer2, sizeof(buffer2));
        printf("P: %d\n", buffer2);
        exit(0);
    }
    wait(&stato);
    return 0;
}