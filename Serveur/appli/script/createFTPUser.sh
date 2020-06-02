#!/bin/bash
# Purpose - Script to add a user to Linux system including passsword
# Author - Vivek Gite <www.cyberciti.biz> under GPL v2.0+
# ------------------------------------------------------------------
# Am i Root user?
username=$1
password=":root"
egrep "^$username:" /etc/passwd >/dev/null
if [ $? -eq 0 ]; then
	echo "${username} already exists!"
	exit 1
else
	sudo adduser --force-badname --shell /bin/false --home /home/$username --ingroup www-data $username --disabled-password --gecos ""
	[ $? -eq 0 ] && echo "User has been added to system!" || echo "Failed to add a user!"
	echo "${username}${password}" | sudo chpasswd

fi
