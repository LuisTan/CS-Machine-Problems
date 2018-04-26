# MGA PALATANDAAN:
# 1. Inaasahang tukuyin ang pangalang ng pinagmulang kodigo sa pagsagawa ng Python file na ito.
# 2. Ang Solita ay hindi sensitibo sa mga puting espasyo.
# 3. Inaasahang may "" sa lahat ng halagang STRING.
# 4. Ang paggamit ng FLOAT at INT ay itago sa isa't isa.
# 5. Ang tungkuling PAKITA ay tumatanggap lang ng mga baryante at literal na halaga.

import sys
filename=sys.argv[-1]
file=open(filename, "r")

variable_names = {}
class Node:
	def __init__(self, type, children=None, leaf=None):
		self.type=type
		if children:
			self.children = children
		else:
			self.children = []
		self.leaf = leaf

def parsing(node):
	if node.type == "program":
		parsing(node.children[0])
	if(node.type == "body"):
		listnode=node.children
		length=len(listnode)
		if(length == 2):
			parsing(listnode[0])
			parsing(listnode[1])
		else:
			parsing(listnode[0])
	if(node.type == 'statement'):
		listnode=node.children
		listleaf=node.leaf
		if (listleaf[0] == "elementdelete"):
			index = parsing(listnode[0])
			if type(index).__name__ != 'int':
				print("Maling uri sa '%s'." % a)
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			try:
				array = variable_names[listleaf[1]]
				if array[0] != "ARRAY":
					print("Maling uri sa '%s'." % array)
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
			except Exception:
				print("Kamalian sa '%s' : Inaasahang uring STRING para sa itinalagang baryante." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			if (index < 0 or index >= len(array[1])):
				print("Talatuntunan ay lumagpas para sa '%s'." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			del array[1][index]
		elif(listleaf[0] == 'pakita'):
			if(listleaf[1][0] == '\"' and listleaf[1][0] == '\"'):
				print(listleaf[1][1:len(listleaf[1])-1], end="")
			elif (variable_names[listleaf[1]][0]) == 'STRING':
				print(variable_names[listleaf[1]][1][1:len(variable_names[listleaf[1]][1])-1], end="")
			else:
				print(variable_names[listleaf[1]][1], end="")
		elif(listleaf[0] == "basa"):			
			if variable_names[listleaf[1]][0]=='INTEGER':
				try:
					temp=input()
					temp=float(temp)
					temp=int(temp)
				except Exception:
					print("Inaasahang uring INTEGER para sa '%s'." % listleaf[1]) #dagdag line
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				variable_names[listleaf[1]][1] = temp
			elif variable_names[listleaf[1]][0]=='FLOAT':
				try:
					temp=float(input())
				except Exception:
					print("Inaasahang uring FLOAT para sa '%s'." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				variable_names[listleaf[1]][1] = temp
			elif variable_names[listleaf[1]][0]=='STRING':
				temp=input()
				if len(temp) < 2:
					print("Inaasahang \"\" sa binasang STRING.")
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				if temp[0]!='\"' or temp[len(temp)-1]!='\"':
					print("Inaasahang \"\" sa binasang STRING.")
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				else:
					variable_names[listleaf[1]][1] = temp
			else:
				print("Maling parametrong '%s' pinasa sa BASA." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
		elif (listleaf[0] == "assignment"):
			listnode = node.children
			length = len(listnode)
			listleaf = node.leaf
			if(length==2):
				a=parsing(listnode[0])
				b=parsing(listnode[1])
				if(variable_names[listleaf[1]][0]== 'ARRAY'):
					if(type(a).__name__ == 'int'):
						if(a >=0 and a<len(variable_names[listleaf[1]][1])):
							variable_names[listleaf[1]][1][a] = b
						else:
							print("Talatuntunan ay lumagpas para sa '%s'." % listleaf[1])
							print("Kamalian sa hanay %s."  % listleaf[2])
							sys.exit()
					else:
						print("Inaasahang uring INTEGER para sa '%s'." % listleaf[1])
						print("Kamalian sa hanay %s."  % listleaf[2])
						sys.exit()
				else:
					print("Inaasahang uring ARRAY para sa '%s'." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
			else:				
				a = parsing(listnode[0])
				if variable_names[listleaf[1]][0]=='INTEGER':
					if type(a).__name__!='int' and type(a).__name__!='float':
						print("Inaasahang uring INTEGER o FLOAT para sa '%s'." % listleaf[1])
						print("Kamalian sa hanay %s."  % listleaf[2])
						sys.exit()
					else:
						variable_names[listleaf[1]][1] = int(a)
				elif variable_names[listleaf[1]][0]=='FLOAT':
					if type(a).__name__!='int' and type(a).__name__!='float':
						print("Inaasahang uring INTEGER o FLOAT para sa '%s'." % listleaf[1])
						print("Kamalian sa hanay %s."  % listleaf[2])
						sys.exit()
					else:
						if type(a).__name__=='int':
							variable_names[listleaf[1]][1]=float(a)
						else:
							variable_names[listleaf[1]][1] = a
				elif variable_names[listleaf[1]][0]=='STRING':
					if type(a).__name__!='str':
						print("Inaasahang uring STRING para sa '%s'." % listleaf[1])
						print("Kamalian sa hanay %s."  % listleaf[2])
						sys.exit()
					else:
						variable_names[listleaf[1]][1] = a
				else:
					print("Hindi pwede magbigay ng halaga sa uring ARRAY para sa '%s'" %listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
		elif(listleaf[0] == "push"):
			try:
				temp = variable_names[listleaf[1]][1]
			except LookupError:
				print("Hindi tiyak na baryante '%s'" % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			if variable_names[listleaf[1]][0] == 'ARRAY':
				a = parsing(listnode[0])
				variable_names[listleaf[1]][1].append(a)
			else:
				print("Inaasahang uring ARRAY para sa parametro 1 ng DAGDAG. Tunay na uring ipinasa para sa '%s' ay " % temp, end="")
				print("'%s'." % variable_names[listleaf[1]][0])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
		elif(listleaf[0] == "if"):
			a = parsing(listnode[0])
			if(a == True):
				parsing(listnode[1])
			else:
				return
		elif(listleaf[0] == "elseif"):
			a = parsing(listnode[0])
			if(a == True):
				parsing(listnode[1])
			else:
				parsing(listnode[2])
		elif(listleaf[0] == "loop"):
			while((parsing(listnode[0])) == True):
				parsing(listnode[1])			
	elif(node.type == "expression"):
		listnode = node.children
		listleaf = node.leaf
		if(listleaf[0] == "binop"):
			a = parsing(listnode[0])
			b = parsing(listnode[1])
			if type(a).__name__ == 'str' or type(a).__name__== 'list':
				print("Maling uri sa '%s'." % a)
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			elif type(b).__name__=='str' or type(b).__name__=='list':
				print("Maling uri sa '%s'." % b)
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit() 
			else:
				if listleaf[1] == '+': 
					return a + b
				elif listleaf[1] == '-': 
					return a - b
				elif listleaf[1] == '*': 
					return a * b
				elif listleaf[1] == '/': 
					return a / b
				elif listleaf[1] == '^': 
					return a ** b
				elif listleaf[1] == '%':
					return a % b
		elif(listleaf[0] == "uminus"):
			a = parsing(listnode[0])
			return a*-1
		elif(listleaf[0] == "group"):
			return parsing(listnode[0])
		elif(listleaf[0] == "literals"):
			return listleaf[1]
		elif(listleaf[0] == "var"):
			return variable_names[listleaf[1]][1]
		elif(listleaf[0] == "concat"):
			str1 = "" 
			str2 = ""
			try:
				temp1 = variable_names[listleaf[1]]
			except Exception:
				if listleaf[1][0]=='\"' and listleaf[1][len(listleaf[1])-1] =='\"':
					temp1 = listleaf[1][0:len(listleaf[1])-1]
					str1 = temp1
				else:
					print("Hindi tiyak na baryante '%s'." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()
			try:
				temp2 = variable_names[listleaf[2]]
			except Exception:
				if listleaf[2][0]=='\"' and listleaf[2][len(listleaf[2])-1] =='\"':
					temp2 = listleaf[2][1:len(listleaf[2])]
					str2 = temp2
				else:
					print("Hindi tiyak na baryante '%s'." % listleaf[2])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()			
			if(temp1[0] == 'STRING' and temp2[0] == 'STRING'):
				str1 = temp1[1][0:len(temp1[1])-1]
				str2 = temp2[1][1:len(temp2[1])]
			elif(temp1[0] == 'STRING' and temp2 == str2):
				str1 = temp1[1][0:len(temp1[1])-1]
			elif (temp2[0] == 'STRING' and temp1 == str1):
				str2 = temp2[1][1:len(temp2[1])]
			else:
				print("Inaasahang uring STRING para sa mga parametro ng tungkuling SAMA.")
				print("Kamalian sa hanay %s."  % listleaf[3])
				sys.exit()
			return str1+str2
		elif(listleaf[0] == "strlen"):
			temp=variable_names[listleaf[1]]
			if temp[0] == 'STRING':
				return len(temp[1]) -2
			elif temp[0] == 'ARRAY':
				return len(temp[1])
		elif(listleaf[0] == "substrsearch"):
			str1 = ""
			str2 = ""
			try:
				temp1 = variable_names[listleaf[1]]
				if temp1[0] != 'STRING':
					print("Kamalian sa '%s' : Inaasahang uring STRING para sa parametro 1 ng tungkuling HANAP." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()
				else:
					str1 = temp1[1]
			except Exception:
				if listleaf[1][0]=='\"' and listleaf[1][len(listleaf[1])-1] =='\"':
					str1 = listleaf[1]
				else:
					print("Hindi tiyak na baryante '%s'." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()
			try:
				temp2 = variable_names[listleaf[2]]
				if temp2[0] != 'STRING':
					print("Kamalian sa '%s' : Inaasahang uring STRING para sa parametro 2 ng tungkuling HANAP." % listleaf[2])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()
				else:
					str2 = temp2[1]
			except Exception:
				if listleaf[2][0]=='\"' and listleaf[2][len(listleaf[2])-1] =='\"':
					str2 = listleaf[2]
				else:
					print("Hindi tiyak na baryante '%s'." % listleaf[2])
					print("Kamalian sa hanay %s."  % listleaf[3])
					sys.exit()
			return str1[1:len(str1)-1].find(str2[1:len(str2)-1])
		elif(listleaf[0] == "stringindexing"):
			index = -1
			str = ""
			try:
				temp = variable_names[listleaf[1]]
				if temp[0] != 'STRING':
					print("Kamalian sa '%s' : Inaasahang uring STRING para sa parameter 1 ng tungkuling SA." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				else:
					str = temp[1][1:len(temp[1])-1]
			except Exception:
				print("Kamalian sa '%s' : Inaasahang uring STRING para sa itinalagang baryante." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			a = parsing(listnode[0])
			if type(a).__name__ == 'int':
				if a < 0 or a > len(str) - 1:
					print("Talantuntunan ay lumagpas para sa tungkuling SA.")
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				return "\""+str[a]+"\""
			else:
				print("Inaasahang uring INTEGER para sa talatuntunang '%s'." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
		elif(listleaf[0] == "arrayaccess"):
			try:
				temp = variable_names[listleaf[1]]
				#NADAGDAGAN
				if temp[0] != "ARRAY":
					print("Maling uri sa '%s'." % temp)
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
			except Exception:
				print("Hindi tiyak na baryante '%s'." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
			a = parsing(listnode[0])
			if type(a).__name__ == 'int':
				if a < 0 or a > len(variable_names[listleaf[1]][1]) - 1:
					print("Talatuntunan ay lumagpas para sa '%s'." % listleaf[1])
					print("Kamalian sa hanay %s."  % listleaf[2])
					sys.exit()
				return variable_names[listleaf[1]][1][a]
			else:
				print("Inaasahang uring INTEGER para sa '%s'." % listleaf[1])
				print("Kamalian sa hanay %s."  % listleaf[2])
				sys.exit()
	elif(node.type=="cond"):
		listnode = node.children
		listleaf = node.leaf
		a = parsing(listnode[0])
		b = parsing(listnode[1])
		if(type(a).__name__ != type(b).__name__):
			print("Magkaiba ang uri ng %s at %s na ikinukumpara." %(a,b))
			print("Kamalian sa hanay %s."  % listleaf[1])
			sys.exit()
		if(listleaf[0] == '=='):
			return a == b
		elif(listleaf[0] == '!='):
			return a != b
		elif(listleaf[0] == '>='):
			return a >= b
		elif(listleaf[0] == '<='):
			return a <= b
		elif(listleaf[0] == '>'):
			return a > b
		elif(listleaf[0] == '<'):
			return a < b
	elif(node.type == "elseblocks"):
		listnode = node.children
		parsing(listnode[0])
	elif(node.type == "elseblock"):
		listnode = node.children
		parsing(listnode[0])
	elif(node.type == "elseifblock"):
		listnode = node.children
		a = parsing(listnode[0])
		if(a == True):
			parsing(listnode[1])
		else:
			parsing(listnode[2])

import ply.lex as lex

# Tala ng mga nakalaang salita:
reserved={
	'INTEGER':'INTEGER',
	'FLOAT':'FLOAT',
	'STRING':'STRING',
	'VARIABLES':'VARIABLES',
	'PAKITA':'PAKITA',
	'BASA':'BASA',
	'SAMA' : 'SAMA',
	'HABA' : 'HABA',
	'HANAP' : 'HANAP',
	'SA' : 'SA',
	'DAGDAG' : 'DAGDAG',
	'ARRAY' : 'ARRAY',
	'PAG' : 'PAG',
	'KUNDIPAG' : 'KUNDIPAG',
	'KUNDI' : 'KUNDI',
	'HABANG' : 'HABANG',
	'BURA' : 'BURA'
}

# Tala ng mga tandang salita:
tokens=['NAME', 'LITSTRING', 'LITINTEGER', 'LITFLOAT', 'ISEQUAL', 'LTE', 'GTE', 'LT', 'GT', 'NE', 'EQUAL', 'PLUS', 'MINUS', 'TIMES', 'DIVIDE', 'POWER', 'MODULO']+list(reserved.values())
literals=['{','}','(',')',',','[',']']

# Tuntunin ng mga regular na expresyon:

def t_LITSTRING(t):
	r'\".*\"'
	str=t.value
	new_str=""
	escaped=0
	for i in range(0, len(str)):
		c=str[i]
		if escaped:
			if c=="n":
				c="\n"
			elif c=="t":
				c="\t"
			elif c=="\\":
				c="\\"
			escaped=0
		elif c=="\\":
			escaped=1
			continue		
		new_str+=c
	t.value=new_str
	return t

def t_LITFLOAT(t):
	r'\d+[.]\d+'
	try:
		t.value = float(t.value)
	except ValueError:
		print("Halaga ay masyadong malaki para sa FLOAT.")
		print("Kamalian sa hanay ", end="")
		print(t.lineno, end="")
		print(".")
		sys.exit()
		t.value = 0.0
	return t

def t_LITINTEGER(t):
	r'\d+'
	try:
		t.value = int(t.value)
	except ValueError:
		print("Halaga ay masyadong malaki para sa INTEGER.")
		print("Kamalian sa hanay ", end="")
		print(t.lineno, end="")
		print(".")
		sys.exit()
		t.value = 0
	return t
	
def t_ID(t):
	r'[A-Z]+'
	t.type=reserved.get(t.value, 'ID')
	if t.type =='ID':
		print("Ilegal na titik '%s' sa hanay " % t.value, end="")
		print(t.lineno)
		sys.exit()
	else:
		return t

# Tuntunin ng mga pangalan ng tanda:
t_NAME=r'[a-z][a-z0-9_]*'
t_ISEQUAL = r'=='
t_LTE = r'<='
t_GTE = r'>='
t_LT = r'<'
t_GT = r'>'
t_NE = r'!='
t_PLUS    = r'\+'
t_MINUS   = r'-'
t_TIMES   = r'\*'
t_DIVIDE  = r'/'
t_POWER = r'\^'
t_MODULO = r'\%'
t_EQUAL=r'='

def t_lbrace(t):
	r'\{'
	t.type='{'
	return t

def t_rbrace(t):
	r'\}'
	t.type='}'
	return t

def t_lparen(t):
	r'\('
	t.type='('
	return t;
	
def t_rparen(t):
	r'\)'
	t.type=')'
	return t

def t_comma(t):
	r','
	t.type = ','
	return t
	
def t_lsquare(t):
	r'\['
	t.type='['
	return t
	
def t_rsquare(t):
	r'\]'
	t.type=']'
	return t
	
t_ignore=' \t'

# Pagbibilang ng pang-ilang hanay:
def t_newline(t):
	r'\n+'
	t.lexer.lineno+=len(t.value)

def t_error(t):
	print("Ilegal na titik '%s' sa hanay " % t.value[0], end="")
	print(t.lineno)
	sys.exit()

lexer=lex.lex()

import ply.yacc as yacc

precedence = (
	('nonassoc',  'IFX'),
	('nonassoc', 'KUNDI'),
	('left', 'GT', 'LT', 'GTE', 'LTE', 'ISEQUAL', 'NE'),
    ('left','PLUS','MINUS'),
    ('left','TIMES','DIVIDE', 'MODULO'),
	('right','POWER'),
    ('right','UMINUS')
    )

# Tala ng pangalan ng mga baryante:

# Pagdedeklara ng mga baryante bago ang natitira sa programa:
def p_program_completestart(t):
	'''program : init body '''
	t[0] = Node("program", [t[2]],[])
	parsing(t[0])
	
def p_program_initstart(t):
	'''program : init '''
			   
# Maaaring hindi gumamit ng mga baryante:
def p_program_bodystart(t):
	'''program : body '''
	t[0] = Node("program", [t[1]],[])
	parsing(t[0])

# Kinakailangan ng laman:
def p_init_variable(t):
	'''init : VARIABLES '{' vardecnames '}' '''

# Mahalagang paalala: Ang mga STRING na walang laman ay binibigyan ng halaga na "".
#					  Ang mga INTEGER at FLOAT ay binibigyan ng halaga na 0.
#					  Ang mga ARRAY ay binibigyan ng halaga na [].
def p_vardecnames_iternames(t):
	'''vardecnames : INTEGER NAME vardecnames 
				   | FLOAT NAME vardecnames
				   | STRING NAME vardecnames
				   | ARRAY NAME vardecnames 
				   | INTEGER NAME 
				   | FLOAT NAME
				   | STRING NAME
				   | ARRAY NAME'''
	try:
		temp = variable_names[t[2]]
		print("Baryanteng '%s' ay nagamit na." % t[2])
		print("Kamalian sa hanay %s."  % t.lineno(1))
		sys.exit()
	except Exception:
		if t[1]=='INTEGER':
			variable_names[t[2]]=[t[1], 0]
		elif t[1]=='FLOAT':
			variable_names[t[2]]=[t[1], 0.0]
		elif t[1]=='STRING':
			variable_names[t[2]]=[t[1], "\"\""]
		elif t[1]=='ARRAY':
			variable_names[t[2]]=[t[1], []]

def p_body_bodystate(t):
	'''body : body statement '''
	t[0] = Node("body", [t[1], t[2]],[])
	
def p_body_state(t):
	'''body : statement '''
	t[0] = Node("body", [t[1]],[])
	
# Kapag nagpaPAKITA ng ARRAY, ang mga STRING ay sinasamahan ng "".
def p_statement_print(t):
	'''statement : PAKITA '(' NAME ')' 
				 | PAKITA '(' LITSTRING ')' '''
	if t[3][0]=='\"' and t[3][len(t[3])-1] =='\"':
		t[0] = Node("statement", [], ["pakita", t[3]])
	else:
		try: 
			temp=variable_names[t[3]]
		except LookupError:
			print("Baryanteng '%s' ay hindi nahanap." % t[3])
			print("Kamalian sa hanay %s."  % t.lineno(1))
			sys.exit()
		t[0] = Node("statement", [], ["pakita",t[3]])
		
def p_statement_read(t):
	'''statement : BASA '(' NAME ')' '''
	try:
		temp=variable_names[t[3]]
	except Exception:
		print("Baryanteng '%s' ay hindi nahanap." % t[3])
		print("Kamalian sa hanay %s."  % t.lineno(1))
		sys.exit()
	t[0] = Node("statement",[],["basa",t[3], t.lineno(1)])
	
def p_statement_assignment(t):
	'''statement : NAME EQUAL expression
				 | NAME '[' expression ']' EQUAL expression 
	'''
	if(t[2] == '='):
		try:
			temp=variable_names[t[1]]
		except Exception:
			print("Baryanteng '%s' ay hindi nahanap." % t[1])
			print("Kamalian sa hanay %s."  % t.lineno(1))
			sys.exit()
		t[0] = Node("statement",[t[3]],["assignment",t[1], t.lineno(1)])
	else:
		try:
			temp=variable_names[t[1]]
		except Exception:
			print("Baryanteng '%s' ay hindi nahanap." % t[1])
			print("Kamalian sa hanay %s."  % t.lineno(1))
			sys.exit()
		t[0] = Node("statement",[t[3],t[6]],["assignment",t[1], t.lineno(1)])
	
def p_statement_push(t):
	''' statement : DAGDAG '(' NAME ',' expression ')' '''
	t[0] = Node("statement", [t[5]], ["push", t[3], t.lineno(1)])
	
def p_statement_elementdelete(t):
	''' statement : BURA '(' NAME ',' expression ')' '''
	t[0] = Node("statement", [t[5]],["elementdelete",t[3], t.lineno(1)])
		
def p_statement_conditionalif(t):
	'''statement : PAG '(' cond ')' '{' body '}' %prec IFX '''
	t[0] = Node("statement", [t[3], t[6]], ["if"])
	
def p_statement_conditionalelif(t):
	'''statement : PAG '(' cond ')' '{' body '}' elseblocks '''
	t[0] = Node("statement", [t[3], t[6], t[8]], ["elseif"])
	
def p_statement_loop(t):
	'''statement : HABANG '(' cond ')' '{' body '}' '''
	t[0] = Node("statement", [t[3], t[6]], ["loop"])

	
def p_elseblocks_elifblocks(t):
	'''elseblocks : elseifblock
				  | elseblock '''
	t[0] = Node("elseblocks", [t[1]], [])
	
def p_elseblock_else(t):
	'''elseblock : KUNDI '{' body '}' '''
	t[0] = Node("elseblock", [t[3]], [])
	
def p_elseifblock_elifblock(t):
	'''elseifblock : KUNDIPAG '(' cond ')' '{' body '}' elseblocks '''
	t[0] = Node("elseifblock", [t[3], t[6], t[8]], [])
	
def p_cond_conditions(t):
	''' cond : expression ISEQUAL expression 
			 | expression NE expression 
			 | expression GTE expression 
			 | expression LTE expression 
			 | expression LT expression 
			 | expression GT expression 
	'''
	t[0] = Node("cond", [t[1], t[3]], [t[2], t.lineno(2)])

def p_expression_binop(t):
	'''expression : expression PLUS expression 
				  | expression MINUS expression
				  | expression TIMES expression
				  | expression DIVIDE expression
				  | expression POWER expression 
				  | expression MODULO expression '''
	t[0] = Node("expression",[t[1],t[3]],["binop",t[2], t.lineno(2)])
		
def p_expression_uminus(t):
	'expression : MINUS expression %prec UMINUS'
	t[0]=Node("expression",[t[2]],["uminus"])
	
def p_expression_group(t):
	'''expression : '(' expression ')' '''
	t[0] = Node("expression",[t[2]],["group"])
	
def p_expression_types(t):
	'''expression : LITSTRING
				  | LITINTEGER
				  | LITFLOAT'''
	t[0]=Node("expression",[],["literals",t[1]])
	
def p_expression_var(t):
	'''expression : NAME'''
	try:
		temp = variable_names[t[1]][1]
	except LookupError:
		print("Baryanteng '%s' ay hindi nahanap." % t[1])
		print("Kamalian sa hanay %s."  % t.lineno(1))
		sys.exit()
	t[0] = Node("expression",[],["var", t[1]])
	
# Mahalagang paalala: Ang tungkuling SAMA ay hindi tumatanggap ng 2 literal na STRING.
def p_expression_concat(t):
	'''expression : SAMA '(' NAME ',' NAME ')' 
				  | SAMA '(' NAME ',' LITSTRING ')' 
				  | SAMA '(' LITSTRING ',' NAME ')' ''' 
	t[0] = Node("expression",[],["concat", t[3], t[5], t.lineno(1)])

# Mahalagang paalala: Ang tungkuling HABA ay tumatanggap lamang ng baryanteng STRING bilang parametro.
def p_expression_strlength(t):
	''' expression : HABA '(' NAME ')' '''
	try:
		temp = variable_names[t[3]]	
		if(temp[0] != 'STRING' and temp[0]!='ARRAY'):
			print("Inaasahang uring STRING o ARRAY para sa HABA.")
			print("Kamalian sa hanay %s."  % t.lineno(1))
			sys.exit()		
	except Exception: 
		print("Baryanteng '%s' ay hindi nahanap." % t[3])
		print("Kamalian sa hanay %s."  % t.lineno(1))
		sys.exit()
	t[0] = Node("expression", [], ["strlen", t[3]])

# Mahalagang paalala: Ang tungkuling HANAP ay hindi tumatanggap ng 2 literal na STRING.
def p_expression_substrsearch(t):
	''' expression : HANAP '(' NAME ',' NAME ')'
				   | HANAP '(' NAME ',' LITSTRING ')'
				   | HANAP '(' LITSTRING ',' NAME ')'
	'''	
	t[0] = Node("expression", [], ["substrsearch", t[3], t[5], t.lineno(1)])

# Mahalagang paalala: Ang tungkuling SA ay hindi tumatanggap ng literal na STRING.
def p_expression_stringdexing(t):
	''' expression : SA '(' NAME ',' expression ')'
	'''
	t[0] = Node("expression", [t[5]], ["stringindexing", t[3], t.lineno(1)])
	
def p_expression_arrayaccess(t):
	''' expression : NAME '[' expression ']' '''
	t[0] = Node("expression", [t[3]], ["arrayaccess", t[1], t.lineno(1)])

def p_error(t):
	if t == None:
		print("Hindi inaasahang engkwentro ng dulo ng programa.")
	else:
		print("Kamalian sa hanay %s."  % t.lineno)
		print("	-> Ang nahanap ay '%s' \n" % t.value)
	sys.exit()
	
parser=yacc.yacc()
while True:
	line=file.read()
	if not line:
		file.close()
		break
	else:
		parser.parse(line)