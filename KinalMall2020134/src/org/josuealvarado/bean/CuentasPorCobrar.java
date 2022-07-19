
package org.josuealvarado.bean;


public class CuentasPorCobrar {
    private int codigoCuentasPorCobrar;
    private String numeroFactura;
    private  int anio;
    private int mes;
    private double valorNetoPago;
    private int codigoAdministracion;
    private int CodigoCliente;
    private int codigoLocal;

    public CuentasPorCobrar() {
    }

    public CuentasPorCobrar(int codigoCuentasPorCobrar, String numeroFactura, int anio, int mes, double valorNetoPago, int codigoAdministracion, int CodigoCliente, int codigoLocal) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
        this.numeroFactura = numeroFactura;
        this.anio = anio;
        this.mes = mes;
        this.valorNetoPago = valorNetoPago;
        this.codigoAdministracion = codigoAdministracion;
        this.CodigoCliente = CodigoCliente;
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoCuentasPorCobrar() {
        return codigoCuentasPorCobrar;
    }

    public void setCodigoCuentasPorCobrar(int codigoCuentasPorCobrar) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }
    
    
}
