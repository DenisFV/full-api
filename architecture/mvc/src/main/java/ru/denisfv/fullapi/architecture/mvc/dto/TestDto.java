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
@Schema(description = "TestDto")
public class TestDto extends AbstractEntity<Long> {

    @Schema(description = "id", required = true, example = "1")
    @JsonProperty("test_id")
    Long id;

    @Schema(description = "value")
    String value;
}
