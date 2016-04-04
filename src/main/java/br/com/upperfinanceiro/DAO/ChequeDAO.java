package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Cheque;
import br.com.upperfinanceiro.model.ChequeId;
import br.com.upperfinanceiro.model.Conta;
import java.util.List;

public interface ChequeDAO
{
    public void salvar(Cheque cheque);
    
    public void excluir(Cheque cheque);
    
    public Cheque carregar(ChequeId chequeId);
    
    public List<Cheque> listar(Conta conta);
    
}
