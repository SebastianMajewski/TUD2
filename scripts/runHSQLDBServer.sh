#!/bin/bash

#java -cp ./scripts/hsqldb-2.2.4.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb
java -cp /opt/devel/hsqldb-2.3.0/hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb

