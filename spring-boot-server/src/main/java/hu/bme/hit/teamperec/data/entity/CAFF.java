package hu.bme.hit.teamperec.data.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import hu.bme.hit.teamperec.data.response.CAFFResponse;
import hu.bme.hit.teamperec.data.response.CAFFUserResponse;
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String caffEncodedString;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String gifEncodedString;

    @OneToMany(mappedBy = "caff")
    private Set<Comment> comments = new HashSet<>();

    @CreatedDate
    private Date uploadedAt;

    public CAFFResponse toResponse() {
        return new CAFFResponse(this.getId(),
                this.getName(),
                this.getDescription(),
                this.getComments().stream().map(Comment::toResponse).toList(),
                this.getGifEncodedString(),
                this.getUploader().getId(),
                this.getUploader().getUsername());
    }

    public CAFFUserResponse toUserResponse() {
        return new CAFFUserResponse(this.getId(), this.getName());
    }

}
