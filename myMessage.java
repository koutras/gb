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

// look at the overrides where I have commented them
//akis: this must be placed in utils folder

//akis: opou exei type vazw to delta

package org.apache.giraph.examples.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * Data sent via a message that includes the source vertex id.
 */
public class myMessage implements Writable {
 

  /** Vertex ID of the sender. */
  private long  senderId;
  /** sigma. **/
  private long  sigma;
  private long delta;

  /** Default empty constructor. */
  public BrachaTouegDeadlockMessage() { /* no action */ }

  /**
   * @param id        id of the vertex
   * @param sigma      actual message content
   * @param delta
   * @param distance      distance
   
   */
  public myMessage(long id, long sigma, long delta,long distance) { //akis: this is the constructor I need
    this.senderId = id;
    this.sigma = sigma;
	this.delta = delta;
	this.distance=distance
  }

/*
  @Override
  public void readFields(DataInput input) throws IOException {
    senderId = input.readLong();
    this.value = input.readLong();
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeLong(senderId);
    output.writeLong(this.value);
  }
*/

  /**
   * @return long the id
   */
  public long getSenderId() { //akis: this is also the function id in order to get the parent of a node
    return senderId;
  }

  /**
   * @return long value of a node
   */
  public long getSigma() {
    return sigma;
  }

  public long getDelta(){
	return delta;
}
  public long getDistance(){
	return distance;
}

  @Override
  public String toString() { //den xerw an to xreiazomai gia thn wra
    StringBuffer buffer = new StringBuffer();

    buffer.append("Message ");
    buffer.append("{ sender: " +  this.senderId + "; sigma: ");
		
		
	buffer.append(Long.toString(sigma)); //den xerw an exw kanei kalh metatroph
	buffer.append("; delta: ")
	buffer.append(Long.toString(delta));
    buffer.append(" }");

    return buffer.toString();
  }
}
