#!/bin/bash

#
# $1 - authentication key
# $2 - XML file
#

. ./libtest.sh

LOG_FILE=$LOG_DIRECTORY/creategame-$$.log

if [ -e $LOG_FILE ] ; then
	rm -f $LOG_FILE
fi

CreateGame $1 $2 > $LOG_FILE

HTTP_CODE=$(cat $LOG_FILE | tail -1)

echo ""
if [ $HTTP_CODE -eq 201 ] ; then
	echo " Game has been succesfully created"	
else
	echo " Error $HTTP_CODE - see the log file $LOG_FILE"	
fi
echo ""

#exit $HTTP_CODE
