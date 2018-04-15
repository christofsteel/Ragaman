
# OSM Export

  * ID für Dortmund: 1829065
  * [hier](http://polygons.openstreetmap.fr/) die boundaries Dortmunds
    runterladen (poly)
  * [hier](http://download.geofabrik.de/) die osm.pbf für Regierungsbezirk
    Arnsberg herunterladen
  * osm-Datei für Dortmund generieren:

```
osmosis --read-pbf-fast file="arnsberg-regbez-latest.osm.pbf" --bounding-polygon file="Dortmund.poly" --write-xml file="Dortmund.osm"
```

  * Nodes extrahieren, um Koordinaten an Haltestellen, Straßen, etc zu haben

```
osmconvert Dortmund.osm --all-to-nodes > Dortmund_nodes.osm
```

## Haltestellen

```
./create_json.sh "public_transport=stop_position or =station or =platform or =stop_area" > dsw21.json 
```

## Straßen

```
./create_json.sh "highway and name" "highway=bus_stop or =motorway_junction =elevator or =traffic_signals or =crossing or =street_lamp or =emergency_access_point or public_transport"
```

## POI

```
./create_json.sh "tourism or leisure or amenity" > poi.json
```

# Credits

  * POI symbol by Picol from flaticon: https://www.flaticon.com/free-icon/point-of-interest_14738 (CC-BY)
  * Straßen, Haltestellen und Ortsnamen von Open Street Maps (ODbL)
