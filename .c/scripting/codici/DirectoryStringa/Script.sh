#!/bin/sh

#Scrivere uno script che riceva come parametri di input il path di una directory ed una stringa da ricercare all’interno dei file con estensione .txt presenti in essa.
# Lo script dovrà restituire in output l’elenco dei file che contengono la stringa, e la relativa riga in cui essa è stata trovata, secondo il seguente formato:
#./quesito3.sh directory stringa
#(linea) nomefile.txt
#(linea) nomefile.txt
#Si implementi inoltre un controllo per verificare il corretto numero di parametri in ingresso e l’esistenza della directory.
if [ $# -eq 2 ]
then
    if [ -d $2 ]
    then
        echo "String: $1"
        echo "Directory: $2"
        grep -nr $1 $2
    else
        echo "Errore"
    fi
fi
