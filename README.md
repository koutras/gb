Implement Brandes way of calculating betweenness

Todo:
	0)Wrapper for the sendMessage GetMessage
 	1)enable Logger Debug Output
	2)Find a way to pass a external global Variable to Giraph(number of nodes) 
		org.apache.hadoop.conf.Configuration
		org.apache.giraph.conf.LongConfOption;
	3) As I have bypassed vertex Value, there must be a way to fix the way output is printed after
		the computation, for example override giraph.io.VertexWriter
	4) In order to permit a vertex to have at least two values, I looked at the example of Bracha
		where they play with org.apache.hadoop.io.Writable; (I have not yet have the proper background to mess with that)
		

idea -> change BrachaTouegDeadlockVertexValue
---------------------------------------------------------
1)override value that a node saves by default a number
with a class that supports storing extra parameters,
for example, predecessor

2) treximo gia osa superstep osa einai to plithos to komvwn
etsi wste na exasfalisoume oti exei ginei h bfs anazhthsh
	
	number_of_phases= nodes_num
	
	for number_of_phases
		for super_steps in range(0, nodes_num) //in essence perform bfs, so there must be at least nodes_num supersteps
												//in order to update the distance of each node
			do the shortest path compute and update distance and sigma
		end
		source_id=next_source_id (I assume that the nodes have ids in range [0,nodes_num] )
	end

3) add value isDiscovered

---utils BrachaTouegMessage
Converting Bracha


3)vote to halt after 34*34 supersteps have been perfomed

SimpleShortestPathsComputation extend BasicComputation
---------------------------------------------------------------

<vertex id, vertex value, edge value>


----giraph's main vertex
org.apache.giraph.graph.Vertex


some probably useful files to see from giraph code
---------------------------------------------------------------------
Max Computation
Simple Out Degree Computation
simplePageRank Computation



