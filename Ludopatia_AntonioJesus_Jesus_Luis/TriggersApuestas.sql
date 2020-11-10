USE ApuestasBaloncesto

CREATE TRIGGER MaximoApuestas ON Apuesta AFTER INSERT
    AS
    BEGIN
        DECLARE @IDPartido INT = (SELECT ID FROM inserted)
        DECLARE @TipoApuesta INT = (SELECT TipoApuesta FROM inserted)
        DECLARE @MaximoApuestas REAL
        DECLARE @DineroApostado FLOAT = (SELECT (Cuota * DineroApuesta) FROM inserted)

        SET @MaximoApuestas =
            CASE
                WHEN @TipoApuesta = 1 THEN (SELECT MaxApuestas1 FROM Partido INNER JOIN Apuesta A on Partido.ID = A.IDPartido
                WHERE ID = @IDPartido)
                WHEN @TipoApuesta = 2 THEN (SELECT MaxApuestas2 FROM Partido INNER JOIN Apuesta A on Partido.ID = A.IDPartido
                WHERE ID = @IDPartido)
                WHEN @TipoApuesta = 3 THEN (SELECT MaxApuestas3 FROM Partido INNER JOIN Apuesta A on Partido.ID = A.IDPartido
                WHERE ID = @IDPartido)
            END

        IF (@DineroApostado > @MaximoApuestas)
        BEGIN
            PRINT '--ERROR--'
            ROLLBACK
        end

    end

CREATE TRIGGER ApuestaPartidoFinalizado ON Apuesta AFTER INSERT
    AS
    BEGIN
        DECLARE @IDParitdo INT = (SELECT ID FROM inserted)

        IF ((SELECT FechaPartido FROM Partido) - CURRENT_TIMESTAMP <= '15')
        BEGIN
            PRINT '--ERROR--'
            ROLLBACK
        end
    end

CREATE TRIGGER UsuarioRegistrado ON Apuesta AFTER INSERT
    AS
    BEGIN
        DECLARE @IDUsuario INT = (SELECT IDUsuario FROM inserted)

        IF NOT EXISTS (SELECT * FROM Usuario WHERE Usuario.ID = @IDUsuario)
        BEGIN
            PRINT '--ERROR--'
            ROLLBACK
        end

    end

CREATE TRIGGER NegativoSaldoUsuario ON Transaccion AFTER UPDATE
    AS
    BEGIN
        DECLARE @IDUsuario INT = (SELECT IDUsuario FROM inserted)
        DECLARE @DineroRetirado FLOAT = (SELECT Dinero FROM inserted)

        IF ((SELECT SaldoUsuario FROM Usuario WHERE ID = @IDUsuario) - @DineroRetirado < 0)
        BEGIN
            PRINT '--ERROR--'
            ROLLBACK
        end
    end

CREATE TRIGGER ApuestaMayorACero ON Apuesta AFTER INSERT
    AS
    BEGIN
        DECLARE @IDApuesta INT = (SELECT ID FROM inserted)
        DECLARE @DineroApostado REAL = (SELECT DineroApuesta FROM inserted)

        IF ((SELECT DineroApuesta FROM Apuesta WHERE ID = @IDApuesta) <= 0)
        BEGIN
            PRINT '--ERROR--'
            ROLLBACK
        end
    end

CREATE TRIGGER NoCambiarApuesta ON Apuesta INSTEAD OF DELETE, UPDATE
    AS
    BEGIN
        THROW 51000, 'La apuesta no se puede ni modificar ni eliminar cuando ya se ha realizado', 1
		ROLLBACK
    end