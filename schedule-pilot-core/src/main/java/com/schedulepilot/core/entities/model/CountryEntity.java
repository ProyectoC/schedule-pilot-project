package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false, name = "iso", unique = true)
    private String iso;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "nicename", unique = true)
    private String niceName;

    @Column(nullable = false, name = "iso3")
    private String iso3;

    @Column(nullable = false, name = "numcode")
    private Integer numCode;

    @Column(nullable = false, name = "phonecode")
    private String phoneCode;
}
