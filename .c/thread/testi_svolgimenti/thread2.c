#include <pthread.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

/* 		REALIZZARE UN PROGRAMMA PER LA CREAZIONE DI DUE THREAD
		BISOGNA STAMPRE n NUMERI ED m LETTERE SCELTE CASUALMENTE
		UTILIZZARE SOLO UN'UNICA THREAD FUNCTION					*/

struct print_params {
	int n_volte;
	char da_stampare[];
};

int main() {

	pthread_t thread1_id; // id del primo thread
	pthread_t thread2_id; // id del secondo thread

	struct print_params thread1_args;
	struct print_params thread2_args;

	int x = rand() % 100; // genero il numero casuale che indica quanti numeri random dovranno essere stampati
	int y = rand() % 100; // genero il numero casuale che indica quante lettere random dovranno essere stampate

	thread1_args.n_volte = x;
	thread1_args.da_stampare = ''; // no panic, questa stringa vuota servirà dopo nelL'if della thread function

	thread2_args.n_volte = y;
	thread2_args.da_stampare = {a, b, c, d, e, f, g, h, i, l, m, n, o, p, q, r, s, t, u, v, z}; // alfabeto

	pthread_create(&thread1_id, NULL, &thread_function, &thread1_args);
	phtread_create(&thread2_id, NULL, &thread_function, &thread2_args);

	phtread_join(thread1_id, NULL); // metto NULL perchè stavolta non ritorna nulla
	pthread_join(thread2_id, NULL);

	void* thread_function(void* parameters) {
		struct print_params* a = (struct print_params*) parameters; 
		int casual = 0; // inizializzo il numero casuale a 0
		if (a->da_stampare == '') { // se il valore da stampare è '' allora siamo in procinto di stampare i numeri
			for (int i = 0; i < a->n_volte; i++) { // il ciclo si ripeterà per n_volte, quindi nel caso dei numeri x volte
				casual = rand(); // ad ogni ciclo for il numero casuale prenderà un valore random da 0 a 32766
				printf("%d\n", casual); // stampo il numero casuale
			}
		}
		else { // altrimenti dobbiamo stampare le lettere
			for (int j = 0; j < a->n_volte; j++) { // il ciclo si ripeterà per n_volte, quindi qui y volte
				casual = rand()%21; // qui il numero random prenderà valore da 0 a 20, va a ricoprire il numero di lettere
				// ora stamperemo il casual-esimo elemento dell'array da_stampare che in questo caso è quello delle lettere
				printf("%c\n", a->da_stampare[casual]);
			}
		}
	}

}