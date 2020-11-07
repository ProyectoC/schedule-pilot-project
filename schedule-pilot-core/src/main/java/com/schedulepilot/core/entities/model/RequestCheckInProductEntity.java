package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
}
