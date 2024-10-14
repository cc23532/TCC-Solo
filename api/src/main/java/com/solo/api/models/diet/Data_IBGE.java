
package com.solo.api.models.diet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ibge_food_data", schema = "appSolo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Data_IBGE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int codigo;

    @Column(nullable = false)
    private String descricao_do_alimento;

    @Column(nullable = false)
    private String Categoria;

    @Column(nullable = false)
    private String descricao_da_preparacao;
    
    @Column
    private Double Energia_kcal;
    
    @Column
    private Double Proteina_g;
    
    @Column
    private Double Lipidios_totais_g;
    
    @Column
    private Double Carboidrato_g;
    
    @Column
    private Double Fibra_alimentar_total_g;
    
    @Column
    private Double Colesterol_mg;
    
    @Column
    private Double AG_Saturados_g;
    
    @Column
    private Double AG_Mono_g;
    
    @Column
    private Double AG_Poli_g;
    
    @Column
    private Double AG_Linoleico_g;
    
    @Column
    private Double AG_Linolenico_g;
    
    @Column
    private Double AG_Trans_total_g;
    
    @Column
    private Double Acucar_total_g;
    
    @Column
    private Double Acucar_de_adicacao_g;
    
    @Column
    private Double Calcio_mg;

    @Column
    private Double Magnesio_mg;
    
    @Column
    private Double Manganes_mg;
    
    @Column
    private Double Fosforo_mg;
    
    @Column
    private Double Ferro_mg;
    
    @Column
    private Double Sodio_mg;
    
    @Column
    private Double Sodio_de_adicao_mg;
    
    @Column
    private Double Potassio_mg;
    
    @Column
    private Double Cobre_mg;
    
    @Column
    private Double Zinco_mg;
    
    @Column
    private Double Selenio_mcg;
    
    @Column
    private Double Retinol_mcg;
    
    @Column
    private Double Vitamina_A_RAE_mcg;
    
    @Column
    private Double Tiamina_mg;
    
    @Column
    private Double Riboflavina_mg;
    
    @Column
    private Double Niacina_mg;

    @Column
    private Double Piridoxina_mg;
    
    @Column
    private Double Cobalamina_mcg;
    
    @Column
    private Double Folato_DFE_mcg;
    
    @Column
    private Double Vitamina_D_mcg;
    
    @Column
    private Double Vitamina_E_mg;
    
    @Column
    private Double Vitamina_C_mg;

}
