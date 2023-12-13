// INPUT: path di una directory e stringa da ricercare nei file .txt
// OUTPUT: file che contengono la stringa, e il numero di riga in cui è stata trovata

if[$#-le1];
then
    echo 'Numero parametri errato'
elif[$#-ge3];
then
    echo 'Numero parametri errato'
else
    if[-d $1];
        grep -n $2 '*\.txt'
    else
        echo 'directory inesistente'
    fi
fi
