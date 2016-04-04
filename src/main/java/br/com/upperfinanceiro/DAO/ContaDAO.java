package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Usuario;
import java.util.List;

public interface ContaDAO
{
    public void salvar(Conta conta);
    
    public void excluir(Conta conta);
    
    public Conta carregar(Integer conta);
    
    //Método para listar as contas por parâmetro, de um determinado usuário.
    public List<Conta> listar(Usuario usuario);
    
    //Retorna a conta favorita de um usuário
    public Conta buscarFavorita(Usuario usuario);
    
    
}
