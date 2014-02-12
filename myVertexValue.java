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

package org.apache.giraph.examples.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

/**
 * Vertex value used for Single Source Bfs betweeness calculation based on BrachTouegDeadlockValue must be placed in utils folder
 */
public class myVertexValue implements Writable {
	
 //akis
 private Long distance;
 private Long sigma;
 private Long delta;
 private ArrayList<LongWritable> myParents;



  public myVertex() {

	this.sigma=0;
	this.delta=0;
	this.distance=Double.MAX_VALUE;
	myParents = new ArrayList<LongWritable>(); //parents of a node (predessesor)
   
  }


	  // Accessors -------------------------------------------------------------



	 public Long getDistance(){
		return this.distance;
	}

	 public void setDistance(Long distance){
		this.distance=distance;
	}

	 public Long getSigma(){
		return this.sigma;
	}

	 public void setSigma(Long sigma){
		this.value=sigma;
	}
	public Long getDelta(){
		return this.delta;
	}
	
	public void setDelta(Long delta){
		this.delta=delta
	}
	
	public void addParent(LongWritable pid){
		myParents.add(pid);
	}
	
	public ArrayList<LongWritable> getParents)(){
		return myParents;
	}
	
	public void removeParents(){
		myParents.clear();
	}


	  @Override
	  public String toString() {
	    return "sigma:" + Long.toString(sigma) +"delta: " + Long.toString(delta);
	  }

	//akis: I cannot touch them yet
	
  // Serialization functions -----------------------------------------------
/*
  @Override
  public void readFields(DataInput input) throws IOException {
    int sz;

    this.isFree = input.readBoolean();
    this.isNotified = input.readBoolean();

    sz = input.readInt();
    for (int i = 0; i < sz; ++i) {
      ArrayList<Long> targets = new ArrayList<Long>();
      Long tag = input.readLong();
      int sw = input.readInt();

      for (int j = 0; j < sw; ++j) {
        Long target = input.readLong();

        targets.add(target);
      }

      this.requests.put(tag, targets);
    }

    sz = input.readInt();
    for (int i = 0; i < sz; ++i) {
      Long key = input.readLong();
      Long value = input.readLong();

      this.waitingList.put(key, value);
    }

    sz = input.readInt();
    for (int i = 0; i < sz; ++i) {
      this.parents.add(Long.valueOf(input.readLong()));
    }

    this.idWithInHoldAck  = input.readLong();
    this.idWithInHoldDone = input.readLong();
  }

  @Override
  public void write(DataOutput output) throws IOException {
    int sz;

    output.writeBoolean(this.isFree);
    output.writeBoolean(this.isNotified);

    sz = this.requests.size();
    output.writeInt(sz);
    for (Map.Entry<Long, ArrayList<Long>> entry : this.requests.entrySet()) {
      ArrayList<Long> targets;

      output.writeLong(entry.getKey());
      targets = entry.getValue();
      sz = targets.size();
      output.writeInt(sz);
      for (Long target : targets) {
        output.writeLong(target);
      }
    }

    sz = this.waitingList.size();
    output.writeInt(sz);
    for (Map.Entry<Long, Long> entry : this.waitingList.entrySet()) {
      output.writeLong(entry.getKey());
      output.writeLong(entry.getValue());
    }

    sz = this.parents.size();
    output.writeInt(sz);
    for (int i = 0; i < sz; ++i) {
      output.writeLong(this.parents.get(i));
    }

    output.writeLong(this.idWithInHoldAck);
    output.writeLong(this.idWithInHoldDone);
  }
*/

}
