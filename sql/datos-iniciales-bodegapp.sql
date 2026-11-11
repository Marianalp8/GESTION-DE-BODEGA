use Bodegapp;
go

insert into rol (nombre) values
('Sin asignar'),
('Administrador'),
('Repartidor');

insert into estado_usuario (nombre) values
('Pendiente'),
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
