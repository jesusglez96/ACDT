--TRIGGER
--No se pueden realizar apuestas a partidos ya finalizados.
GO
ALTER TRIGGER PartidosFinalizados ON Apuestas AFTER INSERT
AS
BEGIN
	DECLARE @ID_Partido int = (SELECT IDpartido FROM INSERTED)
	
	IF((SELECT P.FechaFin FROM Partidos AS [P]
		WHERE P.id = @ID_Partido) < CURRENT_TIMESTAMP)
	BEGIN
		ROLLBACK
	END 
END
GO

--TRIGGER
-- El saldo se disminuye cuando se realiza una apuesta
GO
CREATE TRIGGER RestarSaldo ON Apuestas AFTER INSERT	
AS
BEGIN
	DECLARE @Apostada SMALLMONEY = ( SELECT cantidadApostada FROM inserted)
	DECLARE @IDUsuario int = (SELECT IDUsuario FROM inserted)
	UPDATE Usuarios SET saldo = Saldo - @Apostada
	WHERE ID = @IDUsuario
END
GO

--Trigger
-- Solo se pueden hacer apuestas a partidos que esten abiertos.
CREATE TRIGGER apuestasPartidosAbiertos ON Apuestas AFTER INSERT AS
BEGIN
	DECLARE @fechaApuesta SMALLDATETIME = (SELECT fecha FROM inserted)
	DECLARE @partido INT = (SELECT IDpartido FROM inserted)

	--Si la fecha de la apuesta es inferior a la de inicio o superior a la de fin del partido abierto se cancela la operacion.
	IF NOT EXISTS (SELECT * FROM Partidos
		WHERE id = @partido AND (@fechaApuesta BETWEEN inicioAbierto AND finAbierto))
	ROLLBACK
END





--Triggers--
--No se pueden realizar apuestas de un tipo si se supera el máximo beneficio fijado para las apuestas de dicho tipo.
GO
CREATE TRIGGER BeneficioMaximoApuestas ON Apuestas AFTER INSERT AS
BEGIN
	DECLARE @ID_Partido int = (SELECT IDpartido FROM inserted)
	DECLARE @ID_Tipo int = (SELECT IDTipo FROM inserted)
	DECLARE @BeneficioMaximo smallmoney
	DECLARE @CapitalApostado smallmoney = (SELECT (cuota * cantidadApostada) AS [PosibleBeneficio] FROM inserted)
	
	SET @BeneficioMaximo =
	CASE
		WHEN @ID_Tipo = 1 THEN (SELECT P.beneficioMax1 FROM Partidos AS [P] INNER JOIN Apuestas AS [A] ON P.id = A.IDpartido
		 WHERE A.IDpartido = @ID_Partido)
		WHEN @ID_Tipo = 2 THEN (SELECT P.beneficioMax2 FROM Partidos AS [P] INNER JOIN Apuestas AS [A] ON P.id = A.IDpartido
		 WHERE A.IDpartido = @ID_Partido)
		WHEN @ID_Tipo = 3 THEN (SELECT P.beneficioMax3 FROM Partidos AS [P] INNER JOIN Apuestas AS [A] ON P.id = A.IDpartido
		 WHERE A.IDpartido = @ID_Partido)

	END

	IF(@CapitalApostado > @BeneficioMaximo)
	BEGIN
		ROLLBACK
	END
END

--GO
--CREATE FUNCTION BeneficioTipo1()
--RETURNS @resultado TABLE(
--	IDusuario INT,
--	beneficio SMALLMONEY
--)
--AS
--BEGIN
--INSERT INTO @resultado
--SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT1] FROM (
--	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
--		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
--		INNER JOIN Tipos1 AS [T1] ON A.ID = T1.ID
--		WHERE A.IDTipo = 1 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
--		T1.golesLocal = P.golesLocal AND T1.golesVisitante = P.golesVisitante AND 
--		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
--		GROUP BY C1.IDusuario

--		RETURN
--END
--GO

--	DECLARE @PosibleBeneficio smallmoney

--	--SELECT @BeneficioTotal = SUM(A.cantidadApostada * A.cuota) FROM Apuestas AS [A]
--		--WHERE A.IDpartido = @ID_Partido AND ID = @ID_Tipo
--	SELECT @PosibleBeneficio = A.cantidadApostada * A.cuota FROM Apuestas AS [A]
--		WHERE A.ID = @ID_Partido

--	IF(@PosibleBeneficio > @ID_BeneficioMaximo)
--	BEGIN
--		ROLLBACK
--	END
--END
--GO
--Pruebas 
select * from tipos1
select * from usuarios
select * from Apuestas
SELECT * FroM Partidos
BEGIN TRANSACTION
--INSERT INTO Partidos VALUES (1,2,3,'LEO','20-09-2019 08:15:00','20-09-2019 12:15:00','18-09-2019 08:15:00','20-09-2019 12:10:00',1000,1.1,1.2)
INSERT INTO Usuarios VALUES (1,'DPMY','QUIEROMATAR','DMPY@GMAIL.COM',20.00)
INSERT INTO Partidos VALUES (1,2,3,'LEO','08-10-2019 08:15:00','08-10-2019 18:15:00','08-10-2019 06:15:00','08-10-2019 18:20:00',50,90.1,80.2)
--INSERT INTO Apuestas VALUES (1,'20-09-2019 08:15:00',2,12,2,1,1)
INSERT INTO Apuestas VALUES (1,'08-10-2019 07:30:00',2,2,1,1,1)
UPDATE Partidos SET FechaFin = '08-10-2019 10:30:00'
INSERT INTO Tipos1 VALUES(1,3,2)
ROLLBACK
SELECT * FROM Partidos
SELECT * FROM Usuarios
COMMIT
SELECT * FROM BeneficioTipo1()