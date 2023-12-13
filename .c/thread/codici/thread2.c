/* 		REALIZZARE UN PROGRAMMA PER LA CREAZIONE DI DUE THREAD
		BISOGNA STAMPRE n NUMERI ED m LETTERE SCELTE CASUALMENTE
		UTILIZZARE SOLO UN'UNICA THREAD FUNCTION					*/

#include <pthread.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

struct print_params{
    int rep;
    char print_args[];
}

int main(){

    p_thread thread1;
    p_thread thread2;

    struct print_params thread1_params;
    struct print_params thread2_params;

    int x = rand() %100;
    int y = rand() %100;

    thread1_params.rep = x;
    thread2_params.rep = y;
    thread1_params.print_args = "";
    thread2_params.print_args = {a, b, c, d, e, f, g, h, i, l, m, n, o, p, q, r, s, t, u, v, z};

    pthread_create(&thread1, NULL, &thread_function, &thread1_params);
    pthread_create(&thread2, NULL, &thread_function, &thread2_params);
    pthread_join(&thread1, NULL); //Questa volta i thread non ritornano nulla;
    pthread_join(&thread2, NULL); //Questa volta i thread non ritornano nulla;

    void* thread_function(void* parameters){
        struct print_params* pp = (struct print_params*) parameters;

        int casual = 0; // inizializzo il numero casuale a 0
		if (pp->print_args == '') { // se il valore da stampare è '' allora siamo in procinto di stampare i numeri
			for (int i = 0; i < pp->rep; i++) { // il ciclo si ripeterà per n_volte, quindi nel caso dei numeri x volte
				casual = rand(); // ad ogni ciclo for il numero casuale prenderà un valore random da 0 a 32766
				printf("%d\n", casual); // stampo il numero casuale
			}
		}
		else { // altrimenti dobbiamo stampare le lettere
			for (int j = 0; j < pp->rep; j++) { // il ciclo si ripeterà per n_volte, quindi qui y volte
				casual = rand()%21; // qui il numero random prenderà valore da 0 a 20, va a ricoprire il numero di lettere
				// ora stamperemo il casual-esimo elemento dell'array da_stampare che in questo caso è quello delle lettere
				printf("%c\n", pp->print_args[casual]);
			}
		}
    } 

}