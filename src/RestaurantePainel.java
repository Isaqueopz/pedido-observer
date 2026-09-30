public class RestaurantePainel implements Observer {
    @Override
    public void update(Pedido pedido) {
        System.out.println("Restaurante recebeu atualização: Pedido está " + pedido.getStatus());
    }
}
