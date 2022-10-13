package me.dmmax.example.grpc.service.common.model;

import java.util.Objects;

public class EntityIdDto {

  private final String uuid;

  public EntityIdDto(String uuid) {
    this.uuid = uuid;
  }

  public String uuid() {
    return uuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityIdDto that = (EntityIdDto) o;
    return Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

  @Override
  public String toString() {
    return "EntityIdDto{" +
        "uuid='" + uuid + '\'' +
        '}';
  }
}
