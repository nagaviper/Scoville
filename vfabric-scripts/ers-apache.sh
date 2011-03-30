#!/bin/sh
#
#

PROGRAM="ERS Apache httpd"

#
# Set the USER variable to the user to start as
#
USER=root

#
# Set SERVER_DIR to your agent installation path.  For example:
#
SERVER_DIR=/home/user/vfabric/ers/servers/apache-2.2


checksetup() {
    if ! [ -d $SERVER_DIR ]; then
        echo "ERS instance directory: $SERVER_DIR not found."
	exit 1;
    fi
    if [ $USER = "" ]; then
        echo "$PROGRAM USER not set."
        return 1;
    fi
}

start() {
    echo -n "Starting $PROGRAM services: "
    su - $USER -c "$SERVER_DIR/bin/apache_startup.sh start > /dev/null 2>&1"
    return $?
}	

stop() {
    echo -n "Shutting down $PROGRAM services: "
    su - $USER -c "$SERVER_DIR/bin/apache_startup.sh stop > /dev/null 2>&1"
    return $?
}

restart() {
    stop
    start
}	


checksetup
RET=$?
if [ $RET -ne 0 ]; then
    exit $RET
fi

case "$1" in
start)
    start
    ;;
stop)
    stop
    ;;
restart)
    restart
    ;;
*)
    echo $"Usage: $0 {start|stop|restart}"
    exit 1
esac
    
exit $?
