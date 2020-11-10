USE ApuestasBaloncesto
GO

CREATE PROCEDURE IngresarDinero
@DineroIngresar AS INT,
@IDUsuario AS INT
AS
    BEGIN
        BEGIN TRANSACTION
            BEGIN TRY
                UPDATE Usuario
		            SET SaldoUsuario += @DineroIngresar
		            WHERE id = @IDusuario
            COMMIT
            end try
            begin catch
                ROLLBACK TRANSACTION
            end catch
    end
GO

CREATE PROCEDURE RetirarDinero
@DineroRetirar AS INT,
@IDUsuario AS INT
AS
    BEGIN
        BEGIN TRANSACTION
            BEGIN TRY
                UPDATE Usuario
		            SET SaldoUsuario += @DineroRetirar
		            WHERE id = @IDusuario
            COMMIT
            end try
            begin catch
                ROLLBACK TRANSACTION
            end catch
    end
GO

CREATE PROCEDURE ComprobarApuestaPremiada @IDApuesta INT, @Tipo INT, @Acertada BINARY OUTPUT
AS
    BEGIN

        SET @Acertada = 0

        IF(@Tipo = 1)
	    BEGIN
	        DECLARE @EquipoGanador VARCHAR

	        IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta) >
	            (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta))
            BEGIN
                SET @EquipoGanador = (SELECT E.NombreEquipo FROM Equipo AS E INNER JOIN Partido AS P on E.ID = P.IDEquipoLocal
                                        INNER JOIN Apuesta A5 on P.ID = A5.IDPartido WHERE A5.ID = @IDApuesta)
            end
            ELSE IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta) <
                    (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta))
            BEGIN
                SET @EquipoGanador = (SELECT NombreEquipo FROM Equipo AS E INNER JOIN Partido AS P on E.ID = P.IDEquipoVisitante
                                        INNER JOIN Apuesta A5 on P.ID = A5.IDPartido WHERE A5.ID = @IDApuesta)
            end
	        ELSE
	            BEGIN
                    SET @EquipoGanador = 'Empate'
                end

            IF EXISTS (SELECT * FROM Apuesta AS A
            INNER JOIN Partido AS P ON A.IDPartido = P.ID
            INNER JOIN TipoApuesta1 AS A1 ON  A1.IDApuesta = A.ID
            WHERE A1.ID = @IDApuesta AND A1.Ganador = @EquipoGanador)
            BEGIN
                SET @acertada = 1
		    END
	    END

        IF(@tipo = 2)
	    BEGIN
	        DECLARE @ResultadoEquipo INT = 0

	        IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta) >
	            (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta))
	            BEGIN
                    SET @ResultadoEquipo = (SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A6 on P.ID = A6.IDPartido
                                            WHERE A6.ID = @IDApuesta)
                end
	        ELSE IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta) <
	                (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE A4.ID = @IDApuesta))
	            BEGIN
                    SET @ResultadoEquipo = (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A6 on P.ID = A6.IDPartido
                                            WHERE A6.ID = @IDApuesta)
                end

            IF EXISTS (SELECT * FROM Apuesta AS A
            INNER JOIN Partido AS P ON A.IDPartido = P.ID
            INNER JOIN TipoApuesta2 AS A2 ON A2.IDApuesta = A.ID
            WHERE A.ID = @idApuesta AND (A2.PuntosBase + A2.PuntosDiferencia) = @ResultadoEquipo)
            BEGIN
                SET @acertada = 1
            END
	    END

        IF(@tipo = 3)
        BEGIN
            IF EXISTS (SELECT * FROM Apuesta AS A
            INNER JOIN ResultadoCuartos AS R ON R.IDApuesta = A.ID
            INNER JOIN TipoApuesta3 AS A3 ON A3.ID = A.ID
            WHERE A.ID = @idApuesta AND A3.Puntos = CASE
                                                        WHEN A3.Cuarto = 1 THEN (SELECT PuntosPrimerCuarto
                                                        FROM ResultadoCuartos)
                                                        WHEN A3.Cuarto = 2 THEN (SELECT PuntosSegundoCuarto
                                                        FROM ResultadoCuartos)
                                                        WHEN A3.Cuarto = 3 THEN (SELECT PuntosTercerCuarto
                                                        FROM ResultadoCuartos)
                                                        WHEN A3.Cuarto = 4 THEN (SELECT PuntosCuartoCuarto
                                                        FROM ResultadoCuartos)
                                                    END)
            BEGIN
                SET @acertada = 1
            END
        END

        RETURN @acertada
    end
GO

CREATE PROCEDURE CalculaBeneficiosApuestas @IDPartido AS UNIQUEIDENTIFIER
AS
    BEGIN
        DECLARE @EquipoGanador VARCHAR = 'Empate'

        DECLARE @APUESTASGANADORAS AS TABLE(
	    ID INT IDENTITY(1,1) NOT NULL,
	    CANTIDAD FLOAT NOT NULL,
	    CUOTA FLOAT,
	    IDUsuario INT
	    )

        IF (CURRENT_TIMESTAMP >= (SELECT P.HoraFinalizacion FROM Partido AS P WHERE P.ID = @IDPartido))
        BEGIN
            IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE P.ID = @IDPartido) <
                        (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE P.ID = @IDPartido))
                BEGIN
                    SET @EquipoGanador = (SELECT NombreEquipo FROM Equipo INNER JOIN Partido P on Equipo.ID = P.IDEquipoLocal
                                            INNER JOIN Apuesta A3 on P.ID = A3.IDPartido AND P.ID = @IDPartido)
                end
            IF ((SELECT P.ResultadoEquipoLocal FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE P.ID = @IDPartido) <
                        (SELECT P.ResultadoEquipoVisitante FROM Partido AS P INNER JOIN Apuesta A4 on P.ID = A4.IDPartido WHERE P.ID = @IDPartido))
                BEGIN
                    SET @EquipoGanador = (SELECT NombreEquipo FROM Equipo INNER JOIN Partido P on Equipo.ID = P.IDEquipoVisitante
                                            INNER JOIN Apuesta A3 on P.ID = A3.IDPartido AND P.ID = @IDPartido)
                end

            INSERT INTO @APUESTASGANADORAS
                    SELECT A.ID, A.DineroApuesta, A.Cuota, A.IDUsuario
                    FROM Apuesta AS A
                    INNER JOIN Partido AS P ON P.ID = A.IDPartido
                    INNER JOIN Equipo E on E.ID = P.IDEquipoLocal
                    INNER JOIN Equipo E2 on E2.ID = P.IDEquipoVisitante
                    INNER JOIN TipoApuesta1 AS T1 ON T1.IDApuesta = A.ID
                    WHERE T1.Ganador = E.NombreEquipo AND P.ID = @IDPARTIDO
        end

end
