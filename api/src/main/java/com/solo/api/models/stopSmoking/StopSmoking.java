package com.solo.api.models.stopSmoking;

import com.solo.api.models.user.SoloUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Entity
@Table(name = "StopSmoking", schema = "appSolo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StopSmoking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCount;

    @OneToOne
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_Finances_SoloUser"))
    private SoloUser idUser;

    @Column(nullable = false)
    private OffsetDateTime baseDate;

    @Column(nullable = false)
    private Integer cigsPerDay;

    @Column(nullable = false)
    private Integer cigsPerPack;

    @Column(nullable = false)
    private Double packPrice;
}
