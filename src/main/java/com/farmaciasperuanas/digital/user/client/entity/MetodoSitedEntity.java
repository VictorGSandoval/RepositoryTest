package com.farmaciasperuanas.digital.user.client.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@Data
public class MetodoSitedEntity {
    @Id
    private String id;
}
