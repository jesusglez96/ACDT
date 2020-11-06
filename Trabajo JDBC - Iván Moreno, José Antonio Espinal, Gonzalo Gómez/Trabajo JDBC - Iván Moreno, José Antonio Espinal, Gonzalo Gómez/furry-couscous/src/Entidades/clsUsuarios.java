package Entidades;

public class clsUsuarios {
    private int _idUsuario;
    private String _nick;
    private String _pass;
    private String _email;
    private double _saldo;
    private char tipo;

    public clsUsuarios() {

    }

    public clsUsuarios(int idUsuario, String nick, String pass, String email, double saldo, char tipo) {
        this._idUsuario = idUsuario;
        this._nick = nick;
        this._pass = pass;
        this._email = email;
        this._saldo = saldo;
        this.tipo = tipo;
    }

    public clsUsuarios(String nick, String pass, String email, double saldo, char tipo)
    {
        this._idUsuario = 0;
        this._nick = nick;
        this._pass = pass;
        this._email = email;
        this._saldo = saldo;
        this.tipo = tipo;
    }

    public clsUsuarios(int idUsuario)
    {
        this._idUsuario = idUsuario;
    }

    public int getId() {
        return _idUsuario;
    }

    public void setId(int id) {
        this._idUsuario = id;
    }

    public String getNick() {
        return _nick;
    }

    public void setNick(String nick) {
        this._nick = nick;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String pass) {
        this._pass = pass;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public double getSaldo() {
        return _saldo;
    }

    public void setSaldo(double saldo) {
        this._saldo = saldo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}
