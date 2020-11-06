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
CONSTRAINT Check_Usuario_correo CHECK(email LIKE '%@_%.__%')
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

CONSTRAINT PK_Partidos PRIMARY KEY(ID)
)


--Lo mas correcto es: parcial y exclusiva. Osea que habria que crear una tabla tanto para el supertipo como para los subtipos. A la hora de poner las FK seria: En los subtipos se pone como Fk la clave primaria del supertipo.
CREATE TABLE Tipos(
ID TINYINT NOT NULL,
BeneficioMaximo SMALLMONEY Not null,
CONSTRAINT PK_Tipos PRIMARY KEY(id),
-- Las apuestas solo pueden ser de 3 tipos (1, 2 o 3).
CONSTRAINT CK_ApuestasTipo CHECK (ID BETWEEN 1 AND 3)
)

CREATE TABLE Apuestas(
id INT NOT NULL,
fecha SMALLDATETIME NOT NULL,
cuota TINYINT NOT NULL,
cantidadApostada SMALLMONEY NOT NULL,
IDpartido INT NOT NULL,
IDusuario INT NOT NULL,
IDTipo TINYINT NOT NULL,
CONSTRAINT PK_Apuestas PRIMARY KEY(id),
CONSTRAINT FK_Apuestas_Partidos FOREIGN KEY (IDpartido) REFERENCES partidos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT FK_Apuestas_Usuarios FOREIGN KEY (IDusuario) REFERENCES Usuarios(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT FK_Tipo_Apuestas FOREIGN KEY (IDtipo) REFERENCES Tipos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
--La cuota tiene que ser mayor a 1.
CONSTRAINT CK_Cuota CHECK (Cuota > 1.0)
)

CREATE TABLE Tipos1(
golesLocal TINYINT NOT NULL,
golesVisitante TINYINT NOT NULL,
IDTipo TINYINT NOT NULL,

CONSTRAINT PK_Tipos1 PRIMARY KEY(IDTipo),
CONSTRAINT FK_Tipo_Tipos1 FOREIGN KEY (IDTipo) REFERENCES Tipos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,
)

CREATE TABLE Tipos2(
LocVis char(1) NOT NULL, -- Local o visitante
goles TINYINT NOT NULL,
IDTipo TINYINT NOT NULL,

CONSTRAINT PK_Tipos2 PRIMARY KEY(IDTipo),
CONSTRAINT FK_Tipo_Tipos2 FOREIGN KEY (IDTipo) REFERENCES Tipos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE
)

CREATE TABLE Tipos3(
equipoGanador CHAR(1) NOT NULL, -- Elige si gana el local o el visitante o empate.
IDTipo TINYINT NOT NULL,

CONSTRAINT PK_Tipos3 PRIMARY KEY(IDtipo),
CONSTRAINT FK_Tipo_Tipos3 FOREIGN KEY (IDTipo) REFERENCES Tipos(id)
	ON DELETE NO ACTION ON UPDATE CASCADE,

--EquipoGanador puede ser 1(Gana local), 2(empate), 3(Gana visitante)
CONSTRAINT CK_Tipos3 CHECK (equipoGanador BETWEEN 1 AND 3)
)
