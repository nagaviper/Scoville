#!/bin/sh

#############################################################################
# Customized for Capsaicine
#############################################################################

$HOME/vfabric-scripts/hyperic-agent.sh stop
$HOME/vfabric-scripts/tcserver-tc01.sh stop
$HOME/vfabric-scripts/rabbitmq-server.sh stop
$HOME/vfabric-scripts/gemfire-jmx.sh stop
$HOME/vfabric-scripts/gemfire-gf01.sh stop
$HOME/vfabric-scripts/gemfire-locator.sh stop
$HOME/vfabric-scripts/ers-apache.sh stop
$HOME/vfabric-scripts/hyperic-server.sh stop
