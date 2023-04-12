package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.dto.MaeConvenioDto;
import com.farmaciasperuanas.digital.user.client.dto.MaeTipoProductoDto;
import com.farmaciasperuanas.digital.user.client.dto.PblTabGeneralDto;
import com.farmaciasperuanas.digital.user.client.entity.MetodoSitedEntity;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoSitedRepository extends JpaRepository<MetodoSitedEntity, String> {

    @Modifying
    @Procedure(procedureName = "PTOVENTA.PTOVENTA_CONV_SITEDS.INDICADOR_RIMAC")
    PblTabGeneralDto validateRimac();

    @Query(nativeQuery = true, value = "SELECT PTOVENTA.PTOVENTA_CONV_SITEDS.INDICADOR_RIMAC FROM DUAL")
    char indRimac();

    @Procedure(name = "PTOVENTA.PTOVENTA_CONV_SITEDS.GET_TIPO_PRODUCTO")
    MaeTipoProductoDto getTipoProducto(@Param("pCOD_PRODUCTO") String codProducto);
/*
    @Query(name = "call BTLPROD.PKG_SITED10.SPR_BUSCA_CONVENIO(:pCOD_CIA," +
            ":pCOD_LOCAL," +
            ":pCOD_IAFA," +
            ":pIN_BUSQUEDA," +
            ":pCOD_REFERENCIA," +
            ":pINDICADOR," +
            ":pCO_TI_PRODUCTO)",nativeQuery = true)
    List<MaeConvenioDto> getConvenio(@Param("pCOD_CIA") String codCia,
                                     @Param("pCOD_LOCAL") String codLocal,
                                     @Param("pCOD_IAFA") String coIafa,
                                     @Param("pIN_BUSQUEDA") String inBusqueda,
                                     @Param("pCOD_REFERENCIA") String coRef,
                                     @Param("pINDICADOR") String indicador,
                                     @Param("pCO_TI_PRODUCTO") String coTiProducto);
 */
    /*
    public default  String getTipoProducto(String codProducto) {
        String response = "";
        try {
            Session session = entityManager.unwrap(Session.class);
            String str = session.doReturningWork(
                    connection -> {
                        try (CallableStatement function = connection
                                .prepareCall("{ ? = call PTOVENTA.PTOVENTA_CONV_SITEDS.GET_TIPO_PRODUCTO(?) }")) {
                            function.registerOutParameter(1, Types.CHAR);
                            function.execute();
                            return function.getString(1);
                        }
                    }
            );
            response = str;
        } catch (Exception ex) {
            response = "N";
        }
        return response;
    }*/
}
