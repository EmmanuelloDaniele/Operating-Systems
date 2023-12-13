/*
Si scriva un programma in linguaggio C per generare soltanto i due processi schematizzati di seguito:
F2 ---- P ---- F1
I processi dovranno stampare il loro PID e terminare.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>

int main(){
    pid_t P;
    int status,status1;
    
    P = fork();
    if(P == 0){
        printf("Pid F1:%d", (int)getpid());
    }
    else{
        pid_t P2 = fork();
        if(P2 == 0){
            printf("Pid F2:%d", (int)getpid());
        }
            else{
            printf("Pid P:%d", (int)getpid());
            }
        }
    wait(&status);
    wait(&status1);
    return 0;
}

