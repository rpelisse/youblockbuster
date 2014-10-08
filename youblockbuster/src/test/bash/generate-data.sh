#!/bin/bash

readonly XML_TEMPLATE='src/main/resources/movie.xml.tmpl.erb'
readonly TARGET=$(mktemp)

randomValueBetweenZeroAnd() {
  local upperBound=${1:-'100'}

  echo $((0x$(head -c5 /dev/random|xxd -ps)%${upperBound}+1))
}

set -e
echo -n "Generating data in file ${TARGET} ... "
echo '<movies>' >> "${TARGET}"
for id in {1..200}
do
  sed -e "s;<id><%=.*%></id>;<id>${id}</id>;" "${XML_TEMPLATE}" | \
    sed -e "s/<%=.*%>/${RANDOM}/g"  |
  sed -e "s;<rating>.*</rating>;<rating>$(randomValueBetweenZeroAnd '10')</rating>;g" >> "${TARGET}"
  sleep 1
done
echo '</movies>' >> "${TARGET}"
xmlwf "${TARGET}"
echo 'Done.'
