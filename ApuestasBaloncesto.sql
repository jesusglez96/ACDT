

USE ApuestasBaloncesto
GO

--TABLA USUARIO creada
CREATE  TABLE Usuario(

    ID INT IDENTITY(1,1) NOT NULL,
    NombreUsuario VARCHAR(20) NOT NULL,
    CorreoUsuario VARCHAR(100) NOT NULL,
    ContraseniaUsuario VARCHAR(16) NOT NULL,
    SaldoUsuario VARCHAR(20) NOT NULL,

    CONSTRAINT PK_Usuario PRIMARY KEY (ID)

)
GO


--TABLA EQUIPO creada
CREATE TABLE Equipo(

    ID INT IDENTITY(1,1) NOT NULL,
    NombreEquipo VARCHAR(25) NOT NULL,

    CONSTRAINT PK_Equipo PRIMARY KEY (ID)

)



--TABLA PARTIDO creada
CREATE TABLE Partido(

    ID INT IDENTITY(1,1) NOT NULL,
    IDEquipoLocal INT NOT NULL,
    IDEquipoVisitante INT NOT NULL,
    ResultadoEquipoLocal INT NULL,
    ResultadoEquipoVisitante INT NOT NULL,
    EstaAbierto INT NOT NULL,
    FechaPartido DATE NOT NULL,
    HoraFinalizacion DATETIME NOT NULL,
    MaxApuestas1 INT NOT NULL,
    MaxApuestas2 INT NOT NULL,
    MaxApuestas3 INT NOT NULL,

    CONSTRAINT PK_Partido PRIMARY KEY (ID),
    CONSTRAINT FK_Partido_EquipoLocal FOREIGN KEY (IDEquipoLocal) REFERENCES Equipo (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_Partido_EquipoVisitante FOREIGN KEY (IDEquipoVisitante) REFERENCES Equipo (ID) ON DELETE NO ACTION ON UPDATE NO ACTION

)
GO



--TABLA APUESTA creada
CREATE TABLE Apuesta(

    ID INT IDENTITY(1,1) NOT NULL,
    IDUsuario INT NOT NULL,
    IDPartido INT NOT NULL,
    Cuota FLOAT NOT NULL,
    DineroApuesta FLOAT NOT NULL,
    TipoApuesta INT NOT NULL,

    CONSTRAINT PK_Apuesta PRIMARY KEY (ID),
    CONSTRAINT FK_Apuesta_Usuario FOREIGN KEY (IDUsuario) REFERENCES Usuario (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_Apuesta_Partido FOREIGN KEY (IDPartido) REFERENCES Partido (ID) ON DELETE NO ACTION ON UPDATE CASCADE

)
GO



--TABLA TRANSACCION creada
CREATE TABLE Transaccion(

    ID INT IDENTITY(1,1) NOT NULL,
    IDApuesta INT NULL,
    IDUsuario INT NULL,
    Dinero FLOAT NOT NULL,

    CONSTRAINT PK_Transaccion PRIMARY KEY (ID),
    CONSTRAINT FK_Transaccion_Partido FOREIGN KEY (IDUsuario) REFERENCES Usuario (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_Transaccion_Apuesta FOREIGN KEY (IDApuesta) REFERENCES Apuesta (ID) ON DELETE NO ACTION ON UPDATE NO ACTION

)
GO



--TABLA ResultadoCuartos
CREATE TABLE ResultadoCuartos(

    ID INT IDENTITY NOT NULL,
    IDEquipo INT NOT NULL,
    IDApuesta INT NOT NULL,
    PuntosPrimerCuarto TINYINT NOT NULL,
    PuntosSegundoCuarto TINYINT NOT NULL,
    PuntosTercerCuarto TINYINT NOT NULL,
    PuntosCuartoCuarto TINYINT NOT NULL,

    CONSTRAINT PK_ResultadoCuarto PRIMARY KEY (ID),
    CONSTRAINT FK_ResultadoCuarto_Equipo FOREIGN KEY (IDEquipo) REFERENCES Equipo(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_ResultadoCuarto_Apuesta FOREIGN KEY (IDApuesta) REFERENCES Apuesta(ID) ON DELETE NO ACTION ON UPDATE NO ACTION

)
GO



--TABLA TIPOAPUESTA1 creada
CREATE TABLE TipoApuesta1(

    ID INT NOT NULL IDENTITY(1,1),
    IDApuesta INT NOT NULL,
    Ganador VARCHAR(25) NOT NULL,

    CONSTRAINT PK_TipoApuesta1 PRIMARY KEY (ID),
    CONSTRAINT FK_TipoApuesta1_Apuesta FOREIGN KEY (IDApuesta) REFERENCES Apuesta (ID) ON DELETE NO ACTION ON UPDATE CASCADE

)
GO



--TABLA TIPOAPUESTA2 creada
CREATE TABLE TipoApuesta2(

    ID INT IDENTITY(1,1) NOT NULL,
    IDApuesta INT NOT NULL,
    PuntosBase INT NOT NULL,
    PuntosDiferencia INT NOT NULL,

    CONSTRAINT PK_TipoApuesta2 PRIMARY KEY (ID),
    CONSTRAINT FK_TipoApuesta2_Apuesta FOREIGN KEY (IDApuesta) REFERENCES Apuesta (ID) ON DELETE NO ACTION ON UPDATE CASCADE

)
GO



--TABLA TIPOAPUESTA3 creada
CREATE TABLE TipoApuesta3(

    ID INT IDENTITY(1,1) NOT NULL,
    IDApuesta INT NOT NULL,
    Cuarto INT NOT NULL,
    Puntos INT NOT NULL,
    Equipo VARCHAR(25) NOT NULL,

    CONSTRAINT PK_TipoApuesta3 PRIMARY KEY (ID),
    CONSTRAINT FK_TipoApuesta3_Apuesta FOREIGN KEY (IDApuesta) REFERENCES Apuesta (ID) ON DELETE NO ACTION ON UPDATE CASCADE

)
GO

