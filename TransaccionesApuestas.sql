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

	        IF ((SELECT ResultadoEquipoLocal FROM Partido) > (SELECT ResultadoEquipoVisitante FROM Partido))
            BEGIN
                SET @EquipoGanador = (SELECT NombreEquipo FROM Equipo AS E INNER JOIN Partido P on E.ID = P.IDEquipoLocal)
            end
            IF ((SELECT ResultadoEquipoVisitante FROM Partido) > (SELECT ResultadoEquipoLocal FROM Partido))
            BEGIN
                SET @EquipoGanador = (SELECT NombreEquipo FROM Equipo AS E INNER JOIN Partido P on E.ID = P.IDEquipoVisitante)
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

	        IF ((SELECT ResultadoEquipoLocal FROM Partido) > (SELECT ResultadoEquipoVisitante FROM Partido))
	            BEGIN
                    SET @ResultadoEquipo = (SELECT ResultadoEquipoLocal FROM Partido)
                end
	        ELSE IF ((SELECT ResultadoEquipoLocal FROM Partido) < (SELECT ResultadoEquipoVisitante FROM Partido))
	            BEGIN
                    SET @ResultadoEquipo = (SELECT ResultadoEquipoVisitante FROM Partido)
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

CREATE PROCEDURE CalculaBeneficiosApuestas1
AS
    BEGIN
        DECLARE @EquipoGanador VARCHAR

        IF (SELECT )

        IF (CURRENT_TIMESTAMP >= (SELECT FechaPartido FROM Partido))
        BEGIN

        end
    end
