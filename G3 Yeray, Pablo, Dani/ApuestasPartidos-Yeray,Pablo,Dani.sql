CREATE DATABASE Apuestas
GO
USE Apuestas
GO

CREATE TABLE Usuarios(
id INT NOT NULL,
nick varchar(15) NULL,
pass varchar(15) NOT NULL,
email varchar(20) NOT NULL,
saldo SMALLMONEY NULL DEFAULT 0,

CONSTRAINT PK_Jugadores PRIMARY KEY (id),
--No puedes retirar mas dinero del que dispones.
CONSTRAINT CK_RetirarDinero CHECK (Saldo >= 0),
--Email tiene que tener una estructura valida.
CONSTRAINT Check_Usuario_correo CHECK(email LIKE '_%@_%.__%')
)

CREATE TABLE Movimientos(
id INT IDENTITY(1,1) NOT NULL,
incremento SMALLMONEY null,
IDusuario INT NOT NULL, --Aqui atentos a las cardinalidades, Si es 0 acepta valores null
fecha SMALLDATETIME NULL,

CONSTRAINT PK_Movimientos PRIMARY KEY(id),
CONSTRAINT FK_Jugadores_Movimientos FOREIGN KEY (IDusuario) REFERENCES Usuarios(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
)

CREATE TABLE Partidos(
id INT NOT NULL,
golesVisitante TINYINT NOT NULL,
golesLocal TINYINT NOT NULL,
campo varchar(10) NULL,
FechaInicio SMALLDATETIME NOT NULL,
FechaFin SMALLDATETIME NOT NULL,
inicioAbierto SMALLDATETIME NOT NULL,
finAbierto SMALLDATETIME NOT NULL,
beneficioMax1 SMALLMONEY NOT NULL,
beneficioMax2 SMALLMONEY NOT NULL,
beneficioMax3 SMALLMONEY NOT NULL,

CONSTRAINT PK_Partidos PRIMARY KEY(ID)
)


CREATE TABLE Apuestas(
id INT NOT NULL,
fecha SMALLDATETIME NOT NULL,
cuota DECIMAL(10,2) NOT NULL,
cantidadApostada SMALLMONEY NOT NULL,
IDpartido INT NOT NULL,
IDusuario INT NOT NULL,
IDTipo tinyint Not null,

CONSTRAINT PK_Apuestas PRIMARY KEY(id),
CONSTRAINT FK_Apuestas_Partidos FOREIGN KEY (IDpartido) REFERENCES partidos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT FK_Apuestas_Usuarios FOREIGN KEY (IDusuario) REFERENCES Usuarios(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,

--La cuota tiene que ser mayor a 1.
CONSTRAINT CK_Cuota CHECK (Cuota > 1.0)
)

CREATE TABLE Tipos1(
ID INT NOT NULL,
golesLocal TINYINT NOT NULL,
golesVisitante TINYINT NOT NULL,

CONSTRAINT PK_Tipos1 PRIMARY KEY(ID),
CONSTRAINT FK_Tipos1_Apuesta FOREIGN KEY (ID) REFERENCES Apuestas(ID)
	ON DELETE NO ACTION ON UPDATE CASCADE,
)

CREATE TABLE Tipos2(
ID INT NOT NULL,
LocVis char(1) NOT NULL, -- Local o visitante
goles TINYINT NOT NULL,

CONSTRAINT PK_Tipos2 PRIMARY KEY(ID),
CONSTRAINT FK_Tipos2_Apuesta FOREIGN KEY (ID) REFERENCES Apuestas(ID)
	ON DELETE NO ACTION ON UPDATE CASCADE,
)

CREATE TABLE Tipos3(
ID INT NOT NULL,
equipoGanador TINYINT NOT NULL, -- Elige si gana el local o el visitante o empate.

CONSTRAINT PK_Tipos3 PRIMARY KEY(ID),
CONSTRAINT FK_Tipos3_Apuesta FOREIGN KEY (ID) REFERENCES Apuestas(ID)
	ON DELETE NO ACTION ON UPDATE CASCADE,

--EquipoGanador puede ser 1(Gana local), 2(empate), 3(Gana visitante)
CONSTRAINT CK_Tipos3 CHECK (equipoGanador BETWEEN 1 AND 3)
)
