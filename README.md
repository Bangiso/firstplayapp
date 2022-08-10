Scala play mvc app with mysql db that can run on local kubernetes environment. It is exposing the following endpoints.

- `
  POST /api/students
  ` expecting the payload `{"id":1,"name":"Leo","gpa":70}`
- `
  PUT /api/students/{id}
  ` expecting the payload `{"id":1,"name":"Leo","gpa":70}`

- `
  GET /api/students
  `

- `
  GET /api/students/{id}
  `

- `
  DELETE /api/students/{id}
  `

# Database

Requires db connection table outlined below file. The credentials can be modified in the application.conf file or deployment.yaml.

```roomsql
CREATE DATABASE StudentDB;

use StudentDB;

CREATE TABLE students (
id INTEGER PRIMARY KEY,
name TEXT NOT NULL,
gpa double NOT NULL
);

INSERT INTO students (id, name, gpa) values
(1, 'Daniel', 89),
(2, 'Samuel', 76),
(3, 'Sanele', 96);
```
# Running the service
Execute the command `bash start.sh tagname`. This will create a docker image and push it to remote repository. Make sure you change docker repository in `start.sh`and `deployment.yaml` with your own repo. Remember to change docker image in deployment file to match your tagname.
