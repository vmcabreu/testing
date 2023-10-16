package com.crai.platform.event.webbroker.component;

import org.modelmapper.ModelMapper;

public class GenericMapper<I, O> implements RestMapper<I, O> {

   private ModelMapper modelMapper;
   private Class<I> entityClass;
   private Class<O> dtoClass;

   public GenericMapper(final ModelMapper modelMapper, Class<I> entity, Class<O> dto) {

      this.modelMapper = modelMapper;
      this.entityClass = entity;
      this.dtoClass = dto;
   }

   @Override
   public O toDTO(final I input) {
      return input != null ? modelMapper.map(input, dtoClass) : null;
   }

   @Override
   public I toEntity(final O input) {
      return input != null ? modelMapper.map(input, entityClass) : null;
   }
}