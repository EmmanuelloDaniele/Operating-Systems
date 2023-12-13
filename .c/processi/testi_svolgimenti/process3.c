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

int main() {
	pid_t val_1;
	int f1_status, f2_status, f3_status;
	int fd[2];
	int readBuffer;
	pipe(fd);
	val_1 = fork();

	if(val_1 == 0) {
		pid_t val_2 = fork();
		if(val_2 == 0) {
			// p3
			int x = (int)(getpid()); // memorizzo in x il pid di p3
			close(fd[0]);
			write(fd[1], &x, sizeof(x));
			printf("P3 manda il suo Pid:%d a P1\n", x);
			exit(0);
		}
		else {
			// p1
			close(fd[0]);
			printf("p1 sta scrivendo...\n");
			write(fd[1], NULL, 0);
			exit(0);
		}
	}
	else {
		pid_t val_3 = fork();
		if(val_3 == 0) {
			//p2
			sleep(2);
			close(fd[1]);
			read(fd[0], &readBuffer, sizeof(readBuffer));
			printf("P2 legge il pID di P1: %d\n", readBuffer);
			exit(0);
		}
		else {
			//p0
			close(fd[0]);
			printf("p0 sta scrivendo\n");
			write(fd[1], NULL, 0);
			exit(0);
		}
	}
	wait(&f1_status);
	wait(&f2_status);
	wait(&f3_status);
	return 0;
}