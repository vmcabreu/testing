package com.crai.platform.event.webbroker.component;

public interface RestMapper<I, O> {
   
   O toDTO(I input);
   I toEntity(O input);
}
