// INPUT: file quesito1.tx
// OUTPUT: stampare righe in cui è presente la stringa 'thread' nel file 

!#/bin/bash

if[$# -eq0];
then
    echo 'Numero file non corretto'
elif[$# -gt1];
then
    echo 'Numero file non corretto'

else
    if[-f $*];
    then
        grep -i thread quesito1.tx
    else
        echo 'File non presente'


