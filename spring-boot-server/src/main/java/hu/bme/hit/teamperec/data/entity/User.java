package hu.bme.hit.teamperec.data.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import hu.bme.hit.teamperec.data.response.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "uploader")
    private Set<CAFF> caffs = new HashSet<>();

    @OneToMany(mappedBy = "commenter")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "downloader")
    private Set<Download> downloads = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserResponse toResponse() {
        return new UserResponse(this.getId(),
                this.getUsername(),
                this.getEmail(),
                this.getDownloads().stream().map(Download::toResponse).collect(Collectors.toSet()),
                this.getCaffs().stream().map(CAFF::toUserResponse).collect(Collectors.toSet()));
    }
}
