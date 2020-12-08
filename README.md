# elasticsearch_service
  The service starts an elasticserach container using Docker API and tests creating and deleting Index API.

	
## Setup Instructions

	* Clone the repository project
	* Open the Docker Desktop    
	* Open the terminal
	* Run a command - `docker pull elasticsearch:7.9.3` (elasticsearch image should be added to the Docker)
	* Run a command - `docker network create mynetwork`
	* Run a command - `docker run -d --name elasticsearch --net mynetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.9.3` 
	  (elasticsearch container should be running on the Docker)
	


## Tests Setup
	
	Choose one of the next two options:
	
		* Running tests from the command line:
			* Add the "Maven" variable to the environment variables of your machine (more details here- https://www.tutorialspoint.com/maven/maven_environment_setup.htm)
			* Open the terminal from the project folder
			* Run a command - "mvn test" (tests for creating and deleting Index API should be running automatically)
			
		* Running tests from an IDE:
			* Open the project folder from your IDE
			* Right click src/test/java/Service/elasticsearchContainer/createIndexAPITests.java file, Run as > JUnit Test 
			* Right click src/test/java/Service/elasticsearchContainer/deleteIndexAPITests.java file, Run as > JUnit Test 
			
	
	

	
