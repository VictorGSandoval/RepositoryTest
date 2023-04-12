package com.farmaciasperuanas.digital.user.client.rest;

import com.farmaciasperuanas.digital.user.client.dto.*;
import com.farmaciasperuanas.digital.user.client.entity.MaeEstadoEntity;
import com.farmaciasperuanas.digital.user.client.repository.MaeEstadoRepository;
import com.farmaciasperuanas.digital.user.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
public class ClientRest {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/decode-beneficiario",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsultaDerivaResponseDto> decodeBeneficiario(@RequestBody List<ResponsePacienteDto> resp, @RequestParam(value = "inDerivaAtencion") String inDerivaAtencion){
        log.info("--- start method decodeBeneficiario --- inDerivaAtencion: {}", inDerivaAtencion);
        return clientService.decodeBeneficiario(resp, inDerivaAtencion);
    }

    @RequestMapping(value = "/decode-cobertura",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsultaAsegResponseDto> decodeCobertura(@RequestBody List<ResponseCoberturaDto> resp,
                                                         @RequestParam(value = "inBusqueda") String inBusqueda,
                                                         @RequestParam(value = "deComercial") String deComercial,
                                                         @RequestParam(value = "idReceptor") String idReceptor,
                                                         @RequestParam(value = "coTipoProducto") String coTipoProducto,
                                                         @RequestParam(value = "coConvenioSel") String coConvenioSel) throws SQLException {
        log.info("--- start method decodeCobertura ---");
        return clientService.decodeCobertura(resp, inBusqueda, deComercial, idReceptor, coTipoProducto, coConvenioSel);
    }

    @PostMapping(value = "/decode-autorizacion")
    public List<NumAutorizacionResponseDto> decodeAutorizacion(@RequestBody List<ResponseAutorizacionDto> resp) {
        log.info("--- start method decodeAutorizacion ---");
        return clientService.decodeAutorizacion(resp);
    }
}
