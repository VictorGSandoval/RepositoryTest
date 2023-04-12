package com.farmaciasperuanas.digital.user.client.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "MAE_ESTADO",
        schema = "PTOVENTA"
)
@Data
public class MaeEstadoEntity {
    @Id
    @Column( name = "CO_ESTADO")
    private String coEstado;
    @Column( name = "DE_ESTADO")
    private String deEstado;
    @Column( name = "ES_ESTADO")
    private String esEstado;
}
