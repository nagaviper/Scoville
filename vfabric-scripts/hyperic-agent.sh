#!/bin/sh
#
# description: Starts and stops the Hyperic Agent.  The agent
# must be started once by hand to complete the setup process.
#
# 
#

PROGRAM="Hyperic Agent"

#
# Set the USER variable to the user to start the agent as
#
USER=user

#
# Set AGENT_DIR to your agent installation path.  For example:
# AGENT_DIR=/usr/local/agent-2.7.2
#
AGENT_DIR=/home/user/vfabric/hyperic

# Set HQ_JAVA_HOME if you want to use a different JDK
# export HQ_JAVA_HOME=/opt/bea/jrockit81sp1_141_03

checksetup() {
    if ! [ -d $AGENT_DIR ]; then
        echo "Agent directory: $AGENT_DIR not found."
	exit 1;
    fi
    # SKIP BELOW due to pre configured agent 
    #if ! [ -e $AGENT_DIR/data/tokendata ]; then
    #    echo "$PROGRAM must be setup.  Run start command by hand"
    #    return 1;
    #fi
    if [ $USER = "" ]; then
        echo "$PROGRAM USER not set."
        return 1;
    fi
}

start() {
    echo -n "Starting $PROGRAM services: "
    su - $USER -c "$AGENT_DIR/bin/hq-agent.sh start > /dev/null 2>&1 &"
    return $?
}	

stop() {
    echo -n "Shutting down $PROGRAM services: "
    su - $USER -c "$AGENT_DIR/bin/hq-agent.sh stop > /dev/null 2>&1"
    return $?
}

restart() {
    stop
    start
}	

ping() {
    echo -n "Pinging $PROGRAM"
    su - $USER -c "$AGENT_DIR/bin/hq-agent.sh ping > /dev/null 2>&1"
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
ping)
    ping
    ;;
*)
    echo $"Usage: $0 {start|stop|restart|ping}"
    exit 1
esac
    
exit $?
