package hu.bme.hit.teamperec.data.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.bme.hit.teamperec.data.response.DownloadResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "downloads")
@Getter
@Setter
public class Download extends BaseEntity {

    @ManyToOne
    private CAFF downloadedCaff;

    @ManyToOne
    private User downloader;

    @CreatedDate
    private Date downloadDate;

    public DownloadResponse toResponse() {
        return new DownloadResponse(this.getDownloadedCaff().getId(),
                this.getDownloadedCaff().getName(),
                this.getDownloadDate().toString());
    }

}
