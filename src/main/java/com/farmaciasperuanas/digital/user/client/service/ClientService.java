package com.farmaciasperuanas.digital.user.client.service;

import com.farmaciasperuanas.digital.user.client.dto.*;
import com.farmaciasperuanas.digital.user.client.entity.MaeEstadoEntity;
import com.farmaciasperuanas.digital.user.client.repository.MaeEstadoRepository;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    List<ConsultaDerivaResponseDto> decodeBeneficiario(List<ResponsePacienteDto> resp, String inDerivaAtencion);
    List<ConsultaAsegResponseDto> decodeCobertura(List<ResponseCoberturaDto> resp, String inBusqueda, String deComercial, String idReceptor, String coTipoProducto, String coConvenioSel) throws SQLException;
    List<NumAutorizacionResponseDto> decodeAutorizacion(List<ResponseAutorizacionDto> resp);

}
