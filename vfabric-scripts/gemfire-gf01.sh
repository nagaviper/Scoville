#!/bin/sh

#############################################################################
# Customized for Capsaicine
#############################################################################


#
# ---------------------------------------------------------------------------
# chkconfig:
# description: 	Start up the GemFire server cache node

# Source function library.

# We bind it to the local address
BIND_IP=`/sbin/ifconfig |grep "192\.168\.1\." |cut -d: -f2|cut -d" " -f1`
BIND_VFABRIC=`/bin/hostname`
BIND_VFABRIC_C=`echo $BIND_VFABRIC | grep vfabric | wc -l`
if [ "x$BIND_VFABRIC_C" = "x1" ];
then
	#proceed
	echo
else
	echo "GemFire cache node will NOT start on this machine $BIND_VFABRIC"
	exit 0
fi



#The user account that will run the GemFire instance
GEM_USER=user

if [ "$GEM_USER" = "$USER" ] ; then
	SU_CMD=
else
	SU_CMD="/bin/su $GEM_USER"
fi



#The installation location for the binaries
GF_SERVER_HOME="/home/user/vfabric/gemfire"

#GemFire bin
GF_BIN="$GF_SERVER_HOME/bin/cacheserver"

#The name of the instance we want to stop/start
INSTANCE_NAME=gf01

INSTANCE_OPTS="-J-Xmx1024m -J-Xms1024m name=$BIND_VFABRIC-$INSTANCE_NAME bind-address=$BIND_IP -rebalance"

#JAVA_HOME must be visible
JAVA_HOME=/usr/lib/jvm/java-6-sun

# DO NOT EDIT BEYOND THIS LINE
RETVAL=$?
export JAVA_HOME

stop() {
    if [ -x "$GF_BIN" ];
    then
        echo "Stopping GemFire"
        if [ -z $SU_CMD ] ; then
        	$GF_BIN stop -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME
        else
        	$SU_CMD -c "$GF_BIN stop -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        fi
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}

status() {
    if [ -x "$GF_BIN" ];
    then
        echo "Status-ing GemFire"
        if [ -z $SU_CMD ] ; then
        	$GF_BIN status -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME
        else
        	$SU_CMD -c "$GF_BIN status -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        fi
        RETVAL=$?
    else
        echo "Startup script $GF_BIN doesn't exist or is not executable."
        RETVAL=255
    fi
}


start() {
    if [ -x "$GF_BIN" ];
    then
        echo "Starting GemFire"
        if [ -z $SU_CMD ] ; then
        	$GF_BIN start $INSTANCE_OPTS -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME
        else
	        $SU_CMD -c "$GF_BIN start $INSTANCE_OPTS -dir=$GF_SERVER_HOME/servers/$INSTANCE_NAME"
        fi
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


