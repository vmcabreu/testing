package com.crai.platform.starter.event.type;

import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventTypeMgr {

  Set<String> eventTypeClasses;

  public EventTypeMgr(Set<String> eventTypeClasses){
    this.eventTypeClasses=eventTypeClasses;
  }

  public Class<?> resolveEventType(String eventType){
    List<String> matchedEventTypeSet=eventTypeClasses.stream()
      .filter(eventPojoClass ->this.matchEventType(eventPojoClass,eventType))
      .collect(Collectors.toList());

    if (matchedEventTypeSet.isEmpty()){
      throw new IllegalArgumentException("EventType class "+eventType+" can not be found.");
    }
    if (matchedEventTypeSet.size() > 1){
      throw new IllegalArgumentException("Multiple EventType classes found for "+eventType);
    }
    return ClassUtils.resolveClassName(matchedEventTypeSet.get(0),null);
  }

  private boolean matchEventType(String eventTypeClass, String eventType){
    return eventTypeClass!=null && (eventTypeClass.equals(eventType) || ClassUtils.getShortName(eventTypeClass).equals(eventType));
  }
}
