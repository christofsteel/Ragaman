
# OSM Export

  * ID für Dortmund: 1829065
  * [hier](http://polygons.openstreetmap.fr/) die boundaries Dortmunds
    runterladen (poly)
  * [hier](http://download.geofabrik.de/) die osm.pbf für Regierungsbezirk
    Arnsberg herunterladen
  * osm-Datei für Dortmund generieren:

    osmosis --read-pbf-fast file="arnsberg-regbez-latest.osm.pbf" --bounding-polygon file="Dortmund.poly" --write-xml file="Dortmund.osm"

## Haltestellen

    osmfilter Dortmund.osm --keep="public_transport=stop_position or =station or =platform or =stop_area" --ignore-dependencies --drop-relations | osmconvert --csv="name" - | sort | uniq | jq -R '[.]' | jq -s -c 'add' | sed -e "s/^/{ \"words\" :/" -e "s/\$/}/" > dsw21.json

## Straßen

    osmfilter Dortmund.osm --keep="highway and name" --drop="highway=bus_stop or =motorway_junction =elevator or =traffic_signals or =crossing or =street_lamp or =emergency_access_point or public_transport" --ignore-dependencies --drop-relations | osmconvert --csv="name" - | sort | uniq | jq -R '[.]' | jq -s -c 'add' | sed -e "s/^/{ \"words\" :/" -e "s/\$/}/" > strassen.json

## POI

    osmfilter Dortmund.osm --keep="tourism or leisure or amenity" --ignore-dependencies --drop-relations | osmconvert --csv="name" - | sort | uniq | jq -R '[.]' | jq -s -c 'add' | sed -e "s/^/{ \"words\" :/" -e "s/\$/}/" > poi.json

# Credits

POI symbol by Picol from flaticon: https://www.flaticon.com/free-icon/point-of-interest_14738 (CC-BY)
