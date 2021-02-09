package com.schedulepilot.core.entities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_chart_status_returns_made")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewChartStatusReturnMade {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_account_id_fk")
    private Long userAccountId;

    @Column(name = "name")
    private String name;

    @Column(name = "returns")
    private Long returns;
}
