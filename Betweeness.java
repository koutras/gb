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
//package Betweeness;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;


import org.apache.giraph.examples.utils.myVertexValue; //akis: this is my import
import org.apache.giraph.examples.utils.myMessage;


@Algorithm(
    name = "Betweeness "
)
public class Betweeness
 extends BasicComputation<LongWritable,myVertexValue, FloatWritable, myMessage> {  //akis: exchanged DoubleWritable for myVertexValue
 

 long nodes_num=34l; //numbers of nodes for the karate example


  public int getSourceId(){
	return (int)(getSuperstep()/nodes_num);
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
	
	double kids_sigma;
	double kids_delta;
	double delta = vertex.getValue().getDelta();
	double sigma = vertex.getValue().getSigma();
	ArrayList<LongWritable>myParents = vertex.getValue().getParents(); //kathe kombos exei lista me tous goneis tou (predessesor)
	
	long minDist = isSource(vertex) ? 0l : Long.MAX_VALUE;
	long vertexId = vertex.getId().get();
    
    
	
	if(getSuperstep()%nodes_num==0){ //send message to my parents containing delta
		
		//node sends it's sigma to its parents
		for(int i=0;i<myParents.size();i++)
			sendMessage(vertexId, myParents.get(i), sigma, delta, minDist); 
		
	}
	if(getSuperstep()%nodes_num==1){ //each node collects the message from it's kids and updates delta
		
		for(myMessage message:messages){ //hopefully in this step the messages are from a node's kids
		 	
			kids_sigma=message.getSigma();
			kids_delta=message.getDelta();
			//do computations of delta
			delta+=(sigma/kids_sigma)*(1+kids_delta);
			vertex.getValue().setDelta(delta);
		}

  
		//clear vertex info
		vertex.getValue().removeParents();
		vertex.getValue().setDistance(Long.MAX_VALUE); // return a node to  undiscovered
		vertex.getValue().setSigma(0d);
		vertex.getValue().setDelta(0d);
		
	}
	if(getSuperstep()==nodes_num*nodes_num){
		if (LOG.isDebugEnabled()) {
	      LOG.debug("Vertex " + vertex.getId() + " has betweenness = " + vertex.getValue().getDelta());
	    }
		vertex.voteToHalt();  
	}
	
	
    if (getSuperstep() == 0) {
      vertex.getValue().setDistance(0l);
	  vertex.getValue().setDelta(0d);
	  vertex.getValue().setSigma(0d);
	  delta=0d;
    }

    for (myMessage message : messages) {
	
	 if(minDist>message.getDistance()){ //minDist here is the minimum value amongst the messages
       		minDist = message.getDistance();
		vertex.getValue().setSigma(sigma + message.getSigma());
		vertex.getValue().addParent(new LongWritable(message.getSenderId())); //adding the parent
	}
	
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Vertex " + vertex.getId() + " got minDist = " + minDist +
          " vertex sigma = " + vertex.getValue().getSigma());
    }
    if (minDist < vertex.getValue().getDistance()) { //if the minimum value from the messages is smaller that the vertex's...
	
      vertex.getValue().setDistance(minDist);
      for (Edge<LongWritable, FloatWritable> edge : vertex.getEdges()) {
        double distance = minDist + edge.getValue().get();
        if (LOG.isDebugEnabled()) {
          LOG.debug("Vertex " + vertex.getId() + " sent to " +
              edge.getTargetVertexId() + " = " + distance);
        }
        sendMessage(vertexId,edge.getTargetVertexId(),sigma, delta, minDist);		
      }
    }
  }
	
	
	//send message wrapper from bracha
	
	private void sendMessage(long sender, LongWritable receiver,double sigma, double delta, long distance) {

    myMessage message = new myMessage(sender, sigma, delta, distance);
    sendMessage(receiver, message);
    if (LOG.isDebugEnabled()) {
      LOG.debug("sent message " + message + " from " + sender +
                " to " + receiver);
    }
  }
}



