#!/bin/sh
#
# ---------------------------------------------------------------------------
# chkconfig:
# description: 	Start up the GemFire locator

# Source function library.

# WE ONLY START IT on vfabric1 and vfabric2
# and bind it to the local address
BIND_IP=`/sbin/ifconfig |grep "192\.168\.1\." |cut -d: -f2|cut -d" " -f1`
BIND_VFABRIC=`/bin/hostname`
if [ "x$BIND_VFABRIC" = "xvfabric1" ];
then
	#proceed
	echo
elif [ "x$BIND_VFABRIC" = "xvfabric2" ];
then
	#proceed
	echo
else
	echo "GemFire locator will NOT start on this machine $BIND_VFABRIC"
	exit 0
fi



#The user account that will run the tcServer instance
USER=user

#The installation location for the binaries
GF_SERVER_HOME="/home/user/vfabric/gemfire"

#GemFire bin
GF_BIN="$GF_SERVER_HOME/bin/gemfire"

#The name of the instance we want to stop/start
INSTANCE_NAME=gflocator

INSTANCE_OPTS="-address=$BIND_IP"

#JAVA_HOME must be visible
JAVA_HOME=/usr/lib/jvm/java-6-sun

# DO NOT EDIT BEYOND THIS LINE
RETVAL=$?
export JAVA_HOME

stop() {
    if [ -x "$GF_BIN" ];
    then
        echo "Stopping GemFire locator"
        /bin/su - $USER -c "$GF_BIN stop-locator -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME $INSTANCE_OPTS"
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}

status() {
    if [ -x "$GF_BIN" ];
    then
        echo "Status-ing GemFire locator"
        /bin/su - $USER -c "$GF_BIN info-locator -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        /bin/su - $USER -c "$GF_BIN status-locator -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}


start() {
    if [ -x "$GF_BIN" ];
    then
        echo "Starting GemFire locator"
        /bin/su - $USER -c "$GF_BIN start-locator -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME $INSTANCE_OPTS"
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}


case "$1" in
 start)
        start
	;;
 stop)
        stop
 	;;
 restart)
        stop
        start
        ;;
 status)
        status
        ;;
 *)
 	echo $"Usage: $0 {start|stop|restart|status}"
	exit 1
	;;
esac

exit $RETVAL


