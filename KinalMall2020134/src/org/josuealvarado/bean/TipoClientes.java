
package org.josuealvarado.bean;


public class TipoClientes {
    
    private int codigoTipoCliente;
    private String descripcion;

    public TipoClientes() {
    }

    public TipoClientes(int codigoTipoCliente, String descripcion) {
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoTipoCliente() + "  | " + getDescripcion(); 
    }
    
    
}
