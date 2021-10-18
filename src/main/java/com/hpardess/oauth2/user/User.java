package com.hpardess.oauth2.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
// @ToString(exclude="groups, instanceComments, environments")
// @EqualsAndHashCode
@Entity(name = "User")
@Table(name = "user_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    public enum Role { USER, ADMIN, USER_MANAGER }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tbl_generator")
    @SequenceGenerator(name = "user_tbl_generator", sequenceName = "user_tbl_seq", allocationSize = 1)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String signiture;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column
    private String phoneNo;

    // @Pattern( regexp = "(?=.*[0-9]).+" ),
    // @Pattern( regexp = "(?=.*[a-z]).+" ),
    // @Pattern( regexp = "(?=.*[A-Z]).+"),
    // @Pattern( regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+"),
    // @Pattern( regexp = "(?=\\S+$).+" )
    @NotNull
    @Size(min = 3, max = 100, message = "Password must at least 3 characters.")
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @Column
    @NotNull
    @Size(min = 3, max = 100, message = "Username must at least 3 characters.")
    private String email;

    @Column
    private String avatar;

    @Column
    private Boolean deleted;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public User(Long id, String name, String email, String avatar, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.active = active;
    }

    @JsonIgnore
    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", active=" + active + ", email=" + email + ", created_at=" + createdAt
                + ", updated_at=" + updatedAt + "]";
    }
}
