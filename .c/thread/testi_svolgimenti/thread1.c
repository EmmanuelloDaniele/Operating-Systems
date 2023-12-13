#include <pthread.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>

/*
	CONSEGNA: realizzare un programma che sfrutti i thread per verificare se una stringa è palindroma
																										*/

// le struct vanno fatte sempre prima del main

struct print_params {
	char word[];
	int position;
}

int main() {

	p_thread thread1_id; // creo id del primo thread
	p_thread thread2_id; // creo id del secondo thread

	char* thread1_returnValue; // serve per far ritornare il valore della thread function 1, tutto ciò grazie al join
	char* thread2_returnValue; 

	char string[] = "osso"; // inizializzo la stringa che voglio controllare
	int cen = (int)(strlen(string))/2;

	struct print_params thread1_args; // serve per far associare le variabili della struct con il relativo thread
	struct print_params thread2_args;

	thread1_args.word = string;
	thread2_args.word = string;

	thread1_args.position = cen;
	thread2_args.position = cen + 1;

	pthread_create(&thread1_id, NULL, &print_string1, &thread1_args);
	pthread_create(&thread2_id, NULL, &print_string2, &thread2_args);

	/* 	
		commento sulle due funzioni pthread_create:
		&thread1_id		->		id del thread
		NULL			->		si scrive sempre cosi
		&print_string1	->		puntatore alla thread function, che inizializzeremo dopo
		&thread1_args	->		puntatore all'insieme di argomenti di input che specifica su quali dati dovrà lavorare il thread
	*/

	pthread_join(thread1_id, (void**)&thread1_returnValue);
	pthread_join(thread2_id, (void**)&thread2_returnValue);

	/*
		commento sulle due funzioni pthread_join:
		thread1_id						->		id del thread
		(void**)&thread1_returnValue	->		puntatore a variable void che punta al valore di ritorno del thread
	*/

	if(thread1_returnValue == thread2_returnValue) { // confronta i valori di ritorno dei due thread
		printf("Stringa palindroma");
	}
	else {
		printf("Stringa non palindroma");
	}

// le thread function vanno sempre dopo il main

	void* print_string1(void* parameters) { // creo la prima thread function
		/* la variabile parameters sarà associata ai valori della struct tramite la variabile pp */
		struct print_params* pp = (struct print_params*) parameters;
		/* il buffer è una stringa di lunghezza pp, in questo caso specifico 2 */
		char buffer[pp->position];
		for(int i = 0; i <= pp->position; i++) {
			buffer[i] = pp->word[i]; // memorizzo nel buffer le lettere della prima metà della parola
		}
		return (void*)&buffer;
	}

	void* print_String2(void* parameters) { // creo la seconda thread function
		struct print_params* pp = (struct print_params*) paramaters;
		char buffer[(sizeof(pp->word() - pp->position())) + 1]; // ( 4 - 3 ) + 1 = 2
		int c = 0;
		for(int i = sizeof(pp->word()); i >= position; i--) {
			buffer[c] = pp->word[i]; 
			// metto buffer[c] perchè se mettessi i, partirebbe da buffer[3] anzichè da buffer[0]
			c++;
		}
		return (void*)&buffer;
	}

}