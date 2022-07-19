
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuealvarado.bean.Departamento;
import org.josuealvarado.db.Conexion;
import org.josuealvarado.system.Principal;

public class DepartamentosController implements Initializable{

    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO,REPORTE};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Departamento> listaDepartamentos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoDepartamento;
    @FXML private TextField txtNombre;
    @FXML private TableView tblDepartamentos;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colNombre;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblDepartamentos.setItems(getDepartamentos());
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,Integer>("codigoDepartamento"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Departamento,String>("nombreDepartamento"));
    }
    
    public ObservableList<Departamento> getDepartamentos(){
        ArrayList<Departamento> lista = new ArrayList<Departamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"),
                                           resultado.getString("nombreDepartamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblDepartamentos.getSelectionModel().getSelectedItem()!=null){
            txtCodigoDepartamento.setText(String.valueOf(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            txtNombre.setText(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getNombreDepartamento());
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
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
        Departamento registro = new Departamento();
        registro.setNombreDepartamento(txtNombre.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDepartamentos(?)}");
            procedimiento.setString(1, registro.getNombreDepartamento());
            procedimiento.execute();
            listaDepartamentos.add(registro);
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
                tipoDeOperacion = operaciones.NINGUNO;
                 break;
            default:
                if(tblDepartamentos.getSelectionModel().getSelectedItem()!=null){
                  int respuesta = JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?", "Eliminar Departamento", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);  
                  if(respuesta == JOptionPane.YES_OPTION){
                      try{
                          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDepartamentos(?)}");
                          procedimiento.setInt(1,((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                          procedimiento.execute();
                          listaDepartamentos.remove(tblDepartamentos.getSelectionModel().getSelectedIndex());
                          limpiarControles();
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                  }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        
        }
    }       
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO: 
                if(tblDepartamentos.getSelectionModel().getSelectedItem()!= null){
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
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDepartamentos(?,?)}");
           Departamento registro  = (Departamento)tblDepartamentos.getSelectionModel().getSelectedItem();
           registro.setNombreDepartamento(txtNombre.getText());
           procedimiento.setInt(1, registro.getCodigoDepartamento());
           procedimiento.setString(2, registro.getNombreDepartamento());
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
        txtCodigoDepartamento.setEditable(false);
        txtNombre.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombre.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoDepartamento.clear();
        txtNombre.clear();
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
