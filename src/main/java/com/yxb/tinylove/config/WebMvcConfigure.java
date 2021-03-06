package com.yxb.tinylove.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yxb.tinylove.netty.NettyServerListener;
import com.yxb.tinylove.netty.WsHandler;
import com.yxb.tinylove.netty.handler.GetCurrentLoginUser;
import com.yxb.tinylove.netty.handler.MsgHandler;
import com.yxb.tinylove.netty.handler.OnlineUserListHandler;
import com.yxb.tinylove.netty.handler.PingHandler;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * @author yxb
 * @since 2019/12/17
 */
@Configuration
@EnableWebMvc
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonConverter());
        converters.add(stringConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }

    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        jsonConverter.setFastJsonConfig(config);
        return jsonConverter;
    }

    @Bean
    public StringHttpMessageConverter stringConverter() {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        return stringConverter;
    }

    @Bean
    public ServletListenerRegistrationBean<NettyServerListener> serverListenerServletListenerRegistrationBean(NettyServerListener nettyServerListener) {
        ServletListenerRegistrationBean<NettyServerListener> srb = new ServletListenerRegistrationBean<>();
        srb.setListener(nettyServerListener);
        return srb;
    }
}
