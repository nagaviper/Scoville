#!/bin/sh
#
# description: Starts and stops the Hyperic Server.
#
# 
#

PROGRAM="Hyperic Server"

# WE ONLY START IT on vfabric1
BIND_VFABRIC=`/bin/hostname`
if [ "x$BIND_VFABRIC" = "xvfabric1" ];
then
	#proceed
	echo
else
	echo "Hyperic server will NOT start on this machine $BIND_VFABRIC"
	exit 0
fi

#
# Set the USER variable to the user to start as
#
USER=user

#
# Set HQ_DIR to your installation path.  For example:
# HQ_DIR=/usr/local/hq-4.5.1
#
HQ_DIR=/home/user/vfabric/hyperic-server/server-4.5.1-EE

# Set HQ_JAVA_HOME if you want to use a different JDK
# export HQ_JAVA_HOME=

checksetup() {
    if ! [ -d $HQ_DIR ]; then
        echo "Hyperic directory: $HQ_DIR not found."
	exit 1;
    fi
    if [ $USER = "" ]; then
        echo "$PROGRAM USER not set."
        return 1;
    fi
}

start() {
    echo -n "Starting $PROGRAM services: "
    su - $USER -c "$HQ_DIR/bin/hq-server.sh start"
    return $?
}	

stop() {
    echo -n "Shutting down $PROGRAM services: "
    su - $USER -c "$HQ_DIR/bin/hq-server.sh stop"
    return $?
}

restart() {
    stop
    start
}	

status() {
    echo -n "Status-ing $PROGRAM"
    su - $USER -c "$HQ_DIR/bin/hq-server.sh status"
    return $?
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
status)
    status
    ;;
*)
    echo $"Usage: $0 {start|stop|restart|status}"
    exit 1
esac
    
exit $?
