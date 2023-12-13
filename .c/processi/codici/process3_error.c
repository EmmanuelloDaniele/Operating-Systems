//Il Padre manda al figlio un segnale di terminazione, il figlio lo ignora.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <signal.h>

int main(){
    int child_status;
    pid_t pid;
    pid = fork();

    if(pid == 0){
        signal(SIGTERM, SIG_IGN); //Il processo figlio ignora il segnale di terminazione
        printf("Il figlio ignora il segnale e stampa il suo pid: %d\n ", (int)getpid());
    }
    else{
        kill(pid, SIGTERM); //Il processo padre manda al figlio un segnale di terminazione
        printf("Il processo padre:%d, manda un segnale di terminazione al figlio: %d\n", pid, (int)getpid());
    }


}

//Il figlio non ignora il sig_term