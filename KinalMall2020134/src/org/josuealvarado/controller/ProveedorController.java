
package org.josuealvarado.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuealvarado.bean.Administracion;
import org.josuealvarado.bean.Proveedor;
import org.josuealvarado.db.Conexion;
import org.josuealvarado.system.Principal;


public class ProveedorController implements Initializable {
    
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,NINGUNO,ACTUALIZAR};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Administracion> listaAdministracion;
    private Principal escenarioPrincipal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoProveedor;
    @FXML private TextField txtNitProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelProveedor;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private ComboBox cmbCodigoAdmin;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colNitProveedor;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelProveedor;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblProveedores.setItems(getProveedor());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("servicioPrestado"));
        colTelProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefonoProveedor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("direccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("saldoContra"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        cmbCodigoAdmin.setItems(getAdministracion());

    }
    
    public void seleccionarElemento(){
        if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
            txtCodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNitProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccion.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra()));
            cmbCodigoAdmin.getSelectionModel().select(buscarAdministracion(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));

        
        }
    }
    
    public ObservableList<Proveedor> getProveedor(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"), 
                                        resultado.getString("servicioPrestado"),
                                        resultado.getString("telefonoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getDouble("saldoFavor"),
                                        resultado.getDouble("saldoContra"),
                                        resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaProveedor = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new  ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                            resultado.getString("direccion"),
                                            resultado.getString("telefono")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                               registro.getString("direccion"),
                                               registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
     public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/josuealvarado/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josuealvarado/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/josuealvarado/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/josuealvarado/images/eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;

        }
    }
    
     
    
     public void guardar(){
         Proveedor registro = new Proveedor();
         registro.setNITProveedor(txtNitProveedor.getText());
         registro.setServicioPrestado(txtServicioPrestado.getText());
         registro.setTelefonoProveedor(txtTelProveedor.getText());
         registro.setDireccionProveedor(txtDireccion.getText());
         registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
         registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
         registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?,?,?,?,?,?,?)}");
             procedimiento.setString(1, registro.getNITProveedor());
             procedimiento.setString(2, registro.getServicioPrestado());
             procedimiento.setString(3,registro.getTelefonoProveedor());
             procedimiento.setString(4, registro.getDireccionProveedor());
             procedimiento.setDouble(5,registro.getSaldoFavor());
             procedimiento.setDouble(6,registro.getSaldoContra());
             procedimiento.setInt(7, registro.getCodigoAdministracion());
             procedimiento.execute();
             listaProveedor.add(registro);
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
     public void eliminar(){
         switch(tipoDeOperacion){
             case GUARDAR:
                 desactivarControles();
                 limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/josuealvarado/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/josuealvarado/images/eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion =  operaciones.NINGUNO;
                break; 
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1,((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.executeQuery();
                            listaProveedor.remove(tblProveedores.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
                }
        }
    }
    
     public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO: 
                if(tblProveedores.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/josuealvarado/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/josuealvarado/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
                }break;
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/josuealvarado/images/editar.png"));
                imgReporte.setImage(new Image("/org/josuealvarado/images/reporte.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;

               
        }
    }
     
     public void actualizar(){
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?,?,?,?,?,?,?,?)}");
             Proveedor registro = (Proveedor)tblProveedores.getSelectionModel().getSelectedItem();
             registro.setNITProveedor(txtNitProveedor.getText());
             registro.setServicioPrestado(txtServicioPrestado.getText());
             registro.setTelefonoProveedor(txtTelProveedor.getText());
             registro.setDireccionProveedor(txtDireccion.getText());
             registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
             procedimiento.setInt(1,registro.getCodigoProveedor());
             procedimiento.setString(2, registro.getNITProveedor());
             procedimiento.setString(3, registro.getServicioPrestado());
             procedimiento.setString(4, registro.getTelefonoProveedor());
             procedimiento.setString(5,registro.getDireccionProveedor());
             procedimiento.setDouble(6,registro.getSaldoFavor());
             procedimiento.setDouble(7,registro.getSaldoContra());
             procedimiento.setInt(8, registro.getCodigoAdministracion());
             procedimiento.execute();
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
     public void reporte(){
       switch(tipoDeOperacion){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();
               btnEditar.setText("Editar");
               btnReporte.setText("Reporte");
               imgEditar.setImage (new Image("/org/josuealvarado/images/editar.png"));
               imgReporte.setImage (new Image("/org/josuealvarado/images/reporte.png"));
               btnNuevo.setDisable (false);
               btnEliminar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
       }       
   }
         
     
    public void desactivarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelProveedor.setEditable(false);
        txtDireccion.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodigoAdmin.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNitProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelProveedor.setEditable(true);
        txtDireccion.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodigoAdmin.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoProveedor.clear();
        txtNitProveedor.clear();
        txtServicioPrestado.clear();
        txtTelProveedor.clear();
        txtDireccion.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodigoAdmin.getSelectionModel().clearSelection();
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
