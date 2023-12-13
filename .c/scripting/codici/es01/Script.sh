#!/bin/bash

# Comandi utilizzati
# mv -> move
# ls -l -> stampa la lista dei file presenti all'interno di una directory, con rispettivi permessi.
# awk -> script utilizzato per la manipolazione dei dati
# | -> pipe 

#La prima riga di codice funziona nella seguente maniera:
# 1) Inizialmente mi prendo tutti i file presenti all'interno della directory, e sarà l'input di awk
# 2) awk controlla se, all'interno della directory, esistono dei file che hanno una linea orizzonale (in qualunque posizione) e, al tempo stesso, verificano se l'ultima lettera è la a.
# 3) Successivamente, verifica se, oltre alla lettera a, ci sia solamente un numero che vada da 0 a 9.
# 4) Infine, stampa su schermo il risultato ed eseguirà il comando mv, spostando i file richiesti nella dirA
mv $(ls -l | awk '/^[-].*a[0-9]+$/ {print $9}') dirA
mv $(ls -l | awk '/^[-].*b[0-9]+$/ {print $9}') dirB

