
mvn clean

cd src/main/elm
./compile.sh
cd ../../..
mkdir -p target/classes/static
mv src/main/elm/o.html target/classes/static/index2.html


mvn spring-boot:run
