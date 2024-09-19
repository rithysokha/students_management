# Getting Started

### Create secret.properties file in resource folder

<b>Set value in secret.properties</b>

    spring.datasource.username=
    spring.datasource.password=
    student.default-username=
    student.default-password=
    student.jwt-secret=

### Third party

    I have created an API service to check if the student is in the black list

#### Here is the student in the black list

```json
{"firstName":"John", "lastName":"Doe", "dateOfBirth":"2000-01-01"},
{"firstName":"Jane", "lastName":"Smith", "dateOfBirth":"2000-02-02"},
{"firstName":"Jim", "lastName":"Beam", "dateOfBirth":"2000-03-03"}