FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=secret123
ENV MYSQL_DATABASE=daamdb

# Copy SQL files from the database_scripts directory
COPY database_scripts/*.sql /docker-entrypoint-initdb.d/

EXPOSE 3306