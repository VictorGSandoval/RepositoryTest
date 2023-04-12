package com.farmaciasperuanas.digital.user.client.dto;

public class NumAutorizacionResponseDto {
    String NuAutorizacion;
    String CoError;
    String InCoErrorEncontrado;

    public String getNuAutorizacion() {
        return NuAutorizacion;
    }

    public void setNuAutorizacion(String nuAutorizacion) {
        NuAutorizacion = nuAutorizacion;
    }

    public String getCoError() {
        return CoError;
    }

    public void setCoError(String coError) {
        CoError = coError;
    }

    public String getInCoErrorEncontrado() {
        return InCoErrorEncontrado;
    }

    public void setInCoErrorEncontrado(String inCoErrorEncontrado) {
        InCoErrorEncontrado = inCoErrorEncontrado;
    }
}
