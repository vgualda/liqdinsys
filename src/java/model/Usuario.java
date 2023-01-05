
package model;


import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * @author mushr
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario {
    private int idUsuario;
    private String nome;
    private String login;
    private String senha;
    private int status;
    private Perfil perfil;
    
}
