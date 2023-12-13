/*
Si supponga che nella cartella corrente sia presente il file esempio.c
Con riferimento ai processi creati nell'esercizio 1, si implementi il seguente comportamento:
    ° Il processo P invia al processo F1 la stringa "esempio2.c".
    ° Il processo F1 riceve la stringa ed esegue il comando di sistema necessario per rinominare il file "esempio.c" nel nuovo nome specificato da F1.
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
    char stringa[] = "esempio.c";
    pid_t P = fork();

    if(P == 0){
        char buffer[20];
        close(array[1]);
        read(array[0], &buffer, sizeof(buffer));
        printf("F1 riceve da P il nome del file %s\n", buffer);
        rename("esempio.c", buffer);
        exit(0);
    }
    else{
        close(array[0]);
        write(array[1], &stringa, sizeof(stringa));
        printf("P invia la stringa a F1");
        exit(0);
    }
    wait(&stato);
    return 0;
}