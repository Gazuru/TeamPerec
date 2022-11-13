package hu.bme.hit.teamperec.data.entity;

import javax.persistence.*;

import hu.bme.hit.teamperec.data.enums.ERole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}