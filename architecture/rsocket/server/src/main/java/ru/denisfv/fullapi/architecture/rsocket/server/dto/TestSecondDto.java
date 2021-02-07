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
public class TestSecondDto extends AbstractEntity<String> {

    @JsonProperty("test_second_id")
    String id;

    String value;
}
