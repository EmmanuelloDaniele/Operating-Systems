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
    int stato, stato1;
    int array[2];
    pipe(array);
    int array2[2];
    pipe(array2);
    pid_t P = fork();

    if (P == 0){
        int buffer1;
        close(array[1]);
        read(array[0], &buffer1, sizeof(buffer1));
        close(array2[0]);
        write(array2, &buffer1, sizeof(buffer1));
        printf("F1 riceve l'ID di P : %d e lo manda a F2", buffer);
        exit(0);
    }
    else{
        pid_t P1 = fork();

        if( P1 == 0){
            int buffer3;
            close(array2[1]);
            read(array2[[0], &buffer3, sizeof(buffer3)]);
            printf("F2 riceve il pID di P da F1 :%d", buffer3);
            exit(0);
        }
        else{
            int buffer = getpid();
            close(array[0]);
            write(array[1], &buffer, sizeof(buffer))
            exit(0);
        }
    }
    wait(stato);
    wait(stato1);
    return 0;

}