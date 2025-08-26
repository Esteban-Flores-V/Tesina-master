CREATE DATABASE IF NOT EXISTS inmobiliaria;
USE inmobiliaria;

-- Tabla de usuarios del sistema (agentes inmobiliarios o administradores)
CREATE TABLE IF NOT EXISTS usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Clientes (personas que compran, venden, alquilan)
CREATE TABLE IF NOT EXISTS clientes (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

-- Propiedades administradas por la inmobiliaria
CREATE TABLE IF NOT EXISTS propiedades (
    idPropiedad INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    tipo ENUM('Casa', 'Departamento', 'Terreno', 'Local', 'Otro') NOT NULL,
    descripcion TEXT,
    precio DECIMAL(15,2) NOT NULL,
    estado ENUM('Disponible', 'Reservada', 'Vendida', 'Alquilada') DEFAULT 'Disponible',
    idUsuario INT NOT NULL, -- agente que la gestiona
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);

-- Contratos (venta, alquiler, permuta)
CREATE TABLE IF NOT EXISTS contratos (
    idContrato INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Venta', 'Alquiler', 'Permuta') NOT NULL,
    fechaInicio DATE NOT NULL,
    fechaFin DATE,
    monto DECIMAL(15,2) NOT NULL,
    idCliente INT NOT NULL,
    idPropiedad INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
    FOREIGN KEY (idPropiedad) REFERENCES propiedades(idPropiedad)
);

-- Reservas (antes de concretar el contrato)
CREATE TABLE IF NOT EXISTS reservas (
    idReserva INT AUTO_INCREMENT PRIMARY KEY,
    fechaReserva DATE NOT NULL,
    estado ENUM('Pendiente', 'Confirmada', 'Cancelada') DEFAULT 'Pendiente',
    idCliente INT NOT NULL,
    idPropiedad INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
    FOREIGN KEY (idPropiedad) REFERENCES propiedades(idPropiedad)
);

-- Tabla que define los posibles roles
CREATE TABLE IF NOT EXISTS tipos_cliente (
    idTipo INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Propietario', 'Inquilino', 'Comprador') NOT NULL UNIQUE
);

-- Relación muchos a muchos: un cliente puede ser varios tipos
CREATE TABLE IF NOT EXISTS roles_cliente (
    idRolCliente INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    idTipo INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente) ON DELETE CASCADE,
    FOREIGN KEY (idTipo) REFERENCES tipos_cliente(idTipo) ON DELETE CASCADE,
    UNIQUE (idCliente, idTipo) -- evita duplicados
);