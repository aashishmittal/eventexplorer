/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package amq;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.apache.cassandra.thrift.Cassandra;

import org.apache.activemq.command.ActiveMQMessage;

import javax.jms.Message;

/**
 *
 */
public class ListenerTest {

	@Mock
	private Cassandra.Client client;
    
	public ListenerTest() {
	
	MockitoAnnotations.initMocks(this);

  }

  @Test
  public void testListener() throws Exception {
		Message test_msg=new ActiveMQMessage();
		test_msg.setObjectProperty("id", "123");
		test_msg.setObjectProperty("description", "test message");
		test_msg.setObjectProperty("scope", "test class");

		Listener l=new Listener(client);
		l.onMessage(test_msg);
  }

}
