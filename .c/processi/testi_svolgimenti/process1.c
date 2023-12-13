#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys.types.h>

// p genera un intero casuale x e lo invia a f1
// f1 riceve e lo invia a f2
// f2 lo rimanda a p che controlla se il valore ricevuto è uguale a quello inviato in precedenza

int main() {

	pid_t val_1; // inizializzo il id del primo processo
	int f1_status;
	int f2_status;
	int fd[2]; // sempre fd[2] perchè abbiamo stato 0 (legge), 1 (scrive), 2 (errore), sarebbe un array di interi per il file descriptor
	char readBuffer[100]; // buffer di lettura, obbligatorio quando c'è la pipe, 100 messo a caso
	pipe(fd); // pipe va messa quando ci si scambia dei messaggi
	val_1 = fork(); // la fork va messa sempre dopo il pipe

	if(val_1 == 0) {
		// f1
		close(fd[0]); // f1 scrive, quindi chiudo l'estremità di lettura (0)
		printf("f1 sta scrivendo...");
		write(fd[1], NULL, 0); // metto null perchè non c'è messaggio da trasmettere e quindi dimensione 0 
		exit(0); // distrugge il processo
	}

	else {
		pid_t val_2 = fork();
		if (val_2 == 0) {
			// f2
			close(fd[0]); // f2 scrive, quindi chiudo l'estremità di lettura (0)
			printf("f2 sta scrivendo...");
			write(fd[1], NULL, 0); // metto null perchè non c'è messaggio da trasmettere e quindi dimensione 0
			exit(0); // distrugge il processo
		}
		else {
			// p
			int x = rand() % 100; // genero un numero random tra 0 e 99
			printf("p sta scrivendo");
			write(fd[1], x, 2); // scrivo x, da due bit
			sleep(3); // 3 sono i secondi, abbastanza arbitrario purchè sia grande
			read(fd[0], readBuffer, sizeof(readBuffer)); // leggo il valore di x 
			if(x == readBuffer) { // verifico se x è uguale a quando l'ho inviata
				printf("valori uguali");
			}	
			else {
				printf("errore");
			}
		}
	}
	
	wait(&f1.status);
	wait(&f2.status);
} 