// INPUT: path di una directory, file in estensione .ext1 e file in estensione .ext2
// OUTPUT: modificare i file in formato .ext1 in file .ext2

!#/bin/bash

if[$# -le2]
then
    echo'Numero oggetti in input non corretto'
elif[$# -ge4]
then
    echo'Numero di oggetti in input non corretto'
else
    if[-d $1]
    then
        cd $1
        mv *\.ext1 \*.ext2
    else
        echo 'Directory non esistente'
    fi
fi 