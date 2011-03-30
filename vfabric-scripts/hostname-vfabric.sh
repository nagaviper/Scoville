#!/bin/sh

# 192.168.1.x --> vfabricX
BIND_IP=`/sbin/ifconfig |grep "192\.168\.1\." |cut -d: -f2|cut -d" " -f1`
IP4=`echo $BIND_IP | cut -d. -f4`
IP1=`echo $BIND_IP | cut -d. -f1`
OLDH=`/bin/hostname`
H="vfabric$IP4" 

case "$1" in
start)
    ;;
restart)
    ;;
*)
    exit 0
esac

echo "Checking vFabric hostname: $H IP $BIND_IP - was $OLDH"
if [ "x$IP1" = "x192" ]
then
	# looks correct
	echo "Setting hostname to $H"
	hostname $H
	echo $H > /etc/hostname
	#sed -i s/^127.0.0.1.*vfabric[0-9]*.*/127.0.0.1\\t$H.localdomain\\t$H/ /etc/hosts
	if [ "x$H" = "x$OLDH" ]
	then
		echo "hostname unchanged"
	else	
		echo "hostname changed from $OLDH to $H - doing some cleanup"
		# clean up Hyperic agent data
		rm /home/user/vfabric/hyperic/data/*
		# todo - clean rabbit, logs etc on hostname change?	
	fi
fi

