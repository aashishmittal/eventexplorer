
This contains the instructions for setting up the Event Explorer project.

The SakaiModification contains a patch for modification of Sakai codebase for testing the project.
The AMQBrokerClasses contain classes for setting up Consumers for Sakai messages and inputting them to database.
The Cassandra folder contains a mockup of schema and modification to the config file.
The TimelineJSP contains the classes for retrieving the data and view for EventExplorer.

Procedure:

- First include the patch in the Sakai codebase and modify it to make it interacting with our project and rebuild it.
- Startup the AMQ broker on the host machine specified in the Sakai Activator.java.
- Run the Consumer classes as per instructions in the AMQBrokerClasses/README file.
- Modify the Cassandra installation according to Cassaandra/README file and run it.
- Run the JSP as per TimelineJSP/README file and view a Sakai Message in the JSP.