/*
Si scriva un programma in linguaggio C per generare soltanto i due processi schematizzati di seguito:
F2 ---- P ---- F1
I processi dovranno stampare il loro PID e terminare.
*/

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <wait.h>

int main(){
    pid_t P;
    int stato, stato2;
    P = fork();
    if(P == 0)
        printf("F1: %d\n", (int)getpid());
    else{
        pid_t P2 = fork();
        if(P2 == 0)
            printf("F2: %d\n", (int)getpid());
        else
            printf("P: %d\n", (int)getppid());
    }
    wait(&stato);
    wait(&stato2);
    return 0;
}