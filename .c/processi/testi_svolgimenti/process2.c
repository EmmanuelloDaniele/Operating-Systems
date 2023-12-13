#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <wait.h>
#include <sys.types.h>

// p manda un segnale di terminazione a f, che lo ignorerà 

int main() {
	int child_status; // per aspettare la terminazione del figlio
	pid_t pid;
	pid = fork();

	if(pid == 0) {
		// child process
		signal(SIGTERM, SIG_IGN); // SIGTERM è il segnale di terminazione che viene ricevuto, SIG_IGN per ignorarlo
		printf("%d", (int)getpid()); // stampo il pid del figlio
	} 
	else {
		// p
		printf("%d", (int)getpid()); // stampo il pid del padre
		printf("%d", (int)pid()); // stampo il pid del figlio
		int kill(pid, SIGTERM); // con kill invio un segnale, in questo caso SIGTERM
	}

	wait(&child_status);
}