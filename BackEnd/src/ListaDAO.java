import java.util.List;

public interface ListaDAO<T, K> {
    public T get(K chave);
    public void add(T p);
    public ListaDAO update(T p);
    public ListaDAO delete(T p);
    public List<T> getAll();

}
