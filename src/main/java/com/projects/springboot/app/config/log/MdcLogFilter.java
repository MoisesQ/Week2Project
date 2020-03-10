package com.projects.springboot.app.config.log;

import java.util.UUID;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class MdcLogFilter implements WebFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(MdcLogFilter.class);
  
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    UUID uuid = UUID.randomUUID();
    String path = exchange.getRequest().getURI().getPath();

    return chain.filter(exchange).doAfterTerminate(() -> {

      MDC.put("transaction.id", uuid.toString());
      MDC.put("transaction.path", path);
      MDC.put("transaction.status", exchange.getResponse().getStatusCode());
      LOGGER.info("Served transaction ");

    });
  }

}
