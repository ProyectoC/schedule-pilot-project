package com.schedulepilot.core.entities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_chart_loan_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewChartLoanProduct {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_account_id")
    private Long userAccountId;

    @Column(name = "username")
    private String username;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "date")
    private LocalDateTime date;
}
