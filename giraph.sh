#!/bin/bash



#hadoop jar /directory-to-giraph/giraph-core/target/giraph-0.2-SNAPSHOT-for-hadoop-0.20.203.0-jar-with-dependencies.jar org.apache.giraph.GiraphRunner org.apache.giraph.examples.SimpleShortestPathsVertex -vif org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat -vip /in/input-json -of org.apache.giraph.io.formats.IdWithValueTextOutputFormat -op /outShortest -w 1

#configuring the above example for my system

JAR_FILE="/Users/akis/giraph/giraph-core/target/giraph-1.0.0-for-hadoop-0.20.203.0-jar-with-dependencies.jar "
GIRAPH_RUNNER="org.apache.giraph.GiraphRunner"
MAIN_CODE="org.apache.giraph.examples.SimpleShortestPathsVertex"

INPUT_FILE="/in/input.json"
OUTPUT_FILE="/outShortest"

WORKERS_NUM="1"

HANDLER="org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat \
	 -vip $INPUT_FILE -of org.apache.giraph.io.formats.IdWithValueTextOuputFormat -op $OUTPUT_FILE -w $WORKERS_NUM"

COMMAND="$JAR_FILE $GIRAPH_RUNNER $MAIN_CODE -vif $HANDLER"
function runner {
	hadoop jar $JAR_FILE $GIRAPH_RUNNER -la #-h 
#	echo hadoop jar /Users/akis/giraph/giraph-examples/target/giraph-examples-1.0.0-for-hadoop-0.20.203.0-jar-with-dependencies.jar org.apache.giraph.GiraphRunner -h
 }
function run_ssp {
	  echo hadoop jar $COMMAND
	  hadoop jar $COMMAND
}
function hadoop_mkdir {
	echo hadoop fs -mkdir $1 
	 hadoop fs -mkdir $1 
}
function hadoop_put {
	 echo hadoop fs -put $1 $2
	 hadoop fs -put $1 $2
}






#sp
#runner


#hadoop jar $JAR_FILE $GIRAPH -h
#echo $COMMAND
