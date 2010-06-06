
/*
 * This is the usable class for retrieving data
 * from Cassandra and providing it to JSP.
 */

package cass;

import java.util.ArrayList;
import java.util.List;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.SlicePredicate;
import org.json.*;



public class User extends Base 
{
	public static JSONObject jUser=new JSONObject();
    public User() 
    {
        Cassandra.Client client = setupConnection();
        System.out.println("Selecting the event message 'Message1' from Cassandra.\n");
        selectSingleMessage(client);
        jUser=j;												//Used in the JSP
        closeConnection();

    }
    private static void selectSingleMessage(Cassandra.Client client) {
        try {
            KeyRange keyRange = new KeyRange(1);
            keyRange.setStart_key("User1");
            keyRange.setEnd_key("");

            List<byte[]> columns = new ArrayList<byte[]>();
            columns.add("Message1".getBytes(ENCODING));

            SlicePredicate slicePredicate = new SlicePredicate();
            slicePredicate.setColumn_names(columns);

            ColumnParent columnParent = new ColumnParent(COLUMN_FAMILY_USERS);
            List<KeySlice> keySlices = client.get_range_slices(KEYSPACE, columnParent, slicePredicate, keyRange, ConsistencyLevel.ONE);

            for (KeySlice keySlice : keySlices) {
                print(keySlice.getKey(), keySlice.getColumns());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
