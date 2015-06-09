#!/bin/bash

if [ ! -f configuration.properties ]
then
	cp configuration.properties.sample configuration.properties
fi

sed 's/{sla_tomcat_directory/'$sla_tomcat_directory'/g' configuration.properties -i
sed 's/{sla_db_password}/'$sla_db_password'/g' configuration.properties -i
sed 's/{sla_db_host}/'$sla_db_host'/g' configuration.properties -i
sed 's/{sla_log_slaatos_fullpathFilename}/'$sla_log_slaatos_fullpathFilename'/g' configuration.properties -i
sed 's/{sla_log_thirdpartysw_fullpathFilename}/'$sla_log_thirdpartysw_fullpathFilename'/g' configuration.properties -i
sed 's/{sla_service_basicsecurity_password}/'$sla_service_basicsecurity_password'/g' configuration.properties -i

exit 0
