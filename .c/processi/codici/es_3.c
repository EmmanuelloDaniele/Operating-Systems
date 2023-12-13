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
    int status;
    int array[2] //Array che mi permette di gestire lo stato del buffer(Read,Write)

    pipe(array);
    pid_t P = fork();

    if(P == 0){
        close(array[0]); //Termino la lettura del buffer da parte di F1
        int buffer = (int)getpid();
        write(array[1], &buffer, sizeof(buffer));
        printf("F1:%d", buffer);
        exit(0);
    }
    else{
        close(array[1]); //Termino la scrittura del buffer da parte di P
        int buffer2;
        read(array[0], &buffer2, sizeof(buffer2));
        printf("P print F1: %d\n", buffer2);
        
        exit(0);  
    }
    wait(&status);
    return 0;
}