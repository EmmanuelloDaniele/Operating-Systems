#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct myrecord {
    int codice;
    int rw;
    char text[100];
};

void cancella_testo(cost char *filename, int cod_richiesto){
    int fd = open(filename, O_RDWR);
    if (fd == -1){
        perror("errore nell'apetura del file");
        exit(EXIT_FAILURE);
    }


struct myrecord record;
ssize_t byteLetti;
int recordFound = 0 ;
while ((byteLetti = read(fd, &record, sizeof(struct myrecord)))>0){
    if (record.codice == cod_richiesto){
        recordFound = 1;
        if (record.rw == 0){
            printf("cancellazione non consetita,\n");
            close(fd);
            exit(EXIT_SUCCESS);
        }else{
            memset(record.text, '\0', sizeof(record.text));

            lseek(fd, sizeof(struct myrecord), SEEK_CUR);
            //sovrascrivo il record:
            write(fd, &record, sizeof(struct myrecord));
            
            printf("Cancellazzione avvenuta.\n");
    }
    break;
}
}
    if (!recordFound){
        print("record non trovato.\n");
    }
    close(fd)
    }

int main(){
    int cod_richiesto;

    printf("inserisci il codice richiesto:");
    scanf("%d",&cod_richiesto);
    
    cancella_testo("intestazioni.dat", cod_richiesto);

    retun 0;
}

