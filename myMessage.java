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

//akis: this must be placed in utils folder
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
  /** delta. */
  private long  delta;

  /** Default empty constructor. */
  public BrachaTouegDeadlockMessage() { /* no action */ }

  /**
   * @param id        id of the vertex
   * @param type      actual message content
   */
  public myMessage(long id, long type) { //akis: this is the constructor I need
    this.senderId = id;
    this.type = type;
  }

  @Override
  public void readFields(DataInput input) throws IOException {
    senderId = input.readLong();
    this.delta = input.readLong();
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeLong(senderId);
    output.writeLong(this.type);
  }

  /**
   * @return long the id
   */
  public long getSenderId() { //akis: this is also the function id in order to get the parent of a node
    return senderId;
  }

  /**
   * @return long the type
   */
  public long getType() {
    return type;
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("Message ");
    buffer.append("{ sender: " +  this.senderId + "; type: ");
    if (this.type == BrachaTouegDeadlockMessage.NOTIFY) {
      buffer.append("notify");
    } else if (this.type == BrachaTouegDeadlockMessage.GRANT) {
      buffer.append("grant");
    } else if (this.type == BrachaTouegDeadlockMessage.ACK) {
      buffer.append("ack");
    } else if (this.type == BrachaTouegDeadlockMessage.DONE) {
      buffer.append("done");
    } else if (this.type == BrachaTouegDeadlockMessage.CTRL_IN_EDGE) {
      buffer.append("<ctrl>");
    } else {
      buffer.append("unknown");
    }
    buffer.append(" }");

    return buffer.toString();
  }
}
