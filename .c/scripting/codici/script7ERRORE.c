// INPUT: file
// OUTPUT: stampare file dove ogni riga è preceduta dal suo numero corrispondente

!#/bin/bash

if[$# -lt1];
then
    echo 'Numero file input errato'
elif[$# -gt1]; 
then
    echo 'Numero file input errato'
else
    if[-f $*]
    then
        grep -n