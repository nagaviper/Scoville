#!/bin/bash

. ./libtest.sh

#
# $1 - error code
# $2 - error message
#
ExitOnError() {
	echo ""
	echo " ERROR $1 - $2"
	echo ""	
	exit $1
}


email=$1
password=$2

cookie=$COOKIES_DIRECTORY/$(echo $email | sed -e "s/@/./")'.cookie'

if [ -e $cookie ] ; then
	rm -f $cookie
fi

LOG_FILE=$LOG_DIRECTORY/client-$(echo $email | sed -e "s/@/./")-$$.log

#if [ -e $LOG_FILE ] ; then
#	rm -f $LOG_FILE
#fi

echo "Login $email"

Login $cookie $email $password >> $LOG_FILE

HTTP_CODE=$(cat $LOG_FILE | tail -1)
echo $HTTP_CODE

if [ $HTTP_CODE -ne 201 ] ; then
	ExitOnError $HTTP_CODE "for login request"
fi

#for (( i=1 ; i < 6 ; i++ )) ; do
for i in 1 2 3 4 5 ; do
	
	echo "Sending request for question $i"
	GetQuestion $cookie $i >> $LOG_FILE
	HTTP_CODE=$(cat $LOG_FILE | tail -1)
	echo $HTTP_CODE

	if [ $HTTP_CODE -ne 200 ] ; then
		ExitOnError $HTTP_CODE "for question $i request"
	fi
	
	a=$(($[ $RANDOM % 4 ] + 1))
	echo "Sending answer $a for question $i"
	AnswerQuestion $cookie $i $a >> $LOG_FILE
	HTTP_CODE=$(cat $LOG_FILE | tail -1)
	echo $HTTP_CODE

	if [ $HTTP_CODE -ne 201 ] ; then
		ExitOnError $HTTP_CODE "for answer $i request"
	fi
	
done

echo "Waiting questiontimeframe+synchrotime before sending ranking request"
questiontimeframe=$(grep "questiontimeframe" $GAMEFILEPATH | tr -dc '[0-9]')
synchrotime=$(grep "synchrotime" $GAMEFILEPATH | tr -dc '[0-9]')
let sleeptime=$questiontimeframe+$synchrotime
sleep $sleeptime

echo "Sending ranking request"
AskRanking $cookie >> $LOG_FILE
HTTP_CODE=$(cat $LOG_FILE | tail -1)
echo $HTTP_CODE

if [ $HTTP_CODE -ne 200 ] ; then
	ExitOnError $HTTP_CODE "for ranking request"
fi

echo ""
echo " ### Finished. No error occurred ###"
echo ""
