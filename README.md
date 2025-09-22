# Task Management API (Spring Boot)

Java 21 + Spring Boot REST API to manage "Task" objects stored in MongoDB.
No Lombok. Plain POJOs and simple service/controller architecture.

## Requirements
- Java 21
- Maven
- MongoDB running on `localhost:27017` (or change `application.properties`)

## Build & Run
```bash
mvn clean package
mvn spring-boot:run
```

## Endpoints
- `GET /tasks` - returns all tasks (or `GET /tasks?id={id}` returns one task)
- `PUT /tasks` - create/update a task (JSON body)
- `DELETE /tasks/{id}` - delete task by id
- `GET /tasks/find?name={substring}` - find tasks by name substring
- `PUT /tasks/{id}/execute` - execute the task's command; stores TaskExecution in history

## Example curl
Create:
```bash
curl -X PUT http://localhost:9333/tasks \
  -H "Content-Type: application/json" \
  -d '{"id":"task1","name":"HelloTask","owner":"Alice","command":"echo Hello, World!"}'
```

Get all:
```bash
curl http://localhost:9333/tasks
```

Execute:
```bash
curl -X PUT http://localhost:9333/tasks/task1/execute
```

Delete:
```bash
curl -X DELETE http://localhost:9333/tasks/task1
```

## Notes
- Command validation is basic; adjust rules in `TaskService.validateCommand`.
- Running shell commands from a web app is potentially dangerous. Only use for trusted environments.
