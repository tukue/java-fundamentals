# Issue Management (Java Streams Practice)

A small, dependency-light in-memory issue tracker for practicing Java records, lambdas, and streams.

## Run

From this directory, run `mvn test` to compile and test the project, or
`mvn exec:java -Dexec.mainClass=se.lexicon.issues.IssueManagementApp` to run the sample app.

## Stream exercises implemented

- `findByStatus` and `findByAssignee`: filtering with predicates and method references.
- `search`: matching user-provided text across issue fields.
- `findOpenHighPriority`: chained filters and priority sorting.
- `countByStatus`: grouping and counting with a collector.
- Create, update, assign, transition status, and delete issue operations.
- The demo combines stream filtering, mapping, and `forEach` with lambdas/method references.

The service stores data in memory, so issues reset when the application exits. Persistence and a repository abstraction are possible next steps.
