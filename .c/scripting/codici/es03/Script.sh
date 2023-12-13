#!/bin/bash
#Scrivere uno script che riceva il path di una directory come parametro.
#Lo script deve ricercare degli indirizzi email nel contenuto di tutti i file con estensione .txt presenti
#nella directory passata come parametro, in cui il nome inizi con una lettera e termini con un
#numero.
#La stringa da ricercare deve avere il formato nome.cognome@dominio.com (si scriva l’opportuna
#espressione regolare in cui nome, cognome, dominio sono sequenze di sole lettere).
#Restituire in output la lista di email trovate.
#Input:
#./searchmail path
#Output:
#vincenzo.agate@gmail.com
#vincenzo.agate@unipa.it
grep -E '[a-z]+\.[a-z]+@[a-z]+\.com' $(ls -l | awk '$9 ~ /^[a-z].*[0-9]\.txt/ {print $9}') | awk -F : '{print $2}'
