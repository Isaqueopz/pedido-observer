import java.util.ArrayList;
import java.util.List;

// Subject concreto: mantém o estado e avisa os observadores registrados.
public class Pedido implements Subject {
    private final List<Observer> observadores = new ArrayList<>();
    private StatusPedido status = StatusPedido.RECEBIDO;

    @Override
    public void registerObserver(Observer observer) {
        observadores.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observadores.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.update(this);
        }
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
        notifyObservers();
    }
}
