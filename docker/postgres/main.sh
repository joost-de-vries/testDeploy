#!/usr/bin/env bash

# working directory for script
thisDir=`dirname $0`

# Clean up running containers
docker rm -f postgres 2> /dev/null
rm -f ${thisDir}/CONTID

${thisDir}/build.sh .
${thisDir}/run.sh

# Postgres does not start instantaneously, so we have to wait a bit before prepping the DB
#while [[ $VERSION != ReleaseVersion* ]]
#do
    echo "Sleeping";
    sleep 1;
#    VERSION=$(docker exec postgres su postgres; pg_ctl status)
#    VERSION=$(docker exec postgres su - postgres; cat $DATADIR/postmaster.pid)
#    VERSION=$(docker exec postgres psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U postgres)
#done

# Prep the DB, create user and database
${thisDir}/prepdb.sh

echo "postgres ready"
