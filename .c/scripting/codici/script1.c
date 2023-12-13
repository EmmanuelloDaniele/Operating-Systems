//Input directory e due stringhe
//output modifica il proprietario e gruppo di file che iniziano con a

!#/bin/bash

if[$# -lt3];
then
    echo'Numero argomenti insufficenti'
elif[$# -ge4];
then
    echo'Numero argomenti errato'
else
    if[-d $1]
    then
        cd $1
        $ls -l | awk '$NF~/^A/' | chmod "u=$2", "g=$3" 
    else
    then
        echo'Non é presente nessuna directory in cui cercare'
    fi
fi

/*  Spiegazione riga 16
    $ls-l divide i campi e li mostra in maniera estesa
    awk per usare l'espressione regolare
    NF nell'ultimo campo /^A/ tutti gli elementi che iniziano con A
    chmod comando per modificare i permessi dei file
    u modifica proprietario, g gruppo, = assegna le modifiche dando come assegnazione gli input ricevuti dallo script
*/


1- Capire quali sono le entità conivolte 
2-Capire qualè la risosrsa
3-Capire come queste entità operano sulla risorsa
4-Implementare il monitor(classe che contiene i metodi che saranno syncronized)
5- Classe che contiene il main dove vengono creati l'oggetto del monitor i thead e instanziarli sull'oggeto monitor
(facendo conto che è riconducibile al concetto di consumatore produttore, un metodo che corrisponde vagamente al set o al get, una variabile che può essere letta o modificata)

