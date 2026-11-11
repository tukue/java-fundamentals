# Issue Management (Java Streams Practice)

A simulated issue tracker for application support across deployed applications, built to practice Java records, lambdas, streams, and text-file persistence. Every issue is associated with an application/service and deployment environment (`DEVELOPMENT`, `STAGING`, or `PRODUCTION`).

## Run

From this directory, run `mvn test` to compile and test the project, or
`mvn exec:java -Dexec.mainClass=se.lexicon.issues.IssueManagementApp` to start the console app.
The app creates and updates `application.issue.txt` in the current working directory.

## Stream exercises implemented

- `findByStatus` and `findByAssignee`: filtering with predicates and method references.
- `findByApplication` and `findByEnvironment`: filter issues by deployed service or environment.
- `search`: matching user-provided text across issue fields.
- `findOpenHighPriority`: chained filters and priority sorting.
- `countByStatus`: grouping and counting with a collector.
- Create, update, assign, transition status, and delete issue operations.
- The console menu combines stream filtering, mapping, and `forEach` with lambdas/method references.

Issue data is loaded at startup and saved after every create, update, assignment, status change, or deletion. The plain-text file escapes field delimiters and line breaks. The next available ID is persisted too, so deleted IDs remain retired after restarting the app. This is a local simulation; it does not connect to or monitor deployed systems.
