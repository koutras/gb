Implement Brandes way of calculating betweenness

Todo: 1)enable Logger Debug Output
	2)Find a way to pass a external global Variable to Giraph(number of nodes)
	3) As I have bypassed vertex Value, there must be a way to fix the way output is printed after
		the computation, for example override giraph.io.VertexWriter

idea -> change BrachaTouegDeadlockVertexValue
---------------------------------------------------------
1)override value that a node saves by default a number
with a class that supports storing extra parameters,
for example, predecessor

2) treximo gia osa superstep osa einai to plithos to komvwn
etsi wste na exasfalisoume oti exei ginei h bfs anazhthsh

3) add value isDiscovered

---utils BrachaTouegMessage
Converting Bracha


3)vote to halt after 34*34 supersteps have been perfomed

SimpleShortestPathsComputation extend BasicComputation
---------------------------------------------------------------
seira orismatwn
<vertex id, vertex value, edge value>


----giraph's main vertex
org.apache.giraph.graph.Vertex


some probably useful files to see from giraph code
---------------------------------------------------------------------
Max Computation
Simple Out Degree Computation
simplePageRank Computation



