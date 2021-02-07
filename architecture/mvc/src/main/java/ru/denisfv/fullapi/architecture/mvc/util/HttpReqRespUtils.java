package ru.denisfv.fullapi.architecture.mvc.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Достает IP клиента, пробегаясь по всем header-ам
 */
@Slf4j
public class HttpReqRespUtils {

    static List<String> IP_HEADER_NAMES = new ArrayList<>(Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA"));

    public static String getRemoteIP(HttpServletRequest request) {

        if (request == null) return "0.0.0.0";

        log.debug("IP_HEADER_NAMES : {}",
                IP_HEADER_NAMES.stream().map(e -> e + " : " + request.getHeader(e)).collect(Collectors.toList()));

        Optional<String[]> ip = IP_HEADER_NAMES.stream()
                .map(request::getHeader)
                .filter(e -> Objects.nonNull(e) && e.length() > 0 && !"unknown".equalsIgnoreCase(e))
                .map(e -> e.split(","))
                .findFirst();

        return ip
                .map(e -> e[0])
                .orElseGet(request::getRemoteAddr);
    }
}
