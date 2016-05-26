package corp.app.com.serviflash_cliente.Modelos;

/**
 * Created by root on 19/05/16.
 */
public class Cliente {

    private int id;
    private String cedula;
    private String nombreape;
    private String celular;
    private String email;
    private String pass;
    private String idpush;
    private String idface;

    public String getIdface() {
        return idface;
    }

    public void setIdface(String idface) {
        this.idface = idface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdpush() {
        return idpush;
    }

    public void setIdpush(String idpush) {
        this.idpush = idpush;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreape() {
        return nombreape;
    }

    public void setNombreape(String nombreape) {
        this.nombreape = nombreape;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
