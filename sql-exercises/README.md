# SQL Exercises

This folder contains SQL exercises written for MySQL. The examples currently use a
MySQL 8.4 database and cover DDL, DML, DQL, joins, and aggregation.

## Run

Open `exercises.sql` in a MySQL client and execute it using an account that can create
databases. The script creates and selects the `school_management` database, creates
`courses`, and supplies small `student` and `attendance` sample tables so all queries
can be run without the lecture database.

The script is designed as a learning worksheet: exercise statements are grouped and
commented, and the delete exercise uses the ID of the sample course it inserted.
Running the script again resets the sample tables, including `courses`.

## Provision a MySQL database with Docker

The following steps run MySQL 8.4 in a Docker container. Install and start Docker,
then run the commands from a terminal at the repository root. Docker Desktop can
also be used to view and manage the container.

1. Create a local environment file from the template and set a password for
   `MYSQL_ROOT_PASSWORD` in `sql-exercises/.env`. This file is ignored by Git; do
   not commit it:

   ```bash
   cp sql-exercises/.env.example sql-exercises/.env
   ```

2. Load the password into your current shell and create/start a MySQL container:

   ```bash
   set -a
   . ./sql-exercises/.env
   set +a

   docker run --name sql-exercises-mysql \
     --env-file sql-exercises/.env \
     -p 3306:3306 \
     -d mysql:8.4
   ```

   To provision it using Docker Desktop instead, pull the `mysql:8.4` image and
   choose **Run**. Set the container name to `sql-exercises-mysql`, publish host
   port `3306` to container port `3306`, and set the `MYSQL_ROOT_PASSWORD`
   environment variable to the local value from `.env`. Keep the password local.

3. Wait for MySQL to finish starting:

   ```bash
   docker exec -e MYSQL_PWD="$MYSQL_ROOT_PASSWORD" sql-exercises-mysql \
     mysqladmin ping -uroot --wait
   ```

4. Send the exercise script to MySQL. It creates the `school_management` database
   and tables, inserts sample rows, and executes the exercise queries:

   ```bash
   docker exec -e MYSQL_PWD="$MYSQL_ROOT_PASSWORD" -i sql-exercises-mysql \
     mysql -uroot < sql-exercises/exercises.sql
   ```

5. Fetch the attendance data to verify the database returns the expected rows:

   ```bash
   docker exec -e MYSQL_PWD="$MYSQL_ROOT_PASSWORD" sql-exercises-mysql \
     mysql -uroot school_management \
     -e "SELECT s.name, a.attendance_date, a.status
         FROM student AS s
         JOIN attendance AS a ON a.student_id = s.id
         ORDER BY s.id, a.attendance_date;"
   ```

6. To inspect the database interactively, open a MySQL prompt:

   ```bash
   docker exec -it sql-exercises-mysql mysql -uroot -p school_management
   ```

   Enter the local password when prompted. Use `SHOW TABLES;` to list the tables
   and `exit` to leave the prompt.

Stop the container with `docker stop sql-exercises-mysql` and start it again with
`docker start sql-exercises-mysql`. The container retains its data when stopped.

## Optional PostgreSQL and Oracle containers

PostgreSQL and Oracle Free are available as optional Docker Compose profiles. Their
passwords and host ports are configured separately from the MySQL settings in
`sql-exercises/.env`. Replace each `REPLACE_ME` value with a local password. The
`.env` file is ignored by Git and should not be committed.

| Database | Profile | Host port setting | Default port |
| --- | --- | --- | ---: |
| PostgreSQL | `postgres` | `POSTGRES_HOST_PORT` | `5432` |
| Oracle Free | `oracle` | `ORACLE_HOST_PORT` | `1521` |

The host port can be changed if it is already in use; the container port remains
the database default. Start PostgreSQL with:

```bash
docker compose --env-file sql-exercises/.env -f sql-exercises/compose.yaml \
  --profile postgres up -d postgres
```

Start Oracle Free with:

```bash
docker compose --env-file sql-exercises/.env -f sql-exercises/compose.yaml \
  --profile oracle up -d oracle
```

Stop a profile with `docker compose ... --profile <profile> down`. The current
`exercises.sql` uses MySQL syntax and must be converted before it can run on
PostgreSQL or Oracle.
