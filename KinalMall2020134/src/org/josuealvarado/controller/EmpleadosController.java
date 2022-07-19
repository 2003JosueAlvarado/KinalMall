

package org.josuealvarado.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.josuealvarado.bean.Administracion;
import org.josuealvarado.bean.Cargos;
import org.josuealvarado.bean.Departamento;
import org.josuealvarado.bean.Empleado;
import org.josuealvarado.bean.Horario;
import org.josuealvarado.db.Conexion;
import org.josuealvarado.system.Principal;


public class EmpleadosController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private DatePicker fechaContratacion;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<Departamento> listaDepartamentos;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Horario> listaHorarios;
    private ObservableList<Cargos> listaCargos;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private GridPane grpFechaContratacion;
    @FXML private ComboBox cmbCodigoDepartamentos;
    @FXML private ComboBox cmbCodigoCargo;
    @FXML private ComboBox cmbCodigoHorario;
    @FXML private ComboBox cmbCodigoAdmin;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombreEmpleado;
    @FXML private TableColumn colApellidoEmpleado;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCorreoElectronico;
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colFechaContratacion;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableView tblEmpleados;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContratacion, 5, 0);
        fechaContratacion.getStylesheets().add("/org/josuealvarado/resource/DatePicker.css");
        cargarDatos();
        
    }
    
     public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, Integer>("codigoEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory <Empleado,Double>("sueldo"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory <Empleado, String>("correoElectronico"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("telefonoEmpleado"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory <Empleado, Date>("fechaContratacion"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory <Horario,Integer>("codigoHorario"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory <Departamento, Integer>("codigoDepartamento"));
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory <Cargos, Integer>("codigoCargo"));
        cmbCodigoAdmin.setItems(getAdministracion());
        cmbCodigoHorario.setItems(getHorarios());
        cmbCodigoDepartamentos.setItems(getDepartamentos());
        cmbCodigoCargo.setItems(getCargos());
    }
    
     public ObservableList<Empleado> getEmpleados(){
        ArrayList <Empleado> lista = new ArrayList <Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("  call sp_ListarEmpleado()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                                        resultado.getString("nombreEmpleado"),
                                        resultado.getString("apellidoEmpleado"),
                                        resultado.getString("correoElectronico"),
                                        resultado.getString("telefonoEmpleado"),
                                        resultado.getDate("fechaContratacion"),
                                        resultado.getDouble("sueldo"),
                                        resultado.getInt("codigoDepartamento"),
                                        resultado.getInt("codigoCargo"),
                                        resultado.getInt("codigoHorario"),
                                        resultado.getInt("codigoAdministracion")));
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista); 
    }
     
     
     public ObservableList <Departamento> getDepartamentos(){
        ArrayList <Departamento> lista = new ArrayList <Departamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"),
                                            resultado.getString("nombreDepartamento")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <Administracion> getAdministracion(){
        ArrayList <Administracion> lista = new ArrayList <Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }    
    
    public ObservableList <Horario> getHorarios(){
        ArrayList <Horario> lista = new ArrayList <Horario>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorario()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Horario(resultado.getInt("codigoHorario"),
                                            resultado.getString("horarioEntrada"),
                                            resultado.getString("horarioSalida"),
                                            resultado.getBoolean("lunes"),
                                            resultado.getBoolean("martes"),
                                            resultado.getBoolean("miercoles"),
                                            resultado.getBoolean("jueves"),
                                            resultado.getBoolean("viernes")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaHorarios = FXCollections.observableArrayList(lista);
    }    
    
     public ObservableList <Cargos> getCargos(){
        ArrayList <Cargos> lista = new ArrayList <Cargos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargo()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargo"),
                                                resultado.getString("nombreCargo")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return listaCargos = FXCollections.observableArrayList(lista);
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
    
        
        public Horario buscarHorario(int codigoHorario){
        Horario resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Horario(registro.getInt("codigoHorario"),
                                            registro.getString("horarioEntrada"),
                                            registro.getString("horarioSalida"),
                                            registro.getBoolean("lunes"),
                                            registro.getBoolean("martes"),
                                            registro.getBoolean("miercoles"),
                                            registro.getBoolean("jueves"),
                                            registro.getBoolean("viernes"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public Departamento buscarDepartamento(int codigoDepartamento){
        Departamento resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamento(?)}");
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Departamento(registro.getInt("codigoDepartamento"),
                                                registro.getString("nombreDepartamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    } 
    
    public Cargos buscarCargo(int codigoCargo){
        Cargos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}");
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cargos(registro.getInt("codigoCargo"),
                                                registro.getString("nombreCargo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }        
     
     public void seleccionarElemento(){
        if(tblEmpleados.getSelectionModel().getSelectedItem()!=null){
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombreEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellidoEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtSueldo.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtCorreoElectronico.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
        txtTelefonoEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
        fechaContratacion.selectedDateProperty().set(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
        cmbCodigoHorario.getSelectionModel().select(buscarHorario(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        cmbCodigoAdmin.getSelectionModel().select(buscarAdministracion(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoCargo.getSelectionModel().select(buscarCargo(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        cmbCodigoDepartamentos.getSelectionModel().select(buscarDepartamento(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        } 
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
          Empleado registro = new Empleado();
          registro.setNombreEmpleado(txtNombreEmpleado.getText());
          registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
          registro.setCorreoElectronico(txtCorreoElectronico.getText());
          registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
          registro.setFechaContratacion(fechaContratacion.getSelectedDate());
          registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
          registro.setCodigoDepartamento(((Departamento)cmbCodigoDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
          registro.setCodigoCargo(((Cargos)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
          registro.setCodigoHorario(((Horario)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
          registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
          try{
              PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
              procedimiento.setString(1,registro.getNombreEmpleado());
              procedimiento.setString(2,registro.getApellidoEmpleado());
              procedimiento.setString(3,registro.getCorreoElectronico());
              procedimiento.setString(4,registro.getTelefonoEmpleado());
              procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
              procedimiento.setDouble(6, registro.getSueldo());
              procedimiento.setInt(7,registro.getCodigoDepartamento());
              procedimiento.setInt(8, registro.getCodigoCargo());
              procedimiento.setInt(9,registro.getCodigoHorario());
              procedimiento.setInt(10,registro.getCodigoAdministracion());
              procedimiento.execute();
              listaEmpleado.add(registro);
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
                if(tblEmpleados.getSelectionModel().getSelectedItem()!=null){
                  int respuesta = JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);  
                  if(respuesta == JOptionPane.YES_OPTION){
                      try{
                          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                          procedimiento.setInt(1,((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                          procedimiento.execute();
                          listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
                if(tblEmpleados.getSelectionModel().getSelectedItem()!= null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?,?)}");
            Empleado registro = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreEmpleado.getText());
            registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
            registro.setCorreoElectronico(txtCorreoElectronico.getText());
            registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2,registro.getNombreEmpleado());
            procedimiento.setString(3,registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5,registro.getTelefonoEmpleado());
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldo());
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
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtCorreoElectronico.setEditable(false);
        txtTelefonoEmpleado.setEditable(true);
        fechaContratacion.setDisable(false);
        cmbCodigoAdmin.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoDepartamentos.setDisable(true);
        cmbCodigoCargo.setDisable(true);
    }    
    
     public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        fechaContratacion.setDisable(false);
        cmbCodigoAdmin.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoDepartamentos.setDisable(false);
        cmbCodigoCargo.setDisable(false);
    }
    
   
    
    
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtSueldo.clear();
        txtCorreoElectronico.clear();
        txtTelefonoEmpleado.clear();
        fechaContratacion.setSelectedDate(null);
        cmbCodigoAdmin.getSelectionModel().clearSelection();
        cmbCodigoHorario.getSelectionModel().clearSelection();
        cmbCodigoDepartamentos.getSelectionModel().clearSelection();
        cmbCodigoCargo.getSelectionModel().clearSelection();
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
