
. ./libtest.sh


email=$1
password=$2

cookie=$COOKIES_DIRECTORY/$(echo $email | sed -e "s/@/./")'.cookie'

LOG_FILE=$LOG_DIRECTORY/client-$email-$$.log

if [ -e $LOG_FILE ] ; then
	rm -f $LOG_FILE
fi

Login $cookie $email $password >> $LOG_FILE

HTTP_CODE=$(cat $LOG_FILE | tail -1)

echo $HTTP_CODE

GetQuestion $cookie 1

#randomAnswer=$[ $RANDOM % 5 ]

#while true ; do
	
	
#done