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
@Table(name = "v_chart_dashboard_loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewChartDashboardLoans {

    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "loans")
    private Long loans;
}
