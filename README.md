# grocery example

## Prerequisites:
* MySQL 5.5 or 5.6 
* IDE ( preferred Eclipse 4.3+) 
* JDK 1.7 (if you want to use Jetty 9 with the jetty-maven-plugin from project)
* Maven 3.*

## Install and run the project 
1. download/clone the project 
2. prepare the database
  * import in MySQL the self-contained file that comes with the project - [grocery / src / main / resources / setup_data / grocery.sql]
  * 
3. change to the root folder of the project and execute the following maven command 
  * `mvn clean install jetty:run  -Djetty.port=8888 -DskipTests=true`
  * now the REST api is up and running with Jetty on `localhost:8888` 
  

> **Note:** after you `mvn install` the application, you can deploy the generated __.war__ file in any web container like Tomcat for example. 

## Testing the project 

### From Maven (failsafe plugin)
Run the following maven command on the console in the root directory of the project 
  
  ```mvn clean install verify -Djetty.port=8888```

