package com.solo.api.models.diet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "food_data", schema = "ibge")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Data_IBGE {
    @Column
    private int codigo;

    @Column(nullable = false)
    private String descricao_do_alimento;

    @Column(nullable = false)
    private String Categoria;

    @Column(nullable = false)
    private String descricao_da_preparacao;
    
    @Column
    private double Energia_kcal;
    
    @Column
    private double Proteina_g;
    
    @Column
    private double Lipidios_totais_g;
    
    @Column
    private double Carboidrato_g;
    
    @Column
    private double Fibra_alimentar_total_g;
    
    @Column
    private double Colesterol_mg;
    
    @Column
    private double AG_Saturados_g;
    
    @Column
    private double AG_Mono_g;
    
    @Column
    private double AG_Poli_g;
    
    @Column
    private double AG_Linoleico_g;
    
    @Column
    private double AG_Linolenico_g;
    
    @Column
    private double AG_Trans_total_g;
    
    @Column
    private double Acucar_total_g;
    
    @Column
    private double Acucar_de_adicacao_g;
    
    @Column
    private double Calcio_mg;

    @Column
    private double Magnesio_mg;
    
    @Column
    private double Manganes_mg;
    
    @Column
    private double Fosforo_mg;
    
    @Column
    private double Ferro_mg;
    
    @Column
    private double Sodio_mg;
    
    @Column
    private double Sodio_de_adicao_mg;
    
    @Column
    private double Potassio_mg;
    
    @Column
    private double Cobre_mg;
    
    @Column
    private double Zinco_mg;
    
    @Column
    private double Selenio_mcg;
    
    @Column
    private double Retinol_mcg;
    
    @Column
    private double Vitamina_A_RAE_mcg;
    
    @Column
    private double Tiamina_mg;
    
    @Column
    private double Riboflavina_mg;
    
    @Column
    private double Niacina_mg;

    @Column
    private double Piridoxina_mg;
    
    @Column
    private double Cobalamina_mcg;
    
    @Column
    private double Folato_DFE_mcg;
    
    @Column
    private double Vitamina_D_mcg;
    
    @Column
    private double Vitamina_E_mg;
    
    @Column
    private double Vitamina_C_mg;

}
