// INPUT: un programma
// OUTPUT: terminare il programma se è in esecuzione

#!/bin/bash

if[$#-eq0];
then
    echo 'Numero di argomenti invalido'
elif[$#-gt1];
then
    echo 'Numero di argomenti invalido'
else
    program id = $(ps-e | grep $1 | awk '{print $1}')
    if[program_id -eq0];
    then
        echo 'Programma non in esecuzione'
    else
        kill program_id
        echo 'Programma trovato e interrotto'
    fi 
fi 

/* COMMENTO RIGA 15
   creo variabile program_id
   con ps-e mi spuntano tutti i programm in esecuzione dettagliatamente
   con grep $* prendo tutti i programmi
   con awk '{print $1}' stampa il primo, che è quello che mi serve
*/
