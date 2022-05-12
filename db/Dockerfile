FROM postgres
ENV POSTGRES_PASSWORD=1234 
COPY tables.sql /docker-entrypoint-initdb.d/1.sql
COPY inserts.sql /docker-entrypoint-initdb.d/2.sql