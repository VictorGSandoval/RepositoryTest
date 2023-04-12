package com.farmaciasperuanas.digital.user.client.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "MAE_TIPO_AFILIACION",
        schema = "PTOVENTA"
)
@Data
public class MaeTipoAfiliacionEntity {
    @Id
    @Column( name = "CO_AFILIACION")
    private String coAfiliacion;
    @Column( name = "DE_AFILIACION")
    private String deAfiliacion;
    @Column( name = "ES_ESTADO")
    private String esEstado;
}
