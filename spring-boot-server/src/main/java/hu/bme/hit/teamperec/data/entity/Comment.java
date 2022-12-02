package hu.bme.hit.teamperec.data.entity;

import java.util.Date;
import javax.persistence.*;

import hu.bme.hit.teamperec.data.response.CommentResponse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caff_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CAFF caff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User commenter;

    private String commentText;

    @CreatedDate
    private Date createdAt;

    public CommentResponse toResponse() {
        return new CommentResponse(this.getId(),
                this.getCommenter().getUsername(),
                this.getCommentText(),
                this.getCreatedAt().toString());
    }

}
