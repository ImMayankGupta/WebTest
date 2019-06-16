### Web Test Automation
---
#### System Requirement:

* JDK 1.6 or above
* Maven 3.1 or above

  

#### Execution Steps:
Please follow the instructions to execute the tests on local machine:

1. To Execute the test using the eclipse 
	# Open project in eclipse
	# from package tests
	# right click on WebTest.java and run as testng test
	Or
	#right click on testng.xml file and run as testng test
	
2.To Execute the single test class open the command prompt and exectute following command -
		 ##for without email report
		 mvn clean integration-test -Dtest=WebTest 
		 mvn clean integration-test -Dtestxml=Testng.xml
		 
		 ##for eamil report
		 mvn clean verify -Dtest=WebTest
		 mvn clean verify -Dtestxml=Testng.xml
     
#### Result Files:	
The Test Execution Results will be stored in the following directory once the test has completed

    ./target/test-output/emailable-report.html (for complete test suite)
    ./target/surefire-reports/emailable-report.html (for single test suite)
