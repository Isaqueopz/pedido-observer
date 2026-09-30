public class EntregadorApp implements Observer {
    @Override
    public void update(Pedido pedido) {
        System.out.println("Entregador recebeu atualização: Pedido está " + pedido.getStatus());
    }
}
