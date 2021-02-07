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
@Table(name = "v_chart_status_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewChartStatusOperation {

    @Id
    @Column(name = "user_account_id")
    private Long userAccountId;

    @Column(name = "requests")
    private Long requests;

    @Column(name = "tickets")
    private Long tickets;

    @Column(name = "refunds")
    private Long refunds;
}
