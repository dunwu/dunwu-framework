@echo off
rem -------------------------------------------------
rem  本脚本用于发布 dunwu-boot 到中央仓库
rem  环境要求：Maven + JDK8
rem -------------------------------------------------

pushd %~dp0..
call mvn clean deploy -DskipTests
popd

pause
