/*
Con riferimento ai processi creati nell'esercizio 2, si implementi il seguente comportamento:
    ° Il processo P invia il proprio PID al processo F1.
    ° Il processo F1 riceve il PID da P e lo invia al processo F2.
    ° Il processo F2 riceve il PID di P da F1 e lo stampa.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <time.h>

int main(){
    int stato1, stato2;
    int array[2];
    pipe(array);
    int array2[2];
    pipe(array2);
    pid_t P = fork();
    if(P == 0){
        int buffer2;
        close(array[1]);
        read(array[0], &buffer2, sizeof(buffer2));
        printf("F1-> PID inviato da P: %d\n", buffer2);
        close(array[0]);
        write(array2[1], &buffer2, sizeof(buffer2));
        exit(0);
    }
    else{
        pid_t P2 = fork();
        if(P2 == 0){
            int buffer3;
            close(array2[1]);
            read(array2[0], &buffer3, sizeof(buffer3));
            printf("F2-> PID ricevuto da F1: %d\n", buffer3);
            exit(0);
        }
        else{
            int buffer = (int)getppid();
            close(array[0]);
            write(array[1], &buffer, sizeof(buffer));
            printf("P-> Invio a F1 il mio PID: %d\n", buffer);
            exit(0);
        }
    }
    wait(&stato1);
    wait(&stato2);
    return 0;
}