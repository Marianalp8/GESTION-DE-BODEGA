
use Bodegapp;
go

-- los roles que puede tener un usuario; 'Sin asignar' es el del que se acaba de registrar
create table rol (
id_rol uniqueidentifier primary key default newid(),
nombre varchar(20) not null unique,

constraint ck_rol_nombre
  check (nombre in ('Sin asignar', 'Administrador', 'Repartidor'))
);

-- los estados de una cuenta; 'Pendiente' es el del que se acaba de registrar
create table estado_usuario (
id_estado_usuario uniqueidentifier primary key default newid(),
nombre varchar(20) not null unique,

constraint ck_estado_usuario_nombre
  check (nombre in ('Pendiente', 'Activo', 'Inactivo'))
);

-- las personas registradas, con el rol y el estado que tienen hoy.
-- los default de id_rol y id_estado_usuario son los id fijos de 'Sin asignar' y 'Pendiente'
-- (se insertan con ese id en datos-iniciales-bodegapp.sql)
create table usuario (
id_usuario uniqueidentifier primary key default newid(),
nombre varchar(100) not null,
correo varchar(100) not null unique,
nombre_usuario varchar(30) not null unique,
contrasena varchar(255) not null,
id_rol uniqueidentifier not null default '00000000-0000-0000-0000-000000000000',
id_estado_usuario uniqueidentifier not null default '00000000-0000-0000-0000-000000000000',
fecha_registro datetime2 not null default sysdatetime(),

constraint fk_usuario_id_rol
  foreign key (id_rol) references rol(id_rol),
constraint fk_usuario_id_estado_usuario
  foreign key (id_estado_usuario) references estado_usuario(id_estado_usuario),
constraint ck_usuario_nombre
  check (len(nombre) >= 2),
constraint ck_usuario_correo
  check (correo like '%_@_%._%'),
constraint ck_usuario_nombre_usuario
  check (len(nombre_usuario) >= 4 and nombre_usuario not like '% %')
);

-- cada aprobacion, cambio de rol, desactivacion o reactivacion que hace un administrador.
-- nunca se edita ni se borra: solo se agregan filas
create table historial_usuario (
id_historial_usuario uniqueidentifier primary key default newid(),
id_usuario uniqueidentifier not null,
id_administrador uniqueidentifier not null,
id_rol uniqueidentifier not null,
id_estado_usuario uniqueidentifier not null,
fecha datetime2 not null default sysdatetime(),

constraint fk_historial_usuario_id_usuario
  foreign key (id_usuario) references usuario(id_usuario),
constraint fk_historial_usuario_id_administrador
  foreign key (id_administrador) references usuario(id_usuario),
constraint fk_historial_usuario_id_rol
  foreign key (id_rol) references rol(id_rol),
constraint fk_historial_usuario_id_estado_usuario
  foreign key (id_estado_usuario) references estado_usuario(id_estado_usuario)
);

go
