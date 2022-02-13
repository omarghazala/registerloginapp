# registerloginapp
<h2>My registerloginapp Application</h2>

- This is a backend Java API register and login with the help of web tokens

<h2>Technologies</h2>

- JDK 11
- MySQL

<h2>Post End points<h2>

- http://localhost:8080/api/user/signup 


** Use a JSON body (raw) for it sample : 
{
        "fullName": "omar ghazala2",
        "email": "omarghazala@gmail.com",
        "password": "1234",
        "phoneNumber": 11111,
        "dateOfBirth": "11/12/1994",
        "roles": [
            {
                "id": 1
            }
        ]
    }
    
- http://localhost:8080/api/user/login


** Use a JSON body (x-www-form-undencoded) for it sample : 

- key -> value
- email -> omarghazala585@gmail.com
- password -> 1234

<h2>Get End points<h2>

- http://localhost:8080/api/users  (bonus for testing purposes )
- http://localhost:8080/api/user/logout
- http://localhost:8080/api/token/refresh (bonus was not in the task ) 

** Use a JSON header  for it sample : 

- key -> value
- Authorization -> Bearer eyJ0eXAiOiJKV1QiLCJhGciOiJIUzI1NiJ9.eyJzdWIiOiJvbWFyZ2hhemFsYUBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS91c2VyL3NpZ251cCIsImV4cCI6MTY0NDcyODQ3NH0.erG3K0YQOp72HY9R-YvkreMq53n4JM7MMiB6xZ3zgGs





