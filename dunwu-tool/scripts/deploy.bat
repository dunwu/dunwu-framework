@echo off
rem -------------------------------------------------
rem  本脚本用于发布 dunwu-tool 到中央仓库
rem  环境要求：Maven + JDK8
rem -------------------------------------------------

pushd %~dp0..
call mvn clean install org.apache.maven.plugins:maven-deploy-plugin:2.8:deploy -DskipTests
popd

pause
