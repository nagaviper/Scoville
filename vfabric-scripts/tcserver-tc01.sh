#!/bin/sh

#############################################################################
# Customized for Capsaicine
#############################################################################

#
# ---------------------------------------------------------------------------
# tc Runtime/Tomcat application server bootup script
#
# Copyright 2009 SpringSource Inc. All Rights Reserved.
# ---------------------------------------------------------------------------
# chkconfig:
# description: 	Start up the tc Runtime/Tomcat application server.
# author: fhanik
# version: 2.1.0.RELEASE
# build date: 20101104124840

# Source function library.

#The user account that will run the tcServer instance
TOMCAT_USER=user

if [ "$TOMCAT_USER" = "$USER" ] ; then
	SU_CMD=
else
	SU_CMD="/bin/su $TOMCAT_USER"
fi

#The installation location for the binaries
TC_SERVER_HOME="/home/user/vfabric/tcserver"

#INSTANCE_BASE - points to the base directory for your instances
INSTANCE_BASE=$TC_SERVER_HOME/servers

#The name of the instance we want to stop/start
INSTANCE_NAME=tc01
export INSTANCE_NAME

#JAVA_HOME must be visible
JAVA_HOME=/usr/lib/jvm/java-6-sun

# DO NOT EDIT BEYOND THIS LINE
RETVAL=$?
export INSTANCE_BASE
export JAVA_HOME

stop() {
    if [ -x "$TC_SERVER_HOME/tcruntime-ctl.sh" ];
    then
        echo "Stopping tcServer"
        $SU_CMD $TC_SERVER_HOME/tcruntime-ctl.sh $INSTANCE_NAME stop
        RETVAL=$?
    else
        echo "Startup script $TC_SERVER_HOME/tcruntime-ctl.sh doesn't exist or is not executable."
        RETVAL=255
    fi
}

status() {
    if [ -x "$TC_SERVER_HOME/tcruntime-ctl.sh" ];
    then
        echo "Status-ing tcServer"
        $SU_CMD $TC_SERVER_HOME/tcruntime-ctl.sh $INSTANCE_NAME status
        RETVAL=$?
    else
        echo "Startup script $TC_SERVER_HOME/tcruntime-ctl.sh doesn't exist or is not executable."
        RETVAL=255
    fi
}

printstatus() {
	if [ $(status | tail -1 | grep "NOT RUNNING" | wc -l) -eq 0 ] ; then
		echo "running"
	else
		echo "not running"
	fi
	RETVAL=0 	
}

start() {
    if [ -x "$TC_SERVER_HOME/tcruntime-ctl.sh" ];
    then
        echo "Starting tcServer"
        $SU_CMD $TC_SERVER_HOME/tcruntime-ctl.sh $INSTANCE_NAME start
        RETVAL=$?
    else
        echo "Startup script $TC_SERVER_HOME/tcruntime-ctl.sh doesn't exist or is not executable."
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
 printstatus)
        printstatus
        ;;
 *)
 	echo "Usage: $0 {start|stop|restart|status}"
	exit 1
	;;
esac

exit $RETVAL


