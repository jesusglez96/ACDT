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
		INNER JOIN Tipos AS [T] ON A.IDTipo = T.ID
		INNER JOIN Tipos1 AS [T1] ON T.ID = T1.IDTipo
		WHERE T.ID = 1 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		T1.golesLocal = P.golesLocal AND T1.golesVisitante = P.golesVisitante AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC1]
		LEFT JOIN (

	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT2] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos AS [T] ON A.IDTipo = T.ID
		INNER JOIN Tipos2 AS [T2] ON T.ID = T2.IDTipo
		WHERE T.ID = 2 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((T2.goles = P.golesLocal AND T2.LocVis = 'L') OR (T2.goles = P.golesVisitante AND T2.LocVis = 'V')) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC2] ON CC1.IDusuario = CC2.IDusuario
		LEFT JOIN (

	SELECT C1.IDusuario, SUM(C1.cantidadApostada * C1.cuota) AS [BeneficiosT3] FROM (
	SELECT A.IDusuario, A.cantidadApostada, A.cuota FROM Apuestas AS [A]
		INNER JOIN Partidos AS [P] ON A.IDpartido = P.id
		INNER JOIN Tipos AS [T] ON A.IDTipo = T.ID
		INNER JOIN Tipos3 AS [T3] ON T.ID = T3.IDTipo
		WHERE T.ID = 3 AND A.fecha >= DATEADD(HOUR, -24, CURRENT_TIMESTAMP) AND 
		((P.golesLocal > P.golesVisitante AND T3.equipoGanador = 1) OR (P.golesLocal = P.golesVisitante AND T3.equipoGanador = 2) OR
		P.golesLocal < P.golesVisitante AND T3.equipoGanador = 3) AND 
		CURRENT_TIMESTAMP >= P.FechaFin) AS [C1]
		GROUP BY C1.IDusuario) AS [CC3] ON CC2.IDusuario = CC3.IDusuario) AS [CCT] ON U.id = CCT.IDusuario
END
GO