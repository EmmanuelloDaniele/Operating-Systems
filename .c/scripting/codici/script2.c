// INPUT: 	directory
// OUTPUT:	file java con metodo main e compila

if[$#-eq0];
then
    echo 'Nessun parametro'
elif[$#-gt1]
then
    echo 'Troppi parametri'
else
    if[-d $#]
    then
        cd$#
            grep main */.java | javac
        else
            echo 'Directory inesistente'
    fi
fi
