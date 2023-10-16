package com.crai.platform.starter.event.autoconfig;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import com.crai.platform.starter.event.type.EventType;
import com.crai.platform.starter.event.type.EventTypeMgr;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class EventTypeAutoConfig {

  @Autowired
  EventsProperties eventsProperties;

  @Bean
  public Set<String> eventTypeClasses() {
    ClassPathScanningCandidateComponentProvider scanner =
      new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(EventType.class));

    Set<String> eventTypeClasses=scanner.findCandidateComponents(eventsProperties.scanBasePackage).stream()
      .map(BeanDefinition::getBeanClassName)
      .collect(Collectors.toSet());
    log.info("===== Found eventTypeClasses => {}",eventTypeClasses);
    return Collections.unmodifiableSet(eventTypeClasses);
  }

  @Bean
  public EventTypeMgr eventTypeMgr(){
    return new EventTypeMgr(eventTypeClasses());
  }
}
