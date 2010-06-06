/*
 * This is a data retriever class for the Event viewer.
 * This connects to the local Cassandra instance and 
 * retrieves data from Cassandra and converts them into
 * JSON objects for Simile timeline usage
 */


package cass;


import java.util.List;
import org.json.*;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Base {

    protected static final String KEYSPACE = "SAKAI";
    protected static final String COLUMN_FAMILY_USERS = "Users";
    protected static final String ENCODING = "utf-8";
    protected static TTransport tr = null;
    
    public static JSONObject j;
    public static JSONArray ja;    
    public static JSONObject j1;
	
    public Base()
    {
	}
    
    protected static Cassandra.Client setupConnection() 			//Connection start-up block
    {
        try 
        {
            tr = new TSocket("localhost", 9160);
            TProtocol proto = new TBinaryProtocol(tr);
            Cassandra.Client client = new Cassandra.Client(proto);
            tr.open();
            return client;
        } 
        catch (TTransportException exception) 
        {
            exception.printStackTrace();
        }
        return null;
    }

    protected static void closeConnection()							//Connection terminator 
    {
        try 
        {
            tr.flush();
            tr.close();
        } 
        catch (TTransportException exception) 
        {
            exception.printStackTrace();
        }
    }

    protected static void print(String key, List<ColumnOrSuperColumn> result) 
    {
        try {
        	
        	/*Initialize the JSON objects so that for every new
        	 * query to Cassandra from the JSP, fresh objects are created
        	 * and new data is processed. 
        	 */
        	
        	j=new JSONObject();										  
        	ja=new JSONArray();
        	j1=new JSONObject();
        	long timestamp=0;
            System.out.println("Key: '" + key + "'");
            String desc="";
            
            for (ColumnOrSuperColumn c : result) 
            {
            	
            	/*
            	 * Traversing all the columns of the supercolumn
            	 * one-by-one. Each supercolumn represents an event
            	 * message and its sub-columns represents the 
            	 * properties of the message.
            	 */
            	
               	if (c.getColumn() != null) 
            	{
                    String name = new String(c.getColumn().getName(), ENCODING);
                    String value = new String(c.getColumn().getValue(), ENCODING);
                    timestamp = c.getColumn().getTimestamp();
                    System.out.println("  name: '" + name + "', value: '" + value + "', timestamp: " + timestamp);
                }
            	else if (c.getSuper_column() != null)
            	{
            	    SuperColumn superColumn = c.getSuper_column();
                    String supername=new String(superColumn.getName(), ENCODING);
                	j1.put("title", supername);
                    System.out.println("    Supercolumn: " + new String(superColumn.getName(), ENCODING));
                    for (Column column : superColumn.getColumns()) 
                    {
                    	
                        String name = new String(column.getName(), ENCODING);
                        String value = new String(column.getValue(), ENCODING);
                       	if(name=="timestamp")
                       	{
                       		System.out.println("Testing this if block");
                       	}
                       	else
                       	{           	
                       		desc=desc+""+name+" : "+value+"      ";
                       	}
                        timestamp = column.getTimestamp();
                        System.out.println("        name: '" + name + "', value: '" + value + "', timestamp: " + timestamp);
                    }
                    
                }
            }
            double div=1000*60*60*24*365d;
            double strt=(timestamp/div);
            int start=(int) (strt+1970);
            j1.put("description", desc);
        	j1.put("start", start);
            ja.put(j1);
            j.put("events", ja);
        	String out=j.toString();
            System.out.println(out);
         
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
