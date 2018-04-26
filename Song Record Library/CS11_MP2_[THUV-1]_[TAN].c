//  Tan, Luis Carlos -------- 2015-04318 ------- THUV-1 
/*This is a Music Record Library. This program basically will help you in recording 
songs and some of the information about the song, namely, the title, the artist, composer, album,
genre,your own rating and also your remarks about the song). It is easy to use and also
very convenient in many ways.  It has mainly 4 functions.  First is that the program can 
add songs information. Just enter the title and  fill up the other fields. (which is optional -
skipping through that field means that it is either empty or in default). Second, the program 
can also update information of the songs that were updated in the library. Just input the title 
(be careful for it is case-sensitive) and fill up the other fields.  Third, the program can also 
list songs according to the input query you chose, and of course, in alphabetical order. Lastly, 
there's the exit function which is needed for freeing memory-allocations and updating the library
to the record file. Don't worry for the program validates input and is flexible to changes.*/ 

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#define NEWLINE printf("\n");


typedef struct song_info{ // This is the structure for the song which will hold the necessary information and data. 
	int song_ID; // Randomized number which is given by the program itself
	char title[25]; // This attribute holds the title of the song (Cannot be empty)
	char artist[25]; // This attribute holds the artist of the song (Can be empty and "No Artist" is allowed)
	char composer[25]; // This attribute holds the composer of the song (Can be empty and "No Composer" is allowed)
	char album[25]; // This attribute holds the album of the song (It's default value if "Single")
	char genre[25]; // This attribute holds the genre of the song (Only restricted to Art Music, Popular Art and Traditional Music)
	int rating; // This attribute holds the rating of the song (Values can only range from 0 to 5 as 0 being the lowest and 5 as the highest)
	char remarks[50]; // This attribute holds the title of the song
	struct song_info *next; //Points to next node (traverses towards NULL)
	struct song_info *previous; //Points to previous node (traverses away NULL)
} Song;

//This function removes unnecessary spaces made by the user such as leading and trailing spaces
char *remove_spaces(char string[]){
	int n = strlen(string);
	int i = 0;
	int j = 0; 
	char *correct_string;
	
	correct_string = malloc(25);
	for ( i = 0 ; i < n ; i++ ){
		if (!(isspace(string[i]))){
			correct_string[j] = string[i];
			j++;
		}
		else if (j != 0){
			if (i == n-1) break;
			if (isspace(correct_string[j-1])){
				j--;
				break;
			}
			else {
				correct_string[j] = string[i];
				j++;
			}
		}
		else continue;
	}
	correct_string[j] = '\0';
	//correct_string = realloc (correct_string,strlen(correct_string));
	return correct_string;
}

//This function just double checks if there are any spaces or invalid characters which are not present in most of the information for songs.
int check_string(char title[]){

	if(isalnum(title[0])){
		if (!(isspace(title[0]))){
			return 1;
		}
	} 
	
	return 0;
}
//Thie function checks and counts how many nodes that have "title" equal to the node's title attribute
int check_title(Song *head,char title[]){
	int i = 0;
	Song *current = head;
	
	while (current){
		if (strcmp(current->title,title) == 0) i++;
		current = current->next;
	}
	return i;
}
/*This is the function that will update program and the library if any changes in the
 current data is wanted.  Returns 1 if the title of the song found and 0 if not.*/
int update_song(char title[], Song *head){
	Song *listcurrent;
	Song *current = head; //Variable for the current node in process also starts with the head
	char songID[10];
	char string[25];  //variable for the inputted title
	char genre[10]; // Needed for the input validation for genre
	char rating[10]; //Needed for the input validation for rating
	int EXIT = 0; //Needed for the input validation for genre as well.  If EXIT == 1 then the program continues if 0, then it loops
	int length = strlen(string); // Length of the inpuuted string
	int n = 0;
	int i;

	strcpy(string,title); //Transferring title tp string
	if(check_title(head,title) == 1){ // This means that there is only one node found with "title" as its attribute
		//Traverses through the list
		while (current){
			//Checks if the length of the current->title is equal to the length of the inputted string 
			if (strcmp(current->title,string) == 0){ 
				/*If successful it will do the process it will return 1, else it will return 0.  The process here is almost identical to each other. Will ask for input,
				make the last element equal to null then validates it, continues if correct and loops if it is wrong. The data is stored to to the corresponding field*/
				printf("Found it!\n");
				printf("Please update Song #%d - %s\n", current->song_ID, current->title);
				
				//ARTIST
				printf("Artist : (%s) ", current->artist);
				do{
					fgets(current->artist,25,stdin);
					current->artist[strlen(current->artist)-1] = '\0';
					strcpy(current->artist,remove_spaces(current->artist));
					if (check_string(current->artist)) break;
					else if (strlen(current->artist) == 0) {
						strcpy(current->artist,"No artist");
						break;
					}
					else printf("Please input a valid artist.\nArtist : ");
				}while (1);
				
				//COMPOSER
				printf("Composer : (%s) ", current->composer);
				do{
					fgets(current->composer,25,stdin);
					current->composer[strlen(current->composer)-1]= '\0';
					strcpy(current->composer,remove_spaces(current->composer));
					if (check_string(current->composer)) break;
					else if (strlen(current->composer) == 0) {
						strcpy(current->composer,"No composer");
						break;
					}
					else printf("Please input a valid composer.\nComposer : ");
				}while (1);
				
				//ALBUM
				printf("Album : (%s) ", current->album);
				do{
					fgets(current->album,25,stdin);
					current->album[strlen(current->album)-1]= '\0';
					strcpy(current->album,remove_spaces(current->album));
					if (check_string(current->album)) break;
					else if (strlen(current->album) == 0) {
						strcpy(current->album,"Single");
						break;
					}
					else printf("Please input a valid album.\nAlbum : ");
				}while (1);
				
				//GENRE
				printf("Genre : (%s) ", current->genre);
				do{
					printf("\n   1 = Art Music\n   2 = Popular Music\n   3 = Traditional Music\nChoice : ");

					fgets(genre,10,stdin);
					genre[strlen(genre)-1] = '\0';
					
					if (strlen(genre) == 1){
						switch (genre[0]){
							case '1': 
								strcpy(current->genre,"Art Music");
								EXIT = 1;
								break;
							case '2': 
								strcpy(current->genre,"Popular Music");
								EXIT = 1;
								break;
							case '3': 
								strcpy(current->genre,"Traditional Music");
								EXIT = 1;
								break;
							default:
								printf("Wrong input. Please input just one of the given choices.\n");
								break;
						}
					}
					else printf("Please input one of the given choices.\n");
					
				}while (EXIT == 0);
				
				//RATING
				printf("Rating : (%d) ", current->rating);
				do{
					fgets(rating,10,stdin);
					rating[strlen(rating)-1]= '\0';
					if (strlen(rating) == 1){
						if (isdigit(rating[0])){
							current->rating = atoi(rating);
							if (current->rating >= 0 && current->rating <= 5) break;
						}
					}
					else if (strlen(rating) == 0){
						current->rating = 0;
						break;
					}
					printf("Please input a valid rating.\nRating : ");
				}while (1);
				
				//REMARKS
				printf("Remarks : ");
				fgets(current->remarks,50,stdin);
				current->remarks[strlen(current->remarks)-1]= '\0';
				if (strlen(current->remarks) == 0) strcpy(current->remarks,"No remarks.");			
				
				return 1;
			} 
			current = current->next;
		}
	}
	else if (check_title(head,title) > 1){ // This means that there are more than one node found with "title" as their attribute
		//Same prevoess with the earlier but now asks the Song ID field which will be the one to be based on to what song the user wants to update
		printf("Multiple entries. Please input the Song ID of the song that you want to update.\n");
		do{
			printf("Song ID - ");
			fgets(songID,10,stdin);
			songID[strlen(songID)-1] = '\0';
			strcpy(songID,remove_spaces(songID));
			for ( i = 0 ; i < strlen(songID) ; i++)
				if (isdigit(songID[i])) continue;
			if (i == strlen(songID)) break;
			else printf("Invalid input.\n");
		} while (1);

		while (current){
			if (current->song_ID == atoi(songID)){
				printf("Found it!\n");
				printf("Please update Song #%d - %s\n", current->song_ID, current->title);
					
				//ARTIST
				printf("Artist : (%s) ", current->artist);
				do{
					fgets(current->artist,25,stdin);
					current->artist[strlen(current->artist)-1]= '\0';
					strcpy(current->artist,remove_spaces(current->artist));
					if (check_string(current->artist)) break;
					else if (strlen(current->artist) == 0) {
						strcpy(current->artist,"No artist");
						break;
					}
					else printf("Please input a valid artist.\nArtist : ");
				}while (1);
				
				//COMPOSER
				printf("Composer : (%s) ", current->composer);
				do{
					fgets(current->composer,25,stdin);
					current->composer[strlen(current->composer)-1]= '\0';
					strcpy(current->composer,remove_spaces(current->composer));
					if (check_string(current->composer)) break;
					else if (strlen(current->composer) == 0) {
						strcpy(current->composer,"No composer");
						break;
					}
					else printf("Please input a valid composer.\nComposer : ");
				}while (1);
				
				//ALBUM
				printf("Album : (%s) ", current->album);
				do{
					fgets(current->album,25,stdin);
					current->album[strlen(current->album)-1]= '\0';
					strcpy(current->album,remove_spaces(current->album));
					if (check_string(current->album)) break;
					else if (strlen(current->album) == 0) {
						strcpy(current->album,"Single");
						break;
					}
					else printf("Please input a valid album.\nAlbum : ");
				}while (1);
				
				//GENRE
				printf("Genre : (%s) ", current->genre);
				do{
					printf("\n1 = Art Music\n2 = Popular Music\n3 = Traditional Music\nChoice : ");

					fgets(genre,10,stdin);
					genre[strlen(genre)-1] = '\0';
					
					if (strlen(genre) == 1){
						switch (genre[0]){
							case '1': 
								strcpy(current->genre,"Art Music");
								EXIT = 1;
								break;
							case '2': 
								strcpy(current->genre,"Popular Music");
								EXIT = 1;
								break;
							case '3': 
								strcpy(current->genre,"Traditional Music");
								EXIT = 1;
								break;
							default:
								printf("Wrong input. Please input just one of the given choices.\n");
								break;
						}
					}
					else printf("Please input one of the given choices.\n");
					
				}while (EXIT == 0);
				
				//RATING
				printf("Rating : (%d) ", current->rating);
				do{
					fgets(rating,10,stdin);
					rating[strlen(rating)-1]= '\0';
					if (strlen(rating) == 1){
						if (isdigit(rating[0])){
							current->rating = atoi(rating);
							if (current->rating >= 0 && current->rating <= 5) break;
						}
					}
					else if (strlen(rating) == 0){
						current->rating = 0;
						break;
					}
					printf("Please input a valid rating.\nRating : ");
				}while (1);
				
				//REMARKS
				printf("Remarks : ");
				fgets(current->remarks,50,stdin);
				current->remarks[strlen(current->remarks)-1]= '\0';
				if (strlen(current->remarks) == 0) strcpy(current->remarks,"No remarks.");			
				
				return 1;
			}
			current = current->next;
		}
	}
	else return 0; // This means none were found with "title" as its attribute.
}
//This function will validate the action done in the main program(Choosing the library function to use).
int check_action(char string[]){
	int choice;
	
	string[strlen(string)-1] = '\0';
	if(strlen(string) == 1){
		if (isdigit(string[0])){
			choice = atoi(string);
			if (choice >= 0 && choice <= 3) return 1; 
		}
	}

	return 0;
}
/* This function  is basically the add song function which is literally used for adding songs in the library.
The head of the null is needed and also for the numbers of nodes in the list. Returns the pointer to the added 
song node. The process are almost identical to each other. Will ask for input,make the last eleent equal to null 
then validates it, continues if correct and loops if it is wrong*/
Song *add_song(Song* tail, int n){
	int EXIT = 0; // Needed for validation
	char rating[10]; // Needed for inputting and its validation for rating
	char genre[10]; // Needed for inputting and its validation for genre
	Song *current = NULL; // Shows the current node in process
	Song *head = tail; // 
	
	//Allocating Song size memory
	current = (Song*) malloc(sizeof(Song));
	printf("Fill up the necessary information.\n");
	
	//S0NG ID
	current->song_ID = n;
	
	//TITLE
	do{
		printf("Title : ");
		fgets(current->title,25,stdin);
		current->title[strlen(current->title)-1]= '\0';
		//printf("<%d>",strlen(current->title));
		strcpy(current->title,remove_spaces(current->title));
		if (strlen(current->title) == 0) printf("Please input the title of the song.\n");
		else if (check_string(current->title)) break;
		else printf("Please input a valid title.\n");
	}while (1);
	
	//ARTIST 
	do{
		printf("Artist : ");
		fgets(current->artist,25,stdin);
		current->artist[strlen(current->artist)-1]= '\0';
		strcpy(current->artist,remove_spaces(current->artist));
		if (check_string(current->artist)) break;
		else if (strlen(current->artist) == 0) {
			strcpy(current->artist,"No artist");
			break;
		}
		else printf("Please input a valid artist.\n");
	}while (1);
	
	//COMPOSER
	do{
		printf("Composer : ");
		fgets(current->composer,25,stdin);
		current->composer[strlen(current->composer)-1]= '\0';
		strcpy(current->composer,remove_spaces(current->composer));
		if (check_string(current->composer)) break;
		else if (strlen(current->composer) == 0){
			strcpy(current->composer,"No composer");
			break;
		}
		else printf("Please input a valid name of composer.\n");
	}while (1);
	
	//ALBUM
	do{
		printf("Album : ");
		fgets(current->album,25,stdin);
		current->album[strlen(current->album)-1]= '\0';
		strcpy(current->album,remove_spaces(current->album));
		if (check_string(current->album)) break;
		else if (strlen(current->album) == 0) {
			strcpy(current->album,"Single");
			break;
		}
		else printf("Please input a valid album name.\n");
	}while (1);

	//GENRE
	do{
		printf("Genre -\nChoose:\n   1 = Art Music\n   2 = Popular Music\n   3 = Traditional Music\nChoice : ");

		fgets(genre,10,stdin);
		genre[strlen(genre)-1] = '\0';
		
		if (strlen(genre) == 1){
			switch (genre[0]){
				case '1': 
					strcpy(current->genre,"Art Music");
					EXIT = 1;
					break;
				case '2': 
					strcpy(current->genre,"Popular Music");
					EXIT = 1;
					break;
				case '3': 
					strcpy(current->genre,"Traditional Music");
					EXIT = 1;
					break;
				default:
					printf("Wrong input. Please input just one of the given choices.\n");
					break;
			}
		}
		else printf("Please input one of the given choices.\n");
		
	}while (EXIT == 0);
	
	//RATING
	do{
		printf("Rating : ");
		fgets(rating,10,stdin);
		rating[strlen(rating)-1]= '\0';
		if (strlen(rating) == 1){
			if (isdigit(rating[0])){
				current->rating = atoi(rating);
				if (current->rating >= 0 && current->rating <= 5) break;
			}
		}
		else if (strlen(rating) == 0){
			current->rating = 0;
			break;
		}
		printf("Please input a valid rating.\n");
	}while (1);
	
	//REMARKS
	printf("Your remarks : ");
	fgets(current->remarks,50,stdin);
	current->remarks[strlen(current->remarks)-1]= '\0';
	if (strlen(current->remarks) == 0) strcpy(current->remarks,"No remarks.");
	
	current->next = head;
	if (head != NULL) head->previous = current; //To connect the current node to the node before the node(away from the NULL pointer)
	
	return current;
}
/*This function is like the add song function but only contains nodes selected nodes.  The fields are filled by 
copying the field from the current's field. Returns the pointer of the node added*/
Song *unsorted_list(Song* tail, Song* current){
	Song *listcurrent = (Song*) malloc (sizeof(Song));
	//TITLE
	strcpy(listcurrent->title,current->title);
	//ARTIST
	strcpy(listcurrent->artist,current->artist);
	//COMPOSER
	strcpy(listcurrent->composer,current->composer);
	//ALBUM
	strcpy(listcurrent->album,current->album);
	//GENRE
	strcpy(listcurrent->genre,current->genre);
	//RATING
	listcurrent->rating = current->rating;
	//REMARKS
	strcpy(listcurrent->remarks,current->remarks);

	listcurrent->next = tail;
	if (tail != NULL) tail->previous = listcurrent;
	return listcurrent;
}
/* This function is the exit library function. It's purpose is to update the library and freeing the memory allocation
happened during the program.*/
void exit_library(FILE *fp, Song* top){
	Song *obselete = NULL;
	Song *current = top;
	
	fp = fopen("record file.txt","w");
	if (fp == NULL) printf("ERROR. Failed opening the record file.\n");
	else {
		while (current != NULL){
			fprintf(fp,"%d%c", current->song_ID, '\n');
			fprintf(fp,"%s%c", current->title, '\n');
			fprintf(fp,"%s%c", current->artist, '\n');
			fprintf(fp,"%s%c", current->composer, '\n');
			fprintf(fp,"%s%c", current->album, '\n');
			fprintf(fp,"%s%c", current->genre, '\n');
			fprintf(fp,"%d%c", current->rating, '\n');
			fprintf(fp,"%s%c", current->remarks, '\n');
			
			obselete = current;
			current = current->next; 
			free(obselete);
		}
		fclose(fp);
	}
	
	return;
}
//This function checks the two strings if they are equal to each other regardless of the case. Return 1 if yes and 0 if no.
int check_substring (char string1[], char string2[]){
	int i;
	int j = 0;
	int k = 0;
	int m = strlen(string1);
	int n = strlen(string2);


	for ( i = 0 ; i <= m - n ; i++){
		if (tolower(string1[i]) == tolower(string2[j])){
			for (k = i ; j < n ; j++, k++){
				if (tolower(string1[k]) != tolower(string2[j])) break;
			}
			if ( j == n) return 1;
		}
	}

	return 0;
}
//This function checks the two strings if they are equal considering the case.  Returns 1 if no and 0 if yes
int check_substring2(char string[], char string2[]){
	char string1[25];
	char *temp;
	int answer;

	strcpy(string1,string);

	temp = strtok(string1," ");

	answer = strcmp(temp,string2);
	if (answer == 0) return 0;
	else {
		while (temp){
			if (strcmp(temp,string2) == 0) return 0;
			else temp = strtok(NULL," ");
		}
	}
	return 1;
}
/*This function checks two strings if they are equal while also without considering  the case of the characters of the string..
Returnd 1 if they are equal and 0 if no*/
int check_equal(char string1[], char string2[]){
	int i;
	int m = strlen(string1);
	int n = strlen(string2);
	char temp1[25];
	char temp2[25];

	for (i = 0 ; i < m ; i++)
		temp1[i] = tolower(string1[i]);
	
	for (i = 0 ; i < n ; i++)
		temp2[i] = tolower(string2[i]);
	
	return strcmp(temp1,temp2);
}
/*This function sorts the list according to the title field of the nodes in alphabetical order.If ever the two nodes have the 
same song title, they will be sorted acoording to the artist field*/
Song* sort_list(Song *tail, int n){
	int i;
	int j;
	Song *listcurrent0 = tail;
	Song *listcurrent;
	Song *listhead = tail;

	//The used algorithm is the same for all conditions.
	for ( i = 1 ; i < n ; i++){
		listcurrent0 = NULL;
		listcurrent = listhead; 
		for ( j = 1 ; j < n - i + 1; j++){
			if (check_equal(listcurrent->title,listcurrent->next->title) == 0){
				//If check_equal returns 0, it means that the two strings are the same and now will proceed for checking the artist
				if (check_equal(listcurrent->artist,listcurrent->next->artist) > 0){
					//If check _equal returns greater than 0, it means that we need to swap the current positions of the two nodes
					if  (listcurrent0 != NULL){
						listcurrent0->next = listcurrent->next;
						listcurrent->next = listcurrent->next->next;
						listcurrent0->next->next = listcurrent;
						listcurrent = listcurrent0->next;
					}
					else{
						listcurrent = listcurrent->next;
						listhead->next = listcurrent->next;
						listcurrent->next = listhead;
					}
				}
			}
			else if (check_equal(listcurrent->title,listcurrent->next->title) > 0){
				//If check _equal returns greater than 0, it means that we need to swap the current positions of the two nodes
				if  (listcurrent0 != NULL){
					listcurrent0->next = listcurrent->next;
					listcurrent->next = listcurrent->next->next;
					listcurrent0->next->next = listcurrent;
					listcurrent = listcurrent0->next;
				}
				else{
					listcurrent = listcurrent->next;
					listhead->next = listcurrent->next;
					listcurrent->next = listhead;
				}
			} 
			// If check_equal returns less than 0, it means that the two string are arranged in alphabetical order
			if ( listcurrent0 == NULL) listcurrent0  = listcurrent;
			else listcurrent0 = listcurrent0->next;
			if ( j == 1 ) listhead = listcurrent0; //Needed for maintaing the top as the head. (farthest to the NULL pointer)
			listcurrent = listcurrent->next; //traverses the list
		}
	}
	return listhead;	
}
/*This function is exclusively for the sorting of nodes based on rating in the list song function. It will sort the list 
in decreasing order, 1 as the lowest and 5 as the highest*/
Song* sort_list2(Song *tail, int n){
	int i;
	int j;
	Song *listcurrent0 = tail;
	Song *listcurrent;
	Song *listhead = tail;


	for ( i = 1 ; i < n ; i++){
		listcurrent0 = NULL;
		listcurrent = listhead; 
		for ( j = 1 ; j < n - i + 1; j++){
			if (listcurrent->rating == listcurrent->next->rating){
				//If check_equal returns 0, it means that the two strings are the same and now will proceed for checking the artist
				if (check_equal(listcurrent->title,listcurrent->next->title) == 0){
					//If check_equal returns 0, it means that the two strings are the same and now will proceed for checking the artist
					if (check_equal(listcurrent->artist,listcurrent->next->artist) > 0){
						if  (listcurrent0 != NULL){
							listcurrent0->next = listcurrent->next;
							listcurrent->next = listcurrent->next->next;
							listcurrent0->next->next = listcurrent;
							listcurrent = listcurrent0->next;
						}
						else{
							listcurrent = listcurrent->next;
							listhead->next = listcurrent->next;
							listcurrent->next = listhead;
						}
					}
				}
				else if (check_equal(listcurrent->title,listcurrent->next->title) > 0){
					//If check _equal returns greater than 0, it means that we need to swap the current positions of the two cells
					if  (listcurrent0 != NULL){
						listcurrent0->next = listcurrent->next;
						listcurrent->next = listcurrent->next->next;
						listcurrent0->next->next = listcurrent;
						listcurrent = listcurrent0->next;
					}
					else{
						listcurrent = listcurrent->next;
						listhead->next = listcurrent->next;
						listcurrent->next = listhead;
					}
				} 
			}
			else if (listcurrent->rating < listcurrent->next->rating){
				//If check _equal returns greater than 0, it means that we need to swap the current positions of the two cells
				if  (listcurrent0 != NULL){
					listcurrent0->next = listcurrent->next;
					listcurrent->next = listcurrent->next->next;
					listcurrent0->next->next = listcurrent;
					listcurrent = listcurrent0->next;
				}
				else{
					listcurrent = listcurrent->next;
					listhead->next = listcurrent->next;
					listcurrent->next = listhead;
				}
			} 
			// If check_equal returns less than 0, it means that the two string are arranged in alphabetical order
			if ( listcurrent0 == NULL) listcurrent0  = listcurrent;
			else listcurrent0 = listcurrent0->next;
			if ( j == 1 ) listhead = listcurrent0;
			listcurrent = listcurrent->next; //traverses the list
		}
	}
	return listhead;	
}
/* This function is the list song function. It will have the head node of the list as its argument.  It will basically ask for 
a choice to what songs will be listed according to the field.  It will output the nodes that satisfied the condition in sorted manner.
It returns head node but returns listhead instead if "All" was chosen for it is the head of the sorted list*/
Song* list_song(Song* top){
	char input[10];
	char substring[20];
	char genred[10];
	char ratingd[10];
	int score;
	int correct = 0;
	int stringlength;
	int i;
	int j;
	int n = 0; //Needed for the counting of the cells
	Song *current = top;
	Song *listcurrent = NULL; //Variable pointer for the current cell in process (to be sorted list) 
	Song *listcurrent1 = NULL; //Same as for the listcurrent but is needed for sorting the list (to be sorted list)
	Song *listhead = NULL; //Variable pointer needed for the connection of the cells in the list (to be sorted list)
	Song *liststart = NULL; //Varibale pointer for the starting cell of the 'to-be-sorted' list near the CELL

	printf("What do you want to list?");
	printf("\n  1 - Title\n  2 - Artist\n  3 - Composer\n  4 - Genre\n  5 - Album\n  6 - Rating\n  7 - All\n");

	// Program asks for an input and also validates it.
	do{
		printf("Choice : ");
		fgets(input,10,stdin);
		stringlength = strlen(input);
		input[stringlength-1] = '\0';
		if (stringlength == 2)
			if (atoi(input) > 0 && atoi(input) < 8) correct = 1;
			else printf("Wrong Input.\n");
		else printf("Wrong Input.\n");
	} while (correct == 0);


	if (input[0] == '4'){
		printf("Choose one of the three.\n   1 - Art Music\n   2 - Popular Music\n   3 - Traditional Music\n");
		do{
			printf("Choice : ");
			fgets(genred,10,stdin);
			genred[strlen(genred)-1] = '\0';
			if (strlen(genred) == 1){
				if(genred[0] == '1'){strcpy(substring,"Art Music"); break;}
				else if(genred[0] == '2'){strcpy(substring,"Popular Music"); break;}
				else if(genred[0] == '3'){strcpy(substring,"Traditional Music"); break;}
			}
			printf("Wrong Input.\n");
		}while (1);
	} 

	else if (input[0] == '6'){
		printf("Input the score that you want as the minimum.\n");
		do{
			printf("Score : "); 
			fgets(ratingd,10,stdin);
			ratingd[strlen(ratingd)-1] = '\0';
			if (strlen(ratingd) == 1){
				if (isdigit(ratingd[0])){
					score = atoi(ratingd);
					if (score >= 0 && score <= 5) break;
				}
			}
			printf("Please input only from the range 1 to 5. (5 as the highest and 1 as the lowest)\n");
		} while (1);
	}
	else if (input[0] == '7') printf("\n");
	else{
		printf("Please enter what you are searching : ");
		fgets(substring,20,stdin);
		stringlength = strlen(substring);
		substring[stringlength-1] = '\0';
		strcpy(substring,remove_spaces(substring));
	}

	if (current == NULL) printf("Sorry. The Library has nothing in it.\n");
	else {
		//The processes are almost the same in the manner of getting the nodes,sorting and printing the fields (without the Song ID). 
		printf("\nRESULTS.\n\n");
		switch (input[0]){
			case '1':
				//TITLE
				while (current != NULL){
					if (check_substring(current->title,substring) == 1){
						//Gets the data in the fields of the current node in check in another list which is to be sorted and printed
						n++; // counts the number of the nodes in the list
						listhead = unsorted_list(listhead, current);
					}
					current = current->next;
				}
				//Checking if there were any titles found with 'substring' as substring
				listcurrent = listhead;
				if (listcurrent == NULL) printf("Sorry. No results.\n");
				else {
					listhead = sort_list(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '2':
				//ARTIST
				while (current != NULL){
					//printf("(%d) %s - (%d) %s\n",strlen(substring),substring, strlen(current->title),current->title);
					if (check_substring2(current->artist,substring) == 0){
						//Gets the data in the fields of the current node in check in another list which is to be sorted and printed
						n++;
						listhead = unsorted_list(listhead, current);
					}
					current = current->next;
				}
				//Checking if there were any titles found with 'substring' as substring
				listcurrent = listhead;
				if (listcurrent == NULL) printf("Sorry. No results.\n");
				else {
					listhead = sort_list(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '3':
				//COMPOSER
				while (current != NULL){
					if (check_substring2(current->composer,substring) == 0){
						//Gets the data in the fields of the current node in check in another list which is to be sorted and printed
						n++;
						listhead = unsorted_list(listhead, current);
					}
					current = current->next;
				}
				//Checking if there were any titles found with 'substring' as substring
				listcurrent = listhead;
				if (listcurrent == NULL) printf("Sorry. No results.\n");
				else {
					listhead = sort_list(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '4':
				//GENRE
				while (current != NULL){
					if (strcmp(current->genre,substring) == 0){
						//Gets the data in the fields of the current node in check in another list which is to be sorted and printed
						n++;
						listhead = unsorted_list(listhead, current);
					}
					current = current->next;
				}
				//Checking if there were any titles found with 'substring' as substring
				listcurrent = listhead;
				if (listcurrent == NULL) printf("Sorry. No results.\n");
				else {
					listhead = sort_list(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '5':
				//ALBUM
				while (current != NULL){
					if (check_substring2(current->album,substring) == 0){
						//Gets the data in the fields of the current node in check in another list which is to be sorted and printed
						n++;
						listhead = unsorted_list(listhead, current);
					}
					current = current->next;
				}
				//Checking if there were any titles found with 'substring' as substring
				listcurrent = listhead;
				if (listcurrent == NULL) printf("Sorry. No results.\n");
				else {
					listhead = sort_list(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '6':
				//RATING
				while(current != NULL){
					if (current->rating >= score){
						n++;
						listhead = unsorted_list(listhead,current);
					}
					current = current->next;
				}
				listcurrent = listhead;
				if (listcurrent == NULL) printf(" Sorry. No results.\n");
				else{
					listhead = sort_list2(listhead,n);
					listcurrent = listhead;
					for ( i = 1 ; i <= n ; i++){
						printf("%d.) Title : %s\n",i,listcurrent->title);
						printf("    Artist : %s\n",listcurrent->artist);
						printf("    Composer : %s\n",listcurrent->composer);
						printf("    Album : %s\n",listcurrent->album);
						printf("    Genre : %s\n",listcurrent->genre);
						printf("    Rating : %d\n",listcurrent->rating);
						printf("    Remarks : %s\n\n",listcurrent->remarks);
						listcurrent = listcurrent->next;
					}
				}
				break;
			case '7':
				//Will print all of the nodes that there are currently and all of its field (include the Song ID) in a sorted manner(alphabetically),
				while (current){
					n++;
					current = current->next;
				}
				current = top;
				listhead = sort_list(current,n);
				current = listhead;
				for ( i = 1 ; i <= n ; i++){
					printf("%d.) Song ID : %d\n",i,current->song_ID);
					printf("    Title : %s\n",current->title);
					printf("    Artist : %s\n",current->artist);
					printf("    Composer : %s\n",current->composer);
					printf("    Album : %s\n",current->album);
					printf("    Genre : %s\n",current->genre);
					printf("    Rating : %d\n",current->rating);
					printf("    Remarks : %s\n\n",current->remarks);
					current = current->next;
				}
				return listhead;
				break;
		}
	}
	return top;
}

main(){
	srand(time(NULL)); //Needed for the randomization of the value for the Song ID
	FILE *record_ptr; //Pointer to the record file
	
	int i = 0; // Iteration purposes
	int EXIT = 0; // For the main loop 
	
	char action[10]; //variable for the library functions
	
	char fstart[15]; //variable in putting the first data in the nodes (as the start) which is also the song ID. 
	char frating[3]; //variable for getting the rating in the record file
	char update[50]; //variable for the inputted string in the update function
	
	Song *current = NULL; //Pointer variable for the current cell in process
	Song *head = NULL; //Pointer variable for the cell farthest the NULL
	Song *start = NULL; //Pointer variable for the cell nearest the NULL Pointer
	
	printf("==================== Accessing the Library ====================\n"); 
	printf("Good day, Yvonne!\n\n");
	
	record_ptr = fopen("record file.txt", "r"); //Opens the file
	if (record_ptr == NULL) printf("ERROR.  Failed to read the file.\n");
	else{
		//Checks if the file has something in it. Also, updates the program with tha data from the record file.
		while (fgets(fstart,15,record_ptr) != NULL){
			fstart[strlen(fstart)-1] = '\0';
			//Allocating memory (of 'Song' type) for updating the program
			current = (Song*) malloc(sizeof(Song));
			current->song_ID = atoi(fstart);
			//TITLE
			fgets(current->title,25,record_ptr);
			current->title[strlen(current->title)-1] = '\0';
			//ARTIST
			fgets(current->artist,25,record_ptr);
			current->artist[strlen(current->artist)-1] = '\0';
			//COMPOSER
			fgets(current->composer,25,record_ptr);
			current->composer[strlen(current->composer)-1] = '\0';
			//ALBUM
			fgets(current->album,25,record_ptr);
			current->album[strlen(current->album)-1] = '\0';
			//GENRE
			fgets(current->genre,20,record_ptr);
			current->genre[strlen(current->genre)-1] = '\0';
			//RATING
			fgets(frating,3,record_ptr);
			frating[strlen(frating)-1] = '\0';
			current->rating = atoi(frating);
			//REMARKS
			fgets(current->remarks,50,record_ptr);
			current->remarks[strlen(current->remarks)-1] = '\0';
			
			current->next = head;
			if (start == NULL) start = current; // Declaring the current as the start (nearest the NULL pointer) of the list
			else head->previous = current; //Connecting the Cell of the previous cell into the current
			head = current;
		}
		
		fclose(record_ptr);
		
		//Input and also its validation. The program ends after failing the condition of the do-while loop.
		do{
			NEWLINE
			printf("What do you want to do?\n");
			printf("Please choose:\n    1 - Add a song\n    2 - Update a song\n    3 - List songs\n    0 - Exit the library\n");
			//Inputting thw user's choice (with Input Validation)
			do{
				printf("Choice : ");
				fgets(action,10,stdin);
				NEWLINE
				if(check_action(action)) break;
				else printf("ERROR. Wrong input. Try again.\n");
			} while(1);
			
			switch (action[0]){
				case '0': 
					EXIT = 1; 
					exit_library(record_ptr,head);
					break;
				case '1': 
					printf("----------==========Add a Song==========----------\n");
					head = add_song(head,rand());
					if ( start == NULL) start = head; 
					printf("--------========Function termination========-------\n");
					break;
				case '2': 
					if (head == NULL){ // Will check if there are any nodes in the list
						printf("Sorry. The Library has nothing in it.\n");
						break;
					} 
					else {
						printf("---------==========Update a Song=========---------\n");
						printf("Which song will be updated? : ");
						fgets(update,50,stdin);
						update[strlen(update)-1] = '\0';
						strcpy(update,remove_spaces(update));
						if (!(update_song(update,head))) printf("Song record does not exist.\n");
						printf("--------========Function termination========-------\n");
						break;
					}
				case '3': 
					if (head == NULL){ // Will check if there are any nodes in the list
						printf("Sorry. The Library has nothing in it.\n");
						break;
					} 
					else {
						printf("----------===========List Songs==========----------\n");
						head = list_song(head);
						printf("--------========Function termination========-------\n");
						break;
					}
			}
		} while ( EXIT == 0 );
	}
	printf("\nTerminating the program...\nGoodbye!\n");
	printf("=================== Closed the Library ====================");
}

/*
\\\\\\\---v^v^v^---v^v^v^---v^v^v^---v^v^v^---  DAILY LOG  ---v^v^v^---v^v^v^---v^v^v^--v^v^v^---/////

Day 1 (November 20, 2015)
-I started filling up the main function.  I started builfing and planning the interface of my program
to reduce and prevent confusion in reading and understanding the program in execution.
-Problems encounter:
	>No problems encountered except for the design of the programs.
	>I attempted using the gotoxy function under the windows.h library but the problem is there is a possiblity
	that the one checking the program will not use windows but instead, Linux or Mac which will conflict to compilation 
	and execution of the program.
	*SOLUTION : Less text and more use of the remaining white space without being OS and Compiler dependent
	                                   				 ----***----
Day 2 (November 23, 2015)
-I started doing test runs and experiments in file manipulation - reading, writing and appending.
-I tried doing appending as my way of updating the library but have encounterd problems - having NULL between the 
data of the original file and the file appended. I failed in finding the solution for this problem, how 
to update the library and manipulate the files.
	                                   				 ----***----
Day 3 (November 27, 2015)
-After consulting with an upperclassmen, I have grasped a part of knowledge in manipulations and why things 
is not working out. Also, I found the solution in updating the library.
	*SOLUTION - First read the file then run the program. Before terminating the program, overwrite the file
	with the updates in the program.
-I experimented and mande many test runs until it became successful. (Mostly the problems is what and how to use the functions
for files)
	                                   				 ----***----
DAY 4 (November 28, 2015) 
-I started doing the Add Song library function.
-I made test runs in another source file that is why I didn't encounter much problem in this exact source file. 
-Problems encountered:
	>I had a problem in outputing random numbers for the Song ID of a song.
	 It either gets the same series of numbers or repeats the same number in the local function.
	*SOLUTION : I put the "randomization" process outside the local function but inside the main function.  After that, It outputted random numbers without problems.
	>How to  make the program look readable through modular programming.  The problems is how to put data in the structures correctly since I used
	pointers.  I want to avoid wasting memory. 
	*SOLUTION : Because of the attributes of the structure, it is pretty inevitable to lessen the space in writing the code
	but I used repeated patterns for readability purposes. By allocating and resizing the memory I was able to minimize the usage of the memory.
	                                   				 ----***----
Day 5 (November 30, 2015)
-I started doing the Update Song library function
-This part was easy because this function has the same logic of the add song function
- The only problem encounter was the allocation memory. At first, the program outputted garbage characters because I wasn't able to 
notice that mistake.
	*SOLUTION = allocation, resizing and also freeing if necessary memories were done.
	                                   				 ----***----
DAY 6 (November 28, 2015) 
-I started doing the List song function
-It was hard at first doing this because I do not know how to swap the values/nodes in the list. It took me the whole day to come up with the 
solution. I first thought I can just swap the two nodes like how to swap values in number but it wasn't successful because the pointers was only 
overwritten with the same pointer since it is a pointer. The values and the nodes weren't swapped. They were overwritten and became the same at the end 
of the sorting process (Bubble sort). I then tried to use brute force and swap the values themselves to the other nodes but it was also successful
because there were many moments that the program freezes up and shows run time errors. I then tried swapping the address of the two pointers of the nodes
to see if thehy will be swapped.  But for some reason, it didn't work and I ddon't why.
	*SOLUTION : For the first, second and third node, to swap the second and third node, I must point the first->next pointer to the third node.
	Then connect the second)next to NULL and lastly to connect the third_next to the second.
-That solution only came up in the morning actually what I was doing while coming up with the solution is fixing the documentation of tis Machine problem
-But I started also filling up the list song function with input validations.  The only thing that is missing is the sorting algorithm itself.
	                                   				 ----***----
Day 7 (December 2, 2015)
- I got the logic for the sorting of the nodes but I stil can't implement it through code. I came up with 4 different algorithms and all of them failed.
- I stopped working for the list song function.  I started testing the current program that I made. 
- Problem encountered:
	>I get random run time errors and get random characters for my 
Day 8 (December 5, 2015)
- 
*/