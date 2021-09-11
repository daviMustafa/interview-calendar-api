# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
* [Spring Web Starter](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#using-boot-devtools)
* [Project Lombok](https://projectlombok.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building Java Projects with Maven](https://spring.io/guides/gs/maven/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Install Lombok Eclipse](https://projectlombok.org/setup/eclipse)
* [Install Lombok Intellij](https://projectlombok.org/setup/intellij)
* [Install Lombok Visual Code](https://projectlombok.org/setup/vscode)
* [Install Lombok Netbeans](https://projectlombok.org/setup/netbeans)

## I've chosen to use PROJECT LOMBOK to build objects faster for this project, check the guides above (you might need to install a plugin).

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

#### <h1>Steps </h1>
* [DOCKER](https://docs.docker.com/get-docker/)
    - Install docker (Tested on linux containers)

#### <h1> Reuse Test Containers (optional) </h1>
    Locate your .testcontainers.properties file in user's home folder. Example locations:
    - Linux: /home/myuser/.testcontainers.properties
    - Windows: C:/Users/myuser/.testcontainers.properties
    - macOS: /Users/myuser/.testcontainers.properties

    And add this property:
    - testcontainers.reuse.enable=true

#### To enable reuse of test containers, you must set 'testcontainers.reuse.enable=true' in a file located at C:\Users\e-doliveira\.testcontainers.properties

#### <h1> `DEV profile` </h1>
#### In case you want to use only database container and run application in your IDE, remove `interview-calendar-api` service from `docker-compose.yml` file. 

#### Start only the database one (`docker compose up --build`).

#### Build project for `dev` profile:
    - mvn clean package -Pdev
#### Run your application with `dev` profile so it picks up `application-dev.properties` file.

#### <h1> `DOCKER profile` </h1>
#### PS: In case your machine is under some VPN configuration that causes problem (not downloading openjdk for example) to build image when running `docker compose up` inside WSL2, try first to build the image in your local machine.
    - Example: docker compose build interview-calendar-api

#### PS2: Check docker volumes (docker-compose.yml) for which one you want your log files to be exposed outside your container.

    - Run: 'mvn clean package -Pdocker' to generate jar file
    - Go to your project folder and run 'docker-compose config' to check for docker-compose file errors


## Run docker:
    - Run docker-compose up --build

#### This project already comes with a few data inserted, so you can test without having to insert more data.
    - Check file: V1.0.0__create-tables-and-insert-data.sql

#### Most of the implementation is covered by tests, you might want to look into to it on how the project works or just access 
#### one of the links below since on SWAGGER Docs API is more explanatory:

## Access SWAGGER while running in a docker container:
    - http://localhost:8081/interview-calendar-api/swagger-ui/

## Access SWAGGER while running from IDE with `dev` profile:
    - http://localhost:8080/interview-calendar-api/swagger-ui/