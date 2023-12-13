#!/bin/bash
#Si realizzi uno script che, dato in ingresso il percorso di una directory, cerchi tutti i file con estensione .java in cui è presente il metodo main, e li compili.
#Si implementino i controlli per verificare il corretto numero di parametri in ingresso e l'esistenza della directory
if [[ -d $1 ]]
then
    cd $1 | grep -r $1 -e 'main' | javac *.java
else
    echo "Errore"
fi
