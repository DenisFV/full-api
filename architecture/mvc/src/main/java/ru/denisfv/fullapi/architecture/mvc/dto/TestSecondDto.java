package ru.denisfv.fullapi.architecture.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

@EqualsAndHashCode(callSuper = false, of = "id")
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "TestSecondDto")
public class TestSecondDto extends AbstractEntity<String> {

    @Schema(description = "id", required = true, example = "1")
    @JsonProperty("test_second_id")
    String id;

    @Schema(description = "value")
    String value;
}
