/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.giraph.examples;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

import java.io.IOException;


import org.apache.giraph.examples.utils.myVertexValue; //akis: this is my import
import org.apache.giraph.examples.utils.myMessage;



/**
 * Implementing Betweeness calculaton based on SimpleShortestPathsComputation 
 */
@Algorithm(
    name = "Single Source Betweeness (based on Shortest paths)",
    description = "Finds all shortest paths from a selected vertex and update some values like parents and sigma"
)
public class Betweeness extends BasicComputation<
    LongWritable,myVertexValue, FloatWritable, DoubleWritable> {  //akis: exchanged DoubleWritable for myVertexValue
 

 Long nodes_num=34; //numbers of nodes for the karate example


  public int getSourceId(){
	return getSuperstep()/nodes_num;
}

  /** Class logger */
  private static final Logger LOG =
      Logger.getLogger(SimpleShortestPathsComputation.class);


  private boolean isSource(Vertex<LongWritable, ?, ?> vertex) {
    return vertex.getId().get() == getSourceId(); 
  }
//orizei ton typo vertex pou tha xrhsimopoiei
//orizei kai ton iterator me ta minimata... kai ton tupo kathe minimatos
//akis:must exchange the middle value with mine, myVertexValue
  @Override
  public void compute( 
      Vertex<LongWritable, myVertexValue, FloatWritable> vertex, 
      Iterable<myMessage> messages) throws IOException {
	
	double delta;
	double sigma;
	ArrayList<LongWritable>myParents = vertex.getParents(); //kathe kombos exei lista me tous goneis tou (predessesor)
	double minDist = isSource(vertex) ? 0d : Double.MAX_VALUE;
	long vertexId = vertex.getId().get();
    
    
	
	if((getSuperstep()mod nodes_num==0){ //send message to my parents containing delta
		
		//node sends it's sigma to its parent
  		sendMessage(edge.getTargetVertexId(), new DoubleWritable(sigma));
        
	}
	if((getSuperstep()mod nodes_num==1){ //each node collects the message from it's kids and updates delta
		
		for(myMessage message:messages){
			kids_sigma=message.getValue();
			//do computations of delta
		}

  
		//clear vertex info
		vertex.clearParents();
		vertex.setDistance(Double.MAX_VALUE); // return a node to  undiscovered
		vertex.setValue(0);
		
	}
	if(getSuperstep()==nodes_num*nodes_num){
		vertex.voteToHalt();  
	}
	
	
    if (getSuperstep() == 0) {
      vertex.setDistance(0);
	vertex.setValue(0);
    }


    for (DoubleWritable message : messages) {
	
	 if(minDist>message.getDistance()){
      minDist = message.getDistance();
		vertex.addParent(message.getSenderId())
	}
	
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Vertex " + vertex.getId() + " got minDist = " + minDist +
          " vertex value = " + vertex.getValue());
    }
    if (minDist < vertex.getDistance()) {
	
      vertex.setDistance(minDist);
      for (Edge<LongWritable, FloatWritable> edge : vertex.getEdges()) {
        double distance = minDist + edge.getDistance();
        if (LOG.isDebugEnabled()) {
          LOG.debug("Vertex " + vertex.getId() + " sent to " +
              edge.getTargetVertexId() + " = " + distance);
        }
        sendMessage(edge.getTargetVertexId(), new DoubleWritable(distance));
      }
    }
  }
}




/*------------------------------------------------------------*/

	public class SimpleOutDegreeCountComputation extends BasicComputation<
	  LongWritable, LongWritable, DoubleWritable, DoubleWritable> {

	  @Override
	  public void compute(
	      Vertex<LongWritable, LongWritable, DoubleWritable> vertex,
	      Iterable<DoubleWritable> messages) throws IOException {
	    LongWritable vertexValue = vertex.getValue();
	    vertexValue.set(vertex.getNumEdges());
	    vertex.setValue(vertexValue);
	    vertex.voteToHalt();
	  }
	}
