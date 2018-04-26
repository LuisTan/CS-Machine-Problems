################################################################
.eqv	O	0x0000004f
.eqv	X	0x00000058
.eqv	_	0x0000005f
.eqv	YES	0x0a736579
.eqv	NO	0x000a6f6e
################################################################
.macro		prints (%string)
		move		$s1, $a0		#Preserves the previous value
		la		$a0, %string		#Puts the string to a0
		li		$v0, 4			#Syscall 4 for printing string
		syscall	
		move		$a0, $s1		#Restore previous value
.end_macro
################################################################
.macro		scanaction	()
		li		$v0, 5			#Syscall 5 for reading integers
		syscall
		move		$t3, $v0		#Transfer the read integer to t3
.end_macro
################################################################
.macro		incr	(%i)
		addi		%i, %i, 1		#Incrementation by 1
.end_macro
################################################################
.macro		dec	(%i)
		subi		%i, %i, 1		#Decrementation by 1
.end_macro
################################################################
.macro		exit	()
		li		$v0, 10			#Syscall 10 for exiting
		syscall
.end_macro
################################################################################################################################
.text
main:		la		$k0, tictac		#Setting k0 to be the pointer for the tictac array
		prints		(greetings)		#Print string
.macro		store		(%value, %index)	#Store value 
		li		$t2, %value		#Puts the passed parameter to t2
		sb		$t2, %index($k0)	#Stores at index %index
.end_macro
yesorno:	prints		(yor)			#print string
		la		$a0, input		#Label input will hold the "yes" or "no" string
		li		$a1, 5			#5 bytes will only be considered as the string buffer length
		li		$v0, 8			#Syscall 8 operation for reading strings
		syscall
		
		lw		$t0, ($a0)		#Loading the values of a0 to t0
		
		beq		$t0, YES, yes		#Checks if the input string is equal to "yes"
		beq		$t0, NO, no		#Checks if the input string is equal to "no"
		prints		(wronginput)		#Print string
		b		yesorno			#Loops back from the beginning
		
yes:		jal		fxnprint		#Print current state
		li		$t4, 5			#t4 as no. of moves of player 1
		li		$t6, 6			#t6 max input of player 1 + 1
		b  		reinput			#Branch to reinput
no:		store		(X,4)			#First move of computer is put X in the center
		jal		fxnprint		#Print current state
		move		$s1, $a0
		prints		(compmove)
		li		$a0, 5
		li		$v0, 1
		syscall
		prints		(newline)
		move		$a0, $s1
		li		$t4, 4			#t6 max input of player 1
		li		$t6, 5			#t6 max input of player 1 + 1
reinput:	beq		$t5, $t4, finalmove	#Check if attained max no. of moves
		prints		(move1)			#Print the question what box would player 1 fill
		scanaction()				#Input
		blt		$t3, 1, datainvalid	#If input is less than 1 go back
		bgt		$t3, 9, datainvalid	#If input is greater than 9 go back
		#Check if the index to be changed is _
		dec		($t3)			#Decrements t3 for array accesssing
		lb		$t1, tictac($t3)	#Puts the value of the index in the array to t1
		#debug		()
		beq		$t1, _, tobeput		#Checks if t1 != _
datainvalid:	prints		(wronginput)		#If no print wrong
		b 		reinput			#Branch back to wrong input
		#Updates the board with Player 1's action
tobeput:	
		li		$t2, O			#If yes	store the value in index t3
		sb		$t2, tictac($t3)	#Update the board
		#Computer's action drawloop
finalmove:	jal		fxncheck		#Call fxncheck function
		#debug
		jal		fxnchk1move		#Call fxnchk1move
		move		$a0, $v0		#Make the return value of fxnchk1move as paramter for fxnnextmove
		jal		fxnnextmove		#Call fxnnextmove
		jal		fxncheck		#Call fxncheck
		#debug		()
		incr		($t5)			#Increment
		jal		fxnprint		#Prints the current state of the game
		#MAIN CONDITION FOR NO
		bne		$t5, $t6, reinput	#Conditional statement of the loop
		exit		()			#Exit
################################################################################################################################
################################################################
		#Check for one-move away conditions
fxnchk1move:	#############PREAMBLE###################
		subu		$sp, $sp, 32		#Resize the frame size by 32 bytes
		sw		$ra, 28($sp)		#Preserves ra
		sw		$fp, 24($sp)		#Preserves frame pointer
		sw		$t2, 20($sp)		#Preserves t2
		sw		$t3, 16($sp)		#Preserves t3
		sw		$t4, 12($sp)		#Preserves t4
		sw		$t5, 8($sp)		#Preserves t5
		sw		$t6, 4($sp)		#Preserves t6
		addu		$fp, $sp, 32		#moving the frame poitner to the base of the frame
		#############PREAMBLE###################
		li		$v0, 9			#Walang nahanap
		li		$t0, 0			#Value of index
		li		$t1, 0			#value of index
		li		$t2, 0			#Counter for inner llop
		li		$t3, 0			#Counter for outer loop
		li		$t4, 0			#Counter of blanks
		li		$t6, 0			#Value of number of swaps
		li		$s1, 3			#Value of maximumg inner loop
		li		$a0, 1			#Value of incrementation
crows2:		# Checking for t0
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		bne		$t0, _, r0A		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
r0A:		beq		$t2, $s1, crows1
		lb		$t0, tictac($t2)	#Acessing the element in index t0	
		bne		$t0, _, r0B		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
r0B:		beq		$t2, $s1, crows1		
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		#Checking for t0
		incr		($t2)
		beq		$t2, $s1, crows1
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, r1A		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
r1A:		beq		$t2, $s1, crows1
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, r1B		#checks if  the loaded byte is _
		beq		$t4, 2, crows1		#t2 counts the number of blanks
		beq		$t6, 2, crows1		#Checks if loop traversed 2 timex completely, meaning the game is finishe
		jal		fxnifblank		#Call fxnifblank
r1B:		beq		$t2, $s1, crows1
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		#Main condition and action of the loop
		beq		$t4, 2, crows1		#t2 counts the number of blanks
		beq		$t6, 2, crows1		#Checks if loop traversed 2 timex completely, meaning the game is finished
		bne		$t0, $t1, crowscont	#Checks if the two blocks' values are not equak
		incr		($t6)			#incrementation
		bne		$t6, 2, crows2		#Checks if loop traversed 2 timex completely, meaning the game is finished
crows1:		bne		$t4, 1, crowscont	#Skips retrn value altering
		beq		$t0, O, next1		#If losing condition
		move		$v0, $t5		#Return value equal to value of the latest blank
		b		rend			#Return
next1:		move		$v0, $t5		#Return value equal to value of the latest blank
crowscont:	add		$t3, $t3, 3		#Incrementation by 3
		move		$t2, $t3		#Makes content of t2 be t3
		add		$s1, $t3, 3
		li		$t4, 0			#Initialization
		li		$t6, 0			#Initialization
		bne		$t3, 9, crows2		#Main condition
###	###	###	###	###	###
###	###	###	###	###	### COLUMNS
		li		$t2, 0			#Initialization
		li		$t3, 0			#Initialization
		li		$t4, 0			#Initialization
		li		$t6, 0			#Initialization
		li		$s1, 9
		li		$a0, 3			#Incrementation done by 3
ccols2:		# Checking for t0
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		bne		$t0, _, c0A		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
c0A:		beq		$t2, $s1, ccols1
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		bne		$t0, _, c0B		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
c0B:		beq		$t2, $s1, ccols1
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		add		$t2, $t2, 3		#Increment
		#Checking for t1
		beq		$t2, $s1, ccols1
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, c1A		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
c1A:		beq		$t2, $s1, ccols1
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, c1B		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
c1B:		beq		$t2, $s1, ccols1
		lb		$t1, tictac($t2)	#Acessing the element in index t1	
		#Main condition and action of the loop
		beq		$t4, 2, ccols1		#t2 counts the number of blanks
		beq		$t6, 2, ccols1		#Checks if loop traversed 2 timex completely, meaning the game is finished
		bne		$t0, $t1, ccolscont	#Checks if the two blocks' values are not equak
		incr		($t6)			#incrementation
		bne		$t6, 2, ccols2		#Checks if loop traversed 2 timex completely, meaning the game is finished

ccols1:		bne		$t4, 1, ccolscont	#SKips return value altering
		beq		$t0, O, next2 		#If losing condition
		move		$v0, $t5		#Makes return value equal to latest blank
		b		rend			#Return
next2:		move		$v0, $t5		#Makes return value equal to latest blank
ccolscont:	incr		($t3)			# Incrementation by 1
		move		$t2, $t3		#Initialization for the next column
		add		$s1, $t3, 9
		li		$t4, 0			#Initialization
		li		$t6, 0			#Initialization
		bne		$t3, 3, ccols2		#Main condition
###	###	###	###	###	###	
###	###	###	###	###	### DIAGONALS
		li		$t2, 0			#Intializaiton
		li		$t3, 0			#Intializaiton
		li		$t4, 0			#Intializaiton
		li		$t6, 0			#Intializaiton
		li		$a0, 4			#Incrementation done by 4
		li		$s1, 12
cdiag1:		lb		$t0, tictac($t2)	#Acessing the element in index t0
		bne		$t0, _, d0A		#checks if  the loaded byte is _		
		jal		fxnifblank		#Call fxnifblank
d0A:		beq		$t2, $s1, cdiag2
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		bne		$t0, _, d0B		#checks if  the loaded byte is _	
		jal		fxnifblank		#Call fxnifblank
d0B:		beq		$t2, $s1, cdiag2
		lb		$t0, tictac($t2)	#Acessing the element in index t0
		add		$t2, $t2, $a0		#Incrementation
		beq		$t2, $s1, cdiag2
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, d1A		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
		
d1A:		beq		$t2, $s1, cdiag2
		lb		$t1, tictac($t2)	#Acessing the element in index t1
		bne		$t1, _, d1B		#checks if  the loaded byte is _
		jal		fxnifblank		#Call fxnifblank
d1B:		beq		$t2, $s1, cdiag2
		lb		$t1, tictac($t2)	#Acessing the element in index t0
		beq		$t4, 2, cdiag2		#t2 counts the number of blanks
		beq		$t6, 2, cdiag2		#Checks if loop traversed 2 timex completely, meaning the game is finished
		bne		$t0, $t1, cdiagcont	#Checks if equal
		incr		($t6)			#Increment
		bne		$t6, 2, cdiag1 		#main condition for this loop
cdiag2: 	bne		$t4, 1, cdiagcont 	#Skip return value altering
		beq		$t0, O, next3 		#If losing ocndition
		move		$v0, $t5		#Makes return value equal to latest blank
		b		rend			#return
next3:		move		$v0, $t5		#Makes return value equal to latest blank
cdiagcont:	li		$t2, 2			#Next diagonal check
		li		$s1, 8
		li		$t4, 0			#initialization
		li		$t6, 0			#initialization
		li		$a0, 2			#incrementation done by 2
		incr		($t3)			#Incrementation
		bne		$t3, 2, cdiag1		#Main condition
###	###	###	###	###	###
rend:		#############END###################
		lw		$ra, 28($sp)		#Preserves ra
		lw		$fp, 24($sp)		#Preserves frame pointer
		lw		$t2, 20($sp)		#Preserves t2
		lw		$t3, 16($sp)		#Preserves t3
		lw		$t4, 12($sp)		#Preserves t4
		lw		$t5, 8($sp)		#Preserves t5
		lw		$t6, 4($sp)		#Preserves t6
		addu		$sp, $sp, 32		#Restore the stack pointer
		addu		$fp, $sp, 32		#Restore the frame pointer
		b		return
		#############END###################
fxnifblank:	beq		$t4, 2, return		#If more than 2 blanks
		incr		($t4)			#Increment
		move		$t5, $t2		#t5 holds the index number of the next move move 
		add		$t2, $t2, $a0		#Increment
		incr		($t6)			#Increment
		b		return			#Return
################################################################
################################################################
	
fxnnextmove:	li		$t9, 9			#Nextmove
		li		$t2, 0			#Initialization
		beq		$a0, 9, plan0		#Check if no possible moves to win or to prevent losing
		move		$t9, $a0		#Puts paramter to t9
		b		putX			#Branch to putX
plan0:		lb		$t0, tictac+4		#Loads the center
		bne		$t0,_, plan1 		#Check if _
		li		$t9, 4			#First move is center
		b 		putX 			#Branch to putX
plan1:		lb		$t0, tictac($t2)	#Checks corner
		bne		$t0,_, plan1con  	#Checks if blank
		move		$t9, $t2		#Put the index to t9 
		b 		putX			#branch to putX
plan1con:	add		$t2, $t2, 2		#Increment
		bne		$t2, 10,plan1		#Main condition
		li		$t2, 1			#initialziation
plan2:		lb		$t0, tictac($t2)	#Check edges
		bne		$t0,_,plan2con		#Checks if blank
		move		$t9,$t2			#Moves index to t9
		b 		putX			#Branch to putY
plan2con:	add		$t2, $t2, 2		#increment
		bne		$t2, 9,plan1		#main condition
		
putX:		li		$t1, X			#t1 holds X - next move
		sb		$t1, tictac($t9)	#Updatin gthe array
		move		$s1, $a0		#Saving value of a0
		la		$a0, compmove		#string
		li		$v0, 4			#Syscall 4 for printing strings
		syscall
		incr		($t9)			#Increment
		move		$a0, $t9		#Displaying the computer
		li		$v0, 1			#Sysclal 1 for printing integers
		syscall		
		la		$a0, newline		#Newling
		li		$v0, 4			#Syscall 4 for printing newline
		syscall
		move		$a0, $s1		#Returns a0 back to its originaql value
		b		return 			#Return
################################################################
################################################################
return:		jr		$ra			#Return
################################################################
################################################################
draw:		jal		fxnprint		#Print current state
		prints		(compdraw)		#Print string
		exit		()			#Xit
playerlose:	jal		fxnprint		#Print current state
		prints		(compwin)		#Print string
		exit		()			#Exit
################################################################
################################################################
fxnprint:	#############PREAMBLE###########################
		subu		$sp, $sp, 32		#Resize the frame size by 32 bytes
		sw		$ra, 28($sp)		#Preserves ra
		sw		$fp, 24($sp)		#Preserves frame pointer
		sw		$t0, 20($sp)		#Preserves t0
		sw		$t1, 16($sp)		#Preserves t1
		sw		$t2, 12($sp)		#Preserves t2
		addu		$fp, $sp, 32		#moving the frame poitner to the base of the frame
		#############PREAMBLE###################
		li		$t0, 0			#Counter to 9
		li		$t1, 15			#Counter for printer
		li		$t2, 0			#Holder of byte
loop1:		lb		$t2, tictac($t0)	#Load at index t0
		sb  		$t2, printer($t1)	#Store at index t2
		addi		$t1, $t1, 4		#increment
		incr		($t0)			#increment
		beq		$t1, 27, plus16		#To row 2
		beq		$t1, 55, plus16		#To row 3
		b		continue		#Skips incrementation by 16
plus16:		addi		$t1, $t1, 16		#Increment by 16
continue:	bne		$t0, 9, loop1		#Main ocndition
		la		$a0, printer 		#Loads the whole current state to a0
		li		$v0, 4			#Syscall 4 fo rprinting strings
		syscall
		#######Ende###########
		lw		$ra, 28($sp)		#Restore ra
		lw		$fp, 24($sp)		#Restore frame pointer
		lw		$t0, 20($sp)		#Restore t0
		lw		$t1, 16($sp)		#Restore t1
		lw		$t2, 12($sp)		#Restore t2
		addu		$sp, $sp, 32		#Restore the stack pointer
		addu		$fp, $sp, 32		#Restore the frame pointer
		#######Ende###########
		b		return			#Return
####################################################################
####################################################################
		#check Rows
fxncheck:	#############PREAMBLE###################
		subu		$sp, $sp, 32		#Resize the frame size by 32 bytes
		sw		$ra, 28($sp)		#Preserves ra
		sw		$fp, 24($sp)		#Preserves frame pointer
		sw		$t4, 20($sp)		#Preserves t4
		sw		$t6, 16($sp)		#Preserves t6
		sw		$t7, 12($sp)		#Preserves t7
		sw		$t0, 8($sp)		#Preserves t0
		sw		$t1, 4($sp)		#Preserves t1
		sw		$t2, 0($sp)		#Preserves t2
		addu		$fp, $sp, 32		#moving the frame poitner to the base of the frame
		#############PREAMBLE###################
		li		$t4, 0			#Initialization
		li		$t6, 0			#Initialization
		li		$t7, 0
rows2:		lb		$t0, tictac($t4)	#Acessing the element in index tt
		beq		$t0, _, rows1		#checks if  the loaded byte is
		bne		$t0, X, rows1		#Checks if the two blocks' values are not equak _
		incr		($t4)			#incrementation
		incr		($t7)			#incrementation
		bne		$t7, 3, rows2		#Checks if loop traversed 2 timex completely, meaning the game is finished
rows1:		beq		$t7, 3, playerlose	#Check if the row is complete with X
		add		$t6, $t6, 3		# Incrementation by 3
		li		$t7, 0			#Initialization
		move		$t4, $t6		#Initialization of row
		bne		$t6, 9, rows2		#Main condition
		#Check Columns
		li		$t4, 0			#Initialization
		li		$t6, 0			#Initialization
		li		$t7, 0			#Initialization
columns2:	lb		$t0, tictac($t4)	#Acessing the element in index tt
		beq		$t0, _, columns1	#checks if  the loaded byte is _
		bne		$t0, X, columns1	#Checks if the two blocks' values are not equak
		addi		$t4, $t4, 3		#incrementation
		incr		($t7)			#incrementation
		bne		$t7, 3, columns2	#Main condition
columns1:	beq		$t7, 3, playerlose	#Checks if column is complete
		incr		($t6)			#Increment
		li		$t7, 0			#Inittialization
		move		$t4, $t6		#Initialization
		bne		$t6, 3, columns2	#Main Condition
		#Check Diagonals	
diagonal1:	lb		$t0, tictac		#loading index 0
		lb		$t1, tictac+4		#loading index 4
		lb		$t2, tictac+8		#loading index 8
		bne		$t0, X, diagonal2	#Check if the diagonal is complete
		bne		$t1, X, diagonal2	#Check if the diagonal is complete
		bne		$t2, X, diagonal2	#Check if the diagonal is complete
		b 		playerlose		#Computer wins the game
diagonal2:	lb		$t0, tictac+2		#loading index 2
		lb		$t1, tictac+4		#loading index 4
		lb		$t2, tictac+6		#loading index 6
		bne		$t0, X, drawloop	#Check if the diagonal is complete
		bne		$t1, X, drawloop	#Check if the diagonal is complete
		bne		$t2, X, drawloop	#Check if the diagonal is complete
		b 		playerlose		#computer wins the game
		#Check if draw
drawloop:	li		$t4, 0			#initialization
drloop:		lb		$t1, tictac($t4)	#Accessing the element in index t4
		beq		$t1, _, returner	#Checks if t1 (loaded value) is equal to _ MALI TO MALIT MALI TO
		incr		($t4)			#Incrementation
		beq		$t4, 9, draw		#Branches back to
		ble		$t4, 8, drloop		#Main condition
		#######Ende###########
returner:	lw		$ra, 28($sp)		#Restore ra
		lw		$fp, 24($sp)		#Restore frame pointer
		lw		$t4, 20($sp)		#Preserves t4
		lw		$t6, 16($sp)		#Preserves t6
		lw		$t7, 12($sp)		#Preserves t7
		lw		$t0, 8($sp)		#Preserves t0
		lw		$t1, 4($sp)		#Preserves t1
		lw		$t2, 0($sp)		#Preserves t2
		addu		$sp, $sp, 32		#Restore the stack pointer
		addu		$fp, $sp, 32		#Restore the frame pointer
		#######Ende###########
		b		return			#Return
####################################################################
.data
input:		.space	5
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
tictac:		.ascii	"_________"
		.byte	0
		.byte	0
		.byte	'\0'
printer:	.ascii	" ___________"
		.byte	'\n'
		.ascii	"| _ | _ | _ |" #16 20 24 26
		.byte	'\n'
		.ascii	"|___|___|___|"
		.byte	'\n'
		.ascii	"| _ | _ | _ |" #44 48 52 54
		.byte	'\n'
		.ascii	"|___|___|___|"
		.byte	'\n'
		.ascii	"| _ | _ | _ |" #72 76 80 82
		.byte	'\n'
		.ascii	"|___|___|___|"
		.byte	'\n'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
greetings:	.asciiz	"Hello, Player 1 to game of Tic-Tac-Toe."
yor:		.ascii	"Do you want to play first? (yes or no)"
		.byte	'\n'
		.byte	'\0'
move1:		.ascii	"Choose 1-9 for your move : "
		.byte	'\0'
wronginput:	.ascii "Wrong input. Try again."
		.byte	'\n'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
compwin:	.ascii	"You lose. Good game."
		.byte	'\n'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
complose:	.asciiz	"You win. Good game."
compdraw:	.ascii	"Draw. Good game."
		.byte	'\n'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
compmove:	.ascii	"Computer move : "
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
		.byte	'\0'
newline:	.asciiz	"\n"
		
