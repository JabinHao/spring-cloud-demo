package com.olivine.user.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author jphao
 * @Date 12:47 2021/12/28
 * @Description
 */
@Component
public class HeaderOriginParser implements RequestOriginParser {

    private final Set<String> applications = Stream.of("order", "user").collect(Collectors.toSet());

    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String origin = httpServletRequest.getHeader("origin");
        return (origin == null || !applications.contains(origin)) ? "blank" : origin;
    }
}
