#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

void leggi_conteggi(const char *filename, int contatori[28]) {
    int fd = open(filename, O_RDONLY);
    if (fd == -1) {
        perror("Errore nell'apertura del file");
        exit(EXIT_FAILURE);
    }

    read(fd, contatori, sizeof(int) * 28);
    close(fd);
}

void stampa_occorrenze(const char *parola, int contatori[28]) {
    printf("Ocurrenze della parola \"%s\":\n", parola);
    for (int i = 0; i < 10 && parola[i] != '\0'; i++) {
        char carattere = parola[i];
        if ((carattere >= 'a' && carattere <= 'z') || (carattere >= 'A' && carattere <= 'Z')) {
            printf("%c: %d\n", carattere, contatori[(carattere | 32) - 'a']);
        } else if (carattere >= '0' && carattere <= '9') {
            printf("Numero: %d\n", contatori[26]);
        } else {
            printf("Altro simbolo: %d\n", contatori[27]);
        }
    }
}

int main() {
    int contatori[28] = {0};
    leggi_conteggi("conteggi.dat", contatori);

    char parola[11];
    printf("Inserisci una parola (massimo 10 caratteri): ");
    scanf("%10s", parola);

    stampa_occorrenze(parola, contatori);

    return 0;
}