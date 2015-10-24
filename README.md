BANKHEAD
=======

Requirements
------------
- Java 7
- Maven 3
- npm [install instructions](https://gist.github.com/isaacs/579814#file-only-git-all-the-way-sh)
- Grunt, Bower ```npm install -g grunt-cli bower```
- MySQL
  - Create the database:
    - Run ```mysql -u root```
    - Execute in MySQL: ```create database bankheadweb;``` and ```create database bankheadwebtest;```
    - Exit MySQL shell
  - Set your MySQL password:
    - Run ```mysqladmin -u root password "newpassword"```
    - Create a hibernate.properties file in [bankhead-web-main/src/main/resources](bankhead-web-main/src/main/resources)
      - Use something like ```hibernate.connection.url = jdbc:mysql://localhost:3306/bankheadweb?autoReconnect=true```
    - Create a hibernate.properties file in [bankhead-web-test/src/main/resources](bankhead-web-test/src/main/resources)
      - Use something like ```hibernate.connection.url = jdbc:mysql://localhost:3306/bankheadwebtest?autoReconnect=true```
    - Set the fields hibernate.connection.username and hibernate.connection.password to be root, "newpassword"
    - Advisable to create a 'bankhead' user instead of using root
    - Don't commit changes to hibernate.properties, unless you want your password permanently stored in github history

Build
------------
- From base directory
- Run ```mvn install```

Run Embedded Server
-------------------
- From base directory, or *bankhead-web-main*
- Execute ```mvn tomcat7:run```
- Visit [http://localhost:9090/](http://localhost:9090/)

Run Executable WAR
----------------
- From base directory
- Run ```java -jar bankhead-web-exec/target/bankhead-web-exec-1.0-SNAPSHOT-war-exec.jar```
- Visit [http://localhost:8080/](http://localhost:8080/)

Run Integration Test
--------------------
- From base directory, or *bankhead-web-test*
- Run ```mvn integration-test```
- This will start an embedded Tomcat server with Selenium and run integration tests in Surefire

Architecture
------------
&#x2713; **: Implemented,** &#x2717; **: Not Implemented**

&#x2713; Tomcat 7 (Web Host)

&#x2717; JBoss AS7 (Potential Web Host)
  - After reading [the great java server app debate](http://zeroturnaround.com/rebellabs/the-great-java-application-server-debate-with-tomcat-jboss-glassfish-jetty-and-liberty-profile/) report, and discovering that Glassfish [has just been ditched](http://www.zdnet.com/oracle-abandons-commercial-support-for-glassfish-jee-server-7000022945/) by Oracle, JBoss (recently renamed Wildfly) seems the best option.  It supports JavaEE, has a great management web console, and commercial support available.  Wildfly RC8 was released in december, and a stable version is coming soon but it is too early to use it.

&#x2717; RestExpress (Future REST API)
  - Built with Netty/IO for faster zero-copy, event-driven service (Node.js is event-driven) 

&#x2713; Jersey (Current REST API)

&#x2713; Guice (Dependency Injection)

&#x2713; Maven (Java Build)

&#x2713; Hibernate (DAL)

&#x2713; MySQL (DB)

&#x2713; Bootstrap (Visuals)

&#x2713; Coffescript Compiling (Frontend)

&#x2713; Sass Compiling (Style)

&#x2713; AngularJS (Frontend)

&#x2713; Surefire/JUnit (Java/Server Testing)

&#x2713; Selenium/WebDriver (Integration Testing)

&#x2713; Protractor/Jasmine (Javascript/Client Testing)
- Use [Protractor](http://www.asgarddesigns.com.au/2013/11/end-to-end-testing-with-angularjs-protractor-grunt-and-maven/), which supplants Karma as the recommended test framework for AngularJS

&#x2713; Grunt (Javascript Build)

&#x2713; Sailthru (Email)

&#x2717; Logback (Logging)

Source File Locations
-----
- Static web (HTML, CSS, Javascript)
  - [bankhead-web-main/src/main/webapp](bankhead-web-main/src/main/webapp)
- API
  - [bankhead-web-main/src/main/java/com/bankhead/api](bankhead-web-main/src/main/java/com/bankhead/api)

Notes
-----
- ```mvn compile``` will not work on *bankhead-web-test* and therefore not on *bankhead-web*
- Use ```mvn install``` instead
