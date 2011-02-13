
#
# $1 - URL
#
GET ()
{
    if [ "$1" != "" ] ; then
        OUTPUT="get_curl_out_$$"
        rm -f $OUTPUT
        curl -sL -w "%{http_code} %{url_effective}\\n" "$1" -o $OUTPUT
        if [ -e $OUTPUT ] ; then
	        echo ""
            cat $OUTPUT
            echo ""
            echo ""
            rm -f OUTPUT
        fi
    fi    
}


#
# $1 - URL
# $2 - Request Body
#
POST ()
{
    if [ "$1" != "" ] ; then
        OUTPUT="post_curl_out_$$"
        rm -f $OUTPUT
        CMD='curl -sL -w "%{http_code} %{url_effective}\\n" "'$1'" -d "'$(echo $2 | sed -e 's/\"/\\\"/g')'" -o '$OUTPUT
        echo $CMD > post_curl_$$
        sh post_curl_$$
        rm -f post_curl_$$
        if [ -e $OUTPUT ] ; then
            echo ""
            cat $OUTPUT
            echo ""
            echo ""
            rm -f $OUTPUT
        fi
    fi
}

#
# $1 - nb users to create
#
CreateUsers()
{
    date
	numero=0
	limite=$1
    while test $numero < $limite ; do 
	    #echo $numero
	    POST http://localhost:8080/TestREST/api/user/ '{ "firstname" : "gui", "lastname" : "gia", "mail" : "gui@gia'$numero'", "password" : "pass" }' > create.log
	    numero=$(($numero + 1))
    done
	date
}


# Game creation

#POST http://localhost:8080/TestREST/api/game/ ""


# Question request

#GET http://localhost:8080/TestREST/api/question/1 ""
#GET http://localhost:8080/TestREST/api/question/2 ""
#GET http://localhost:8080/TestREST/api/question/3 ""
#GET http://localhost:8080/TestREST/api/question/4 ""


