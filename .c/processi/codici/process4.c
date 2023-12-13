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
	int child1_status,child2_status,child3_status;
	int file_descriptor[2];
	pipe(file_descriptor);
	pid_t F1,F2,F3;
	char buffer[10];

	F1 = fork();

	if(F1 == 0){ //Codice F1
		F3 = fork();

		if(F3 == 0){  //Codice F3
			sleep(6);
			signal(SIGTERM, SIG_IGN);
			signal(SIGINT, SIG_IGN);
			signal(SIGSTOP, SIG_IGN);
			signal(SIGCONT, SIG_IGN);
			printf("F3 vive");
		}
		else{ //Codice F1

			write(file_descriptor[1],NULL,0);   //F1 inoltra ad F2 (Quindi tramite P?);
			printf("F1 inoltra la stringa a F2\n");

			//sleep(2);

			int intBuffer;
			read(file_descriptor[0], &intBuffer, sizeof(intBuffer));
			printf("F1 ha ricevuto il codice decimale del signal: %d\n", intBuffer);
			kill(F3, intBuffer);
			exit(0);
		}
	}
	else{
		F2 = fork();
			if(F2 == 0){ //Codice F2
				int dec_sign;				
				read(file_descriptor[0], &buffer, sizeof(buffer));

				if(strcmp(buffer, "SIGTERM") == 0) {
				dec_sign = 15;
					}
				else if(strcmp(buffer,"SIGKILL") == 0) {
				dec_sign = 9;
				}
				else if(strcmp(buffer,"SIGINT") == 0) {
				dec_sign = 2;
				}
				else if(strcmp(buffer, "SIGSTOP") == 0) {
				dec_sign = 17;
				}
				else if(strcmp(buffer, "SIGCONT") == 0) {
				dec_sign = 19;
				}
				write(file_descriptor[1], &dec_sign, sizeof(dec_sign));
				printf("F2 traduce la stringa e la manda indietro a F1 %d\n", dec_sign);
				exit(0);
			}
			else{
				//Codice P
				printf("Inserire codice SIGNAL:\n"); //P prende in input il valore del Signal formato char
				scanf("%s", buffer);

				close(file_descriptor[0]);
				write(file_descriptor[1], buffer, sizeof(buffer));  //P invia la stringa ad F1
				printf("P manda il signal in formato stringa ad F1: %s\n", buffer);

				write(file_descriptor[1], NULL, 0); //P inoltra ad F2
				write(file_descriptor[1], NULL, 0); //P inoltra ad F1
			}
	}
	wait(&child1_status);
	wait(&child2_status);
	wait(&child3_status);
	return 0;
}