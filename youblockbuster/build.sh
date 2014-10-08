#!/bin/bash

readonly JBOSS_HOME=${JBOSS_HOME:-'../jdv-6.0.0.git'}
readonly PORT_OFFSET=${PORT_OFFSET:-'100'}
readonly PATH_TO_ARTEFACT=${PATH_TO_ARTEFACT:-'target/movies-cache-rs.war'}

readonly CONTROLLER=${CONTROLLER:-'localhost'}
readonly CLI_PORT=$(expr 9999 + "${PORT_OFFSET}")

computeQuery() {
  local query=${1}

   echo 'Compute result for query:'
   curl -s -X POST http://$(hostname):8180/rest/queries/${query}/compute
   sleep 1
   xml_result=$(mktemp)
   curl -s -X GET http://$(hostname):8180/rest/queries/${query} > "${xml_result}"
   nbResults=$(cat "${xml_result}" | sed -e 's;</movie>;\n;g' | wc -l)
   echo ''
   echo "Nb items returned: ${nbResults}"
   rm "${xml_result}"
}

set -e
if [ -z "${NO_DEPLOY}" ] ; then
  mvn -o -Dmaven.test.skip=true clean package
  if [ "${?}" -ne 0 ] ; then
    cowsay BUILD FAILED
  fi
  ${JBOSS_HOME}/bin/jboss-cli.sh --controller=${CONTROLLER}:${CLI_PORT} --connect --command="deploy --force target/movies-cache-rs.war"
  sleep 2
fi

readonly HOST="http://${CONTROLLER}:$(expr '8080' + "${PORT_OFFSET}")"

echo 'Deploy caches:'
curl -s -X GET "${HOST}/rest/check"
echo 'Done.'

echo -n 'Load data in cache /movie... '
export DATA_FILE=$(pwd)/src/test/data/two-hundred-items.xml
curl -s -X PUT "${HOST}/rest/movies/bulk" -H 'Content-type: application/xml' -d@${DATA_FILE}
echo 'Done.'

computeQuery TopRating
computeQuery TopPopularity
computeQuery TopVoted
