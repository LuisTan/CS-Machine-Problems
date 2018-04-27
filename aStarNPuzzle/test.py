import sys
import heapq
import queue
import copy


def prnt(list):
    for i in range(3):
        print("[",end="")
        for j in range(3):
            print(list[(i*3)+j],end="")
            if j != 2:
                print(" ",end="")
        print("]")


def misplaced_tiles(state,goal):
    return 0
    ctr = 0
    for i in range(len(state)):
        if (state[i] != goal[i]): 
            ctr +=1
    return ctr-1

#def nmaxswap(state,goal)

# pqueue = cost,state
# dictionary = state : "action",cost,prevstate 
def a_star(state,goal,hfunc,dim,g):
    pqueue = []
    heapq.heapify(pqueue)
    discovered = {str(state):["head",0,None,0]}
#   while isequal(current.getstate(),goal):
    #generate moves
    #get hvalue
    #create nodes
    node = [0,0]
    index = state.index('0')
    if(index > dim-1 ): #DOWN 
        next_state = list(state)
        next_state[index] = next_state[index-dim]
        next_state[index-dim] = '0'
        strnext_state = str(next_state)
        hvalue = hfunc(next_state,goal)
        temp = hvalue + node[0]+1
        #If nangyari na yung config, else gawa bago
        if discovered.get(strnext_state) != None:
            config = discovered[strnext_state]
            if config[1] > temp:
                pqueue[pqueue.index([config[1],next_state])][0] = temp
                discovered[strnext_state] = ["down",temp,state,hvalue]
        else:
            discovered[strnext_state] = ["down",temp,state,hvalue]
            pqueue.append([temp,next_state])
        #prnt(next_state)
    if(index < (dim*dim) - dim ):
        next_state = list(state)
        next_state[index] = next_state[index+dim]
        next_state[index+dim] = '0'
        strnext_state = str(next_state)
        temp =node[0]+1
        if next_state[index] == goal[index]:
            temp = temp + temp-1
        else if next_state[index] == goal[state+dim]
            temp = temp + temp + 1
        if goal[index+dim] == '0':
            temp = temp + temp-1
        else if goal[index+dim] == goal[state+dim]
            temp = temp + temp + 1
            
        if discovered.get(strnext_state) != None:
            config = discovered[strnext_state]
            if config[1] > temp:
                pqueue[pqueue.index([config[1],next_state])][0] = temp
                discovered[strnext_state] = ["up",temp,state]
        else:
            discovered[strnext_state] = ["up",temp,state]
            pqueue.append([temp,next_state])
        #prnt(next_state)
    if(index%dim > 0 ):
        next_state = list(state)
        next_state[index] = next_state[index-1]
        next_state[index-1] = '0'
        strnext_state = str(next_state)
        temp =node[0]+1
        if next_state[index] == goal[index]:
            temp += temp-1
        if goal[index-dim] == '0':
            temp += temp-1
            
        if discovered.get(strnext_state) != None:
            config = discovered[strnext_state]
            if config[1] > temp:
                pqueue[pqueue.index([config[1],next_state])][0] = temp
                discovered[strnext_state] = ["right",temp,state]
        else:
            discovered[strnext_state] = ["right",temp,state]
            pqueue.append([temp,next_state])
        #prnt(next_state)
    if(index%dim < 2 ):
        next_state = list(state)
        next_state[index] = next_state[index+1]
        next_state[index+1] = '0'
        strnext_state = str(next_state)
        temp =node[0]+1
        if next_state[index] == goal[index]:
            temp += temp-1
        if goal[index-dim] == '0':
            temp += temp-1
            
        if discovered.get(strnext_state) != None:
            config = discovered[strnext_state]
            if config[1] > temp:
                pqueue[pqueue.index([config[1],next_state])][0] = temp
                discovered[strnext_state] = ["left",temp,state]
        else:
            discovered[strnext_state] = ["left",temp,state]
            pqueue.append([temp,next_state])
    node = heapq.heappop(pqueue)
    currentcost = node[0]
    state = node[1]
    while state != goal:
        index = state.index('0')
        if(index > dim-1 ): #DOWN 
            next_state = list(state)
            next_state[index] = next_state[index-dim]
            next_state[index-dim] = '0'
            strnext_state = str(next_state)
            temp =node[0]+1
            if next_state[index] == goal[index]:
                temp += temp-1
            if goal[index-dim] == '0':
                temp += temp-1
                
            #If nangyari na yung config, else gawa bago
            if discovered.get(strnext_state) != None:
                config = discovered[strnext_state]
                if config[1] > temp:
                    pqueue[pqueue.index([config[1],next_state])][0] = temp
                    discovered[strnext_state] = ["down",temp,state,0]
            else:
                discovered[strnext_state] = ["down",temp,state]
                pqueue.append([temp,next_state])
            #prnt(next_state)
        if(index < (dim*dim) - dim ):
            next_state = list(state)
            next_state[index] = next_state[index+dim]
            next_state[index+dim] = '0'
            strnext_state = str(next_state)
            temp =node[0]+1
            if next_state[index] == goal[index]:
                temp = temp + temp-1
            else if next_state[index] == goal[state+dim]
                temp = temp + temp + 1
            if goal[index+dim] == '0':
                temp = temp + temp-1
            else if goal[index+dim] == goal[state+dim]
                temp = temp + temp + 1
                
            if discovered.get(strnext_state) != None:
                config = discovered[strnext_state]
                if config[1] > temp:
                    pqueue[pqueue.index([config[1],next_state])][0] = temp
                    discovered[strnext_state] = ["up",temp,state]
            else:
                discovered[strnext_state] = ["up",temp,state]
                pqueue.append([temp,next_state])
            #prnt(next_state)
        if(index%dim > 0 ):
            next_state = list(state)
            next_state[index] = next_state[index-1]
            next_state[index-1] = '0'
            strnext_state = str(next_state)
            temp =node[0]+1
            if next_state[index] == goal[index]:
                temp += temp-1
            if goal[index-dim] == '0':
                temp += temp-1
                
            if discovered.get(strnext_state) != None:
                config = discovered[strnext_state]
                if config[1] > temp:
                    pqueue[pqueue.index([config[1],next_state])][0] = temp
                    discovered[strnext_state] = ["right",temp,state]
            else:
                discovered[strnext_state] = ["right",temp,state]
                pqueue.append([temp,next_state])
            #prnt(next_state)
        if(index%dim < 2 ):
            next_state = list(state)
            next_state[index] = next_state[index+1]
            next_state[index+1] = '0'
            strnext_state = str(next_state)
            temp =node[0]+1
            if next_state[index] == goal[index]:
                temp += temp-1
            if goal[index-dim] == '0':
                temp += temp-1
                
            if discovered.get(strnext_state) != None:
                config = discovered[strnext_state]
                if config[1] > temp:
                    pqueue[pqueue.index([config[1],next_state])][0] = temp
                    discovered[strnext_state] = ["left",temp,state]
            else:
                discovered[strnext_state] = ["left",temp,state]
                pqueue.append([temp,next_state])
            #prnt(next_state,g)
        #print(pqueue)
        #print("==============================\n")
        node = heapq.heappop(pqueue)
        currentcost = node[0]
        state = node[1]
    config = discovered[str(state)]
    while config[1] != 0: 
        g.write(config[0])
        g.write("\n")
        config = discovered[str(config[2])]
    #update pqueue by adding the nodes
    #traverse next to the smallest hvalue 

    
#pqueue convention [state,action,h(n),parent]

#Initial Setup
filename = sys.argv
f = open(filename[1]+".txt","r")
g = open("output.txt","w")
contents = f.read()
array = contents.split("\n")
dimension = int(len(array)/2)

#Initializing the initial state and goal state
puzzle = []
goal = []
for i in range(dimension):
    puzzle = puzzle + array[i].split(" ")
    goal = goal + array[i+dimension].split(" ")
#Misplaced Tiles Heuristic
a_star(puzzle,goal,misplaced_tiles,dimension,g)

