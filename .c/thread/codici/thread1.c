/*
	CONSEGNA: realizzare un programma che sfrutti i thread per verificare se una stringa è palindroma
																										*/

// le struct vanno fatte sempre prima del main

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

struct print_params{
    int position;
    char word[];
};

int main(){
    pthread_t thread1;
    pthread_t thread2;
    char* thread1_return;
    char* thread2_return;
    struct print_params thread1_struct;
    struct print_params thread2_struct;

    char string[] = "anna"; //Setto la parola palindroma; 
    int half = ((int)strlen(string))/2;

    thread1_struct.position = half; //Il primo thread arriverá a metá parola;
    thread1_struct.position = half+1; //Il secondo thread partirá da metá parola;

    pthread_create(thread1, NULL,&thread1_function,&thread1_struct);
    pthread_create(thread2, NULL,&thread2_function,&thread2_struct);

    pthread_join(thread1_id, (void**)&thread1_return);
	pthread_join(thread2_id, (void**)&thread2_return);

    if(thread1_return == thread2_return){
        printf("La stringa é palindroma");
    }
    else{
        printf("La stringa non é palindroma");
    }

    void* thread1_function(void* params){
        struct print_params* pp = (struct print_params) params;
        char buffer[pp->position];
        for(int i, i<= pp->position; i++){
            buffer[i] = pp->word[i];
        }
        return (void*)&buffer;

    }

    void* thread2_function(void* params){
        struct print_params* pp = (struct print_params) params;
        char buffer[sizeof((pp->word)) - (pp->position) + 1];
        int c = 0;
        for(int i = sizeof(pp->word), i>= pp->position; i++){
            buffer[c] = pp->word[i];
            c++
        }
        return (void*)&buffer;
    }

}