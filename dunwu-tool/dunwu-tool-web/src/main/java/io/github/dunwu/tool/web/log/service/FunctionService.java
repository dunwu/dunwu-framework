package io.github.dunwu.tool.web.log.service;

public interface FunctionService {

    String parse(String methodName, String value);

    boolean isBeforeFunction(String functionName);

}
