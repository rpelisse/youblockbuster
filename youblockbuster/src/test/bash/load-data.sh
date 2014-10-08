#!/bin/bash

readonly WS_URL=${WS_URL:-"http://$(hostname):8180/rest/movies/bulk"}
readonly DATA_FILE=${DATA_FILE:-"$(pwd)/src/test/data/movies.xml"}

curl -i -X PUT "${WS_URL}" -H 'Content-type: application/xml' -d@${DATA_FILE}
