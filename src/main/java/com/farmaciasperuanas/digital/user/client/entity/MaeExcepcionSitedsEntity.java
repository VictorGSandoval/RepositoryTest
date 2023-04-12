package com.farmaciasperuanas.digital.user.client.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "MAE_EXCEPCION_SITEDS",
        schema = "PTOVENTA"
)
@Data
public class MaeExcepcionSitedsEntity {
    @Id
    @Column( name = "CO_EXCEPCION")
    private String coExcepcion;
    @Column( name = "DE_EXCEPCION_CORTA")
    private String deExcepcionCorta;
    @Column( name = "DE_EXCEPCION_LARGA")
    private String deExcepcionLarga;
    @Column( name = "ES_ESTADO")
    private String esEstado;
}
