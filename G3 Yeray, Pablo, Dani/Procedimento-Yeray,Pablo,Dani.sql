--PROCEDURE
--Cada dia a una hora concreta se realiza el aumento o disminucion del saldo en funcion del resultado de la apuesta. 
--GO
--CREATE PROCEDURE MofificacionDiariaResultadosApuesta AS
--BEGIN
	
--	UPDATE Usuarios
--		set Saldo += CCT.BenefeciosTotales
--	FROM 
--		Usuarios AS [U]
--			INNER JOIN (

--	SELECT CC1.IDusuario, (CC1.BeneficiosT1 + CC2.BeneficiosT2 + CC3.BeneficiosT3) AS [BenefeciosTotales]FROM (
--	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT1] FROM (
--	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
--		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
--		INNER JOIN Tipos1 AS [T1] ON A.ID = T1.ID
--		WHERE A.IDTipo = 1 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
--		T1.golesLocal = P.golesLocal AND T1.golesVisitante = P.golesVisitante AND 
--		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
--		GROUP BY C1.IDusuario) AS [CC1]
--		LEFT JOIN (

--	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT2] FROM (
--	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
--		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
--		INNER JOIN Tipos2 AS [T2] ON A.ID = T2.ID
--		WHERE A.IDTipo = 2 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
--		((T2.goles = P.golesLocal AND T2.LocVis = 'L') OR (T2.goles = P.golesVisitante AND T2.LocVis = 'V')) AND 
--		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
--		GROUP BY C1.IDusuario) AS [CC2] ON CC1.IDusuario = CC2.IDusuario
--		LEFT JOIN (

--	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT3] FROM (
--	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
--		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
--		INNER JOIN Tipos3 AS [T3] ON A.ID = T3.ID
--		WHERE A.IDTipo = 3 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
--		((P.golesLocal > P.golesVisitante AND T3.equipoGanador = 1) OR (P.golesLocal = P.golesVisitante AND T3.equipoGanador = 2) OR
--		P.golesLocal < P.golesVisitante AND T3.equipoGanador = 3) AND 
--		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
--		GROUP BY C1.IDusuario) AS [CC3] ON CC2.IDusuario = CC3.IDusuario) AS [CCT] ON U.id = CCT.IDusuario
--END
--GO

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
--Pruebas
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

