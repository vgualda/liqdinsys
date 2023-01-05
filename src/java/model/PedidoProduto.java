package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author mushr
 */

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
public class PedidoProduto {
    private long idPedidoProduto;
    private int qtd;
    private double precoUnitario;
    private Pedido pedido;
    private Produto produto = new Produto();
    
    public PedidoProduto(Produto produto, int qtd) {
        this.produto = produto;
        this.qtd = qtd;
    }

}
