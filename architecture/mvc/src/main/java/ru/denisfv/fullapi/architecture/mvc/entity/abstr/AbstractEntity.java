package ru.denisfv.fullapi.architecture.mvc.entity.abstr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@EqualsAndHashCode
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractEntity<K> implements Serializable {

    @Transient
    @JsonIgnore
    K id = null;
}
