//P genera un intero casuale x e lo invia a P1
//P1 riceve x e lo invia a P2
//P2 rimanda x a P
//P verifica che il valore ricevuto sia uguale
//a quello inviato

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <time.h>

int main(){
    int stato1,stato2;
    int array[2], array2[2], array3[2];
    srand(time(NULL));
    pipe(array);
    pipe(array2);
    pipe(array3);
    pid_t P1,P2;

    P1 = fork();
    if(P1 == 0){
        int buffer1;
        close(array[1]);
        read(array[0], &buffer1, sizeof(buffer1));
        close(array2[0]);
        write(array2[1], &buffer1, sizeof(buffer1));
        printf("P1 riceve il rand da P :%d e lo manda a P2\n", buffer1);
        exit(0);
    }
    else{
        P2 = fork();
        if(P2 == 0){
            int buffer2;
            close(array2[1]);
            read(array2[0], &buffer2, sizeof(buffer2));
            close(array3[0]);
            write(array3[1], &buffer2, sizeof(buffer2));
            printf("P2 riceve il pId da P1 :%d e lo rimanda a P\n", buffer2);
            exit(0);
        }
        else{
            int buffer = rand();
            int bufferRitorno;
            close(array[0]);
            write(array[1], &buffer, sizeof(buffer));
            printf("P genera un intero positivo: %d e lo invia a P1\n", buffer);

            close(array3[1]);
            read(array3[0], &bufferRitorno, sizeof(bufferRitorno));
            if((int)buffer == bufferRitorno){
                printf("Il numero di ritorno da P2 é uguale\n");
            }
        }
    }
    wait(stato1);
    wait(stato2);
    return 0;
}