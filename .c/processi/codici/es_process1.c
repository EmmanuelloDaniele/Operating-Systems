/*
Abbiamo P0, P1, P2 e P3
Il processo P3 invia il proprio PID al processo P1
Il processo P1 riceve il PID e lo inoltra a P2
*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <wait.h>

int main(){
    int stato,stato1,stato2,stato3;
    pid_t P,P1,P2,P3;
    int array[2],array2[2];
    pipe(array);
    pipe(array2);
    
    P1 = fork()
    if(P1 == 0){    //Codice Fork 1
        int buffer;
        close(array[1]);
        read(array[0], &buffer, sizeof(buffer));
        close(array2[0]);
        write(array2[1], &buffer, sizeof(buffer));
        printf("P1 riceve il pID di P3:%d e lo inoltra a P2\n", buffer);
        exit(0);
    }
    else{
        P2 = fork();
        if(P2 == 0){  //Codice Fork 2
            int buffer2;
            close(array2[1]);
            read(array2[0], &buffer2, sizeof(buffer2));
            printf("P2 riceve il piD: %d\n", buffer2);
        }
        else{
            P3 = fork();
            if(P3 == 0){ //Codice Fork 3
                int buffer3 = getpid();
                close(array[0]);
                write(array[1], &buffer3, sizeof(buffer3));
                printf("P3 invia il proprio PId: %d a P1\n", buffer3);
                exit(0);
            }
            else{};  //Codice Padre
        }
    }
    wait(&stato1);
    wait(&stato2);
    wait(&stato3);
    return 0;
}