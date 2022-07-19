/*Nombre: Josue Alvarado
Carné: 2020134
CodigoTecnico: IN5AV

Creación
	28/04/2021
Modificaciones
	
			01/05/2021
			06/05/2021
			07/05/2021
			12/05/2021
			15/05/2021
			16/05/2021
			25/05/2021
			26/05/2021
            08/06/2021


*/
Drop database if exists DBKinalMall2020134;
Create database DBKinalMall2020134;

use DBKinalMall2020134;

create table Departamentos(
	codigoDepartamento int auto_increment not null,
	nombreDepartamento varchar(45) not null,
		primary key PK_codigoDepartamento (codigoDepartamento)
);

create table TipoCliente(
	codigoTipoCliente int auto_increment not null,
    descripcion varchar(40) not null,
		primary key PK_codigoTipoCliente (codigoTipoCliente)
);

create table Cargos(
	codigoCargo int auto_increment not null,
    nombreCargo varchar(50) not null,
		primary key PK_codigoCargo (codigoCargo)
);

create table Horarios(
	codigoHorario int auto_increment not null,
    horarioEntrada varchar(5) not null,
    horarioSalida varchar(5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean ,
    viernes boolean,
		primary key PK_codigoHorario (codigoHorario)
);

create table Administracion(
	codigoAdministracion int auto_increment not null,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
		primary key PK_codigoAdministracion (codigoAdministracion)
);

create table Locales(
	codigoLocal int auto_increment not null,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double(11,2) default 0.0 not null,
    valorAdministracion double(11,2) default 0.0 not null,
		primary key PK_codigoLocal (codigoLocal)
);

create table Proveedores(
	codigoProveedor int not null auto_increment,
    NITProveedor varchar (45) not null ,
    servicioPrestado varchar (45) not null,
    telefonoProveedor varchar(8) not null,
    direccionProveedor varchar(60) not null,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    codigoAdministracion int not null,
	primary key PK_codigoProveedor (codigoProveedor),
		constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
				references Administracion (codigoAdministracion)
);


create table Empleados(
	codigoEmpleado int not null auto_increment,
    nombreEmpleado varchar(45) not null,
    apellidoEmpleado varchar(45) not null,
    correoElectronico varchar(45) not null,
    telefonoEmpleado varchar(8) not null,
    fechaContratacion date not null,
    sueldo double(11,2) default 0.0,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
		primary key PK_codigoEmpleado (codigoEmpleado),
			constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
				references Departamentos (codigoDepartamento),
			constraint FK_Empleados_Cargos foreign key (codigoCargo)
				references Cargos (codigoCargo),
			constraint FK_Empleados_Horarios foreign key (codigoHorario)
				references Horarios (codigoHorario),
			constraint FK_Empleados_Administracion foreign key (codigoAdministracion)
				references Administracion (codigoAdministracion)
);

create table Clientes(
	codigoCliente int  auto_increment not null,
    nombresCliente varchar(40) not null,
    apellidosCliente varchar(40) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    email varchar(45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
		primary key PK_codigoCliente (codigoCliente),
			constraint FK_Clientes_Locales foreign key (codigoLocal)
				references Locales (codigoLocal),
			constraint FK_Clientes_Administracion foreign key (codigoAdministracion)
				references	Administracion (codigoAdministracion),
			constraint FK_Clientes_TipoCliente foreign key (codigoTipoCliente)
				references TipoCliente (codigoTipoCliente)
);

create table CuentasPorPagar(
	codigoCuentasPorPagar int auto_increment not null,
    numeroFactura varchar(45) not null,
    fechaLimitePago date not null,
    estadoPago varchar(45) not null,
    valorNetoPago double(10,2) default 0.0 not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
		primary key PK_codigoCuentasPorPagar (codigoCuentasPorPagar),
			constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
				references Administracion (codigoAdministracion),
			constraint FK_CuentasporPagar_Proveedores foreign key (codigoProveedor)
				references Proveedores (codigoProveedor)
);

create table CuentasPorCobrar(
	codigoCuentasPorCobrar int not null auto_increment,
    numeroFactura varchar (45) not null,
    anio int not null,
    mes int not null,
    valorNetoPago double(11,2) default 0.0 not null,
    estadoPago varchar (45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    primary key PK_codigoCuentasPorCobrar(codigoCuentasPorCobrar),
		constraint FK_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
			references Administracion (codigoAdministracion),
		constraint FK_CuentasPorCobrar_Clientes foreign key (codigoCliente)
			references Clientes (codigoCliente),
		constraint FK_CuentasPorCobrar_Locales foreign key (codigoLocal)
			references Locales (codigoLocal)
);



/*----------------------------Administracion--------------------*/

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarAdministracion(
		in direccion varchar(45),
        in telefono varchar(8))
    Begin
		insert into Administracion(direccion,telefono)
			values(direccion,telefono);
	End $$
Delimiter  ;
call sp_AgregarAdministracion('9A. Av 5-26 Z.19', '24314552');
call sp_AgregarAdministracion('12 Cl 1-25 Z.10', 23353008);
call sp_AgregarAdministracion('Calzada San Juan 14-06 Colonia Monte Real Z 4', 24314552);
call sp_AgregarAdministracion('7A. Av 11-69 Z.9', 23754500);

Delimiter $$
	create procedure sp_ListarAdministracion()
    Begin
		select
			A.codigoAdministracion,
            A.direccion,
            A.telefono
				from Administracion A;
	End $$
Delimiter ;

call sp_ListarAdministracion();

/*Buscar*/
Delimiter $$
	create procedure sp_BuscarAdministracion(
		in codAdmin int)
	Begin
		select
				A.codigoAdministracion,
                A.direccion,
                A.telefono
                from Administracion A where codAdmin = codigoAdministracion;
	End $$
Delimiter ;

/*Editar*/
Delimiter $$
	create procedure sp_EditarAdministracion(
		in codAdmin int,
        in dir varchar(45),
        in tel varchar(8))
	Begin
		update Administracion
			set
				direccion = dir,
                telefono = tel
                where codigoAdministracion= codAdmin;
	end $$
Delimiter ;


/*Eliminar*/
Delimiter $$ 
	create procedure sp_EliminarAdministracion(in codAdmin int)
		Begin 
			delete from Administracion 
				where codAdmin = codigoAdministracion;
		end $$
Delimiter ;

/*---------------------Departamentos-------------------*/

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarDepartamentos(
		in nombreDepartamento varchar(45))
    Begin
		insert into Departamentos(nombreDepartamento)
			values(nombreDepartamento);
	End $$
Delimiter  ;

call sp_AgregarDepartamentos('Mercadotecnia');


Delimiter $$
	create procedure sp_ListarDepartamentos()
    Begin
		select
			codigoDepartamento,
            nombreDepartamento
				from Departamentos;
	End $$
Delimiter ;

call sp_ListarDepartamentos();

Delimiter $$
	create procedure sp_BuscarDepartamentos(
		in codDepa int)
	Begin
		select
				codigoDepartamento,
                nombreDepartamento
                from Departamentos A where codDepa = codigoDepartamentos;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarDepartamentos(
		in codDepa int,
        in nomDepa varchar(45))
	Begin
		update Departamentos
			set
				nombreDepartamento = nomDepa
                where codigoDepartamento= codDepa;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarDepartamentos(in codDepa int)
		Begin 
			delete from Departamentos
				where codDepa = codigoDepartamento;
		end $$
Delimiter ;

/*TipoCliente*/
Delimiter $$
	create procedure sp_AgregarTipoCliente(
		in descripcion varchar(40))
    Begin
		insert into TipoCliente(descripcion)
			values(descripcion);
	End $$
Delimiter  ;

call sp_AgregarTipoCliente('Frecuente');
call sp_AgregarTipoCliente('Nuevo');
call sp_AgregarTipoCliente('VIP');



Delimiter $$
	create procedure sp_ListarTipoCliente()
    Begin
		select
			codigoTipoCliente,
            descripcion
				from TipoCliente;
	End $$
Delimiter ;
call sp_ListarTipoCliente();

Delimiter $$
	create procedure sp_BuscarTipoCliente(
		in codTipoCliente int)
	Begin
		select
				codigoTipoCliente,
                descripcion
                from TipoCliente A where codTipoCliente = codigoTipoCliente;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarTipoCliente(
		in codTipoCliente int,
        in desp varchar(40))
	Begin
		update TipoCliente
			set
				descripcion = desp
                
                where codigoTipoCliente= codTipoCliente;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarTipoCliente(in codTipoCliente int)
		Begin 
			delete from TipoCliente
				where codTipoCliente = codigoTipoCliente;
		end $$
Delimiter ;

/*Cargos*/
Delimiter $$
	create procedure sp_AgregarCargo(
		in nombreCargo varchar(50))
    Begin
		insert into Cargos(nombreCargo)
			values(nombreCargo);
	End $$
Delimiter  ;

call sp_AgregarCargo('Gerente');
call sp_AgregarCargo('Oficinista');

Delimiter $$
	create procedure sp_ListarCargo()
    Begin
		select
			codigoCargo,
            nombreCargo
				from Cargos;
	End $$
Delimiter ;
call sp_ListarCargo();

Delimiter $$
	create procedure sp_BuscarCargo(
		in codCargo int)
	Begin
		select
				codigoCargo,
                nombreCargo
                from Cargos A where codCargo = codigoCargo;
	End $$
Delimiter ;


Delimiter $$
	create procedure sp_EditarCargo(
		in codCargo int,
        in nom varchar(40))
	Begin
		update Cargos
			set
				nombreCargo = nom
                
                where codigoCargo= codCargo;
	end $$
Delimiter ;


Delimiter $$ 
	create procedure sp_EliminarCargo(in codCargo int)
		Begin
			delete from Cargos
				where codCargo = codigoCargo;
		end $$
Delimiter ;


/*Horarios*/
Delimiter $$
	create procedure sp_AgregarHorario(
		in horarioEntrada varchar(5),
        in horarioSalida varchar(5),
       in  lunes boolean,
       in martes boolean,
      in   miercoles boolean,
        in jueves boolean,
        in viernes boolean)
    Begin
		insert into Horarios(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes)
			values(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes);
	End $$
Delimiter  ;

call sp_AgregarHorario('07:30', '6:45', 1,1,0,0,1);
call sp_AgregarHorario('05:00','17:00',1,0,0,0,1);

Delimiter $$
	create procedure sp_ListarHorario()
    Begin
		select
			codigoHorario,
            horarioEntrada,
            horarioSalida,
            lunes,
            martes,
            miercoles,
            jueves,
            viernes
				from Horarios;
	End $$
Delimiter ;
call sp_ListarHorario();

Delimiter $$
	create procedure sp_BuscarHorario(
		in codHorario int)
	Begin
		select
				codigoHorario,
                horarioEntrada,
				horarioSalida,
				lunes,
				martes,
				miercoles,
				jueves,
				viernes
                from Horarios A where codHorario = codigoHorario;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarHorario(
		in codHorario int,
        in HorEn varchar(5),
        in HorSa varchar(5),
       in  lun boolean,
       in mar boolean,
      in   mie boolean,
        in juv boolean,
        in vin boolean)
	Begin
		update Horarios
			set
				codigoHorario = codHorario,
                horarioEntrada = HorEn,
                horarioSalida = HorSa,
                lunes = lun,
                martes = mar,
                miercoles = mie,
                jueves = juv,
                viernes = vin
                
                where codigoHorario= codHorario;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarHorario(in codHorario int)
		Begin 
			delete from Horarios
				where codHorario = codigoHorario;
		end $$
Delimiter ;

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarLocal(
		
       
        in disponibilidad boolean,
		in valorLocal double(11,2),
        in valorAdministracion double(11,2))
    Begin
		insert into Locales(disponibilidad,valorLocal,valorAdministracion)
			values(disponibilidad,valorLocal,valorAdministracion);
	End $$
Delimiter  ;

call sp_Agregarlocal(false,500,100);
call sp_Agregarlocal(false,400,600);
call sp_Agregarlocal(true,1000,3000);
call sp_Agregarlocal(true,300,500);
call sp_Agregarlocal(true,700,400);
call sp_Agregarlocal(false,600,300);
call sp_Agregarlocal(true,800,650);
call sp_Agregarlocal(false,700,900);
call sp_Agregarlocal(true,500,600);
call sp_Agregarlocal(true,700,900);
 /*Josue Alvarado 2020134*/




Delimiter ;

Delimiter $$
	create procedure sp_ListarLocal()
    Begin
		select
			codigoLocal,
            saldoFavor,
            saldoContra,
            mesesPendientes,
            disponibilidad,
            valorLocal,
            valorAdministracion
				from Locales;
	End $$
Delimiter ;
call sp_ListarLocal();

Delimiter $$
	create procedure sp_BuscarLocal(
		in codLocal int)
	Begin
		select
				codigoLocal,
				saldoFavor,
				saldoContra,
				mesesPendientes,
				disponibilidad,
				valorLocal,
				valorAdministracion
					from Locales A where codLocal = codigoLocal;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarLocal(
		in codLoc int,
        in disp boolean,
		in valLoc double(11,2),
        in valAdmin double(11,2))	
	Begin
		update Locales
			set
                    disponibilidad = disp,
                    valorLocal = valLoc,
                    valorAdministracion = valAdmin
                where codigoLocal= codLoc;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarLocal(in codLoc int)
		Begin 
			delete from Locales
				where codLoc = codigoLocal;
		end $$
Delimiter ;

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarProveedores(
		in NITProveedor varchar(45),
        in servicioPrestado varchar(45),
        in telefonoProveedor varchar(8),
        in direccionProveedor varchar(60),
        in saldoFavor double (11,2),
        in saldoContra double (11,2),
        in codigoAdministracion int)
        
    Begin
		insert into Proveedores(NITProveedor,servicioPrestado,telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion)
			values(NITProveedor,servicioPrestado,telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion);
	End $$
Delimiter  ;
call sp_AgregarProveedores('56678765-9', 'Electricista', '43579345','Calle A 11-3 Zona 7',5000,300,1);
call sp_AgregarProveedores('54657687-8', 'Venta de bolsas', '45678546','Mixco,Guatemala',1000,400,1);
call sp_AgregarProveedores('65789545-1', 'Contabilidad', '63578965', 'Guatemala Zona 1',250,50,1);
call sp_AgregarProveedores('45642564-2', 'Ingenieria', '42522053', 'Calle A Zona 18',500,450,1);
call sp_AgregarProveedores('34567686-4', 'Arquitectura', '42582084', 'Villa Nueva,Guatemala',250,100,1);
call sp_AgregarProveedores('45678597-5', ' Publicidad', '43547684', 'Santa Rosa,Guatemala',9000,500,1);
call sp_AgregarProveedores('6578945-8', 'Servicios Juridicos', '34564768', 'Calle A 11-3,Zona 5',2500,1000,1);
call sp_AgregarProveedores('4567857-4', ' Informaticos', '40084403', 'Guatemala Zona 15',2000,500, 1);
call sp_AgregarProveedores('4567859-0', 'Venta de juguetes', '56797863', 'Calle B 15-7', 7000,300,1);
call sp_AgregarProveedores('546786-6', 'Venta de dulces', '35647687', 'Guatemala Zona 5',600,100,1);
call sp_AgregarProveedores('234567-6','Venta de carros', '42587087','Mixco,Guatemala',5000,650,1);

-- Josue Alvarado 2020134




Delimiter $$
	create procedure sp_ListarProveedores()
    Begin
		select
			codigoProveedor,
			NITProveedor,
			servicioPrestado, 
			telefonoProveedor, 
			direccionProveedor,
			saldoFavor, 
			saldoContra, 
			codigoAdministracion
				from Proveedores;
	End $$
Delimiter ;
call sp_ListarProveedores();

Delimiter $$
	create procedure sp_BuscarProveedor(
		in codProv int)
	Begin
		select
				codigoProveedor,
				NITProveedor,
				servicioPrestado, 
				telefonoProveedor, 
				direccionProveedor,
				saldoFavor, 
				saldoContra, 
				codigoAdministracion
					from Proveedores where codProv = codigoProveedor;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarProveedor(
		in codProv int,
        in NITProv varchar(45),
        in serPret varchar(45),
        in telProv varchar(8),
        in dirProv varchar(60),
        in salFav double(11,2),
        in salCon double(11,2),
        in codAdmin int)
	Begin
		update Proveedores
			set
                    NITProveedor = NITProv,
                    servicioPrestado = serPret,
                    telefonoProveedor = telProv,
                    direccionProveedor = dirProv,
                    saldoFavor = salFav,
                    saldoContra = salCon,
                    codigoAdministracion = codAdmin
                where codigoProveedor= codProv;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarProveedor(in codProv int)
		Begin 
			delete from Proveedores
				where codProv = codigoProveedor;
		end $$
Delimiter ;

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarEmpleado(
		in nombreEmpleado varchar(45),
		in apellidoEmpleado varchar(45) ,
		in correoElectronico varchar(45),
		in telefonoEmpleado varchar(8),
		in fechaContratacion date ,
		in sueldo double(11,2) ,
		in codigoDepartamento int ,
		in codigoCargo int ,
		in codigoHorario int,
		in codigoAdministracion int)
        
    Begin
		insert into Empleados(nombreEmpleado,apellidoEmpleado,correoElectronico,telefonoEmpleado,fechaContratacion,sueldo,codigoDepartamento,codigoCargo,codigoHorario,codigoAdministracion)
			values(nombreEmpleado,apellidoEmpleado,correoElectronico,telefonoEmpleado,fechaContratacion,sueldo,codigoDepartamento,codigoCargo,codigoHorario,codigoAdministracion);
	End $$
Delimiter  ;
call sp_AgregarEmpleado('Juan','Mendez','mendezjuan1990@gmail.com', '34657895', '2010-10-05', 3500,1,1,1,1);


Delimiter $$
	create procedure sp_ListarEmpleado()
    Begin
		select
			codigoEmpleado,
            nombreEmpleado, 
			apellidoEmpleado, 
			correoElectronico, 
			telefonoEmpleado,
			fechaContratacion,
			sueldo,
			codigoDepartamento, 
			codigoCargo, 
			codigoHorario,
			codigoAdministracion
				from Empleados;
	End $$
Delimiter ;
call sp_ListarEmpleado();

Delimiter $$
	create procedure sp_BuscarEmpleado(
		in codEmp int)
	Begin
		select
				codigoEmpleado,
                nombreEmpleado, 
				apellidoEmpleado, 
				correoElectronico, 
				telefonoEmpleado,
				fechaContratacion,
				sueldo,
				codigoDepartamento, 
				codigoCargo, 
				codigoHorario,
				codigoAdministracion
					from Empleados where codEmp = codigoEmpleado;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarEmpleado(
		in codEmp int,
        in nomEmp varchar(45),
		in apllEmp varchar(45) ,
		in corElec varchar(45),
		in telEmpl varchar(8),
		in fecCon date ,
		in suel double(11,2) ,
		in codDepa int ,
		in codCar int ,
		in codHor int,
		in codAdmi int)
        
	Begin
		update Empleados
			set
			
                nombreEmpleado = nomEmp,
                apellidoEmpleado = apllEmp,
                correoElectronico = corElec,
                telefonoEmpleado = telEmpl,
                fechaContratacion = fecCon,
                sueldo = suel,
                codigoDepartamento = codDepa,
                codigoCargo = codCar,
                codigoHorario = codHor,
                codigoAdministracion = codAdmi
					where codigoEmpleado = codEmp;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarEmpleado(in codEmp int)
		Begin 
			delete from Empleados
				where codEmp = codigoEmpleado;
		end $$
Delimiter ;

/*Agregar*/
Delimiter $$
	create procedure sp_AgregarCliente(
   
    in nombresCliente varchar(40) ,
    in apellidosCliente varchar(40),
    in telefonoCliente varchar(8),
    in direccionCliente varchar(60) ,
    in email varchar(45) ,
    in codigoLocal int ,
    in codigoAdministracion int ,
    in codigoTipoCliente int)
        
    Begin
		insert into Clientes(nombresCliente, apellidosCliente,telefonoCliente,direccionCliente,email,codigoLocal,codigoAdministracion,codigoTipoCliente)
			values(nombresCliente, apellidosCliente,telefonoCliente,direccionCliente,email,codigoLocal,codigoAdministracion,codigoTipoCliente);
	End $$
Delimiter  ;
call sp_AgregarCliente('Sergio Leonel', 'Sarabia Menendez', '34567456', '8va avenida 8-23 Zona 1', '1989SergioLeonelSarabia@gmail.com',1,1,1);
call sp_AgregarCliente('Martin Sebastian', 'Sarabia Menendez', '45678907', '8va avenida 8-23 Zona 1', 'MSarabia@gmail.com',1,1,2);
call sp_AgregarCliente('Sergio Leonel', 'Contreras Garcia', '36789546', '8va avenida 8-23 Zona 1', 'ContrerasLeonel@gmail.com',1,1,1);



Delimiter $$
	create procedure sp_ListarCliente()
    Begin
		select
			codigoCliente,
			nombresCliente,
			apellidosCliente,
			telefonoCliente,
			direccionCliente,
			email,
			codigoLocal,
			codigoAdministracion,
			codigoTipoCliente 
				from Clientes;
	End $$
Delimiter ;
call sp_ListarCliente();

Delimiter $$
	create procedure sp_BuscarCliente(
		in codCli int)
	Begin
		select
				codigoCliente,
                nombresCliente,
				apellidosCliente,
				telefonoCliente,
				direccionCliente,
				email,
				codigoLocal,
				codigoAdministracion,
				codigoTipoCliente 
					from Clientes where codCli = codigoCliente;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarCliente(
		in codCli int,
        in nombCli varchar(40) ,
		in apellCli varchar(40),
		in telCli varchar(8),
		in dirCli varchar(60) ,
		in eml varchar(45) ,
		in codLoc int ,
		in codAdmin int ,
		in codTipoCli int)
        
	Begin
		update Clientes
			set
				codigoCliente = codCli,
                nombresCliente = nombCli,
                apellidosCliente = apellCli,
                telefonoCliente = telCli,
                direccionCliente = dirCli,
                email = eml,
                codigoLocal = codLoc,
                codigoAdministracion = codAdmin,
                codigoTipoCliente = codTipoCli
                
					where codigoCliente = codCli;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarCliente(in codCli int)
		Begin 
			delete from Clientes
				where codCli = codigoCliente;
		end $$
Delimiter ;


/*Agregar*/
Delimiter $$
	create procedure sp_AgregarCuentasPorPagar(
    in numeroFactura varchar(45),
    in fechaLimitePago date,
    in estadoPago varchar(45) ,
    in valorNetoPago double(10,2),
    in codigoAdministracion int ,
    in codigoProveedor int)
        
    Begin
		insert into CuentasPorPagar(numeroFactura,fechaLimitePago,estadoPago,valorNetoPago,codigoAdministracion,codigoProveedor)
			values(numeroFactura,fechaLimitePago,estadoPago,valorNetoPago,codigoAdministracion,codigoProveedor);
	End $$
Delimiter  ;
call sp_AgregarCuentasPorPagar('0123', '2021-5-30','cheque',4000,1,1);
call sp_AgregarCuentasPorPagar('105','2021-6-15','Pendiente',5000,1,1);


Delimiter $$
	create procedure sp_ListarCuentasPorPagar()
    Begin
		select
			 codigoCuentasPorPagar,
             numeroFactura,
			 fechaLimitePago,
			 estadoPago ,
			 valorNetoPago,
			 codigoAdministracion,
			 codigoProveedor
				from CuentasPorPagar;
	End $$
Delimiter ;
call sp_ListarCuentasPorPagar();

Delimiter $$
	create procedure sp_BuscarCuentasPorPagar(
		in codCunPorPag int)
	Begin
		select
			 codigoCuentasPorPagar,
             numeroFactura,
			 fechaLimitePago,
			 estadoPago ,
			 valorNetoPago,
			 codigoAdministracion,
			 codigoProveedor
					from CuentasPorPagar where codCunPorPag = codigoCuentasPorPagar;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarCuentasPorPagar(
		in codCunPorPag int,
        in numFact varchar(45),
		in fechLimPag date,
		in estaPag varchar(45) ,
		in valNetPag double(10,2),
		in codAdmin int ,
		in codProv int)
        
	Begin
		update CuentasPorPagar
			set
				codigoCuentasPorPagar = codCunPorPag,
                numeroFactura = numFact,
                fechaLimitePago = fechLimPag,
                estadoPago = estaPag,
                valorNetoPago = valNetPag,
                codigoAdministracion = codAdmin,
                codigoProveedor = codProv
                
					where codigoCuentasPorPagar = codCunPorPag;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarCuentasPorPagar(in codCunPorPag int)
		Begin 
			delete from CuentasPorPagar
				where codCunPorPag = codigoCuentasPorPagar;
		end $$
Delimiter ;


/*Agregar*/
Delimiter $$
	create procedure sp_AgregarCuentasPorCobrar(

    in numeroFactura varchar (45),
    in anio int ,
    in mes int ,
    in valorNetoPago double(11,2) ,
    in estadoPago varchar (45),
    in codigoAdministracion int ,
    in codigoCliente int ,
    in codigoLocal int)
        
    Begin
		insert into CuentasPorCobrar(numeroFactura,anio,mes,valorNetoPago,estadoPago,codigoAdministracion,codigoCliente,codigoLocal)
			values(numeroFactura,anio,mes,valorNetoPago,estadoPago,codigoAdministracion,codigoCliente,codigoLocal);
	End $$
Delimiter  ;
call sp_AgregarCuentasPorCobrar('345',2020,10,5000,'cheque',1,1,1);


Delimiter $$
	create procedure sp_ListarCuentasPorCobrar()
    Begin
		select
			 codigoCuentasPorCobrar,
             numeroFactura,
			 anio ,
			 mes  ,
			 valorNetoPago ,
			 estadoPago ,
			 codigoAdministracion ,
			 codigoCliente ,
			 codigoLocal
				from CuentasPorCobrar;
	End $$
Delimiter ;
call sp_ListarCuentasPorCobrar();

Delimiter $$
	create procedure sp_BuscarCuentasPorCobrar(
		in codCunPorCob int)
	Begin
		select
			 codigoCuentasPorCobrar,
             numeroFactura,
			 anio ,
			 mes  ,
			 valorNetoPago ,
			 estadoPago ,
			 codigoAdministracion ,
			 codigoCliente ,
			 codigoLocal
					from CuentasPorCobrar where codCunPorCob = codigoCuentasPorCob;
	End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarCuentasPorCobrar(
		in codCunPorCob int,
        in numFact varchar(45),
		in anio int,
		in mes int,
        in estaPag varchar(45) ,
		in valNetPag double(10,2),
		in codAdmin int ,
		in codCli int,
        in codLoc int)
        
	Begin
		update CuentasPorCobrar
			set
				codigoCuentasPorCobrar = codCunPorCob,
                numeroFactura = numFact,
                anio = anio,
                mes = mes,
                estadoPago = estaPag,
                valorNetoPago = valNetPag,
                codigoAdministracion = codAdmin,
                codigoCliente = codCli,
                codigoLocal = codLoc
                
					where codigoCuentasPorCobrar = codCunPorCob;
	end $$
Delimiter ;

Delimiter $$ 
	create procedure sp_EliminarCuentasPorCobrar(in codCunPorCob int)
		Begin 
			delete from CuentasPorCobrar
				where codCunPorCob = codigoCuentasPorCobrar;
		end $$
Delimiter ;


Delimiter $$
	create procedure sp_CalcularLiquido(in codLocal int)
		Begin
			select saldoFavor - saldoContra as liquido
				from Locales
					where codigoLocal = codLocal;
        End $$
Delimiter ;
call sp_CalcularLiquido(7);


Delimiter $$
	create procedure sp_Liquido(in codProv int)
		Begin 
			select codigoProveedor, NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor,saldoFavor,saldoContra,
            codigoAdministracion, saldoFavor - saldoContra as liquido
				from Proveedores
					where codigoProveedor = codProv;
		End $$
Delimiter ;
-- Josue Alvarado 2020134
call sp_Liquido(5);

Delimiter $$
	create procedure sp_LiquidoProveedores()
		Begin 
			select codigoProveedor, NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor,saldoFavor,saldoContra,
            codigoAdministracion, saldoFavor - saldoContra as liquido
				from Proveedores;
			
		End $$
Delimiter ;
-- Josue Alvarado 2020134
call sp_LiquidoProveedores();


Delimiter $$
	create procedure sp_CalcularRentaTotal(in codLocal int)
		begin
			select 
                codigoLocal,
				saldoFavor,
				saldoContra,
				mesesPendientes,
				disponibilidad,
				valorLocal,
				valorAdministracion,
            (mesesPendientes*valorLocal) + (saldoFavor-saldoContra) as RentaTotal
				from Locales
					where codigoLocal = codLocal;
		end $$
delimiter ;
-- Josue Alvarado 2020134
call sp_CalcularRentaTotal(5);

Delimiter $$
	create procedure sp_RentasTotales()
		begin
			select 
				codigoLocal,
				saldoFavor,
				saldoContra,
				mesesPendientes,
				disponibilidad,
				valorLocal,
				valorAdministracion,
                (mesesPendientes*valorLocal) + (saldoFavor-saldoContra) as RentasTotales
				from Locales;
		end $$
delimiter ;
-- Josue Alvarado 2020134
call sp_RentasTotales();


Delimiter $$
	create procedure sp_ContarLocales()
		begin
			select count(codigoLocal) as Locales from Locales;
		end $$
delimiter ;
call sp_ContarLocales();
-- Josue Alvarado 2020134

Delimiter $$
	create procedure sp_Disponibles()
		Begin
			select count(disponibilidad) as 'Disponibles'
				from Locales where disponibilidad = true;
		end $$
delimiter ;
call sp_Disponibles();
-- Josue Alvarado 2020134

Delimiter $$
	create procedure sp_Ocupados()
		Begin
			select count(disponibilidad) as 'Ocupados'
				from Locales where disponibilidad = false;
		end $$
delimiter ;
call sp_Ocupados();
-- Josue Alvarado 2020134

select * from TipoCliente TC inner join Clientes C on
	TC.codigoTipoCliente = C.codigoTipoCliente
		where TC.descripcion = 'Frecuente';

select * from Empleados

Create table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)
);

Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100), in usuarioLogin varchar(50),in  contrasena varchar (50))
    Begin
		Insert into Usuarios(nombreUsuario,apellidoUsuario,usuarioLogin,contrasena)
			values (nombreUsuario,apellidoUsuario,usuarioLogin,contrasena);
    End$$
Delimiter ;


Delimiter $$
	Create procedure sp_ListarUsuarios()
		Begin
			Select
				U.codigoUsuario,
                U.nombreUsuario,
                U.apellidoUsuario,
                U.usuarioLogin,
                U.contrasena
            from Usuarios U;
        End $$

Delimiter ;

call sp_AgregarUsuario('Josue','Alvarado','jalvarado','12345');
call sp_ListarUsuarios();

Create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
);







