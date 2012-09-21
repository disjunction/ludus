#!/bin/sh
TARGET=/var/lib/tomcat6/webapps/ttt/WEB-INF/classes/
SOURCE=/home/or/workspace/

/bin/cp -r ${SOURCE}/Metaword/bin/* ${TARGET}
/bin/cp -r ${SOURCE}/MetawordServer/bin/* ${TARGET}
/bin/cp -r ${SOURCE}/Mire/bin/* ${TARGET}

/etc/init.d/tomcat6 restart
