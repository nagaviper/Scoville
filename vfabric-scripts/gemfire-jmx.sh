#!/bin/sh
#
# ---------------------------------------------------------------------------
# chkconfig:
# description: 	Start up the GemFire JMX agent

# Source function library.

# We start it only on vfabric1
# and bind it to the local address
BIND_IP=`/sbin/ifconfig |grep "192\.168\.1\." |cut -d: -f2|cut -d" " -f1`
BIND_VFABRIC=`/bin/hostname`
if [ "x$BIND_VFABRIC" = "xvfabric1" ];
then
	#proceed
	echo
else
	echo "GemFire cache node will NOT start on this machine $BIND_VFABRIC"
	exit 0
fi



#The user account that will run the GemFire instance
USER=user

#The installation location for the binaries
GF_SERVER_HOME="/home/user/vfabric/gemfire"

#GemFire bin
GF_BIN="$GF_SERVER_HOME/bin/agent"

#The name of the instance we want to stop/start
INSTANCE_NAME=jmx

# see also agent.properties in instance folder
INSTANCE_OPTS="rmi-bind-address=$BIND_IP locators=vfabric1:10334,vfabric2:10334 log-file=$GF_SERVER_HOME/servers/$INSTANCE_NAME/agent.log"

#JAVA_HOME must be visible
JAVA_HOME=/usr/lib/jvm/java-6-sun

# DO NOT EDIT BEYOND THIS LINE
RETVAL=$?
export JAVA_HOME

stop() {
    if [ -x "$GF_BIN" ];
    then
        echo "Stopping GemFire JMX"
        /bin/su - $USER -c "$GF_BIN stop -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}

status() {
    if [ -x "$GF_BIN" ];
    then
        echo "Status-ing GemFire JMX"
        /bin/su - $USER -c "$GF_BIN status -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}


start() {
    if [ -x "$GF_BIN" ];
    then
        echo "Starting GemFire JMX"
        /bin/su - $USER -c "$GF_BIN start -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME $INSTANCE_OPTS"
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
	sleep 3
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


