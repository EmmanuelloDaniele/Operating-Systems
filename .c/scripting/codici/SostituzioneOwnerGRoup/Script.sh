#!/bin/sh
#Scrivere uno script che riceva come parametri di input il path di una directory e due stringhe che rappresentano rispettivamente un “owner name” ed un “group name”.
#Lo script dovrà cambiare owner e group di tutti i file il cui nome inizia con la lettera ‘A’.
#Si implementi inoltre un controllo per verificare il corretto numero di parametri in ingresso e l’esistenza della directory.
if [ $# -eq 3 ]
then
    if [ -d $1 ]
    then
        echo "Directory: $1"
        echo "Owner name: $2"
        echo "Group name: $3"
        grep -nr -E "$2|$3" A*.txt | sed "s/$2/provaSostituito/g;t;s/$3/ciaoSostituito/g" A*.txt
        #grep -nr -E "$2|$3" A*.txt | awk '{gsub($1, provaSostituito);gsub($2, ciaoSostituito); print } ' A*.txt
    else
        echo "La directory non esiste"
    fi
else
    echo "Errore numero argomenti"
fi
