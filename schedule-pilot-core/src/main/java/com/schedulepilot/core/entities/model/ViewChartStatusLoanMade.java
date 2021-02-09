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
@Table(name = "v_chart_status_loans_made")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewChartStatusLoanMade {

    @Id
    @Column(name = "user_account_id_fk")
    private Long userAccountId;

    @Column(name = "name")
    private String name;

    @Column(name = "loans")
    private Long loans;
}
