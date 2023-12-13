/*
Si scriva un programma in linguaggio C per generare soltanto i due processi schematizzati di seguito:
P ---- F1
I processi dovranno stampare il loro PID e terminare.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sysltypes.h>
#include <sys/wait.h>


int main(){
    pid_t process //Variabile per monitorare lo stato del fork
    int stato //Variabile per monitorare lo stato del processo corrente
    process = fork();

    if(process == 0){
        printf("Process Id_F1:%d", (int)getpid());
    }
    else{
        printf("Process id_P:%d", (int)getpid());
    }

    wait(&stato);
    return 0;
}
