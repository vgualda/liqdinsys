
package model;

import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author mushr
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Pedido {
    
    private int idPedido;
    private Date dataPedido;
    private double valorTotal;
    private Usuario usuario;
    private Cliente cliente;
    private ArrayList<PedidoProduto> carrinho;
}
