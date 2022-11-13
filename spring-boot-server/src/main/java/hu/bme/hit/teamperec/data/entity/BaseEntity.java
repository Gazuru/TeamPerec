package hu.bme.hit.teamperec.data.entity;

import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;


@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "uuid-char")
    private UUID id;

}
