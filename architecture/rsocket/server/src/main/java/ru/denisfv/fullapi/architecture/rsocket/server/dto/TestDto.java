package ru.denisfv.fullapi.architecture.rsocket.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

@EqualsAndHashCode(callSuper = false, of = "id")
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestDto extends AbstractEntity<Long> {

    @JsonProperty("test_id")
    Long id;

    String value;
}
