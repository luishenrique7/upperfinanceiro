package br.com.upperfinanceiro.RN;

import br.com.upperfinanceiro.DAO.LancamentoDAO;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Lancamento;
import br.com.upperfinanceiro.util.DAOFactory;
import java.util.Date;
import java.util.List;

public class LancamentoRN
{
    private LancamentoDAO lancamentoDAO;

    public LancamentoRN()
    {
        this.lancamentoDAO = DAOFactory.criarLancamentoDAO();
    }
    
    public void salvar(Lancamento lancamento)
    {
        this.lancamentoDAO.salvar(lancamento);
    }
    
    public void excluir(Lancamento lancamento)
    {
        this.lancamentoDAO.excluir(lancamento);
    }
    
    public Lancamento carregar(Integer lancamento)
    {
        return this.lancamentoDAO.carregar(lancamento);
    }
    
    public float saldo(Conta conta, Date data)
    {
        float saldoInicial = conta.getSaldoInicial();
        float saldoNaData = this.lancamentoDAO.saldo(conta, data);
        return saldoInicial + saldoNaData;
    }
    
    public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim)
    {
        return this.lancamentoDAO.listar(conta, dataInicio, dataFim);
    }
    
    
    
}
