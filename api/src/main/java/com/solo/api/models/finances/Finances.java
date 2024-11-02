package com.solo.api.models.finances;

import java.util.Date;

import com.solo.api.models.user.SoloUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Finances", schema = "appSolo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Finances {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActivity;

    @ManyToOne
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_Finances_SoloUser"))
    private SoloUser idUser;

    @Column( length = 50,nullable = false)
    private String transactionType;

    @Column( length = 15,nullable = false)
    private String movementType;

    @Column(nullable = false)
    private Double moneyValue;

    @Column(nullable = false)
    private Date activityDate;

    @Column(length = 50, nullable = false)
    private String label;

    @Column
    private String description;

}
