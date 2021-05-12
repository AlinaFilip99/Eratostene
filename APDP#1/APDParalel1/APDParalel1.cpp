#include "mpi.h"
#include <stdio.h>
#include <math.h>
#include <time.h>
#define Number 100

bool prim(int nr) {//verifica daca numarul este prim 
	bool yes = true;//presupunem numarul prim

	if (nr <= 1) {//daca este cel mult 1
		yes = false;//nu este prim
	}

	for (int i = 2; i < nr; i++) {//cautam un numar care sa se imparta la numarul pe care il verificam
		if (nr % i == 0) {//daca se imparte
			yes = false;//nu este prim
		}
	}

	return yes;//returnam valoarea
}

int main(int argc, char** argv)
{
	int rank, numprocs;
	int i, x, low, high;
	int result[Number - 2];
	int results[Number - 2];
	clock_t t;

	typedef struct {
		int value, mark;
	}          Sir;

	Sir s[Number - 2];
	MPI_Datatype sirtype, oldtypes[1];
	int          blockcounts[1];

	// MPI_Aint type used to be consistent with syntax of
	// MPI_Type_extent routine
	MPI_Aint    offsets[1];

	MPI_Status stat;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);

	offsets[0] = 0;
	oldtypes[0] = MPI_INT;
	blockcounts[0] = 2;

	// define structured type and commit it
	MPI_Type_create_struct(1, blockcounts, offsets, oldtypes, &sirtype);
	MPI_Type_commit(&sirtype);

	if (rank == 0) {
		t = clock();
		for (i = 0; i < Number - 2; i++) {
			s[i].value = i + 2;
			s[i].mark = 0;
		}
	}

	/* broadcast data */
	MPI_Bcast(s, Number - 2, sirtype, 0, MPI_COMM_WORLD);

	/* add portion of data */
	x = (Number - 2) / numprocs;
	int dif = ((numprocs - (Number - 2) % numprocs) * (x + 1)) - ((numprocs - (Number - 2) % numprocs) * x);
	if ((Number - 2) % numprocs != 0) {
		if (rank >= numprocs - (Number - 2) % numprocs) {
			low = rank * (x + 1) - dif;
			high = low + (x + 1);
		}
		else {
			low = rank * x;
			high = low + x;
		}
	}
	else {
		low = rank * x;
		high = low + x;
	}
	//printf("For process %d low: %d si high: %d\n", rank, low, high);

	for (i = low; i < high; i++) {
		if (prim(s[i].value) == true && s[i].mark == 0) {
			result[i] = s[i].value;

			for (int j = 0; j < Number - 2; j++) {
				if (s[j].value % s[i].value == 0) {
					s[j].mark = 1;
				}
			}

		}
		else {
			result[i] = -1;
		}
	}

	/* compute global sum */
	MPI_Reduce(&result, &results, Number - 2, MPI_INT, MPI_MAX, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		t = clock() - t;
		double time_taken = ((double)t) / CLOCKS_PER_SEC;
		printf("Pentru numarul %d s-au gasit numerele prime: \n", Number);
		for (int i = 0; i < Number - 2; i++) {
			if (results[i] != -1) {
				printf(" %d ", results[i]);
			}
		}
		printf("\n Executa a durat %f secunde", time_taken);
	}

	MPI_Finalize();
}