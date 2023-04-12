package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.entity.MaeConvenioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public interface MaeConvenioRepository extends JpaRepository<MaeConvenioEntity, String> {

    @PersistenceContext
    EntityManager entityManager = null;
    public default List<Object[]> buscaConvenios(String p1, String p2, String p3, String p4, String p5, String p6, String p7){
        List<Object[]> data = null;
        StoredProcedureQuery sp = entityManager.createStoredProcedureQuery("BTLPROD.PKG_SITED10.SPR_BUSCA_CONVENIO");
        sp.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(7, Long.class, ParameterMode.IN);
        sp.setParameter(1, p1);
        sp.setParameter(2, p2);
        sp.setParameter(3, p3);
        sp.setParameter(4, p4);
        sp.setParameter(5, p5);
        sp.setParameter(6, p6);
        sp.setParameter(7, p7);
        sp.execute();
        data = sp.getResultList();
        return data;
    }

}
