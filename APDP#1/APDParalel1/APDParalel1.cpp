#include "mpi.h"
#include <stdio.h>
#include <math.h>
#include <time.h>
#define Number 4108

int main(int argc, char** argv)
{
	int rank, numprocs;
	int x, low, high;
	clock_t t;
	int marks[Number], result[Number];
	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);

	/* add portion of data */
	x = Number / (numprocs-1);
	int dif = (((numprocs - 1) - Number % (numprocs - 1)) * (x + 1)) - (((numprocs - 1) - Number % (numprocs - 1)) * x);
	if (Number % (numprocs - 1) != 0) {
		if ((rank-1) >= (numprocs - 1) - Number % (numprocs - 1)) {
			low = (rank - 1) * (x + 1) - dif;
			high = low + (x + 1);
		}
		else {
			low = (rank - 1) * x;
			high = low + x;
		}
	}
	else {
		low = (rank - 1) * x;
		high = low + x;
	}
	
	if (low ==0 || low==1) {
		low = 2;
	}
	//printf("For process %d low: %d si high: %d\n", rank, low, high);

	for (int i = 0; i < Number; i++) {
		marks[i] = 0;//initializeaza toate numerele ca prime
	}

	if (rank == 0) {
		t = clock();
		for (int i = 2; i < Number; i++) {
			if (marks[i] == 0) {//daca este marcat ca prim
				for (int j = 1; j < numprocs; j++) {
					MPI_Send(&i, 1, MPI_INT, j, 1, MPI_COMM_WORLD);//trimite catre celelalte procese pentru marcare
					//printf("prime send to %d: %d\n", j, i);
				}
			}
			else {
				int doNot = -1;
				for (int j = 1; j < numprocs; j++) {
					MPI_Send(&doNot, 1, MPI_INT, j, 1, MPI_COMM_WORLD);//daca nu este marcat trimite o valoare 
					//care le spune sa nu marcheze nimic
				}
			}
			for (int j = 1; j < numprocs; j++) {
				MPI_Recv(&low, 1, MPI_INT, j, 1, MPI_COMM_WORLD, &status);
				MPI_Recv(&high, 1, MPI_INT, j, 1, MPI_COMM_WORLD, &status);//aduna rezultatele de la toate celelalte procese
				MPI_Recv(&result, Number, MPI_INT, j, 1, MPI_COMM_WORLD, &status);
				/*printf("result received from %d:", j);
				for (int j = 0; j < Number; j++) {
					printf("%d", result[j]);
				}
				printf("\n");*/
				for (int k = low; k < high; k++) {
					if (marks[k] == 0 && result[k]==-1) {
						marks[k] = result[k];
					}
				}
				/*printf("marks after result from %d: ", j);
				for (int j = 0; j < Number; j++) {
					printf("%d", marks[j]);
				}
				printf("\n");*/
			}
		}
		t = clock() - t;
		double time_taken = ((double)t) / CLOCKS_PER_SEC;
		printf("Executa a durat %f secunde\n", time_taken);
		/*printf("Rezultat: ");
		for (int i = 2; i < Number; i++) {
			if (marks[i] == 0) {
				printf("%d ", i);
			}
		}
		printf("\n");*/
	}
	else {
		int prim;
		for (int i = 2; i < Number; i++) {
			MPI_Recv(&prim, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
			if (prim != -1) {
				for (int j = low; j < high; j++) {
					if (j % prim == 0 && j!=prim) {//marcheaza pe portiunea lor
						marks[j] = -1;
					}
				}
			}
			MPI_Send(&low, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
			MPI_Send(&high, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
			MPI_Send(&marks, Number, MPI_INT, 0, 1, MPI_COMM_WORLD);//trimit noul sir marcat
		}
	}
	MPI_Finalize();
}