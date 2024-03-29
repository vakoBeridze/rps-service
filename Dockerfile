FROM maven

WORKDIR /app

COPY src src
COPY pom.xml .

RUN mvn -e -B package -DskipTests

EXPOSE 8080

ENTRYPOINT [\
	"java",\
	"-Djava.security.egd=file:/dev/./urandom",\
	"-Dspring.profiles.active=production",\
	"-jar",\
	"/app/target/rps-service.jar"\
]