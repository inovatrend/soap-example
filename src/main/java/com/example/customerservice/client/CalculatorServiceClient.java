package com.example.customerservice.client;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.CalculatorSoap;

public class CalculatorServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorServiceClient.class);

    public static void main(String[] args) {
        CalculatorServiceClient client = new CalculatorServiceClient();
        CalculatorSoap calculatorSoap12 = client.calculatorProxy();
        int addResponse = calculatorSoap12.add(1, 3);
        logger.info("Received addResponse: {}+{}={}", 1, 3, addResponse);

        int multiplyResponse = calculatorSoap12.multiply(15, 13);
        logger.info("Received multiplyResponse: {}*{}={}", 15, 13, multiplyResponse);

        System.exit(0);
    }

    public CalculatorSoap calculatorProxy() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(CalculatorSoap.class);
        jaxWsProxyFactoryBean.setAddress("http://www.dneonline.com/calculator.asmx");

        // add an interceptor to log the outgoing request messages
        jaxWsProxyFactoryBean.getOutInterceptors().add(loggingOutInterceptor());
        // add an interceptor to log the incoming response messages
        jaxWsProxyFactoryBean.getInInterceptors().add(loggingInInterceptor());
        // add an interceptor to log the incoming fault messages
        jaxWsProxyFactoryBean.getInFaultInterceptors().add(loggingInInterceptor());

        return (CalculatorSoap) jaxWsProxyFactoryBean.create();
    }

    public LoggingOutInterceptor loggingOutInterceptor() {
        return new LoggingOutInterceptor();
    }

    public LoggingInInterceptor loggingInInterceptor() {
        return new LoggingInInterceptor();
    }
}
