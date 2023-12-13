#!/bin/bash

#Si realizzi uno script che analizzi tutti i file di estensione .c presente nella directory corrente.
#Bisogna contare il numero di cicli for presenti all'interno di ciascun file
#Il risultato sarà inserito in un file chiamato "report.txt"

grep -n for *.c > report.txt