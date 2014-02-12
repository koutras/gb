Implement Brandes way of calculating betweenness




The main algorithm is based on SimpleShortestPathsComputation  for the main algorithm (brande's algorithm) but I needed to store extra values in each node, and as an effect override
the message also.

The trick I've thought about for the algorithm to work is provide the input of nodes in ascending
value, starting from 1 to number of nodes, and as such, to complete a bfs each time I have to
wait for number of nodes supersteps each time. This can easily be done by 
 mod(superstep,nodes_num).

After the end of that phase I do some calculations in each node, and I clear the distance value,
in order to continue running the SimpleShortestPaths algorithm but with the next vertex
as a source in ascending order. 

 I was also based
on BrachaTouegDeadlockComputation for overriding message and vertex value,


(message is based on BrachaTouegDeadlockMessage and vertex value on  BrachaTouegDeadlockVertexValue located in utils 
on respect.)

The things I had found difficulty so far is not the algorithm by itself that is the brande's algorithm,
but integrating the following things.

 1) overriding the value of vertex with mine using myVertex.java
       Overriding the Serialization functions for my implementation according to
       BrachaTouegDeadlockVertexValue.

2) overriding the message that a node is passing to another with myMessage.java 
     I've added the accessors that I wanted but I dont now what to do with readFields, and write,
     that the BrachaTouegDeadlockMessage has, for my implementation


Any help on the above matters is very appreciated. Thank you all, and sorry for the long post
So this CAN be done! If this algorithm is effective is another topic of conversation.
Thanks for your time, and once again sorry for the long post
where everyone with an interest can browse and provide insight, as I have been developing
for not very long on giraph and I have not the right experience yet.

Todo:
	-1) Look at the overrides in my Message
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



