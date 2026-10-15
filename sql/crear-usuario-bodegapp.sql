-- Prepara SQL Server para que el proyecto Bodegapp se pueda conectar.
-- Cada integrante lo ejecuta UNA sola vez en su propio SQL Server (localhost).
--
-- La clave es la misma que va a usar el codigo para conectarse (constante CLAVE en la factoria).
--
-- Crea:
--   1. La base de datos Bodegapp (si no existe).
--   2. El login bodegappUser, para entrar al servidor.
--   3. El usuario bodegappUser dentro de la base Bodegapp, con permisos completos sobre ella.

IF DB_ID('Bodegapp') IS NULL
    CREATE DATABASE Bodegapp;
GO

IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = 'bodegappUser')
    CREATE LOGIN bodegappUser WITH PASSWORD = 'Bodega2026*', DEFAULT_DATABASE = Bodegapp, CHECK_POLICY = ON;
GO

USE Bodegapp;
GO

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = 'bodegappUser')
    CREATE USER bodegappUser FOR LOGIN bodegappUser;
GO

ALTER ROLE db_owner ADD MEMBER bodegappUser;
GO
