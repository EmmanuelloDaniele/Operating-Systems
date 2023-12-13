#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <wait.h>
#include <string.h>

/*
	p
f1		f2
f3
*/

// p chiede all'utente di inserire da tastiera il nome di un signal, e poi lo invia a f1
// f1 lo inoltra a f2
// f2 traduce la stringa in un signal decimale e la invia a f1
// f1 genera il comando di sistema per inviare il signal a f3
// f3 riceve il signal e lo ignora

int main() {
	pid_t val_1;
	int fd[2];
	int readBuffer[100];
	int f1_status, f2_status, f3_status;
	pipe(fd);
	val_1 = fork();

	if(val_1 == 0) {  //F1
		pid_t val_2 = fork();
		if(val_2 == 0) {
			// f3
			close(fd[1]);
			signal(SIGTER, SIG_IGN);
			signal(SIGINT, SIG_IGN);
			signal(SIGSTOP, SIG_IGN);
			signal(SIGCONT, SIG_IGN);
			exit(0);
		}
		else {
			//f1
			write(fd[1], NULL, 0);
			sleep(5);
			if(dec == 1) {
				kill((int)(getpid()), SIGTERM);
			}
			else if(dec == 2) {
				kill((int)(getpid()), SIGKILL);
			}
			else if(dec == 3) {
				kill((int)(getpid()), SIGINT);
			}
			else if(dec == 4) {
				kill((int)(getpid()), SIGSTOP);
			}
			else {
				kill((int)(getpid()), SIGCONT);
			}
			exit(0);
		}
	}
	else {
		pid_t val_3 = fork();
		if(val_3 == 0) {
			// f2
			int dec;
			read(fd[0], readBuffer, sizeof(readBuffer));
			printf("f2 sta leggendo...");
			if(string == "SIGTERM") {
				dec = 1;
			}
			else if(string == "SIGKILL") {
				dec = 2;
			}
			else if(string == "SIGINT") {
				dec = 3;
			}
			else if(string == "SIGSTOP") {
				dec = 4;
			}
			else if(string == "SIGCONT") {
				dec = 5;
			}
			write(fd[1], dec, 1);
			exit(0);
		}
		else {
			// p
			char string[];
			printf("Inserisci il nome di un signal: ");
			scanf("%s", string);
			printf("p sta scrivendo...");
			write(fd[1], string, strlen(string));
			exit(0);
		}
	}
	wait(&f1_status);
	wait(&f2_status);
	wait(&f3_status);
}