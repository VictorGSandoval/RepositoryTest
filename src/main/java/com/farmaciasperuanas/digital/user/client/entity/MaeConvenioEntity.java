package com.farmaciasperuanas.digital.user.client.entity;

import com.farmaciasperuanas.digital.user.client.dto.MaeConvenioDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(
        name = "MAE_CONVENIO",
        schema = "CMR"
)
@NamedNativeQuery(name="searchAgreementsQ",
                  query="{ ? = call BTLPROD.PKG_SITED10_E.SPR_BUSCA_CONVENIO(:pCOD_CIA,:pCOD_LOCAL,:pCOD_IAFA,:pIN_BUSQUEDA,:pCOD_REFERENCIA,:pINDICADOR,:pCO_TI_PRODUCTO) }",
                  hints = {@javax.persistence.QueryHint(name="org.hibernate.callable", value = "true")},
                  resultClass = MaeConvenioEntity.class)
@Data
public class MaeConvenioEntity {
    @Column( name = "CIA")
    private String cia;
    @Id
    @Column( name = "COD_CONVENIO")
    private String codConvenio;
    @Column( name = "DES_CONVENIO")
    private String desConvenio;
    @Column( name = "COD_CLIENTE")
    private String codCliente;
    @Column( name = "FLG_TIPO_CONVENIO")
    private String flgTipoConvenio;
    @Column( name = "PCT_BENEFICIARIO")
    private String pctBeneficiario;
    @Column( name = "PCT_EMPRESA")
    private String pctEmpresa;
    @Column( name = "IMP_LINEA_CREDITO")
    private String impLineaCredito;
    @Column( name = "FLG_POLITICA")
    private String flgPolitica;
    @Column( name = "FLG_PETITORIO")
    private String flgPetitorio;
    @Column( name = "FLG_PERIODO_VALIDEZ")
    private String flgPeriodoValidez;
    @Column( name = "FCH_INICIO")
    private String fchInicio;
    @Column( name = "FCH_FIN")
    private String fchFin;
    @Column( name = "FLG_RENOVACION_AUTO")
    private String flgRenovacionAuto;
    @Column( name = "FLG_ATENCION_LOCAL")
    private String flgAtencionLocal;
    @Column( name = "FLG_ATENCION_DELIVERY")
    private String flgAtencionDelivery;
    @Column( name = "IMP_MINIMO_DELIVERY")
    private String impMinimoDelivery;
    @Column( name = "FLG_TIPO_PERIODO")
    private String flgTipoPeriodo;
    @Column( name = "DIA_CORTE")
    private String diaCorte;
    @Column( name = "CTD_CANCELACION")
    private String ctdCancelacion;
    @Column( name = "FLG_BENEFICIARIOS")
    private String flgBeneficiarios;
    @Column( name = "COD_TIPDOC_CLIENTE")
    private String codTipDocCliente;
    @Column( name = "COD_TIPDOC_BENEFICIARIO")
    private String codTipDocBeneficiario;
    @Column( name = "FLG_NOTACREDITO")
    private String flgNotaCredito;
    @Column( name = "FLG_FACTURA_LOCAL")
    private String flgFacturaLocal;
    @Column( name = "IMP_MINIMO")
    private String impMinimo;
    @Column( name = "IMP_PRIMERA_COMPRA")
    private String impPrimeraCompra;
    @Column( name = "COD_CLASE_CONVENIO")
    private String codClaseConvenio;
    @Column( name = "COD_PETITORIO")
    private String codPetitorio;
    @Column( name = "FLG_TARJETA")
    private String flgTarjeta;
    @Column( name = "DES_OBSERVACION")
    private String desObservacion;
    @Column( name = "COD_USUARIO_ACTUALIZA")
    private String codUsuarioActualiza;
    @Column( name = "FCH_ACTUALIZA")
    private String fchActualiza;
    @Column( name = "FLG_TIPO_VALOR")
    private String flgTipoValor;
    @Column( name = "IMP_VALOR")
    private String impValor;
    @Column( name = "FLG_TIPO_PRECIO")
    private String flgTipoPrecio;
    @Column( name = "FLG_TIPO_PRECIO_LOCAL")
    private String flgTipoPrecioLocal;
    @Column( name = "COD_LOCAL_REF")
    private String codLocalRef;
    @Column( name = "FLG_COBRO_TODOS_LOCALES")
    private String flgCobroTodosLocales;
    @Column( name = "FLG_REPARTIDOR")
    private String flgRepartidor;
    @Column( name = "FLG_ATENCION_TODOS_LOCALES")
    private String flgAtencionTodosLocales;
    @Column( name = "FLG_MEDICO")
    private String flgMedico;
    @Column( name = "COD_USUARIO")
    private String codUsuario;
    @Column( name = "FCH_REGISTRA")
    private String fchRegistra;
    @Column( name = "FLG_ACTIVO")
    private String flgActivo;
    @Column( name = "USUARIO")
    private String usuario;
    @Column( name = "FECHA")
    private String fecha;
    @Column( name = "NUM_DOC_FLG_KDX")
    private String numDocFlgKdx;
    @Column( name = "FLG_TIPO_PAGO")
    private String flgTipoPago;
    @Column( name = "COD_CONVENIO_GLM")
    private String codConvenioGlm;
    @Column( name = "TMP_RUC")
    private String tmpRuc;
    @Column( name = "FLG_VALIDA_LINCRE_BENEF")
    private String flgValidaLincreBenef;
    @Column( name = "FLG_APLICA_DSCTO_PFIJO")
    private String flgAplicaDsctoPfijo;
    @Column( name = "FLG_DIAGNOSTICO")
    private String flgDiagnostico;
    @Column( name = "FLG_RECETA")
    private String flgReceta;
    @Column( name = "FLG_IMPRIME_IMPORTES")
    private String flgImprimeImportes;
    @Column( name = "FLG_PRECIO_MENOR")
    private String flgPrecioMenor;
    @Column( name = "FLG_PRECIO_DEDUCIBLE")
    private String flgPrecioDeducible;
    @Column( name = "FLG_DATA_RIMAC")
    private String flgDataRimac;
    @Column( name = "FLG_CONV_ASEGURADORA")
    private String flgConvAseguradora;
    @Column( name = "FLG_IMPRIME_COPAGO")
    private String flgImprimeCopago;
    @Column( name = "FCH_IMPRESION_FIN")
    private String fchImpresionFin;
    @Column( name = "FCH_IMPRESION_INI")
    private String fchImpresionIni;
    @Column( name = "DES_IMPRESION")
    private String desImpresion;
    @Column( name = "NUM_NIVELES")
    private String numNiveles;
    @Column( name = "NUM_MAX_BENEF")
    private String numMaxBenef;
    @Column( name = "MTO_MAX_LINEA_COMPRA")
    private String mtoMaxLineaCompra;
    @Column( name = "FLG_PER_ANU_DOC")
    private String flgPerAnuDoc;
    @Column( name = "FLG_PER_NOT_CRE")
    private String flgPerNotCre;
    @Column( name = "FLG_AFILIACION_ACT")
    private String flgAfiliacionAct;
    @Column( name = "FLG_ESCALA_UNICA")
    private String flgEscalaUnica;
    @Column( name = "COD_EJECUTIVO")
    private String codEjecutivo;
    @Column( name = "COD_TRAMA")
    private String codTrama;
    @Column( name = "DES_DESTINATARIO_TRAMA")
    private String desDestinatarioTrama;
    @Column( name = "FLG_PLAN_VITAL")
    private String flgPlanVital;
    @Column( name = "CTD_MAX_UND_PRODUCTO")
    private String ctdMaxUndProducto;
    @Column( name = "COD_CONCEPTO")
    private String codConcepto;
    @Column( name = "MTO_LINEA_CRED_BASE")
    private String mtoLineaCredBase;
    @Column( name = "FLG_ING_BENEFICIARIO")
    private String flgIngBeneficiario;
    @Column( name = "FLG_COMPETENCIA")
    private String flgCompetencia;
    @Column( name = "COD_MATERIAL_SAP")
    private String codMaterialSap;
    @Column( name = "COD_CLIENTE_SAP_BOLSA")
    private String codClienteSapBolsa;
    @Column( name = "COD_TIPO_CONVENIO")
    private String codTipoConvenio;
    @Column( name = "MSG_CONVENIO")
    private String msgConvenio;
    @Column( name = "COD_CONVENIO_REL")
    private String codConvenioRel;
    @Column( name = "FLG_CREACION_CLIENTE")
    private String flgCreacionCliente;
    @Column( name = "FLG_COD_BARRA")
    private String flgCodBarra;
    @Column( name = "TIPO_CABECERA")
    private String tipoCabecera;
    @Column( name = "FLG_VOUCHER_CABECERA")
    private String flgVoucherCabecera;
    @Column( name = "FLG_VOUCHER_DATO_ASEG")
    private String flgVoucherDatoAseg;
    @Column( name = "FLG_VOUCHER_DATO_RETEN")
    private String flgVoucherDatoReten;
    @Column( name = "FLG_VOUCHER_FIRMA")
    private String flgVoucherFirma;
    @Column( name = "FLG_VOUCHER_CREDITO")
    private String flgVoucherCredito;
    @Column( name = "IND_VTA_COMPLEMENTARIA")
    private String indVtaComplementaria;
    @Column( name = "COD_MATERIAL_SAP_INAF")
    private String codMaterialSapInaf;
    @Column( name = "CODIGO_CONVENIO_EMP")
    private String codigoConvenioEmp;
    @Column( name = "FLG_BENEF_ONLINE")
    private String flgBenefOnline;
    @Column( name = "TIP_SELEC_CLI")
    private String tipSelecClit;
    @Column( name = "FLG_HASTA")
    private String flgHasta;
    @Column( name = "COD_CLASIFICACION")
    private String codClasificacion;
    @Column( name = "FLG_SITED")
    private String flgSited;
    @Column( name = "REL_CONV_INK")
    private String relConvInk;
    @Column( name = "COD_VALIDADOR")
    private String codValidador;
    @Column( name = "FLG_DNI_CODBARRA")
    private String flgDniCodBarra;
    @Column( name = "FLG_TIPO_PROD")
    private String flgTipoProd;
    @Column( name = "FLG_GARANTIZADOS")
    private String flgGarantizados;
    @Column( name = "FLG_LOCAL_MF")
    private String flgLocalMf;
    @Column( name = "FLG_LOCAL_IK")
    private String flgLocalIk;
    //

    @Column( name = "DECONVENIO")
    private String deConvenio;
    @Column( name = "PCDESCUENTO")
    private String pcDescuento;
    @Column( name = "VAMONTOCOPAGO")
    private String vaMontoCoPago;
    @Column( name = "PCCOPAGO")
    private String pcCoPago;
    @Column( name = "TIORDENCOPAGO")
    private String tiOrdenCoPago;
    @Column( name = "TICONVENIO")
    private String tiConvenio;
    @Column( name = "COCONVENIO")
    private String coConvenio;
    @Column( name = "NUDIALIQUIDACION")
    private String nuDiaLiquidacion;
    @Column( name = "VAMONTOTOPECOBERTURA")
    private String vaMontoTopeCobertura;
    @Column( name = "VAMONTOMAXIMOCOMPRA")
    private String vaMontoMaximoCompra;
    @Column( name = "COTITULAR")
    private String coTitular;
    @Column( name = "DETITULAR")
    private String deTitular;
    @Column( name = "COBENEFICIARIO")
    private String coBeneficiario;
    @Column( name = "DEBENEFICIARIO")
    private String deBeneficiario;
    @Column( name = "COCOBERTURA")
    private String coCobertura;
    @Column( name = "VAPESOCOBERTURA")
    private String vaPesoCobertura;
}
