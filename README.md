# lonsec
## Requirement 
- To provide an externalize option to change the label of OutPerformance column
- All file's file name and path configuration.
- The possible change of excess formula.
- Report output for decimal number is rounding half-down. that is 2.005 will become 2.00. 2.006 will become 2.01


## Issue found
- the csv file date format pattern has different between files. The design allow multi date format, that is dd/MM/yyyy,yyyy-MM-dd and configurable on config.prop
- The design should allow user to change the configuration file. It use -Dconfig paramter to define the config file location and filename.
- The possible human error for those csv input files. The program will read the file and check if the dataformat incorrect, number cannot convert into number, total number of column are not expected. Skip line or first line would not be read.
- the provided data has some duplicated record. The program will stop process and give a warning message in code.


## Development assumption
- The input csv file would not very large. and assume it would be under 1MB. So that the program would create the instance and in-memory to process including construct new entity object modified and extract the report directly from the in-memory object. The advantage is the process time is very fast and no need too much Dao or DB dependence.
-If any user input got error. It would not process any more and the program will output the error message on Standard error channel that is System.err output stream.
- The excess formula use JavaScript engine as background so that it can accept any javascript input as long as there are three variable name should not be changed, they are excess, fundReturn and benchmarkReturn.
-The only main program is MainStart.java. Then the JVM will create a static ConfigStore class to manage all the configurable items such as read the config.prop file and check their content is it complete as per design.
-For any incorrect date given in csv file would stop the process and give a warning messag. It requied all the feedin csv file has correct data to complete the process.
-Assume the code would run on linux or Mac the only issue is the file path, such as C:/ and .\ different. If the config on config.prop is correct then it would be not a issue.
- The code does not test on any PC yet.
- After the testing success. The program will create an Analyzer instance and only public method named process(), that method will process the following
###Development Detail
<li>load fundReturnSeries.csv file and create the core collection of entity. 
<li>then load the funds.csv to grep the fundName and benchmarkCode,
<li>then load the benchmarkReturnSeries.csv file to check the percent for a given date and benchmarkCode.
<li>then the process will calculate the excess and also the outperformance label.
<li>the next step is to rank the series which grouped by date.
<li>the series was sorted by two custom comparator to meet the requirement for ranking and report output order.and last step is export the record into csv file and stored under the config.prop's filepath. 

## Folder structure of this project
- src/main for java source code
- src/main for java test code
- testData to store the csv file. It is for reference only.
- target for target build folder.
- script for those mac/linux program run script

## Build instruction
- checkout the code from github
- made sure the build machine has JDK 1.8, JAVA_HOME configurable.
- Maven installed.
- the build machine should possible to connect to internet in order the maven would checkout form plugin on the fly.
- go to the folder on pom.xml and run mvn clean install
- then it would show the SUCCESS after some code download and build process.
- then go to target folder and copy the lonsec-0.0.1-SNAPSHOT-jar-with-dependencies.jar

## Testing
- Maven will help to test the code before compile
- The test case does not cover all the method and instance as the time is limited.
- There are more than 60 test case including unit test and integration test.
- To run the test just use mvn test or mvn clean test

## Performance and memory consumption (Measure on Mac book pro)
- if the expected report up to 4200043 lines, it is about 316M files. The time consumption about 5 sec, and total Heap consumption about 1.1G
- if the expected report up to 43 lines, it is about 3K files, the time consumption less than 1 sec, and total Heap consumption about 22M.

## Deployment requirement
- copy the jar from above step
- copy the config.prop from the github folder and put it into the same folder of the target.
- prepare those csv file and edit the config.prop for those csv file location which related to current logical directory.
- run the comment of java -jar lonsec-<XXXX>-jar-with-dependencies.jar. and the screen output will show the config detail, Process start and Process Finished!
- the go to the designed report output folder and find the file as expected. now the file are set under config.prop content MONTHLY_OUTPERFORMANCE_FILEPATH as ./testData/monthlyOutperformance.csv
- if incase the config.prop is locate on other location it can use the -Dconfig option to redirect the config.prop file location. that is java -jar -Dconfig=<somewhere else> lonsec-0.0.1-SNAPSHOT-jar-with-dependencies.jar

##Quick checkout from Github
- download the zip from github. but press the download zip button
- unzip it on your mac or linux
- assume you have java and maven install.
- type mvn clean install
- copy the lonsec-0.0.1-SNAPSHOT-jar-with-dependencies.jar to your working directory
- copy the config.pro to your working directory
- copy the csv file to your target directory
- set the config.prop about the csv file location and file name.
- run the code by java -jar lonsec-0.0.1-SNAPSHOT-jar-with-dependencies.jar



 
