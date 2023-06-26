package com.ufps.practicalogin.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @SequenceGenerator(name="bill_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="bill_id_seq")
    private Integer id;

    @Column(name = "date_bill", nullable = false)
    private LocalDate dateBill;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private Float value;

    @NotNull
    private Integer type;

    @Column(length = 120, nullable = false)
    private String observation;

}
