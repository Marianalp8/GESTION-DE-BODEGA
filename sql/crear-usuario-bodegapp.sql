-- Prepara SQL Server para que el proyecto Bodegapp se pueda conectar.
-- Cada integrante lo ejecuta UNA sola vez en su propio SQL Server (localhost).
--
-- La clave es la misma que va a usar el codigo para conectarse (constante CLAVE en la factoria).
--
-- Crea:
--   1. La base de datos Bodegapp (si no existe).
--   2. El login bodegappUser, para entrar al servidor.
--   3. El usuario bodegappUser dentro de la base Bodegapp, que solo puede leer y escribir datos.

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

-- Solo puede leer y escribir datos; no puede crear ni borrar tablas
ALTER ROLE db_datareader ADD MEMBER bodegappUser;
ALTER ROLE db_datawriter ADD MEMBER bodegappUser;
GO
