use Bodegapp;
go

-- 'Sin asignar' y 'Pendiente' llevan el id fijo de ceros porque son los default de la tabla usuario
insert into rol (id_rol, nombre) values
('00000000-0000-0000-0000-000000000000', 'Sin asignar');
insert into rol (nombre) values
('Administrador'),
('Repartidor');

insert into estado_usuario (id_estado_usuario, nombre) values
('00000000-0000-0000-0000-000000000000', 'Pendiente');
insert into estado_usuario (nombre) values
('Activo'),
('Inactivo');

-- el primer administrador: no se registra desde la app, si no nadie podria aprobar a nadie.
-- contrasena: Admin2026*  (aqui va encriptada con BCrypt; cambiarla despues del primer ingreso)
insert into usuario (nombre, correo, nombre_usuario, contrasena, id_rol, id_estado_usuario)
values ('Administrador General', 'admin@bodegapp.com', 'admin',
        '$2a$10$0bjpFu9gZYiJocx2APGLr.3wJYKNKMYZkRSszt24DqaPKWznj9fEy',
        (select id_rol from rol where nombre = 'Administrador'),
        (select id_estado_usuario from estado_usuario where nombre = 'Activo'));
go
