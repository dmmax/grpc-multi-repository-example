package me.dmmax.example.grpc.service.common.converter;

import com.google.common.base.Converter;
import me.dmmax.example.grpc.common.EntityId;
import me.dmmax.example.grpc.service.common.model.EntityIdDto;

public class EntityIdConverter extends Converter<EntityIdDto, EntityId> {

  @Override
  protected EntityId doForward(EntityIdDto dto) {
    return EntityId.newBuilder()
        .setUuid(dto.uuid())
        .build();
  }

  @Override
  protected EntityIdDto doBackward(EntityId entityId) {
    return new EntityIdDto(entityId.getUuid());
  }
}
