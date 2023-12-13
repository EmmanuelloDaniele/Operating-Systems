#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

void conta_caratteri(const char *filename) {
    int fd = open(filename, O_RDONLY);
    if (fd == -1) {
        perror("Errore nell'apertura del file");
        exit(EXIT_FAILURE);
    }

    int contatori[28] = {0}; // 26 per le lettere, 1 per i numeri, 1 per gli altri

    char buffer;
    while (read(fd, &buffer, 1) > 0) {
        if ((buffer >= 'a' && buffer <= 'z') || (buffer >= 'A' && buffer <= 'Z')) {
            contatori[(buffer | 32) - 'a']++;
        } else if (buffer >= '0' && buffer <= '9') {
            contatori[26]++;
        } else {
            contatori[27]++;
        }
    }

    close(fd);

    int fd_output = open("conteggi.dat", O_WRONLY | O_CREAT | O_TRUNC, 0666);
    if (fd_output == -1) {
        perror("Errore nell'apertura del file conteggi.dat");
        exit(EXIT_FAILURE);
    }

    write(fd_output, contatori, sizeof(contatori));
    close(fd_output);

    printf("Conteggi salvati nel file conteggi.dat\n");
}

int main() {
    conta_caratteri("sorgente.txt");
    return 0;
}