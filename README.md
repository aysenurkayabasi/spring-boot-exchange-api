# Exchange Application

### Project Setup
* Maven 3.9.8
* Java 21
* Docker 27.0.3

### Building the Application
#### TodoList App
* Run `mvn clean package` to create a JAR file in the target folder
* Download Docker Desktop from https://www.docker.com/products/docker-desktop/
* Run the following Docker commands:
  * `docker build -t openpayd-app .`
  * `docker run -p 8090:8080 openpayd-app`
* Access the application at [http://localhost:8090/swagger-ui/index.html#/](http://localhost:8090/swagger-ui/index.html#/)

### External API Integration
* https://currencylayer.com

### Database Configuration
* In-memory database: H2
* Access the H2 console at http://localhost:8080/h2-console
* Data source information:
  * URL: jdbc:h2:mem:foreignexchangehistory
  * Username: superuser
  * Password: password

### API Endpoints
#### Exchange Rate
* Calculate the exchange rate between two given currencies
* Response is cached for 1 minute using Caffeine in-memory cache
#### Exchange List
* Return the current exchange list
#### Convert
* Convert a specific amount from one currency to another
#### Conversion History
* Query the history of currency conversions (paginated list)

View sample request and response models in Swagger

