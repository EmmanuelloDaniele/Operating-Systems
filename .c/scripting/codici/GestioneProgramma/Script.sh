#!/bin/bash
#Realizzare uno script che dato in ingresso il nome di un programma
#Si verifichi che questo sia attualmente in esecuzione ed eventualmente ne provochi la terminazione.
#Si implementi inoltre un controllo per verificare il corretto numero di parametri in ingresso

#pidof consiste nel prendere il PID del processo, tramite parametro -x, di un programma che digitiamo.
#Se il valore è maggiore rispetto a null, allora vuol dire che è già in esecuzione
#Altrimenti, verrà eseguito il programma presso il percorso /usr/bin/'programma digitato'
if pidof -x $1 > /dev/null
then
    echo "Già in esecuzione"
else
    /usr/bin/$1
fi
