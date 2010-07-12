package amq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;


public class Listener extends Base implements MessageListener {

	private static int id;
	private static Cassandra.Client client;
	
	public Listener(Cassandra.Client clnt) {
	    client=clnt;
		id=0;
	}
	public Listener() {
	    
		id=0;
	}
	public static String getid()
	{
		String iden="Message" + id;
		id=id+1;
		return  iden;
	}
	public void onMessage(Message message) {
	try{

	Map<String, List<ColumnOrSuperColumn>> job = new HashMap<String, List<ColumnOrSuperColumn>>();
    List<ColumnOrSuperColumn> columns = new ArrayList<ColumnOrSuperColumn>();
	List<Column> column_list=new ArrayList<Column>();

	Enumeration en=message.getPropertyNames();
	System.out.println("Message");
	while(en.hasMoreElements())
	{
		String prop_name= (String)en.nextElement();
		Object obj = message.getObjectProperty(prop_name);
		String obj_val=obj.toString();
		System.out.println(prop_name + "        " + obj_val);
		
		long timestamp = System.currentTimeMillis();
		Column col = new Column(prop_name.getBytes(ENCODING), obj_val.getBytes(ENCODING), timestamp);
		column_list.add(col);
	}
		System.out.println();

	 SuperColumn column = new SuperColumn(getid().getBytes(ENCODING), column_list);
     ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
     columnOrSuperColumn.setSuper_column(column);
     columns.add(columnOrSuperColumn);
	 
     job.put(COLUMN_FAMILY_USERS, columns);
     client.batch_insert(KEYSPACE, "User1", job, ConsistencyLevel.ALL);
	 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}