# SQL Exercises

This folder contains a MySQL script for the DDL, DML, DQL, join, and aggregation exercises.

## Run

Open `exercises.sql` in a MySQL client and execute it using an account that can create
databases. The script creates and selects the `school_management` database, creates
`courses`, and supplies small `student` and `attendance` sample tables so all queries
can be run without the lecture database.

The script is designed as a learning worksheet: exercise statements are grouped and
commented, and the delete exercise uses the ID of the sample course it inserted.
Running the script again resets the sample tables.

## Provision MySQL with Docker

Install and start Docker, then run these commands from the repository root.

1. Create a local environment file from the template, then set a password of your
   choice in `sql-exercises/.env`. This file is ignored by Git:

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

5. Open an interactive MySQL prompt in the created database:

   ```bash
   docker exec -e MYSQL_PWD="$MYSQL_ROOT_PASSWORD" -it sql-exercises-mysql \
     mysql -uroot school_management
   ```

   At the prompt, use `SHOW TABLES;` to list the tables. Exit with `exit`.

Stop the container with `docker stop sql-exercises-mysql` and start it again with
`docker start sql-exercises-mysql`. The container retains its data when stopped.
