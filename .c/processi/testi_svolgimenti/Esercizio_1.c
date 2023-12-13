/*
Si scriva un programma in linguaggio C per generare soltanto i due processi schematizzati di seguito:
P ---- F1
I processi dovranno stampare il loro PID e terminare.
*/

// Dichiaro le librerie necessarie
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

// Dichiaro la funzione main
int main(){
    pid_t process; //Assegno un PID al processo "process"
    int stato; //Utilizzata per gestire lo stato del processo corrente tramite wait
    process = fork(); //Viene creato il processo "process"

    if(process == 0)
        printf("PID F1: %d\n", (int)getpid());
    else
        printf("PID P: %d\n", (int)getppid());
    wait(&stato);
    return 0;
}