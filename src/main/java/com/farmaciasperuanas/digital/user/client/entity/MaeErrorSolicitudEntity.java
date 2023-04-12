package com.farmaciasperuanas.digital.user.client.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "MAE_ERROR_SOLICITUD",
        schema = "PTOVENTA"
)
@Data
public class MaeErrorSolicitudEntity {
    @Id
    @Column( name = "CO_INDICADOR_ERROR")
    private String coIndicadorError;
    @Column( name = "CO_ERROR")
    private String coError;
    @Column( name = "DE_ERROR_CORTA")
    private String deErrorCorta;
    @Column( name = "DE_ERROR_LARGA")
    private String deErrorLarga;
    @Column( name = "ES_ESTADO")
    private String esEstado;
}
