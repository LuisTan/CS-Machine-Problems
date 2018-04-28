from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import wordnet
from nltk.corpus import words
import enchant
import email
import lxml.html
from bs4 import BeautifulSoup
import sys,time 
import numpy as np
from sklearn.naive_bayes import MultinomialNB, GaussianNB, BernoulliNB

print("Hello. Emails in folder \"trec07p\" are being preprocessed.\nPlease standby for a moment...")
sys.stdout.write("\b")
sys.stdout.write("\b")
sys.stdout.write("\b")
#Preprocessing
for i in range(1,75420):
    f = open("trec07p/data/inmail."+str(i),errors='ignore')
    outputFile = open("./preprocessed/mail"+str(i)+".txt","w")
    out = ""
    mainEmail = email.message_from_string(f.read())
    for part in mainEmail.walk():
        if not(part.is_multipart()):
            out += BeautifulSoup(part.get_payload(),"html.parser").get_text()
    outputFile.write(out)
    f.close()
    outputFile.close()

#Dictionary Building
dictionary = set()
checker = set(words.words())
output = open("dictionary.txt","w")
for i in range(1,75420):
    f = open("./preprocessed/mail"+str(i)+".txt")
    tokens = word_tokenize(f.read())
    for word in tokens: 
        if word.lower() in checker:
            dictionary.add(word.lower())
    f.close()
sorted(dictionary)
out = ""
for item in dictionary:
    out += item+"\n"
output.write(out)
output.close()

#Creating the feature vector/Data Set
indexFV = {}
i = 0
out = ""
for item in dictionary:
    indexFV[item] = i
    i = i+1
dataset = open("dataset.txt","w")


for i in range (1,2):
    f = open("./preprocessed/mail"+str(i)+".txt")
    fvector = [0]*len(dictionary)
    tokens = word_tokenize(f.read())
    for word in tokens:
        index = indexFV.get(word.lower())
        if index != None:
            fvector[index] += 1
    dataset.write(str(fvector)[1:-1] +"\n")

#Beginning the training phase
#Getting the label

#Matrix Train
#75419 = Overall mails
#45251 = 60% emails
#30168 = Remaining 40% emails
#31759 = words in dictionary

flabel = open("trec07p/full/index")
fin = open("dataset.txt")
while True:
    choice = input("Input\n1 - Multinomial Naive Bayes\n2 - Bernoulli Naive Bayes\n")
    model = 0
    if choice == "1":
        model = MultinomialNB()
        break
    elif choice == "2":
        model = BernoulliNB()
        break
    else :
        print("Try again.")
train_vector = np.zeros(2000)
for i in range(1,23):   
    mat = [] 
    for j in range(0,2000):
        mat.append(list(map(int,fin.readline().split(","))))
        if flabel.readline().split(" ")[0] == "spam":
            train_vector[i] = 1
        else:
            train_vector[i] = 0
    train_batch = np.asmatrix(mat)
    model.partial_fit(train_batch,train_vector[(i-1)*2000:(i*2000)],classes=[0,1])
    mat = []
for j in range(0,1251):
    mat.append(list(map(int,fin.readline().split(","))))
train_batch = np.asmatrix(mat)
model.partial_fit(train_batch,train_vector[(22)*2000:45251],classes=[0,1])

fin.close() 

fin = open("dataset.txt")
#Training Phase
score = 0
for i in range(0,22):   
    mat = [] 
    for j in range(0,2000):
        mat.append(list(map(int,fin.readline().split(","))))
    train_batch = np.asmatrix(mat)
    score += model.score(train_batch,train_vector[(i)*2000:((i+1)*2000)])*2000
    mat = []
for j in range(0,1251):
    mat.append(list(map(int,fin.readline().split(","))))
train_batch = np.asmatrix(mat)
score += model.score(train_batch,train_vector[44000:45251])*1251
score = score/45251
print("Training Accuracy : ",end="")
print(score)


