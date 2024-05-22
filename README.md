 ## Endpoints

| HTTP method | REST Resource                     |                                                                                                                                                                                                                                                             | Comment                                                                                |
| ----------- | --------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| POST        | `/api/auth/login`                 | `request:` <br><pre lang="json">{&#13; "email":"email",&#13; "password":"password",&#13; "name":"name",&#13;"phonenumber":0,&#13; }</pre>                                                                                                                   | Login(Token in response) (user access)                                                 |
| POST        | `/api/auth/logout`                | `request:` none                                                                                                                                                                                                                                             | Logout  (user access)                                                                  |
| POST        | `/api/auth/register`              | `request:` <br><pre lang="json">{&#13;"email":"email",&#13; "password":"password",&#13; }</pre>                                                                                                                                                             | Register new user (user access)                                                        |
| POST        | `/api/auth/addRoleToUser`         | `request:` <br><pre lang="json">{&#13;"email":"email",&#13;"role":"role",&#13; }</pre>                                                                                                                                                                      | Add a role to a user                                                                   |
| GET         | `/api/user/notes`                 | `response:` <br><pre lang="json">[{&#13;"title":"title",&#13;"id":0,&#13;"content":"blahblahblah blah",&#13;"lastEditDate":"12-12-2012",&#13;"date":"02-02-2002",&#13;"userID":["mail@mail.com"],&#13;}]</pre>                                              | Get all notes belonging to the user that belongs to authentication token (user access) |
| POST        | `/api/user/notes/createNote`      | `request:` <br><pre lang="json">{&#13;"title":"title",&#13;"content":"blahblahblah blah",&#13;"lastEditDate":"12-12-2012",&#13;"date":"02-02-2002",&#13;}</pre>                                                                                             | Create a new note and adds current logged in user as userID (user access)              |
| PUT         | `/api/user/note/update/{noteId}` | `response:` <br><pre lang="json">{&#13;"title":"title",&#13;"content":"blahblahblah blah",&#13;"lastEditDate":"12-12-2012",&#13;"date":"02-02-2002",&#13;}</
| DELETE         | `/api/user/note/delete/{noteId}` | `response:` <br><pre lang="json">{&#13;"title":"title",&#13;"content":"blahblahblah blah",&#13;"lastEditDate":"12-12-2012",&#13;"date":"02-02-2002",&#13;}</pre>                                                                                             | delete note belonging to logged in user (user access)                                    
| GET | `/api/user/note/search/{title}` | `response:` <br><pre lang="json">[{ALL NOTES MATCHING SOME OR ALL OF TITLE}]</pre> | Retrieve all notes matching some or all of title |
| GET         | `/api/user/note/search/{noteID}`                  | `response:` <br><pre lang="json">{&#13;"title":"title",&#13;"id":0,&#13;"content":"blahblahblah blah",&#13;"lastEditDate":"12-12-2012",&#13;"date":"02-02-2002",&#13;"userID":["mail@mail.com"],&#13;}</pre>                                                | Get *a* note belonging to the user that belongs to authentication token (user access)  |x$x 
| GET         | `/api/user/note/sort/title`       | `response:` <br><pre lang="json">[{SORTED NOTES BY TITLE}]</pre>                                                                                                                                                                                            | sort by title alphabeticly                                                             |
| GET         | `/api/user/note/sort/date`        | `response:` <br><pre lang="json">[{SORTED NOTES BY DATE}]</pre>                                                                                                                                                                                             | Retreive sorted notes by date (user access)                                            |
| GET         | `/api/user/note/sort/category`    | `response:` <br><pre lang="json">[{SORTED NOTES BY CATEGORY}]</pre>                                                                                                                                                                                         | Retreive sorted notes by category (user access)                                        |
| GET         | `/api/users`                      | `response:` <br><pre lang="json">[{&#13; "email": "String",&#13; "password": "String",&#13;}]</pre>                               | Retrive all users (admin access)                                                       |
| PUT         | `/api/users/update`               | `request:` <br><pre lang="json">{&#13;  "email": "String",&#13;  "newPassword": "String"&#13;}</pre>                                                                                                                                                        | update a user  (admin access)                                                          |
| DELETE         | `/api/users/delete/{id}`          | `response:` <br><pre lang="json">{&#13; "email": "String",&#13; "password": "String",&#13;}</pre>                                            |

