package com.farmaciasperuanas.digital.user.client.service.impl;

import com.farmaciasperuanas.digital.user.client.dto.*;
import com.farmaciasperuanas.digital.user.client.entity.*;
import com.farmaciasperuanas.digital.user.client.repository.*;
import com.farmaciasperuanas.digital.user.client.service.ClientService;
import com.farmaciasperuanas.digital.user.client.util.VariablesConvenioSITED;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.hibernate.DuplicateMappingException;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import pe.gob.susalud.jr.transaccion.susalud.bean.*;
import pe.gob.susalud.jr.transaccion.susalud.service.imp.*;

import javax.persistence.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import static org.aspectj.apache.bcel.Constants.types;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private EntityManagerFactory eMF;
    @Autowired
    private EntityManager eM;
    @Autowired
    MaeEstadoRepository maeEstado;
    @Autowired
    MaeExcepcionSitedsRepository maeExcepcion;
    @Autowired
    MaeTipoAfiliacionRepository maeAfiliacion;
    @Autowired
    MaeErrorSolicitudRepository maeErrorSolicitud;
    @Autowired
    MaeConvenioRepository maeConvenio;
    @Autowired
    MetodoSitedRepository metodoSited;
    //@Autowired
    //InConCod271 inConCod271e;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ModelMapper modelMapper;

    private ArrayList<String> lstCobertura;

    @Override
    public List<ConsultaDerivaResponseDto> decodeBeneficiario(List<ResponsePacienteDto> resp, String inDerivaAtencion) {
        List<ConsultaDerivaResponseDto> decodeResponse = new ArrayList<>();
        ResponsePacienteDto responsePaciente = resp.get(0);
        if (responsePaciente.getCodRespuesta() == 0) {
            if (inDerivaAtencion.equalsIgnoreCase("S")) {
                String trama = responsePaciente.getTramaRespuesta();
                ArrayList<In271ResDerivaDetalle> lstAfiliadosDerivacion;
                In271ResDerivaServiceImpl resDerivaImp = new In271ResDerivaServiceImpl();
                In271ResDeriva inResDeriva = resDerivaImp.x12NToBean(trama);
                lstAfiliadosDerivacion = inResDeriva.getIn271ResDerivaDetalles();
                for (int i = 0; i < lstAfiliadosDerivacion.size(); i++) {
                    ConsultaDerivaResponseDto consultaDerivaResponse = new ConsultaDerivaResponseDto();
                    In271ResDerivaDetalle detalle = (In271ResDerivaDetalle) lstAfiliadosDerivacion.get(i);
                    log.info("detalle {}", detalle.toString());
                    String fecha = detalle.getFeAtSalud();
                    String fechaFormato = fecha.substring(6) + "/" + fecha.substring(4, 6) + "/" + fecha.substring(0, 4);
                    consultaDerivaResponse.setDeProducto(detalle.getDeProducto());
                    consultaDerivaResponse.setApPaternoPaciente(detalle.getApPaternoPaciente());
                    consultaDerivaResponse.setApMaternoPaciente(detalle.getApMaternoPaciente());
                    consultaDerivaResponse.setNoPaciente(detalle.getNoPaciente());
                    consultaDerivaResponse.setNoLuAtencion(detalle.getNoLuAtencion());
                    consultaDerivaResponse.setFechaFormato(fechaFormato);
                    consultaDerivaResponse.setNuAtencion(detalle.getNuAtencion());
                    consultaDerivaResponse.setCoAfPaciente(detalle.getCoAfPaciente());
                    consultaDerivaResponse.setTiDocumento(detalle.getCoTiDoPaciente());
                    consultaDerivaResponse.setNuDocumento(detalle.getNuDoPaciente());
                    consultaDerivaResponse.setCoTiCobertura(detalle.getCoTiCobertura());
                    consultaDerivaResponse.setCoSuTiCobertura(detalle.getCoSubTiCobertura());
                    consultaDerivaResponse.setCoProducto(detalle.getCoTiProducto());
                    consultaDerivaResponse.setCoParentesco(detalle.getCoParentesco());
                    consultaDerivaResponse.setTiDoContratante(detalle.getTiDoContratante());
                    consultaDerivaResponse.setIdReContratante(detalle.getIdCaReferencia());
                    consultaDerivaResponse.setCoReContratante(detalle.getReIdContratante());
                    consultaDerivaResponse.setCoTiProducto(detalle.getCoTiProducto());
                    consultaDerivaResponse.setCaPaciente(detalle.getCaPaciente());
                    consultaDerivaResponse.setNuPlan("");
//                    decodeResponse = consultaDerivaResponse;
                    decodeResponse.add(consultaDerivaResponse);
                    String registro =
                            detalle.getDeProducto() + " || " + detalle.getApPaternoPaciente() + " || " + detalle.getApMaternoPaciente() +
                                    " || " + detalle.getNoPaciente() + " || " + detalle.getNoLuAtencion() + " || " + fechaFormato +
                                    " || " + detalle.getNuAtencion() + " || " + detalle.getCoAfPaciente() + " || " +
                                    detalle.getCoTiDoPaciente() + " || " + detalle.getNuDoPaciente() + " || " +
                                    detalle.getCoTiCobertura() + " || " + detalle.getCoSubTiCobertura() + " || " +
                                    detalle.getCoTiProducto() + " || " + detalle.getCoParentesco() + " || " +
                                    detalle.getTiDoContratante() + " || " + detalle.getIdCaReferencia() + " || " +
                                    detalle.getReIdContratante() + " || " + detalle.getCoTiProducto() + " || " +
                                    detalle.getCaPaciente();
                    log.info(registro);
                }
            } else {
                ArrayList<InConNom271Detalle> lstClinicasAfiliadas;
                ConNom271ServiceImpl conNomImp = new ConNom271ServiceImpl();
                InConNom271 inConNom = conNomImp.x12NToBean(responsePaciente.getTramaRespuesta());
                lstClinicasAfiliadas = inConNom.getInConNom271Detalles();
                for (int i = 0; i < lstClinicasAfiliadas.size(); i++) {
                    ConsultaDerivaResponseDto consultaDerivaResponse = new ConsultaDerivaResponseDto();
                    InConNom271Detalle detalle = (InConNom271Detalle) lstClinicasAfiliadas.get(i);
                    String deProducto = detalle.getCoProducto();
                    String estadoDetalle = "";
//                    pendiente a consultar sobre MetodoSITED: segun lo conversado esto hacer una consulta en BD local, función PTOVENTA.PTOVENTA_CONV_SITEDS
//                    segun revisión podria llamar a: PTOVENTA.PTOVENTA_CONV_SITEDS.GET_ESTADO
                    List<MaeEstadoEntity> estado = maeEstado.findByCoEstado(detalle.getCoEsPaciente());
                    if (estado.size()>0) {
                        estadoDetalle = estado.get(0).getDeEstado();
                    }
                    consultaDerivaResponse.setDeProducto(detalle.getCoProducto());
                    consultaDerivaResponse.setApPaternoPaciente(detalle.getApPaternoPaciente());
                    consultaDerivaResponse.setApMaternoPaciente(detalle.getApMaternoPaciente());
                    consultaDerivaResponse.setNoPaciente(detalle.getNoPaciente());
                    consultaDerivaResponse.setNoLuAtencion(estadoDetalle);
                    consultaDerivaResponse.setFechaFormato("");
                    consultaDerivaResponse.setNuAtencion(detalle.getCoAfPaciente());
                    consultaDerivaResponse.setCoAfPaciente(detalle.getCoAfPaciente());
                    consultaDerivaResponse.setTiDocumento(detalle.getTiDoPaciente());
                    consultaDerivaResponse.setNuDocumento(detalle.getNuDoPaciente());
                    consultaDerivaResponse.setCoTiCobertura("");
                    consultaDerivaResponse.setCoSuTiCobertura("");
                    consultaDerivaResponse.setCoProducto(detalle.getCoProducto());
                    consultaDerivaResponse.setCoParentesco(detalle.getCoParentesco());
                    consultaDerivaResponse.setTiDoContratante(detalle.getTiDoContratante());
                    consultaDerivaResponse.setIdReContratante(detalle.getIdReContratante());
                    consultaDerivaResponse.setCoReContratante(detalle.getCoReContratante());
                    consultaDerivaResponse.setCoTiProducto("");
                    consultaDerivaResponse.setCaPaciente(detalle.getCaPaciente());
                    consultaDerivaResponse.setNuPlan(detalle.getNuPlan());
//                    decodeResponse = consultaDerivaResponse;
                    decodeResponse.add(consultaDerivaResponse);
                    String registro =
                            deProducto + " || " + detalle.getApPaternoPaciente() + " || " + detalle.getApMaternoPaciente() +
                                    " || " + detalle.getNoPaciente() + " || " + estadoDetalle + " || " + "" + " || " +
                                    detalle.getCoAfPaciente() + " || " + detalle.getCoAfPaciente() + " || " +
                                    detalle.getTiDoPaciente() + " || " + detalle.getNuDoPaciente() + " || " + "" + " || " + "" +
                                    " || " + detalle.getCoProducto() + " || " + detalle.getCoParentesco() + " || " +
                                    detalle.getTiDoContratante() + " || " + detalle.getIdReContratante() + " || " +
                                    detalle.getCoReContratante() + " || " + "" + " || " + detalle.getCaPaciente();
                    log.info(registro);
                }
            }
        } else {
            ConsultaDerivaResponseDto consultaDerivaResponse = new ConsultaDerivaResponseDto();
            if (responsePaciente.getCodRespuesta() == 4004) {
                consultaDerivaResponse.setDeProducto("4004");
                consultaDerivaResponse.setApMaternoPaciente("Mensaje Sited !!!");
                List<MaeExcepcionSitedsEntity> exception = maeExcepcion.findByCoExcepcion(responsePaciente.getTramaRespuesta());
                if( exception.size()>0) {
                    consultaDerivaResponse.setNoPaciente(exception.get(0).getDeExcepcionCorta());
                } else {
                    consultaDerivaResponse.setNoPaciente("Código de Excepción Sited no encontrado");
                }
            } else {
                consultaDerivaResponse.setDeProducto(responsePaciente.getCodRespuesta().toString());
                consultaDerivaResponse.setApMaternoPaciente("Mensaje Sited !!!");
                consultaDerivaResponse.setNoPaciente("Paciente no encontrado Excepción - 4005");
            }
            decodeResponse.add(consultaDerivaResponse);
        }
        return decodeResponse;
    }

    @Override
    public List<ConsultaAsegResponseDto> decodeCobertura(List<ResponseCoberturaDto> resp, String inBusqueda, String deComercial, String idReceptor, String coTipoProducto, String coConvenioSel) throws SQLException {
        List<ConsultaAsegResponseDto> decodeResponse = new ArrayList<>();
        ResponseCoberturaDto responseCobertura = resp.get(0);
        log.info(responseCobertura.getTramaRespuesta());
        if (responseCobertura.getCodRespuesta() == 0) {
            ConsultaAsegResponseDto consultaAsegResponse = new ConsultaAsegResponseDto();
            InConCod271 inConCod271 = null;
            inConCod271 = new ConCod271ServiceImpl().x12NToBean(responseCobertura.getTramaRespuesta());
            log.info("decodeCobertura.inConCod271 {}", inConCod271.toString());
            StringBuilder builder = new StringBuilder(inConCod271.getApPaternoPaciente());
            builder.append(" ").append(inConCod271.getApMaternoPaciente());
            builder.append(" ").append(inConCod271.getNoPaciente());
            String deEstado = "";
            List<MaeEstadoEntity> estado = maeEstado.findByCoEstado(inConCod271.getCoEsPaciente());
            if (estado.size()>0) {
                deEstado = estado.get(0).getDeEstado();
            }
            String deAfiliacion = "";
            List<MaeTipoAfiliacionEntity> afiliacion = maeAfiliacion.findByCoAfiliacion(inConCod271.getCoTiPoliza());
            if (afiliacion.size()>0) {
                deAfiliacion = afiliacion.get(0).getDeAfiliacion();
            }
            //FUNCIONES QUE NO SE SABEN LO QUE HACEN
            String grupoCia = "001";
            String codLocal = "AD1";
            List<MaeConvenioDto> lstConvenios = this.getConvenios(grupoCia,codLocal, inConCod271, inBusqueda, idReceptor, coTipoProducto);
            if (lstConvenios.size()>0) {
                List<MaeConvenioDto> lstCuentaConvenio = this.getCuentaConvenio(lstConvenios);
                if (lstCuentaConvenio.size()>1){
                    //EN POSU ABRE VENTANA PARA SELECCIONAR CNV (solo debe trabajar con 1 cnv)
                    //OBTIENE COBERTURA DE CNV SELECCIONADOS
                    //*** PARA DU DEBE RETORNAR LISTA DE CNV,
                    // DU DEBE CONSUMIR OTRO ENDPOINT ENVIANDO LOS CNV SELECCCIONADOS CON LA MISMA ESTRUCTURA RECIBIDA
                    // PARA LISTAR COBERTURA
                    if (!(coConvenioSel.trim().isEmpty() || coConvenioSel.equalsIgnoreCase(null) || coConvenioSel.trim().length() == 0 || coConvenioSel.equalsIgnoreCase(""))){
                        List<MaeConvenioDto> tmpConvenio = new ArrayList<MaeConvenioDto>();
                        for (int y = 0; y < lstConvenios.size(); y++) {
                            if (lstConvenios.get(y).getCoConvenio().equalsIgnoreCase(coConvenioSel)) {
                                tmpConvenio.add(lstConvenios.get(y));
                            }
                        }
                        decodeResponse = this.getCoverage(inConCod271.getInConCod271Detalles(), tmpConvenio, idReceptor, responseCobertura, builder, deEstado, deComercial, deAfiliacion, inConCod271);
                    } else {
                        List<MaeConvenioDto> lConvenios = lstCuentaConvenio;
                        consultaAsegResponse.setLstConvenios(lConvenios);
                        decodeResponse.add(consultaAsegResponse);
                    }
                } else {
                    if (this.indConfigSited(lstConvenios.get(0).getCoConvenio()).equalsIgnoreCase("S")) {
                        VariablesConvenioSITED.vSeleccionarConvenio = true;
                        //LISTA COBERTURA
                        decodeResponse = this.getCoverage(inConCod271.getInConCod271Detalles(), lstConvenios, idReceptor, responseCobertura, builder, deEstado, deComercial, deAfiliacion, inConCod271);
                    } else {
                        VariablesConvenioSITED.vSeleccionarConvenio = false;
                        log.info("Convenio mal configurado, Por favor, comuníquese con el área de Convenios, Convenio: {}", lstConvenios.get(0).getCoConvenio());
                    }
                }
            } else {
                if (this.indRimac().equalsIgnoreCase("S")) {
                    VariablesConvenioSITED.vSeleccionarConvenio = false;
                } else {
                    VariablesConvenioSITED.vSeleccionarConvenio = true;
                    consultaAsegResponse.setApellidosNombres("40004");
                    consultaAsegResponse.setEstado("Mensaje Sited !!!");
                    consultaAsegResponse.setNDocumento("No se encontraron Convenios/Coberturas Activas - RAC");
                    log.info(consultaAsegResponse.getNDocumento());
                    decodeResponse.add(consultaAsegResponse);
                }
            }
        } else {
            VariablesConvenioSITED.vSeleccionarConvenio = true;
            ConsultaAsegResponseDto consultaAsegResponse = new ConsultaAsegResponseDto();
            if (responseCobertura.getCodRespuesta() == 4004) {
                List<MaeExcepcionSitedsEntity> excepcion = maeExcepcion.findByCoExcepcion(responseCobertura.getTramaRespuesta());
                consultaAsegResponse.setApellidosNombres(responseCobertura.getCodRespuesta().toString());
                consultaAsegResponse.setEstado("Mensaje Sited !!!");
                if (excepcion.size()>0) {
                    consultaAsegResponse.setNDocumento(excepcion.get(0).getDeExcepcionCorta());
                } else {
                    consultaAsegResponse.setNDocumento("Código de Excepción Sited no encontrado");
                }
            } else {
                consultaAsegResponse.setApellidosNombres(responseCobertura.getCodRespuesta().toString());
                consultaAsegResponse.setEstado("Mensaje Sited !!!");
                consultaAsegResponse.setNDocumento("No existen Coberturas Activas - Excepción - 4005");
            }
            decodeResponse.add(consultaAsegResponse);
        }
        log.info("--- end method ServiceImpl.decodeCobertura, response: {} ---", decodeResponse.toString());
        return decodeResponse;
    }

    @Override
    public List<NumAutorizacionResponseDto> decodeAutorizacion(List<ResponseAutorizacionDto> resp) {
        List<NumAutorizacionResponseDto> decodeResponse = new ArrayList<>();
        ResponseAutorizacionDto responseAutorizacion = resp.get(0);
        log.info(responseAutorizacion.getTramaRespuesta());
        NumAutorizacionResponseDto numAutorizacionResponse;
        if (responseAutorizacion.getCodRespuesta() == 0) {
            In997ResAutServiceImpl resAutServImp = new In997ResAutServiceImpl();
            In997ResAut inResAut = resAutServImp.x12NToBean(responseAutorizacion.getTramaRespuesta());
            log.info("N. Autorización: " + inResAut.getNuAutorizacion());
            log.info("C. Error: " + inResAut.getCoError());
            log.info("Ind. Error: " + inResAut.getInCoErrorEncontrado());
            String codigoError = inResAut.getCoError();
            if (codigoError.equalsIgnoreCase(VariablesConvenioSITED.ACEPTADO_SIN_ERRORES) ||
                codigoError.equalsIgnoreCase(VariablesConvenioSITED.ACEPTADO_CON_ERRORES) ||
                codigoError.equalsIgnoreCase(VariablesConvenioSITED.ATENCION_YA_GENERADA)) {
                numAutorizacionResponse = new NumAutorizacionResponseDto();
                numAutorizacionResponse.setNuAutorizacion(inResAut.getNuAutorizacion());
                numAutorizacionResponse.setCoError(inResAut.getCoError());
                numAutorizacionResponse.setInCoErrorEncontrado(inResAut.getInCoErrorEncontrado());
            } else {
                numAutorizacionResponse = new NumAutorizacionResponseDto();
                List<MaeErrorSolicitudEntity> errorSolicitud = maeErrorSolicitud.findByCoIndicadorErrorAndCoError(inResAut.getInCoErrorEncontrado(),inResAut.getCoError());
                numAutorizacionResponse.setNuAutorizacion("4004");
                numAutorizacionResponse.setCoError("Error al Generar N° de Autorización !!!");
                if (errorSolicitud.size()>0) {
                    numAutorizacionResponse.setInCoErrorEncontrado(errorSolicitud.get(0).getDeErrorLarga());
                } else {
                    numAutorizacionResponse.setInCoErrorEncontrado("Código de ErrorSolicitud no encontrado !!!");
                }
            }
            decodeResponse.add(numAutorizacionResponse);
        } else {
            numAutorizacionResponse = new NumAutorizacionResponseDto();
            if(responseAutorizacion.getCodRespuesta() == 4004) {
                List<MaeExcepcionSitedsEntity> excepcion = maeExcepcion.findByCoExcepcion(responseAutorizacion.getTramaRespuesta());
                numAutorizacionResponse.setNuAutorizacion(responseAutorizacion.getCodRespuesta().toString());
                numAutorizacionResponse.setCoError("Error al Generar N° de Autorización !!!");
                if (excepcion.size()>0) {
                    numAutorizacionResponse.setInCoErrorEncontrado(excepcion.get(0).getDeExcepcionLarga());
                } else {
                    numAutorizacionResponse.setInCoErrorEncontrado("Código de Excepción no encontrado !!!");
                }
            } else {
                numAutorizacionResponse.setNuAutorizacion(responseAutorizacion.getCodRespuesta().toString());
                numAutorizacionResponse.setCoError("Error al Generar N° de Autorización !!!");
                numAutorizacionResponse.setInCoErrorEncontrado("Error al Generar N° de Autorización - Excepción - 4005 !!!");
            }
            decodeResponse.add(numAutorizacionResponse);
        }
        return decodeResponse;
    }

    private List<MaeConvenioDto> getConvenios(String grupoCia, String codLocal, InConCod271 inConCod271, String inBusqueda, String coIafa, String coTiProducto) throws SQLException {
        log.info("--- start method ServiceImpl.getConvenios ---");
        String indicador = "N";
        String response = "";
        Boolean estadoRimac = false;
        if (coIafa.trim().equalsIgnoreCase("20007")) {
            coIafa = "20002";
        } else if (coIafa.trim().equalsIgnoreCase("40007")) {
            coIafa = "40004";
        }
        String coRef = "";
        if (inBusqueda.equalsIgnoreCase("1") || inBusqueda.equalsIgnoreCase("4")) {
            coRef = inConCod271.getCoReContratante();
        } else if (inBusqueda.equalsIgnoreCase("2")) {
            coRef = inConCod271.getCoProducto();
        } else {
            coRef = inConCod271.getIdRemitente();
        }
        if (coIafa.equalsIgnoreCase(VariablesConvenioSITED.COD_IAFA_RIMAC)) {
            //if (metodoSited.validateRimac().getVRESPONSE().equalsIgnoreCase("S")) {
            if (this.indRimac().equalsIgnoreCase("S")) {
                response = this.getTypeProduct(coTiProducto);
                if (response.equalsIgnoreCase("N")) {
                    estadoRimac = true;
                    //msg indicando que no existe codigo tipo producto, comunicarse con el área de convenios
                    //return null;
                }
                indicador = "S";
            } else {
                indicador = "N";
            }
        }

        List data = this.searchAgreements(grupoCia, codLocal, coIafa, inBusqueda, coRef, indicador, response);
        List lstConvenio = mapList(data, MaeConvenioDto.class);

        return lstConvenio;
    }

    private List<MaeConvenioDto> getCuentaConvenio(List<MaeConvenioDto> lstConvenios) {
        log.info("--- start method ServiceImpl.getCuentaConvenio ---");
        ArrayList<MaeConvenioDto> lstCuentaConvenio = new ArrayList<MaeConvenioDto>();
        MaeConvenioDto convenio = null;
        int cantidad = 0;
        if (lstConvenios.size()>0) {
            convenio = new MaeConvenioDto();
            convenio.setCoConvenio(lstConvenios.get(0).getCoConvenio());
            convenio.setDeConvenio(lstConvenios.get(0).getDeConvenio());
            lstCuentaConvenio.add(convenio);
            for (int i = 0; i < lstConvenios.size(); i++) {
                if (lstCuentaConvenio.get(0).getCoConvenio().equalsIgnoreCase(lstConvenios.get(i).getCoConvenio())) {
                    continue;
                } else {
                    for (int x = 0; x < lstCuentaConvenio.size(); x++) {
                        if (!lstCuentaConvenio.get(x).getCoConvenio().equalsIgnoreCase(lstConvenios.get(i).getCoConvenio())) {
                            cantidad++;
                        }
                    }
                    if (lstCuentaConvenio.size() == cantidad) {
                        cantidad = 0;
                        convenio = new MaeConvenioDto();
                        convenio.setCoConvenio(lstConvenios.get(i).getCoConvenio());
                        convenio.setDeConvenio(lstConvenios.get(i).getDeConvenio());
                        lstCuentaConvenio.add(convenio);
                    }
                    cantidad = 0;
                }
            }
        }
        return lstCuentaConvenio;
    }

    public List<ConsultaAsegResponseDto> getCoverage(List<InConCod271Detalle> lstDetalle,
                                                      List<MaeConvenioDto> lstConvenio, String idReceptor,
                                                      ResponseCoberturaDto responseCobertura, StringBuilder builder,
                                                      String deEstado, String deComercial, String deAfiliacion,
                                                      InConCod271 inConCod271) {
        log.info("--- start method ServiceImpl.getCoverage ---");
        List<ConsultaAsegResponseDto> lstAsegCodResponse = new ArrayList<ConsultaAsegResponseDto>();
        ConsultaAsegResponseDto consultaAsegResponse = null;
        matchCoverage(lstDetalle, lstConvenio, idReceptor, responseCobertura);
        if (lstCobertura.size()>0) {
            InConCod271Detalle detalleCobertura = null;
            Iterator iteratorCobertura = lstCobertura.iterator();
            log.info("getCoverage.iteratorCobertura {}", iteratorCobertura.toString());
            log.info("getCoverage.inConCod271 {}", inConCod271.toString());
            while (iteratorCobertura.hasNext()) {
                consultaAsegResponse = new ConsultaAsegResponseDto();
                consultaAsegResponse.setApellidosNombres(builder.toString());
                consultaAsegResponse.setEstado(deEstado);
                consultaAsegResponse.setNDocumento(inConCod271.getNuDoPaciente());
                consultaAsegResponse.setProducto(deComercial);
                consultaAsegResponse.setNContrato(inConCod271.getNuContratoPaciente());
                consultaAsegResponse.setTipoAfiliacion(deAfiliacion);
                consultaAsegResponse.setCodigo(iteratorCobertura.next().toString());
                consultaAsegResponse.setCoberturas(iteratorCobertura.next().toString());
                consultaAsegResponse.setRestricciones(iteratorCobertura.next().toString());
                consultaAsegResponse.setCopagoFijo(iteratorCobertura.next().toString());
                consultaAsegResponse.setCopagoVariable(iteratorCobertura.next().toString());
                consultaAsegResponse.setFinDeCarencia(iteratorCobertura.next().toString());
                consultaAsegResponse.setCondEspeciales(iteratorCobertura.next().toString());

                consultaAsegResponse.setCoEsPaciente(inConCod271.getCoEsPaciente());
                consultaAsegResponse.setTiDoPaciente(inConCod271.getTiDoPaciente());
                consultaAsegResponse.setCoTiPoliza(inConCod271.getCoTiPoliza());
                consultaAsegResponse.setNuPlan(inConCod271.getNuPlan());
                consultaAsegResponse.setTiPlanSalud(inConCod271.getTiPlanSalud());
                consultaAsegResponse.setCoMoneda(inConCod271.getCoMoneda());
                consultaAsegResponse.setFeNacimiento(inConCod271.getFeNacimiento());
                consultaAsegResponse.setGenero(inConCod271.getGenero());
                consultaAsegResponse.setEsMarital(inConCod271.getEsMarital());
                consultaAsegResponse.setFeIniVigencia(inConCod271.getFeIniVigencia());
                consultaAsegResponse.setTiCaContratante(inConCod271.getTiCaContratante());
                consultaAsegResponse.setNoPaContratante(inConCod271.getNoPaContratante());
                consultaAsegResponse.setNoContratante(inConCod271.getNoContratante());
                consultaAsegResponse.setNoMaContratante(inConCod271.getNoMaContratante());
                consultaAsegResponse.setCaTitular(inConCod271.getCaTitular());
                consultaAsegResponse.setNoPaTitular(inConCod271.getNoPaTitular());
                consultaAsegResponse.setNoTitular(inConCod271.getNoTitular());
                consultaAsegResponse.setCoAfTitular(inConCod271.getCoAfTitular());
                consultaAsegResponse.setNoMaTitular(inConCod271.getNoMaTitular());
                consultaAsegResponse.setTiDoTitular(inConCod271.getTiDoTitular());
                consultaAsegResponse.setNuDoTitular(inConCod271.getNuDoTitular());
                consultaAsegResponse.setFeInsTitular(inConCod271.getFeInsTitular());

                detalleCobertura = new InConCod271Detalle();

                for (InConCod271Detalle detalle : inConCod271.getInConCod271Detalles()) {
                    log.info("getCoverage.detalle.coPagoVariable {}", detalle.getCoPagoVariable());
                    String codigo = detalle.getCoTiCobertura() + detalle.getCoSubTiCobertura();
                    if (codigo.equalsIgnoreCase(consultaAsegResponse.getCodigo())) {
                        detalleCobertura = detalle;
                        log.info("getCoverage.detalleCobertura.coPagoVariable {}", detalleCobertura.getCoPagoVariable());
                        break;
                    }
                }

                consultaAsegResponse.setIdProducto(detalleCobertura.getIdProducto());
                consultaAsegResponse.setBeMaxInicial(detalleCobertura.getBeMaxInicial());
                consultaAsegResponse.setCoTiCobertura(detalleCobertura.getCoTiCobertura());
                consultaAsegResponse.setCoSubTiCobertura(detalleCobertura.getCoSubTiCobertura());
                consultaAsegResponse.setCoTiMoneda(detalleCobertura.getCoTiMoneda());
                consultaAsegResponse.setCoPaFijo(detalleCobertura.getCoPagoFijo());
                consultaAsegResponse.setCoCalServicio(detalleCobertura.getCoCalServicio());
                consultaAsegResponse.setCoPagoVariable(detalleCobertura.getCoPagoVariable());
                consultaAsegResponse.setFlagCaGarantia(detalleCobertura.getFlagCaGarantia());
                consultaAsegResponse.setCoConvenio(lstConvenio.get(0).getCoConvenio());
                consultaAsegResponse.setNuPoliza(inConCod271.getNuPoliza());
                consultaAsegResponse.setCoParentesco(inConCod271.getCoParentesco());
                log.info("consultaAsegResponse.setCoPagoVariable {}", consultaAsegResponse.getCoPagoVariable());
                lstAsegCodResponse.add(consultaAsegResponse);
            }
        } else {
            consultaAsegResponse = new ConsultaAsegResponseDto();
            consultaAsegResponse.setApellidosNombres("4004");
            consultaAsegResponse.setEstado("Mensaje Sited !!!");
            consultaAsegResponse.setNDocumento("No existen Coberturas Activas");
            lstAsegCodResponse.add(consultaAsegResponse);
        }
        return lstAsegCodResponse;
    }
    private void matchCoverage(List<InConCod271Detalle> lstDetalle, List<MaeConvenioDto> lstConvenios,
                               String idReceptor, ResponseCoberturaDto responseCobertura ) {
        log.info("--- start method ServiceImpl.matchCoverage ---");
        lstCobertura = new ArrayList<String>();
        for (InConCod271Detalle detalle : lstDetalle) {
            String codigoCobertura = detalle.getCoTiCobertura() + detalle.getCoSubTiCobertura();
            log.info("--- Codigo Cobertura desde SITED: {} ---", codigoCobertura);
            String deCobertura = "";
            List dataSybTypeCoverage = this.getSubTypeCoverage(detalle.getCoTiCobertura(), detalle.getCoSubTiCobertura());
            log.info("--- List data: {} ---", dataSybTypeCoverage.toString());
            List lstSubTipoCobertura = mapList(dataSybTypeCoverage, MaeSubTipoCoberturaDto.class);
            List<MaeSubTipoCoberturaDto> subTipoCobertura = lstSubTipoCobertura;
            if (subTipoCobertura.size()>0) {
                deCobertura = subTipoCobertura.get(0).getDeSubTipoCobertura();
            }
            String restricciones = detalle.getCoInRestriccion().equalsIgnoreCase("0") ? "Ninguna" : "Si";
            String deMoneda = "";
            List dataCurrency = this.getCurrency(detalle.getCoTiMoneda());
            log.info("--- List data: {} ---", dataCurrency.toString());
            List lstCurrency = mapList(dataCurrency, MaeMonedaDto.class);
            List<MaeMonedaDto> maeMoneda = lstCurrency;
            if (maeMoneda != null) {
                deMoneda = maeMoneda.get(0).getDesMoneda();
            }
            String califS = "";
            List dataCalifServicio = this.getCalifService(detalle.getCoCalServicio());
            log.info("--- List data: {} ---", dataCalifServicio.toString());
            List lstCalifServicio = mapList(dataCalifServicio, MaeCalifServicioDto.class);
            List<MaeCalifServicioDto> maeCalifServicio = lstCalifServicio;
            if (maeCalifServicio != null) {
                califS = maeCalifServicio.get(0).getDeCalifServicio();
            }
            log.info("detalle.coPagoFijo {}", detalle.getCoPagoFijo());
            log.info("detalle.coPagoVariable {}", detalle.getCoPagoVariable());
            String coPagoFijo = "";
            String coPagoVariable = "";
            coPagoFijo = this.Redondear(this.validaCoPago(detalle.getCoPagoFijo()), 2) + " " + deMoneda + " " + califS;
            coPagoVariable = "CUBIERTO AL " + this.Redondear(this.validaCoPago(detalle.getCoPagoVariable()), 2) + "%";
            if (VariablesConvenioSITED.COD_IAFA_RIMAC.equalsIgnoreCase(idReceptor) &&
                detalle.getCoInRestriccion().equalsIgnoreCase("1")){
                if (responseCobertura.getCodRespuestaRimac() == 0) {
                    InConProc271 inCoProcedimiento = null;
                    inCoProcedimiento = new In271ConProcServiceImpl().x12NToBean(responseCobertura.getTramaRespuestaRimac());
                    ArrayList<InConProc271Detalle> procDetalles = inCoProcedimiento.getInConProc271Detalles();
                    for (InConProc271Detalle procDetalle : procDetalles) {
                        if (!procDetalle.getCoProcedimiento().equalsIgnoreCase(VariablesConvenioSITED.TIPO_PROCEDIMIENTO)) {
                            continue;
                        } else {
                            log.info("procDetalle.imDeducible {}", procDetalle.getImDeducible());
                            log.info("procDetalle.poCuExDecimal() {}", procDetalle.getPoCuExDecimal());
                            coPagoFijo = this.Redondear(this.validaCoPago(procDetalle.getImDeducible()), 2) + " " + deMoneda + " " + califS;
                            coPagoVariable = "CUBIERTO AL " + this.Redondear(this.validaCoPago(procDetalle.getPoCuExDecimal()), 2) + "%";
                            break;
                        }
                    }

                }
            }
            for (MaeConvenioDto convenio : lstConvenios) {
                if (codigoCobertura.equalsIgnoreCase(convenio.getCoCobertura())) {
                    lstCobertura.add(codigoCobertura);
                    lstCobertura.add(deCobertura);
                    lstCobertura.add(restricciones);
                    lstCobertura.add(coPagoFijo);
                    lstCobertura.add(coPagoVariable);
                    lstCobertura.add(" ");
                    lstCobertura.add(detalle.getMsgConEspeciales());
                }
            }
        }
    }
    public String indConfigSited(String codConvenio) {
        log.info("--- start method ServiceImpl.indConfigSited ---");
        String response;
        Query fn = eM.createNativeQuery("SELECT PTOVENTA.PTOVENTA_CONV_SITEDS.VALIDA_CONFIG_SITED(:codConvenio) FROM DUAL");
        fn.setParameter("codConvenio", codConvenio);
        response = (String) fn.getSingleResult();
        return response;
    }
    public String indRimac() {
        log.info("--- start method ServiceImpl.indRimac ---");
        String response;
        Query sp = eM.createNativeQuery("SELECT PTOVENTA.PTOVENTA_CONV_SITEDS.INDICADOR_RIMAC FROM DUAL");
        response = (String) sp.getSingleResult();
        return response;
    }

    public String getTypeProduct(String coTiProducto) {
        log.info("--- start method ServiceImpl.getTypeProduct ---");
        String response;
        Query sp = eM.createNativeQuery("SELECT PTOVENTA.PTOVENTA_CONV_SITEDS.GET_TIPO_PRODUCTO(:pCOD_PRODUCTO) FROM DUAL");
        sp.setParameter("pCOD_PRODUCTO", coTiProducto);
        log.info("--- execute call DB: {} --- values: {} ---", sp.unwrap(org.hibernate.query.Query.class).getQueryString(), sp.unwrap(Query.class).getParameterValue("pCOD_PRODUCTO"));
        response = (String) sp.getSingleResult();
        return response;
    }

    public List getSubTypeCoverage(String coTiCobertura, String coSubTiCobertura) {
        log.info("--- start method ServiceImpl.getSubTypeCoverage ---");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("PTOVENTA")
                .withCatalogName("PTOVENTA_CONV_SITEDS")
                .withFunctionName("GET_SUB_TIPO_COBERTURA");
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("pCO_TIPO_COBERTURA", coTiCobertura, Types.VARCHAR)
                .addValue("pCO_SUB_TIPO_COBERTURA", coSubTiCobertura, Types.VARCHAR);
        return jdbcCall.executeFunction(List.class, paramMap);
    }
    public List getCurrency(String coMoneda) {
        log.info("--- start method ServiceImpl.getCurrency ---");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("PTOVENTA")
                .withCatalogName("PTOVENTA_CONV_SITEDS")
                .withFunctionName("GET_TIPO_MONEDA");
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("pCOD_MONEDA", coMoneda, Types.VARCHAR);
        return jdbcCall.executeFunction(List.class, paramMap);
    }
    public List getCalifService(String coCalifService) {
        log.info("--- start method ServiceImpl.getCalifService ---");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("PTOVENTA")
                .withCatalogName("PTOVENTA_CONV_SITEDS")
                .withFunctionName("GET_CALIF_SERVICIO");
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("pCO_CALIF_SERVICIO", coCalifService, Types.VARCHAR);
        return jdbcCall.executeFunction(List.class, paramMap);
    }
    public List searchAgreements(String grupoCia, String codLocal, String coIafa, String inBusqueda, String coRef, String indicador, String response) throws SQLException {
        log.info("--- start method ServiceImpl.searchAgreements ---");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("BTLPROD")
                .withCatalogName("PKG_SITED10")
                .withFunctionName("SPR_BUSCA_CONVENIO");
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("pCOD_CIA", grupoCia, Types.VARCHAR)
                .addValue("pCOD_LOCAL", codLocal, Types.VARCHAR)
                .addValue("pCOD_IAFA", coIafa, Types.VARCHAR)
                .addValue("pIN_BUSQUEDA", inBusqueda, Types.CHAR)
                .addValue("pCOD_REFERENCIA", coRef, Types.VARCHAR)
                .addValue("pINDICADOR", indicador, Types.CHAR)
                .addValue("pCO_TI_PRODUCTO", response, Types.CHAR)
                ;
        log.info("--- execute call objDB: {} params: {} ---", jdbcCall.getSchemaName()+"."+jdbcCall.getCatalogName()+"."+jdbcCall.getProcedureName(), paramMap.toString());
        return jdbcCall.executeFunction(List.class, paramMap);
    }
    private double validaCoPago(String coPago) {
        double valor;
        if (coPago.trim().isEmpty() || coPago.equalsIgnoreCase(null) || coPago.trim().length() == 0 || coPago.equalsIgnoreCase("")) {
            valor = 0.0;
        } else {
            valor = Double.parseDouble(coPago);
        }
        return valor;
    }
    private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
    public static double Redondear(double nD, int nDec) {
        return Math.round(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
    }
}
