FROM eclipse-temurin:17-jdk

# Copy everything (including Maven wrapper)
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Run the JAR explicitly by name
CMD ["java", "-jar", "target/studentreg-0.0.1-SNAPSHOT.jar"]
