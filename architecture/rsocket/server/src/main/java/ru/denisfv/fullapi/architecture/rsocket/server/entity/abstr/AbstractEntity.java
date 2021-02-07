package ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class AbstractEntity<K> implements Serializable {

    @Transient
    @JsonIgnore
    K id = null;
}
