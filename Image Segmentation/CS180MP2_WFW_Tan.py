
#10 9
# 7 5 for schistosoma
import cv2
import numpy as np
from sklearn.cluster import KMeans

colors = [[0, 0, 0],[150, 150, 150],[153, 0, 0],[51, 102, 0]]
cluster = 0
name = ""
centroids = np.array([])
ctr = "0"
while ctr != "q":
    ctr = input("Input\n0 - Random Centroids\n1 - Manually Assigned Centroids\n2 - 4 Cluster\n3 - BGR to HSV\n4 - BGR to Ciel Lab\nq - Quit\n")
    if ctr == "q":
        break
    if ctr == "0" or ctr == "1" or ctr == "2" or ctr == "3" or ctr == "4":   
        ctr = int(ctr) 
        for i in range(0,3):      
            if i == 0:
                name = "filaria"
                cluster = 3
                basis1 = "10"
                basis2 = "9"
                centroids = np.array([[229,189,164],[0,0,0],[176,128,142]])
            elif i == 1:
                name = "schistosoma"
                cluster = 3
                basis1 = "5"
                basis2 = "6"
                centroids = np.array([[236,164,26],[0,0,0],[156,85,0]])
            else:
                name = "plasmodium"                                                                                                                                                 
                cluster = 2
                basis1 = "1"
                basis2 = "7"
                centroids = np.array([[174,79,139],[218,201,194]])
            if ctr == 2: #More than 3
                cluster = 4

            if ctr == 3: #HSV
                img1 = cv2.cvtColor(cv2.imread(name+"/"+name+basis1+".jpg",1),cv2.COLOR_BGR2HSV).reshape(-1,3)
                img2 = cv2.cvtColor(cv2.imread(name+"/"+name+basis2+".jpg",1),cv2.COLOR_BGR2HSV).reshape(-1,3)
            elif ctr == 4: #CielLab
                img1 = cv2.cvtColor(cv2.imread(name+"/"+name+basis1+".jpg",1),cv2.COLOR_BGR2Lab).reshape(-1,3)
                img2 = cv2.cvtColor(cv2.imread(name+"/"+name+basis2+".jpg",1),cv2.COLOR_BGR2Lab).reshape(-1,3)
            else :    
                img1 = cv2.imread(name+"/"+name+basis1+".jpg",1).reshape(-1,3)
                img2 = cv2.imread(name+"/"+name+basis2+".jpg",1).reshape(-1,3)

            if ctr == 1:#Manual Centroid
                kmeans = KMeans(n_clusters=cluster, init=centroids).fit(np.vstack((img1,img2)))
            else : 
                kmeans = KMeans(n_clusters=cluster).fit(np.vstack((img1,img2)))

            for number in range(0,10):
                if ctr == 3 :
                    img3 = cv2.cvtColor(cv2.imread(name+"/"+name+str(number+1)+".jpg",1),cv2.COLOR_BGR2HSV)
                elif ctr == 4:
                    img3 = cv2.cvtColor(cv2.imread(name+"/"+name+str(number+1)+".jpg",1),cv2.COLOR_BGR2Lab)
                else:
                    img3 = cv2.imread(name+"/"+name+str(number+1)+".jpg",1)
                label = kmeans.predict(img3.reshape(-1,3)).reshape(len(img3),len(img3[0]))
                for j in range(0,cluster-1):
                    img3[label==j] = colors[j]
                cv2.imwrite('output/'+str(ctr+1)+name+str(number+1)+'.jpg',img3)
        print("Finished!!!")
    else :
        print("Invalid input. Try again.")
