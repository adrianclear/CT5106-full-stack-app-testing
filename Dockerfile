# Use official lightweight JDK image
FROM eclipse-temurin:17-jdk

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Pre-fetch dependencies
RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Expose the Render port and run
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
