


CAPSAICINE_URL_BASE=http://localhost:8080/capsaicine


#
# $1 - URL
#
GET ()
{
    if [ "$1" != "" ] ; then
        OUTPUT="get_curl_out_$$"
        rm -f $OUTPUT
        curl -sL -w "%{http_code} %{url_effective}\\n" -H "Accept: application/json" -X GET "$1" -o $OUTPUT
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
        if [ "$2" != "" ] ; then
            DATA=' -d "'$(echo $2 | sed -e 's/\"/\\\"/g')'"'
        else
            DATA=''
        fi
        CMD='curl -sL -w "%{http_code} %{url_effective}\\n" -H "Accept: application/json" -H "Content-Type: application/json" -X POST '$1$DATA' -o '$OUTPUT
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
# $2 - base url
# $3 - start Id
#
TestCreateUsers()
{
    date
	URL=$2/api/user
	numero=$3
	limite=$(($1 + $3))
    while [ $numero -lt $limite ] ; do 
	    #echo $numero
	    POST $URL '{ "firstname" : "gui", "lastname" : "gia", "mail" : "gui@gia'$numero'", "password" : "pass" }' > create.log
	    numero=$(($numero + 1))
    done
	date
}


# User creation

POST $CAPSAICINE_URL_BASE/api/user '{ "firstname" : "gui", "lastname" : "gia", "mail" : "gui@gia", "password" : "pass" }'

# Game creation

POST $CAPSAICINE_URL_BASE/api/game/ '{ "authentication_key" : "key", "parameters" : "an XML String" }'

# User login

POST $CAPSAICINE_URL_BASE/api/login '{ "mail" : "gui@gia", "password" : "pass" }'

# Question request

GET $CAPSAICINE_URL_BASE/api/question/2

# Answer submission

POST $CAPSAICINE_URL_BASE/api/answer/2 '{ "answer" : 4 }'

# Ranking

GET $CAPSAICINE_URL_BASE/api/ranking 

# Ranking (Admin)

GET $CAPSAICINE_URL_BASE/api/score '{ "user_mail" : "gui@gia", "authentication_key" : "key" }'

# User answers (Admin)

GET $CAPSAICINE_URL_BASE/api/audit '{ "user_mail" : "gui@gia", "authentication_key" : "key" }'

# User answer (Admin)

GET $CAPSAICINE_URL_BASE/api/audit/4 '{ "user_mail" : "gui@gia", "authentication_key" : "key" }'




POST http://localhost:8080/capsaicine/api/user '{ "firstname" : "gui", "lastname" : "gia", "mail" : "gui@gia", "password" : "pass" }'
POST http://localhost:8081/capsaicine/api/user '{ "firstname" : "gui", "lastname" : "gia", "mail" : "gui@gia2", "password" : "pass" }'
GET http://localhost:8081/capsaicine/api/test/user/gui@gia
GET http://localhost:8081/capsaicine/api/test/user/gui@gia2
GET http://localhost:8080/capsaicine/api/test/user/gui@gia
GET http://localhost:8080/capsaicine/api/test/user/gui@gia2



