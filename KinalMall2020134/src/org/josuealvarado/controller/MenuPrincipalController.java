
package org.josuealvarado.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.josuealvarado.system.Principal;


public class MenuPrincipalController implements Initializable  {
    
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void VentanaAdministracion(){
        escenarioPrincipal.VentanaAdministracion();
    }
    
    public void VentanaLocales(){
        escenarioPrincipal.VentanaLocales();
        
    }
    
    public void VentanaTipoClientes(){
        escenarioPrincipal.VentanaTipoClientes();
        
    }
    
    public void VentanaClientes(){
        escenarioPrincipal.VentanaClientes();
    }
    
    public void VentanaDepartamentos(){
        escenarioPrincipal.VentanaDepartamentos();
    }
    
    public void VentanaProveedores(){
        escenarioPrincipal.VentanaProveedores();
    }
    
    public void VentanaCuentasPorPagar(){
        escenarioPrincipal.VentanaCuentasPorPagar(); 
    }
    
    public void VentanaHorarios (){
        escenarioPrincipal.VentanaHorarios();
    }
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaUsuarios();
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargos();
    }
    
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
}
