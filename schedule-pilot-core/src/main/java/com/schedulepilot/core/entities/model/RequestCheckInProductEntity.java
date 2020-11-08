package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_check_in_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckInProductEntity extends BaseEntity implements Serializable {

    @EmbeddedId
    private RequestCheckInProductId requestCheckInProductId;

    @Column(nullable = false, name = "count")
    private int count;

    @Column(nullable = false, name = "loan_date")
    private LocalDateTime loanDate;

    @ManyToOne
    @JoinColumn(name = "product_request_status_id_fk", nullable = false)
    private ProductRequestStatusEntity productRequestStatusEntity;
}
