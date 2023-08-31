FROM eclipse-temurin:8-alpine

COPY target/ipfs-maven-1.0-SNAPSHOT.jar /ipfs/ipfs.jar
COPY src/main/resources/application.yml /ipfs/application.yml
COPY start.sh /ipfs/start.sh

RUN set -ex && chmod +x /ipfs/start.sh

EXPOSE 7001
WORKDIR /ipfs

CMD ["sh","/ipfs/start.sh"]