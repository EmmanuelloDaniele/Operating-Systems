#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <wait.h>
#include <string.h>
#include <sys/types.h>

/*
	p0
p1		p2
 |
 |
p3
*/

// p3 invia pid a p1, che lo riceve e lo invia a p2

int main(){
    int child_st1, child_stat2, child_stat3;
    pid_t F1,F2,F3;
    int buffer;
    int fd[2];
    pipe(fd);

    F1 = fork();
    if(F1 == 0){
        F3 = fork();
        if(F3 == 0){ //Corpo F3
            buffer = getpid(); 
            close(fd[0]);
            write(fd[1], &buffer, sizeof(buffer));
            printf("P3 invia il suo pID:%d a F1\n", buffer);
            exit(0);
        }
        else{ //Corpo F1
            close(fd[0]);
            write(fd[1], NULL, 0);
            printf("F1 inoltra il pID\n");
            exit(0);
        }
    }
    else{ 
        F2 = fork();
        if(F2 == 0){  //Corpo F2
            close(fd[1]);
            read(fd[0], &buffer, sizeof(buffer));
            printf("Il pID ricevuto vale %d\n", buffer);
            exit(0);
        }
        else{ //Corpo P
            close(fd[0]);
            write(fd[1], NULL, 0);
            printf("P inoltra il pID\n");
        }
    }
    wait(&child_st1);
    wait(child_stat2);
    wait(child_stat3);
    return 0;
}