FROM openjdk:17-oracle
ADD target/fuelTracker-0.0.2-SNAPSHOT.jar .
CMD java -jar fuelTracker-0.0.2-SNAPSHOT.jar --envname=prod