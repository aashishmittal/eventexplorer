package amq;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
    static long timestamp;
    protected static TTransport tr = null;
    
    /**
     * Open up a new connection to the Cassandra Database.
     * 
     * @return the Cassandra Client
     */
    protected static Cassandra.Client setupConnection() {
        try {
            tr = new TSocket("localhost", 9160);
            TProtocol proto = new TBinaryProtocol(tr);
            Cassandra.Client client = new Cassandra.Client(proto);
            tr.open();

            return client;
        } catch (TTransportException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Close the connection to the Cassandra Database.
     */
    protected static void closeConnection() {
        try {
            tr.flush();
            tr.close();
        } catch (TTransportException exception) {
            exception.printStackTrace();
        }
    }

}
