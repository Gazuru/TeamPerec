package hu.bme.hit.teamperec.data.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.bme.hit.teamperec.data.response.DownloadResponse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "downloads")
@Getter
@Setter
public class Download extends BaseEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CAFF downloadedCaff;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User downloader;

    private LocalDateTime downloadDate;

    public DownloadResponse toResponse() {
        return new DownloadResponse(this.getDownloadedCaff().getId(),
                this.getDownloadedCaff().getName(),
                this.getDownloadDate().toString());
    }

}
