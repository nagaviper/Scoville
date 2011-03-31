#!/bin/bash

#
# $1 - data file
#
#

. ./libtest.sh

USERS_DATA_FILE=$1

LOG_FILE=$LOG_DIRECTORY/createusers-$$.log
UNIT_LOG_FILE=$LOG_DIRECTORY/createusers-$$-unit.log
ERROR_LOG_FILE=$LOG_DIRECTORY/createusers-$$-errors.log

if [ -e $LOG_FILE ] ; then
	rm -f $LOG_FILE
fi

if [ -e $ERROR_LOG_FILE ] ; then
	rm -f $ERROR_LOG_FILE
fi

NB_CREATED=0
NB_ERROR=0

while IFS=, read firstname lastname email password score ; do
	CreateUser $firstname $lastname $email $password $score > $UNIT_LOG_FILE
	if [ -n $DEBUG ] ; then
		cat $UNIT_LOG_FILE >> $LOG_FILE
	fi
	HTTP_CODE=$(cat $LOG_FILE | tail -1)
	if [ $HTTP_CODE -eq 201 ] ; then
		NB_CREATED=$(($NB_CREATED + 1))
	else
		NB_ERROR=$(($NB_ERROR + 1))
		cat $UNIT_LOG_FILE >> $ERROR_LOG_FILE	
	fi
done < $USERS_DATA_FILE

rm -f $UNIT_LOG_FILE

echo ""
echo " finished with $NB_ERROR error(s)"
echo " $NB_CREATED users had been created"
echo ""
