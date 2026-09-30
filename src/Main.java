public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido();

        ClienteApp cliente = new ClienteApp();
        RestaurantePainel restaurante = new RestaurantePainel();
        EntregadorApp entregador = new EntregadorApp();

        pedido.registerObserver(cliente);
        pedido.registerObserver(restaurante);
        pedido.registerObserver(entregador);

        pedido.setStatus(StatusPedido.PREPARANDO);
        pedido.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
        pedido.setStatus(StatusPedido.ENTREGUE);
    }
}
