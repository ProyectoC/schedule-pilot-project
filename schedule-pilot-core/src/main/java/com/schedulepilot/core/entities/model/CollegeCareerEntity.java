package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "college_career")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollegeCareerEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "college_career_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "college_career_sequence_key_id",
            sequenceName = "college_career_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
