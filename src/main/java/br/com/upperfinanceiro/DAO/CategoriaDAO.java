package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Categoria;
import br.com.upperfinanceiro.model.Usuario;
import java.util.List;

public interface CategoriaDAO
{
    public Categoria salvar(Categoria categoria);
    
    public void excluir(Categoria categoria);
    
    public Categoria carregar(Integer categoria);
    
    public List<Categoria> listar(Usuario usuario);
    
}
