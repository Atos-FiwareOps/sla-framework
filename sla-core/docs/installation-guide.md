# SLA Core Installation Guide

* [Requirements](#requirements)
* [Installation](#installation)
	* [Download the project](#download)
	* [Creating the mysql database](#database)
	* [Importing the code into eclipse](#importeclipse)
* [Configuration](#configuration)
* [Running](#running)
* [Testing](#testing)


## <a name="requirements"> Requirements </a>

The requirements to install a working copy of the sla core are:

* Oracle JDK >=1.6
* Database to install the database schema for the service: Mysql>=5.0
* Maven >= 3.0

## <a name="installation"> Installation </a>

All commands shown here are ready to be executed from the 
root directory of the project (i.e., the one with the 
_configuration.properties_ file) 

### 1. <a name="download"> Download the project </a>

Clone the project using git from the
[sla core repository](https://github.com/Atos-FiwareOps/sla-framework.git)

	$ git clone https://github.com/Atos-FiwareOps/sla-framework.git

It is recommended to checkout the latest released version 
if developing for a specific project. So, if 0.1.0 version wants to be checked out:

	$ git checkout tags/0.1.0

### 2. <a name="database"> Creating the mysql database </a>

From mysql command tool, create a database (with a user with sufficient 
privileges, as root):

	$ mysql -p -u root 
	
	mysql> CREATE DATABASE atossla;

Create a user:

	mysql> CREATE USER atossla@localhost IDENTIFIED BY '_atossla_';
	mysql> GRANT ALL PRIVILEGES ON atossla.* TO atossla@localhost; -- * optional WITH GRANT OPTION;

The SLA Core webapp will create all the needed tables when loaded by first time.

The names used here are the default values of the sla core. See 
[configuration](#configuration) to know how to change the values.

### 3. <a name="importeclipse"> Importing the code into eclipse </a>

The core of the Atos SLS Manager has been developed using the Eclipse Java IDE, 
although others Java editors could be used, here we only provide the 
instructions about how to import the code into Eclipse.

The first step is to tell Maven to create the necessary Eclipse project 
files executing this:

	$ mvn eclipse:eclipse

The previous command is going to generate the eclipse project files: 
.settings, .classpath, and .project. Again, please never upload those 
files to the svn, it is going to deconfigure the eclipse of other 
developers (it is easy to fix, just an annoying waste of time).

After it, from your eclipse you can import the project. Go to 
"import project from file", go to the trunk folder, and you should 
see the "ATOSSLA" project ready to be imported in your Eclipse. 

## <a name="configuration"> Configuration </a>

The project is made up of five main modules:

- SLA Repository
- SLA Enforcement
- SLA Service
- SLA Tools
- SLA Personalization

A _configuration.properties.sample_ that is placed in the parent directory 
has to be copied to *configuration.properties*.

Several parameters can be configured through this file.

1. tomcat.directory when building, war will be automatically copied to this directory,
1. db.\* allows to configure the database username, password and name in case it has been changed from the proposed 
   one in the section [Creating the mysql database](#database). It can be selected if queries from hibernate must be 
   shown or not. These parameters can be overriden at deployment time through the use of environment variables 
   (see section [Running](#running)),
1. log.\* allows to configure the log files to be generated 
   and the level of information,
1. enforcement.\* several parameters from the enforcement can be customized,
1. service.basicsecurity.\* basic security is enabled
   These parameters can be used to set the user name and password to access to the rest services.
1.   ''parser.*'' different parsers can be implemented for the agreement and template. By default, wsag standard parsers are have been implemented and configured in the file. Also dateformat can be configured.

Another way for creating in an automated manner the configurations properties 
file is to set some global variables and run the bin/autoconfigure.sh script.
It is a simple script that takes the values from the exported OS variables, 
substitutes the proper values in the configuration.properties.sample file and
creates the configuration.properties file with the declared values. This allows, 
for example, to automatically configure and deploy the SLA Manager Core in
continuous integration systems like Jenkins.

More concretely, the variables to be set are:

    sla_tomcat_directory
    sla_db_password
    sla_db_host
    sla_log_slaatos_fullpathFilename
    sla_log_thirdpartysw_fullpathFilename
    sla_service_basicsecurity_password
    sla_enforcement_constraintEvaluator_class
    sla_enforcement_metricsRetriever_class

As an example, some of the values we set to get the preview version of
the SLA Core in Fiware to be configured automatcally by Jenkins are:

    sla_tomcat_directory="target\/"
    sla_db_host=192.168.205.36
    sla_log_slaatos_fullpathFilename="\/var\/log\/tomcat7\/atosslafile.log"
    sla_log_thirdpartysw_fullpathFilename="\/var\/log\/tomcat7\/atosslafullfile.log"
    sla_enforcement_constraintEvaluator_class=eu.atos.xifi.sla.monitoring.OrionConstraintEvaluator
    sla_enforcement_metricsRetriever_class=eu.atos.xifi.sla.monitoring.OrionMetricsRetriever

If you're creating the database using the command _mvn test exec:java -f sla-repository/pom.xml_ please make sure that you configure properly sla-repository\src\main\resources\META-INF\persistence.xml. Make sure you're setting the username, password and connection url with the proper parameters.

	<property name="hibernate.connection.username" value="atossla" />
	<property name="hibernate.connection.password" value="_atossla_" />
	<property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/atossla" />
	

## <a name="compiling"> Compiling </a>
	
	$ mvn install
	
If you want to skip tests:
	
	$ mvn install -Dmaven.test.skip=true
	
The result of the command is a war in _sla-service/target_. The war is also copied to
the directory pointed by _tomcat.directory_ in the _configuration.properties_ file.

## <a name="running"> Running </a>

If the war was successfully copied to tomcat.directory, 
then start your tomcat to run the server.

Alternatively, you can run an embedded tomcat:

	$ bin/runserver.sh

that is just a shortcut for:

	$ mvn tomcat:run -f sla-service/pom.xml
	
	
Some configuration parameters can be overriden using environment variables or jdk variables. The list of
parameters overridable is:

* `DB_DRIVER`; default value is `com.mysql.jdbc.Driver`
* `DB_URL`; default value is `jdbc:mysql://${db.host}:${db.port}/${db.name}`
* `DB_USERNAME`; default value is `${db.username}`
* `DB_PASSWORD`; default value is `${db.password}`
* `DB_SHOWSQL`; default value is `${db.showSQL}`

F.e., to use a different database configuration:

	$ export DB_URL=jdbc:mysql://localhost:3306/sla
	$ export DB_USERNAME=sla
	$ export DB_PASSWORD=<secret>
	$ bin/runserver.sh 

## <a name="testing"> Testing </a>

Check that everything is working:

	$ curl http://localhost:8080/sla-service/providers

The actual address depends on the tomcat configuration. 
The embedded tomcat uses _http://localhost:8080/sla-service/_ as service root url. 

Time to check the [API specification](API.md)!
