--PROCEDURE
--Cada dia a una hora concreta se realiza el aumento o disminucion del saldo en funcion del resultado de la apuesta. 
GO
CREATE PROCEDURE MofificacionDiariaResultadosApuesta AS
BEGIN
	
	UPDATE Usuarios
		set Saldo += CCT.BenefeciosTotales
	FROM 
		Usuarios AS [U]
			INNER JOIN (

	SELECT CC1.IDusuario, (CC1.BeneficiosT1 + CC2.BeneficiosT2 + CC3.BeneficiosT3) AS [BenefeciosTotales]FROM (
	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT1] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos1 AS [T1] ON A.ID = T1.ID
		WHERE A.IDTipo = 1 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		T1.golesLocal = P.golesLocal AND T1.golesVisitante = P.golesVisitante AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC1]
		LEFT JOIN (

	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT2] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos2 AS [T2] ON A.ID = T2.ID
		WHERE A.IDTipo = 2 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((T2.goles = P.golesLocal AND T2.LocVis = 'L') OR (T2.goles = P.golesVisitante AND T2.LocVis = 'V')) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC2] ON CC1.IDusuario = CC2.IDusuario
		LEFT JOIN (

	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT3] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos3 AS [T3] ON A.ID = T3.ID
		WHERE A.IDTipo = 3 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((P.golesLocal > P.golesVisitante AND T3.equipoGanador = 1) OR (P.golesLocal = P.golesVisitante AND T3.equipoGanador = 2) OR
		P.golesLocal < P.golesVisitante AND T3.equipoGanador = 3) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC3] ON CC2.IDusuario = CC3.IDusuario) AS [CCT] ON U.id = CCT.IDusuario
END
GO

--Pruebas
GO
CREATE FUNCTION BeneficioTipo1()
RETURNS @resultado TABLE(
	IDusuario INT,
	beneficio SMALLMONEY
)
AS
BEGIN
INSERT INTO @resultado
SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT1] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos1 AS [T1] ON A.ID = T1.ID
		WHERE A.IDTipo = 1 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		T1.golesLocal = P.golesLocal AND T1.golesVisitante = P.golesVisitante AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario

		RETURN
END
GO

GO
CREATE FUNCTION BeneficioTipo2()
RETURNS @resultado TABLE(
	IDusuario INT,
	beneficio SMALLMONEY
)
AS
BEGIN
INSERT INTO @resultado
SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT2] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos2 AS [T2] ON A.ID = T2.ID
		WHERE A.IDTipo = 2 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((T2.goles = P.golesLocal AND T2.LocVis = 'L') OR (T2.goles = P.golesVisitante AND T2.LocVis = 'V')) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario

		RETURN
END
GO

GO
CREATE FUNCTION BeneficioTipo3()
RETURNS @resultado TABLE(
	IDusuario INT,
	beneficio SMALLMONEY
)
AS
BEGIN
INSERT INTO @resultado
SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT3] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos3 AS [T3] ON A.ID = T3.ID
		WHERE A.IDTipo = 3 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((P.golesLocal > P.golesVisitante AND T3.equipoGanador = 1) OR (P.golesLocal = P.golesVisitante AND T3.equipoGanador = 2) OR
		P.golesLocal < P.golesVisitante AND T3.equipoGanador = 3) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario

		RETURN
END
GO

SELECT * FROM BeneficioTipo1()
SELECT * FROM Partidos
select * from Apuestas
SELECT * FROM Usuarios
SELECT * FROM Tipos2

delete from Apuestas
delete from tipos2

begin tran
INSERT INTO Usuarios VALUES (1,'DPMY','QUIEROMATAR','DMPY@GMAIL.COM',20.00)
INSERT INTO Partidos VALUES (1,2,3,'mestalla','07-10-2019 08:15:00','07-10-2019 08:17:00','07-10-2019 08:15:00','07-10-2019 08:17:00',1000,1500,200)
INSERT INTO Apuestas VALUES (2,'07-10-2019 08:15:00',2,1,1,1,2)
INSERT INTO Tipos2 VALUES (2,'L',3)
rollback

Select * from BeneficioTipo1()

GO
CREATE PROCEDURE MofificacionDiariaResultadosApuesta01 AS
BEGIN
	
	UPDATE Usuarios
		set Saldo += B1.beneficio
	FROM 
		Usuarios AS [U] 
			INNER JOIN BeneficioTipo1() AS [B1] ON U.id = B1.IDusuario

	UPDATE Usuarios
		set Saldo += B2.beneficio
	FROM 
		Usuarios AS [U] 
			INNER JOIN BeneficioTipo2() AS [B2] ON U.id = B2.IDusuario

	UPDATE Usuarios
		set Saldo += B3.beneficio
	FROM 
		Usuarios AS [U] 
			INNER JOIN BeneficioTipo3() AS [B3] ON U.id = B3.IDusuario
END
GO

BEGIN TRAN
EXECUTE MofificacionDiariaResultadosApuesta01


-- ///////////////////////////// CODIGO AÑADIDO EXTRA (Para el ejercicio de JDBC) ///////////////////////////////////////////////////////
GO
/* INTERFAZ
Comentario: Realiza una apuesta de tipo 1, insertándola en la base de datos y disminuyendo el saldo del usuario.
Prototipo: CREATE PROCEDURE RealizarApuestaTipo1
	@id AS INT,
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@golesLocal AS TINYINT,
	@golesVisitante AS TINYINT
Entrada: El Id de la apuesta, la fecha, la cuota, la cantidad apostada, el id del partido a apostar, el id del usuario que apuesta, los goles locales y los goles visitantes.
Precondiciones: No hay
Salida: No hay
Postcondiciones: La apuesta de tipo 1 queda formalizada, y el saldo del usuario se verá disminuido en la cantidad apostada.
*/
CREATE PROCEDURE RealizarApuestaTipo1
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@golesLocal AS TINYINT,
	@golesVisitante AS TINYINT
AS
BEGIN

	BEGIN TRANSACTION

		INSERT INTO Apuestas (fecha, cuota, cantidadApostada, IDpartido, IDusuario, IDTipo) VALUES (@fecha, @cuota, @cantidadApostada, @IDpartido, @IDusuario, 1)

		INSERT INTO Tipos1 (ID, golesLocal, golesVisitante) VALUES (@@IDENTITY, @golesLocal, @golesVisitante)

		UPDATE Usuarios
		SET saldo -= @cantidadApostada
		WHERE id = @IDusuario

	COMMIT

END

GO
/* INTERFAZ
Comentario: Realiza una apuesta de tipo 2, insertándola en la base de datos y disminuyendo el saldo del usuario.
Prototipo: CREATE PROCEDURE RealizarApuestaTipo2
	@id AS INT,
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@LocVis AS char(1),
	@goles AS TINYINT
Entrada: El Id de la apuesta, la fecha, la cuota, la cantidad apostada, el id del partido a apostar, el id del usuario que apuesta, si apuesta a local o a visitante, y el numero de goles de la apuesta
Precondiciones: No hay
Salida: No hay
Postcondiciones: La apuesta de tipo 2 queda formalizada, y el saldo del usuario se verá disminuido en la cantidad apostada.
*/
CREATE PROCEDURE RealizarApuestaTipo2
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@LocVis AS char(1), -- Local o visitante
	@goles AS TINYINT
AS
BEGIN

	BEGIN TRANSACTION

		INSERT INTO Apuestas (fecha, cuota, cantidadApostada, IDpartido, IDusuario, IDTipo) VALUES (@fecha, @cuota, @cantidadApostada, @IDpartido, @IDusuario, 2)

		INSERT INTO Tipos2 (ID, LocVis, goles) VALUES (@@IDENTITY, @LocVis, @goles)

		UPDATE Usuarios
		SET saldo -= @cantidadApostada
		WHERE id = @IDusuario

	COMMIT

END

GO
/* INTERFAZ
Comentario: Realiza una apuesta de tipo 3, insertándola en la base de datos y disminuyendo el saldo del usuario.
Prototipo: CREATE PROCEDURE RealizarApuestaTipo3
	@id AS INT,
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@equipoGanador AS TINYINT
Entrada: El Id de la apuesta, la fecha, la cuota, la cantidad apostada, el id del partido a apostar, el id del usuario que apuesta y el equipo ganador 1(Gana local), 2(empate), 3(Gana visitante)
Precondiciones: No hay
Salida: No hay
Postcondiciones: La apuesta de tipo 3 queda formalizada, y el saldo del usuario se verá disminuido en la cantidad apostada.
*/
CREATE PROCEDURE RealizarApuestaTipo3
	@fecha AS SMALLDATETIME,
	@cuota AS DECIMAL(10,2),
	@cantidadApostada AS SMALLMONEY,
	@IDpartido AS INT,
	@IDusuario AS INT,
	@equipoGanador AS TINYINT
AS
BEGIN

	BEGIN TRANSACTION

		INSERT INTO Apuestas (fecha, cuota, cantidadApostada, IDpartido, IDusuario, IDTipo) VALUES (@fecha, @cuota, @cantidadApostada, @IDpartido, @IDusuario, 3)

		INSERT INTO Tipos3 (ID, equipoGanador) VALUES (@@IDENTITY, @equipoGanador)

		UPDATE Usuarios
		SET saldo -= @cantidadApostada
		WHERE id = @IDusuario

	COMMIT

END

GO

CREATE PROCEDURE IngresarDinero
	@incremento AS SMALLMONEY,
	@IDusuario AS INT
AS
BEGIN
	
	BEGIN TRANSACTION

		INSERT INTO Movimientos (incremento, IDusuario, fecha) VALUES (@incremento, @IDusuario, CURRENT_TIMESTAMP)

		UPDATE Usuarios
		SET saldo += @incremento
		WHERE id = @IDusuario

	COMMIT

END

GO
ALTER PROCEDURE IngresarDinero
	@incremento AS SMALLMONEY,
	@IDusuario AS INT
AS
BEGIN
	
	BEGIN TRANSACTION

		UPDATE Usuarios
		SET saldo += @incremento
		WHERE id = @IDusuario

		INSERT INTO Movimientos (incremento, IDusuario, fecha) VALUES (@incremento, @IDusuario, CURRENT_TIMESTAMP)

	COMMIT

END
GO

GO
ALTER PROCEDURE RetirarDinero
	@incremento AS SMALLMONEY,
	@IDusuario AS INT
AS
BEGIN

	SET @incremento = -(@incremento)
	
	BEGIN TRANSACTION

		UPDATE Usuarios
		SET saldo += @incremento
		WHERE id = @IDusuario


		INSERT INTO Movimientos (incremento, IDusuario, fecha) VALUES (@incremento, @IDusuario, CURRENT_TIMESTAMP)

	COMMIT

END
GO