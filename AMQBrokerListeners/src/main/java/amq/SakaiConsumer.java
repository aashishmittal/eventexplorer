package amq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SakaiConsumer {

    private static String brokerURL = "tcp://ashish:61618";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
	private static int id;
        
    public SakaiConsumer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		id=0;
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws JMSException {
    	SakaiConsumer consumer = new SakaiConsumer();
		
    		Destination destination = consumer.getSession().createQueue("SAMPLE_QUEUE");
    		MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
    		messageConsumer.setMessageListener(new Listener());
    	}
    
	
	public Session getSession() {
		return session;
	}


}
