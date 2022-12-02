package hu.bme.hit.teamperec.data.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "caffs")
@Getter
@Setter
public class CAFF extends BaseEntity {

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    //@Lob
    private String caffEncodedString;

    private String gifEncodedString;

    @OneToMany(mappedBy = "caff")
    private Set<Comment> comments = new HashSet<>();

    @CreatedDate
    private Date uploadedAt;

}
