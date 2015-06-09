#!/bin/bash

if [ ! -f ./configuration.properties ]
then
	echo cp configuration.properties.sample configuration.properties
fi

sed 's/{la_tomcat_directory/'$sla_tomcat_directory'/g' configuration.properties -i
sed 's/{sla_db_password}/'$sla_db_password'/g' configuration.properties -i
sed 's/{log_slaatos_fullpathFilename}/'$log_slaatos_fullpathFilename'/g' configuration.properties -i
sed 's/{log_thirdpartysw_fullpathFilename}/'$log_thirdpartysw_fullpathFilename'/g' configuration.properties -i
sed 's/{sla_service_basicsecurity_password}/'$sla_service_basicsecurity_password'/g' configuration.properties -i

exit 0
