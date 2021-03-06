package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "user_account_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_account_sequence_key_id",
            sequenceName = "user_account_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "failed_attempts")
    private Integer failedAttempts;

    @Column(nullable = false, name = "password_expired_date")
    private LocalDateTime passwordExpiredDate;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "identification", unique = true)
    private String identification;

    @Column(nullable = true, name = "identification_code", unique = true)
    private String identificationCode;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(nullable = false, name = "email_backup", unique = true)
    private String emailBackup;

    @Column(nullable = false, name = "phone_number", unique = true)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_country_id_fk", nullable = false)
    private CountryEntity countryEntity;

    @Column(nullable = false, name = "block")
    private Boolean block = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_account_id_fk", nullable = false)
    private RolAccountEntity rolAccountEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_career_id_fk", nullable = true)
    private CollegeCareerEntity collegeCareerEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_token_id_fk", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TokenEntity authTokenEntity;

    @ManyToOne
    @JoinColumn(name = "activation_token_id_fk", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TokenEntity activationTokenEntity;

    @OneToMany(mappedBy = "userAccountEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserNotificationEntity> notificationEntities;
}
