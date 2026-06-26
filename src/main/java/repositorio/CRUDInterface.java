package repositorio;

import java.util.List;

public interface CRUDInterface<T> {
	public void criar(T obj);
	public T localizar(Object chave);
	public T atualizar(T obj);
	public void deletar(T obj) ;
	public List<T> listar();
}
