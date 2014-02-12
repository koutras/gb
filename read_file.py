import re #regular expression
import networkx as nx
#0 [1, 489, 897, 655, 710, 908, 280]

def read_graph_from_file():
	G
	with open("caveman-output") as input:
		for line in input:

			new_line = re.sub('[\[\],]','', line)
			linedata=new_line.strip().split()

	i=linedata[0]
	for j in range(1,len(linedata)):
		G.add_edge(i,j)
	
		
