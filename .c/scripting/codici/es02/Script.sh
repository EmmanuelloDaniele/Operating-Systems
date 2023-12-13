#!/bin/bash
#Scrivere uno script che stampi il nome di tutti i file .txt presenti nella directory corrente nel cui
#contenuto sia presente la stringa “cane e gatto”:
#Input:
#./printfilename
#Output:
#file1
#file2
# Comandi utilizzati
# grep -> utilizzato per la ricerca di stringhe e caratteri, una sorta di filtro
# Se imposto print $1 mi stampa tutti i file che hanno il contenuto 'cane e gatto'
# Se imposto {print $2} mi stampa i testi dei file che hanno la condizione di grep
# Alternativa 1
grep 'cane e gatto' *.txt | awk -F: '{print $1}'
# Alternativa 2
awk '/cane e gatto/ {print FILENAME}' .*txt