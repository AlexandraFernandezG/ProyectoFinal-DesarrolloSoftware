package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table( name = "usuario" )
public class Usuario extends EntidadBase {

    //Columnas

    @Column( name = "nombreUsuario" )
    private String _nombre;
    
    @Column( name = "correo" )
    private String _correoelectronico;

    @Column( name = "token" )
    private String _token;

    //Relaciones
   
    @ManyToOne
    @JoinColumn( name = "fk_rol" )
    private Rol _rol;
    
    ////Getters, Setters, y otros metodos.


    public Rol get_rol() {
        return _rol;
    }

    public void set_rol(Rol _rol) {
        this._rol = _rol;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public String get_correoelectronico() {
        return _correoelectronico;
    }

    public void set_correoelectronico(String _correoelectronico) {
        this._correoelectronico = _correoelectronico;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }


    public Usuario (String estatus) {
        super(estatus);
    }

    public Usuario( long id )
    {
        super( id );
    }

    public Usuario() {
        super();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "_nombre='" + _nombre + '\'' +
                ", _correoelectronico='" + _correoelectronico + '\'' +
                ", _token='" + _token + '\'' +
                ", _rol=" + _rol +
                '}';
    }
}
