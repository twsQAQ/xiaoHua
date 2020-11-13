package com.skyi.ietm.config;

import com.skyi.ietm.interceptor.WebServiceAuthInterceptor;
import com.skyi.ietm.interceptor.WebServiceLogInterceptor;
import com.skyi.ietm.service.UserService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;
    @Autowired
    private UserService userService;


    /**
     * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/userService?wsdl
     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/ietmWebService/userService?wsdl
     */
    @Bean
    public ServletRegistrationBean webServiceServlet(){
        return new ServletRegistrationBean(new CXFServlet(),"/ietmWebService/*");// 发布服务名称 localhost:8080/ietmWebService
    }

    /**
     * 发布服务
     * 指定访问url
     * @return
     */
    @Bean
    public Endpoint userServiceEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus,userService);
        // 添加请求和响应的拦截器（传入链只对In有效，传出链只对Out有效 ）
        endpoint.getInInterceptors().add(new WebServiceAuthInterceptor());//添加请求权限拦截器
        endpoint.getInInterceptors().add(new WebServiceLogInterceptor());//添加请求日志拦截器
        //endpoint.getOutInterceptors().add(new WebServiceLogInterceptor());//添加响应日志拦截器
        endpoint.publish("/userService");// 接口访问地址
        return endpoint;
    }
}
