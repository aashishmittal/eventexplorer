
This is the modifications to the default Cassandra installation to modify it for the Sakai EventExplorer project.

Add the following to the storage-conf.xml file under CASSANDRA_HOME/conf dir under the Keyspaces tag.

 <Keyspace Name="SAKAI">
    <ColumnFamily ColumnType="Super" CompareWith="UTF8Type" Name="Users" /> 
 </Keyspace>

 