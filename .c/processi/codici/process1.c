//P3 riceve il pId da P1 e lo invia a P2

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>


int main(){
    int stato1,stato2,stato3;
    int array[2],array2[2];
    pipe(array);
    pipe(array2);
    pid_t P1,P2,P3;

    P1 = fork();
    if(P1 == 0){
        int buffer = getpid();
        close(array[0]);
        write(array[1], &buffer, sizeof(buffer));
        printf("P1 invia il proprio pId: %d a P3\n", buffer);
        exit(0);
    }
    else{
        P2 = fork();
        if(P2 == 0){
            int buffer2;
            close(array2[1]);
            read(array2[0], &buffer2, sizeof(buffer2));
            printf("P2 riceve il pID di P1 da P3:%d\n", buffer2);
            exit(0);
        }
        else{
            P3 = fork();
            if(P3 == 0){
                int buffer3;
                close(array[1]);
                read(array[0], &buffer3, sizeof(buffer3));
                close(array2[0]);
                write(array2[1], &buffer3, sizeof(buffer3));
                printf("P3 riceve il pId di P1:%d e lo manda a P2\n", buffer3);
                exit(0);
            }
            else{};
        }
    }
    wait(&stato1);
    wait(&stato2);
    wait(&stato3);
    return 0;
}