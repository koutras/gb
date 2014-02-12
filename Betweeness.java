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


//import org.apache.giraph.examples.utils.myVertexValue; //akis: this is my import


/**
 * Demonstrates the basic Pregel shortest paths implementation.
 */
@Algorithm(
    name = "Single Source Betweeness (based on Shortest paths)",
    description = "Finds all shortest paths from a selected vertex and update some values like parents and sigma"
)
public class SimpleShortestPathsComputation extends BasicComputation<
    LongWritable,myVertexValue, FloatWritable, DoubleWritable> {  //akis: exchanged DoubleWritable for myVertexValue
  /** The shortest paths id */
  public static final LongConfOption SOURCE_ID =
      new LongConfOption("SimpleShortestPathsVertex.sourceId", 1,
          "The shortest paths id");
  /** Class logger */
  private static final Logger LOG =
      Logger.getLogger(SimpleShortestPathsComputation.class);

  /**
   * Is this vertex the source id?
   *
   * @param vertex Vertex
   * @return True if the source id
   */
  private boolean isSource(Vertex<LongWritable, ?, ?> vertex) {
    return vertex.getId().get() == SOURCE_ID.get(getConf());
  }
//orizei ton typo vertex pou tha xrhsimopoiei
//orizei kai ton iterator me ta minimata... kai ton tupo kathe minimatos
//akis:must exchange the middle value with mine, myVertexValue
  @Override
  public void compute( 
      Vertex<LongWritable, myVertexValue, FloatWritable> vertex, 
      Iterable<DoubleWritable> messages) throws IOException {
	
	Long nodes_num=34; //o arithmos twn komvwn prosarmosmeno gia to paradeigma karate
	double delta;
	double sigma;
	double minDist = isSource(vertex) ? 0d : Double.MAX_VALUE;
    
	
	if((getSuperstep()mod nodes_num==0){ //send message to my parents containing delta
		minDist=Double.MAX_VALUE; // einai san na to gyrizw se undiscovered ton node
		
		//node sends it's sigma to its parent
  		sendMessage(edge.getTargetVertexId(), new DoubleWritable(sigma));
        
	}
	if((getSuperstep()mod nodes_num==1){ //each node collects the message from it's kids and updates delta
		
		for(myMessage message:messages){
			kids_sigma=message.getValue();
			//do computations of delta
		}

  
	}
	if(getSuperstep()==nodes_num*nodes_num){
		vertex.voteToHalt();  
	}
	
	
    if (getSuperstep() == 0) {
      vertex.setValue(new DoubleWritable(Double.MAX_VALUE));
    }

    myParents = new ArrayList<Long>(); //kathe kombos exei lista me tous goneis tou (predessesor)

    for (DoubleWritable message : messages) {
      minDist = Math.min(minDist, message.get());
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Vertex " + vertex.getId() + " got minDist = " + minDist +
          " vertex value = " + vertex.getValue());
    }
    if (minDist < vertex.getValue().get()) {
      vertex.setValue(new DoubleWritable(minDist));
      for (Edge<LongWritable, FloatWritable> edge : vertex.getEdges()) {
        double distance = minDist + edge.getValue().get();
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
