package ru.denisfv.fullapi.architecture.rsocket.client.dto.abstr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class AbstractEntity<K> implements Serializable {

    @JsonIgnore
    K id = null;
}
