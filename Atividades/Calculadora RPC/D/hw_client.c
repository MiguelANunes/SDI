#include <stdio.h>
#include <rpc/rpc.h>

// Interface gerada pelo RPCGen a partir da IDL (hw.x) especificada
#include "hw.h"

int main (int argc, char *argv[]) {
	// Estrutura RPC de comunicação
	CLIENT *cl;

	// Parâmetros das funçcões
	char        *par_f1 = (char *) malloc(256*sizeof(char));
	int          par_f2;
	struct param par_f3;
	int 				 parcela1 = 999;
	int 				 parcela2 = 999;
	char 				 operador = '-';

	// Retorno das funções
	char **ret_f0 = NULL;
        int   *ret_f1 = NULL;
        int   *ret_f2 = NULL;
        int   *ret_f3 = NULL;
	struct param *ret_f4 = NULL;
	int *ret_f5 = NULL;

	// Verificação dos parâmetros oriundos da console
	if (argc != 2) {
		printf("ERRO: ./client <hostname>\n");
		exit(1);
	}

	if (!fscanf(stdin, "%d %d %c", &parcela1, &parcela2, &operador)) {
		printf ("ERRO\n");
		return 1;
	}/* else {
		//aqui eh o código que o professor fez
		printf ("%d%c%d=1101\n", parcela1, operador, parcela2);
	}*/


	// Conexão com servidor RPC
	cl = clnt_create(argv[1], PROG, VERS, "tcp");
	if (cl == NULL) {
		clnt_pcreateerror(argv[1]);
		exit(1);
	}

	//Implementacao
	par_f3.arg1 = parcela1;
	par_f3.arg2 = parcela2;
	switch (operador)
	{
	case '+':
		ret_f5 = soma_1(&par_f3, cl);
		break;
	case '-':
		ret_f5 = subtracao_1(&par_f3, cl);
		break;
	case '*':
		ret_f5 = multiplicacao_1(&par_f3, cl);
		break;
	case '/':
		ret_f5 = divisao_1(&par_f3, cl);
		break;
	
	default:
		if (ret_f5 == NULL) {
			clnt_perror(cl,argv[1]);
			exit(1);
		}
		break;
	}
	printf("%d%c%d=%d\n",par_f3.arg1,operador,par_f3.arg2,*ret_f5);

	// Atribuições de valores para os parâmetros
	strcpy (par_f1, "mauricio pillon");
	par_f2 = 1;
    par_f3.arg1 = 5;
	par_f3.arg2 = 4;

        // Chamadas das funções remotas
      //  printf ("Chamando func0 (sem parâmetros)\n");
	ret_f0 = func0_1(NULL, cl);
	if (ret_f0 == NULL) {
	    clnt_perror(cl,argv[1]);
	    exit(1);
	}
       // printf ("Retorno func0 (%s)\n", *ret_f0);

        //printf ("Chamando func1 (%s)\n", par_f1);
	ret_f1 = func1_1(&par_f1, cl);
	if (ret_f1 == NULL) {
	    clnt_perror(cl,argv[1]);
	    exit(1);
	}
        //printf ("Retorno func1 (%d)\n", *ret_f1);

        //printf ("Chamando func2 (%d)\n", par_f2);
	ret_f2 = func2_1(&par_f2, cl);
	if (ret_f2 == NULL) {
	    clnt_perror(cl,argv[1]);
	    exit(1);
	}
       // printf ("Retorno func2 (%d)\n", *ret_f2);

        //printf ("Chamando func3 (%d/%d)\n", par_f3.arg1, par_f3.arg2);
        ret_f3 = func3_1(&par_f3, cl);
	if (ret_f3 == NULL) {
	    clnt_perror(cl,argv[1]);
	    exit(1);
	}
       // printf ("Retorno func3 (%d)\n", *ret_f3);

        ret_f4 = (struct param *) malloc (sizeof(struct param));
				ret_f4->arg1 = 1;
        ret_f4->arg2 = 2;
       // printf ("Chamando func4 (%d/%d)\n", ret_f4->arg1, ret_f4->arg2);
        ret_f4 = func4_1(NULL, cl);
	if (ret_f4 == NULL) {
	    clnt_perror(cl,argv[1]);
	    exit(1);
	}
       // printf ("Retorno func4 (%d/%d)\n", ret_f4->arg1, ret_f4->arg2);

  // clnt_destroy(cl);
	myexit_1(NULL, cl);
	pmap_unset(PROG, VERS);

	return 0;
}
