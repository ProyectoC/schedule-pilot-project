package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "request_check_in")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckInEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "request_check_in_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "request_check_in_sequence_key_id",
            sequenceName = "request_check_in_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "track_id", unique = true)
    private String trackId;

    @ManyToOne
    @JoinColumn(name = "user_account_id_fk", nullable = false)
    private UserAccountEntity userAccountEntity;

    @OneToMany(mappedBy = "requestCheckInProductId.requestCheckInEntity",
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<RequestCheckInProductEntity> requestCheckInProductEntities;


    public List<RequestCheckInProductEntity> getRequestCheckInProductEntities() {
        if (requestCheckInProductEntities != null) {
            List<RequestCheckInProductEntity> result = new ArrayList<>();
            for (RequestCheckInProductEntity requestCheckInProductEntity : requestCheckInProductEntities) {
                RequestCheckInProductId requestCheckInProductId = new RequestCheckInProductId(requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity());
                result.add(new RequestCheckInProductEntity(requestCheckInProductId, requestCheckInProductEntity.getAttempts(),
                        requestCheckInProductEntity.getLoanDate(), requestCheckInProductEntity.getProductRequestStatusEntity()));
            }
            return result;
        } else {
            return null;
        }
    }

    public void addProductRole(RequestCheckInProductEntity requestCheckInProductEntity) {
        this.requestCheckInProductEntities.add(requestCheckInProductEntity);
        requestCheckInProductEntity.getRequestCheckInProductId().setRequestCheckInEntity(this);
    }
}
