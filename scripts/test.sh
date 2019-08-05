#!/usr/bin/env bash

oper="+"
oper2="-"

x=10
y=1
val=`expr ${x} ${oper} ${y}`
echo "${x} ${oper} ${y} = $val"
val=`expr ${x} ${oper2} ${y}`
echo "${x} ${oper2} ${y} = $val"
