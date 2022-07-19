
package org.josuealvarado.system;
/*
Programador: Josue Alvarado 2020134 IN5AV
    Fecha de creacion:
        3/05/2021
    Fecha de modificaciones:
     06/05/2021
     07/05/2021
     15/05/2021
     16/05/2021
     18/05/2021
     26/05/2021
     27/05/2021
     30/05/2021
     03/06/2021
     06/06/2021
     07/06/2021
     09/06/2021
     10/06/2021
     11/06/2021
     15/06/2021
     16/06/2021
     17/06/2021
     23/06/2021
     24/06/2021
     30/07/2021
     07/07/2021
     08/07/2021
     10/07/2021
     11/07/2021
*/
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josuealvarado.controller.AdministracionController;
import org.josuealvarado.controller.CargosController;
import org.josuealvarado.controller.ClienteController;
import org.josuealvarado.controller.CuentaPorPagarController;
import org.josuealvarado.controller.DepartamentosController;
import org.josuealvarado.controller.EmpleadosController;
import org.josuealvarado.controller.HorariosController;
import org.josuealvarado.controller.LocalesController;
import org.josuealvarado.controller.LoginController;
import org.josuealvarado.controller.MenuPrincipalController;
import org.josuealvarado.controller.ProgramadorController;
import org.josuealvarado.controller.ProveedorController;
import org.josuealvarado.controller.TipoClientesController;
import org.josuealvarado.controller.UsuarioController;


public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/josuealvarado/view/" ;
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall 2020134");
        //Parent root = FXMLLoader.load(getClass().getResource("/org/josuealvarado/view/ProgramadorView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",400,419);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ventanaProgramador(){
        try{
            ProgramadorController programador =(ProgramadorController) cambiarEscena("ProgramadorView.fxml", 600,405);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void VentanaAdministracion(){
        try{
            AdministracionController admin =(AdministracionController) cambiarEscena("AdministracionView.fxml",931,410 );
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
        
    }
    
    public void VentanaLocales(){
        try{
            LocalesController locales = (LocalesController) cambiarEscena("LocalesView.fxml",907,405);
            locales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaTipoClientes(){
        try{
            TipoClientesController TipoCliente = (TipoClientesController) cambiarEscena("TipoClienteView.fxml", 768,397);
            TipoCliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaClientes(){
        try{
            ClienteController cliente = (ClienteController) cambiarEscena("ClienteView.fxml", 1167,370);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaDepartamentos(){
        try{
            DepartamentosController departamento = (DepartamentosController) cambiarEscena("DepartamentosView.fxml",884,435);
            departamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaProveedores(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena ("ProveedoresView.fxml",1167,370);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaCuentasPorPagar(){
        try{
            CuentaPorPagarController cuentasPagar = (CuentaPorPagarController)cambiarEscena("CuentaPorPagarView.fxml",1188,372);
            cuentasPagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VentanaHorarios(){
        try{
            HorariosController horario = (HorariosController)cambiarEscena("HorariosView.fxml",1167,370);
            horario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCargos(){
        try{
            CargosController cargos = (CargosController) cambiarEscena("CargosView.fxml",823,402);
            cargos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleados(){
        try{
            EmpleadosController empleado = (EmpleadosController)cambiarEscena("EmpleadoView.fxml",1230,402);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuarios(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml",675,357);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        try{
            LoginController loginController = (LoginController)cambiarEscena("LoginView.fxml",600,400);
            loginController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml,int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.getIcons().add(new Image("/org/josuealvarado/images/fondo.png"));
        resultado =(Initializable) cargadorFXML.getController();
        
        return resultado;
    }
}
