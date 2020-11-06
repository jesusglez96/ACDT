--TRIGGER
--No se pueden realizar apuestas a partidos ya finalizados.
GO
CREATE TRIGGER PartidosFinalizados ON Apuestas AFTER INSERT
AS
BEGIN
	DECLARE @ID_Partido int = (SELECT IDpartido FROM INSERTED)
	
	IF((SELECT P.FechaFin FROM Partidos AS [P]
		WHERE P.id = @ID_Partido) > CURRENT_TIMESTAMP)
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
	DECLARE @BeneficioTotal smallmoney

	SELECT @BeneficioTotal = SUM(A.cantidadApostada * A.cuota) FROM Apuestas AS [A]
		WHERE A.IDpartido = @ID_Partido AND IDTipo = @ID_Tipo

	IF(@BeneficioTotal > (SELECT T.BeneficioMaximo FROM Tipos AS [T] WHERE T.ID = @ID_Tipo))
	BEGIN
		ROLLBACK
	END
END
GO


INSERT INTO Tipos VALUES (2,10.20)
INSERT INTO Partidos VALUES (1,2,3,'LEO','20-09-2019 08:15:00','20-09-2019 12:15:00')
INSERT INTO Usuarios VALUES (1,'DPMY','QUIEROMATAR','DMPY@GMAIL.COM',20.00)
SELECT * FROM Tipos
SELECT * FROM Partidos
SELECT * FROM Usuarios
INSERT INTO Apuestas VALUES (1,'20-09-2019 08:15:00',2,12,1,1,2)
