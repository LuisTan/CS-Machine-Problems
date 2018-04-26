// Tan, Luis Carlos B., THUV-1, 2015-04318
/*This program helps the user to find the real rational roots 
of any nth-degree polynomial in a sorted manner.  
By entering the highest degree of the polynomial 
and inputting the set of coefficients arranged from
 the constant coefficient to the nth degree coefficient, 
you will get the real rational roots of the polynomial.  
You can use this program repeatedly 
without the sacrificing the validity of the answers*/  

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <math.h> 
#define MAX 10
#define MAX_STRING 50

int get_degree();
int check_string( char string[], int degree);
int counter(int array[]);
int check_YorN( char YorN[]);
int count_factors(int number);
void bubble_sort(int to_be_sorted1[], int to_be_sorted2[], int roots);
void print_Rroots(int coeffs[], int pos_p, int pos_q, int h_degree, int zeroes, int temp_degree);
int lowest_term(int a, int b);

main()
{
	int index_YorN, index_array=0, index_string = 0, index_temp = 0, i_switch = 0;
	int EXIT_INPUT = 0, EXIT_INPUT2 = 0, Exit = 0;
	int counter_Rroots, no_of_q, no_of_p, highest_degree;
	int n =0, zero =0, i =0; 
	char numeric_string[MAX_STRING], *temp_string, Yes_or_No[MAX];
	
	do
	{
		printf("Enter the highest degree of the input polynomial: ");
		highest_degree = get_degree(); 
		getchar();
		printf("Enter %d integer coefficients starting from the ", highest_degree+1);
		printf("0th degree.\nSeparate each input by a comma: ");
		/*The program will ask for an input of the set of coeffficients and will also 
		check if the inputted is true. */
		EXIT_INPUT = 0; 
		EXIT_INPUT2 = 0; 
		do
		{
			fgets(numeric_string, MAX_STRING, stdin);
			if(check_string(numeric_string, highest_degree) == 1) EXIT_INPUT = 1; 
			else EXIT_INPUT = 0;
		}while (EXIT_INPUT == 0);
		/* Checks if the inputted value for the highest degree is 0.  
		If it is 0, then the program will immediately say that polynomial
		will not have any roots and will ask if the user wants to continue.
		If it is not 0 then the program skips this part.*/
		if (highest_degree == 0) 
		{
			printf("There input polynomial has no rational roots.\n");
			printf("Input new polynomial? ");
			do
			{
				fgets(Yes_or_No, MAX, stdin);
				Yes_or_No[strlen(Yes_or_No) - 1] = '\0';
				if (check_YorN(Yes_or_No) == 1)	EXIT_INPUT2 = 1; 
				else if (check_YorN(Yes_or_No) == 2)
				{
					Exit= 1;
					EXIT_INPUT2 = 1;
				}
				else printf("Invalid input. Yes or No?\n");
			}while (EXIT_INPUT2 == 0);
			if (Exit == 2)	break;
			else continue;
		}
		int TEMPORARY[highest_degree + 1];
		/*The inputted string is going to be converted to a set of integers 
		and these integer are to be put in the array - TEMPORARY*/
		temp_string = strtok(numeric_string, ",");
		do 
		{
			TEMPORARY[index_array] = atoi(temp_string);
			temp_string = strtok(NULL, ",");
			index_array++;
		}while ( temp_string != NULL );
		index_array = 0;
		/*The array of integers is going to be checked if the first or few
		first indeces is/are zero/seroes*/
		n = highest_degree;
		zero = 0;
		if(TEMPORARY[0] == 0)
		{
			do
			{
				for ( i_switch = 0; i_switch <= highest_degree - 1; i_switch++)
				{
					TEMPORARY[i_switch] = TEMPORARY[i_switch + 1];
				}
				zero++;
				n--;
			}while (TEMPORARY[0] == 0);
		}
		int COEFFICIENTS[n + 1] ;
		/*The elemnts in TEMPORARY are going to put in the array 
		COEFFICIENTS which is going to be one used in the process*/
		for ( i_switch = 0; i_switch <= n ; i_switch++)
		{
			COEFFICIENTS[i_switch] = TEMPORARY[i_switch];
		}
		/*Factors*/
		no_of_p = (count_factors(abs(COEFFICIENTS[0])))*2;
		no_of_q = (count_factors(abs(COEFFICIENTS[n])));
		/*Process*/
		print_Rroots(COEFFICIENTS, no_of_p,no_of_q, highest_degree, zero, n);
		printf("Input new polynomial? ");
		/*Check if the user still wants to continue*/
		do
		{
			fgets(Yes_or_No, MAX, stdin);
			Yes_or_No[strlen(Yes_or_No) - 1] = '\0';
			if (check_YorN(Yes_or_No) == 1)	EXIT_INPUT2 = 1; 
			else if (check_YorN(Yes_or_No) == 2)
			{
				Exit= 1;
				EXIT_INPUT2 = 1;
			}
			else printf("Invalid input. Yes or No?\n");
		}while (EXIT_INPUT2 == 0);
	}while (Exit == 0);
}

 /*This function will get the inputted degree
 and will check if the input is correct.*/
int get_degree()
{
	int EXIT = 0, i, converted;
	char string[20]; 
	
	while (EXIT == 0)
	{
		scanf("%s", string);
		for (i = 0; i <= strlen(string)-1 ; i++)
		{
			if (string[0] == '-'  && isdigit (string[1]) > 0)
			{
				printf("Sorry.  The degree must be equal or greater than 0. Please try again.\n");
				break; 
			}
			if (isdigit(string[i]) <= 0 )
			{
				printf("Sorry.  Invalid input. Please try again.\n");
				break;
			}
		}
		if (i > strlen(string) - 1) i--;
		if (isdigit(string[i]) <= 0) EXIT = 0;
		else  EXIT = 1;
	}
	
	converted = atoi(string);
	
	return converted; 
}

 /*This function will check if the string inputted 
 consists of correct numbers of coefficients, characters etc. */
int check_string( char string[], int degree)
{
	int correct = 0, i, ctr = 0;
	
	if (string[strlen(string) - 1] == '\n') string[strlen(string) - 1] = '\0';
	for ( i = 0 ; i <= strlen(string) - 1; i++)
	{
		if (isdigit(string[i]) <= 0 && string[i] != ',' && string[i] != '-' && string[i] != ' ')
		{
			printf("Invalid input.  Try again.\n"); 
			break;
		}
		if (string[i] == ',')
		{
			ctr++;
			if (string[i+1] == ' ')
			{
				if (string[i+2] == ',')
				{
					printf("Invalid input.  Missing coefficient/s.\n"); 
					break;
				}				
			}
			if (string[i+1] == ',')
			{
				printf("Invalid input.  Missing coefficient/s.\n"); 
				break;
			}
		}
		if (string[0] == ',') break;
	}
	if (i > strlen(string) - 1) i--;
	if ( string[strlen(string) - 1] == '0') 
	{
		printf("Last coefficient must not be equal to 0.  Try again.\n");
		i--;
	}
	if (string[i] == ',' || string[i] == '-') 
	{
		printf("Invalid input.  Try Again. \n"); 
		return 0;
	}
	if (isdigit(string[i]) <= 0)  return 0; 
	else if (ctr > degree )
	{
		printf("There must be %d coefficients only.  You inputted %d more coefficients. Try again.", degree+1, ctr - degree);
		return 0;
	}
	else if (ctr < degree)
	{
		printf("There must be %d coefficients.  You are missing %d correct coefficients. Try again.", degree+1, degree - ctr);
		return 0;
	}
	else return 1;
}

/*This function will check if the user wants to continue the program 
by answering yes or no.*/
int check_YorN( char YorN[])
{
	int i, check = 0;	
		
	strlwr(YorN);
	if (YorN[0] == 'y')
	{
		if (strlen(YorN) == 3) 
		{
			if (YorN[1] == 'e')
			{
				if (YorN[2] == 's') check = 1;
			}
		}
	}
	else if (YorN[0] == 'n')
	{
		if (strlen(YorN) == 2) 
		{
			if (YorN[1] == 'o') check = 2; 
		}
	}
	else check = 0; 
	
	return check;
}

/*This function will count the number of factors of a given number 
 (not considering the number of negative factors). */
int count_factors(int number)
{
	int ctr = 0, i;
	
	for (i=1 ; i <= number; i++)
	{
		if ((number%i) == 0) ctr++;
	}	
	
	return ctr;
}

/*This function will sort the elements of the given integer-type 
array in ascending order*/ 
void bubble_sort(int to_be_sorted1[], int to_be_sorted2[], int roots)
{
	int i, j, temp, gcf;
	
	for ( i = 1; i<= roots; i++)
	{
		for ( j = 0; j <= roots-i-1; j++)
		{
			if ((double)((double)to_be_sorted1[j]/(double)to_be_sorted2[j] ) > (double) ((double)to_be_sorted1[j+1]/(double)to_be_sorted2[j+1]))
			{
				temp = to_be_sorted1[j];
				to_be_sorted1[j] = to_be_sorted1[j+1];
				to_be_sorted1[j+1] = temp; 
				temp = to_be_sorted2[j];
				to_be_sorted2[j] = to_be_sorted2[j+1];
				to_be_sorted2[j+1] = temp; 
			}
		}
	}
	printf("The rational roots of the input polynomial are:\n");
	for ( i = 0; i <= roots - 1; i++)
	{
		gcf = lowest_term(to_be_sorted1[i],to_be_sorted2[i]);
		if (to_be_sorted2[i] != 1) printf("%d/%d", (to_be_sorted1[i]/gcf), (to_be_sorted2[i]/gcf));
		else printf("%d", to_be_sorted1[i]);
		if (i != roots-1) printf(",");
		else printf("\n");
	}
}

/*This function will get the greatest common factor of the 
the two integers (which are both in two different arrays)*/
int lowest_term(int a, int b)
{
	int temp;
	
	b = abs(b);
	a = abs(a);
	do
	{
		temp = b;
		b = a%b;
		a = temp;
	}while ( b > 0);
	
	return a;
}

/* This function will get the factors of the constant coefficient and the nth-degree 
coefficient and will check if p/q (where p is a factor of the constant coefficient
and q is a factor of the nth-degree coefficient) is a zero of of the polynomial. 
Then, it will print the zeroes in a ascending order.*/
void print_Rroots(int coeffs[], int pos_p, int pos_q, int h_degree, int zeroes, int temp_degree)
{
	double pos_root, sum, product, restrictions[20] = {0.0} ; 
	double temp_coeffs[temp_degree+1], result_coeffs[temp_degree + 1]; 
	int pos_num[h_degree], pos_den[h_degree], p_factors [pos_p], q_factors[pos_q];
	int i_pn = 0, i_pd = 0, i_rev = 0, i_sum = 0, ip = 0, iq = 0, iR = 0, i_doubles = 0;
	int i_roots = 0, i_syndiv = 0, i_dcurrent = 0, i_switch = 0, i_put = 0; 
	int rev_array[temp_degree + 1], n = temp_degree; 
	int new_num[h_degree], new_den[h_degree];
	int i =0;
	int number;
	
	/*Putting the factors of p in the array p_factors*/
	for (number = 1; number <= abs(coeffs[0]) ; number++)
	{
		if ((abs(coeffs[0]))%number == 0)
		{
			p_factors[ip] = number;
			ip++;
			p_factors[ip] =  number *(-1);
			ip++;
		}
	}
	/*Putting the factors q in the the array q_factors*/
	for (number = 1; number <= abs(coeffs[temp_degree]) ; number++)
	{
		if ((abs(coeffs[temp_degree])%number) == 0)
		{
			q_factors[iq] = number;
			iq++;
		}
	}
	/* Reversing the coefficients from a sub 0 to a sub n to a sub n to sub 0 */
	for (i_rev= 0; i_rev <= temp_degree; i_rev++)
	{
		rev_array[i_rev]  = coeffs[n];
		n--;
	}
	//checks if the possible root is plugged-in the value of x and the answers become 0.  If yes, it is a root if no it is not.
	for (ip= 0; ip <= pos_p -1 ; ip++)
	{
		for( iq = 0; iq <= pos_q - 1; iq++)
		{
			//printf("[ %d/%d = %.2lf] ----- {%.12lf} --- {%d}|| (%.4lf)\n", p_factors[ip], q_factors[iq], pos_root, sum, rev_array[i_sum], pow(pos_root, (h_degree - i_sum)));			
			sum = 0.0;
			pos_root = (double) ((double)p_factors[ip]/(double)q_factors[iq]);
			/*Plugging the possible roots in double type into the values of x*/
			for (i_sum = 0; i_sum <= temp_degree ; i_sum++)
			{
				sum += (double) ((rev_array[i_sum])*((double)pow(pos_root, (h_degree - i_sum))));
			}
			if (sum < 0.0000001 && sum > -0.0000001)
			{
				/*Puts the double type of the root/s of the given coefficients in the double array
				wherein which is also used for checking if the double form of the roots is already 
				present in the array which means that the integers are either repeated value or 
				a value equal to it. */ 
				for (i_doubles = 0; i_doubles < i_dcurrent; i_doubles++)
				{
					if (pos_root != restrictions[i_doubles])  continue;
					else
					{
						break;
					}
				}
				if (i_doubles == i_dcurrent)
				{
					restrictions[i_doubles] = pos_root;
					i_dcurrent++;
					pos_num[i_pn] = p_factors[ip];
					i_pn++;
					pos_den[i_pd] = q_factors[iq];
					i_pd++;
					iR++;
				}
				else continue; 
			}
		}
	}
	if (zeroes != 0)
	{
		pos_num[i_pn] = 0;
		pos_den[i_pd] = 1;
		iR++;
	}
	/*conditionals where in 
	if none of the possible rational roots 
	plugged-in made the sum 0, then it has no real rational roots.
	if it has, it will divide the polynomial synthetically*/
	 if (iR == 0) 
	{
		printf("There are no real rational solutions.\n");
	}
	else if (iR < h_degree)
	{
		i_pn = 0;
		i_pd = 0;
		iR = zeroes;
		/* Putting the original inputted coeffcients in a double type array named result_coeffs*/
		for (i_put = 0; i_put <= temp_degree; i_put++)
		{
			result_coeffs[i_put] = (double) rev_array[i_put];
		}
		do
		{
			pos_root = (double) pos_num[i_roots]/ (double) pos_den[i_roots];
			if (pos_root == 0.0) break; 
			do
			{
				for (i_put = 0; i_put <= temp_degree; i_put++)
				{
					temp_coeffs[i_put] = result_coeffs[i_put];
				}
				product = 0.0;
				sum = 0.0;
				for ( i_syndiv = 0; i_syndiv <= temp_degree; i_syndiv++)
				{
					sum = temp_coeffs[i_syndiv] + product;
					result_coeffs[i_syndiv] = sum; 
					product = sum * pos_root;
				}
				if ( product < 0.0000001 && product > -0.0000001)
				{
					new_num[i_pn] = pos_num[i_roots];
					i_pn++;
					new_den[i_pd] = pos_den[i_roots];
					i_pd++;
					iR++;
				}
				else
				{
					for (i_put = 0; i_put <= temp_degree; i_put++)
					{
						result_coeffs[i_put] = temp_coeffs[i_put];
					}
				}
			}while (product < 0.0000001 && product > -0.0000001);
			i_roots++;
		}while(i_roots <= i_dcurrent-1 );
		if (zeroes != 0)
		{
			do 
			{
				new_num[i_pn] = 0;
				i_pn++;
				new_den[i_pd] = 1;
				i_pd++;
				zeroes--;
			} while (zeroes != 0);
		}
		bubble_sort (new_num, new_den, iR);
	}
	else 
	{
		bubble_sort(pos_num, pos_den, iR); 
	}
}