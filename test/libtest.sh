#!/bin/bash

. ./configuration.sh

if [ -e $LOG_DIRECTORY ] ; then
	if [ -d $LOG_DIRECTORY ] ; then
		if $CLEAN_LOGS_ON_STARTUP ; then
			rm -f $LOG_DIRECTORY/*.log
		fi
	else
		echo ""
		echo " Error. $LOG_DIRECTORY is not a diectory"
		echo ""
	fi
else
	mkdir $LOG_DIRECTORY
fi


if [ -e $COOKIES_DIRECTORY ] ; then
	if [ ! -d $COOKIES_DIRECTORY ] ; then
		echo ""
		echo " Error. $COOKIES_DIRECTORY is not a diectory"
		echo ""
	fi
else
	mkdir $COOKIES_DIRECTORY
fi


#
# $1 - URL
# $2 - Cookie file
#
GET ()
{
    if [ "$1" != "" ] ; then
        OUTPUT="get_curl_out_$$"
        rm -f $OUTPUT
        COOKIE=''
        if [ -e $2 ] ; then
        	COOKIE='-b '$2
        elif [ "$2" != "nocookie" ] ; then
        	COOKIE='-c '$2
        fi
        CMD='curl '$COOKIE' -sL -w "%{http_code}\\n" -H "Accept: application/json" -X GET "'$1'" -o '$OUTPUT   	
    	echo $CMD > get_curl_$$
        if [ -n $DEBUG ] ; then
	        cat get_curl_$$
        fi
        HTTP_CODE=$(sh get_curl_$$ | head -1)        
        rm -f get_curl_$$
        if [ -e $OUTPUT -a -n $DEBUG ] ; then
		    cat $OUTPUT
		    rm -f $OUTPUT
	    fi
	    if [ -e $OUTPUT ] ; then
	        echo ""
            cat $OUTPUT
            echo ""
            echo ""
            rm -f OUTPUT
        fi
	    echo ''
	    echo $HTTP_CODE
    fi    
}

#
# $1 - URL
# $2 - Cookie file
# $3 - Request Body
#
POST ()
{	
    if [ "$1" != "" -a "$2" != "" ] ; then
        OUTPUT="post_curl_out_$$"
        rm -f $OUTPUT
        if [ "$3" != "" ] ; then
            DATA='-d "'$(echo $3 | sed -e 's/\"/\\\"/g')'"'
        else
            DATA=''
        fi
        COOKIE=''
        if [ -e $2 ] ; then
        	COOKIE='-b '$2
        elif [ "$2" != "nocookie" ] ; then
        	COOKIE='-c '$2
        fi
        CMD='curl '$COOKIE' -sL -w "%{http_code}\\n" -H "Accept: application/json" -H "Content-Type: application/json" -X POST '$1' '$DATA' -o '$OUTPUT
        echo $CMD > post_curl_$$
        if [ -n $DEBUG ] ; then
	        cat post_curl_$$
        fi
        HTTP_CODE=$(sh post_curl_$$ | head -1)        
        rm -f post_curl_$$
	    if [ -e $OUTPUT -a -n $DEBUG ] ; then
		    cat $OUTPUT
		    rm -f $OUTPUT
	    fi
	    echo ''
	    echo $HTTP_CODE
    fi
}


#
# $1 - firstname
# $2 - lastname
# $3 - email
# $4 - password
#
CreateUser () {
	POST $CAPSAICINE_URL_BASE/api/user "nocookie" "{ \"firstname\" : \"$1\", \"lastname\" : \"$2\", \"mail\" : \"$3\", \"password\" : \"$4\" }"
}


#
# $1 - authentication key
# $2 - XML file
#
CreateGame () {
	XML=$(cat $2 | sed -e "s/\"/\\\\\\\\\\\"/g")
	POST $CAPSAICINE_URL_BASE/api/game/ "nocookie" "{ \"authentication_key\" : \"$1\", \"parameters\" : \"$XML\" }"	
}


#
# $1 - cookie file
#
# $2 - email
# $3 - password
#
Login () {
	POST $CAPSAICINE_URL_BASE/api/login $1 "{ \"mail\" : \"$2\", \"password\" : \"$3\" }"
}


#
# $1 - cookie file
#
# $2 - question number
#
GetQuestion() {
	GET $CAPSAICINE_URL_BASE_LONGPOLLING/api/question/$2 $1
}


#
# $1 - cookie file
#
# $2 - question number
# $3 - answer number
#
AnswerQuestion() {
	POST $CAPSAICINE_URL_BASE/api/answer/$2 $1 "{ \"answer\" : $3 }	"
}


#
# $1 - cookie file
#
AskRanking() {
	GET $CAPSAICINE_URL_BASE/api/ranking $1
}
