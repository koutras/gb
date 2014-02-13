#!/usr/bin/python

# input: file sourceId destId
# output json that ssp in giraph wants
# [vertexId, value, [ [destVertex1,edgeWeight]2,[destVertex2,]..]]

import sys
filename= sys.argv[1] or raw_input("give comma separated file: ")
valueOfNode=0
valueOfEdge=1
nodes={}
with open(filename) as input:
	for line in input:
		linedata=line.strip().split()
		middle=int(linedata[0])
		right=int(linedata[1])
		try:
			nodes[middle][2].append([right,valueOfEdge])
		except KeyError:
			nodes[middle]=[middle,valueOfNode,[]]
		try:
			nodes[right][2].append([middle,valueOfEdge])
		except KeyError:
			nodes[right]=[right,valueOfNode,[]]

with open(filename+'.json','w') as output:
	import json
	for key in nodes.keys():
		node=nodes[key]
		line=json.dumps(node)+'\n'
		output.write(line)
			  

