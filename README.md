# Getting Started

## PS: In case your maven already uses java version > 11, comment toolchain plugin in pom file.
## Otherwise check your toolchains.xml (pathToApacheMavenFolder/conf/toolchains.xml)
## Example:
    <toolchains>
        <toolchain>
            <type>jdk</type>
            <provides>
                <version>6</version>
            </provides>
            <configuration>
                <jdkHome>C:\Program Files\Java\jdk1.6.0_45</jdkHome>
            </configuration>
        </toolchain>
    
        <toolchain>
            <type>jdk</type>
            <provides>
                <version>8</version>
            </provides>
            <configuration>
                <jdkHome>C:\Program Files\Java\jdk1.8.0_241</jdkHome>
            </configuration>
        </toolchain>
    
        <toolchain>
            <type>jdk</type>
            <provides>
                <version>16</version>
            </provides>
            <configuration>
                <jdkHome>C:\Program Files\Java\jdk-16</jdkHome>
            </configuration>
        </toolchain>
    </toolchains>

#### <h1>Steps</h1>
    - Install docker (Tested on linux containers)

### <h1> Reuse Test Containers (optional) </h1>
    Locate your .testcontainers.properties file in user's home folder. Example locations:
    - Linux: /home/myuser/.testcontainers.properties
    - Windows: C:/Users/myuser/.testcontainers.properties
    - macOS: /Users/myuser/.testcontainers.properties

    And add this property:
    - testcontainers.reuse.enable=true

To enable reuse of test containers, you must set 'testcontainers.reuse.enable=true' in a file located at C:\Users\e-doliveira\.testcontainers.properties

#### <h1> `DEV profile` </h1>
## In case you want to use only database container and run application in your IDE, remove `interview-calendar-api` service container from `docker-compose.yml` file.
## Start only the database one (`docker compose up --build`).
## Run your application with `dev` profile so it picks up `application-dev.properties` file.

#### <h1> `DOCKER profile` </h1>
## This application has no external scripts to be executed to control when this java app is going to boot, so it will probably fail at first because `postgres database` is not properly started yet.
## Workaround: Looking in docker-compose.yml file `backend` service has a `restart: on-failure` property.

    - Run: 'mvn clean package -Pdocker' to generate jar file
    - Go to your project folder and run 'docker-compose config' to check for docker-compose file errors
    - Run docker-compose up --build

## Access SWAGGER:
    - http://{host}:{port}/interview-calendar-api/swagger-ui/