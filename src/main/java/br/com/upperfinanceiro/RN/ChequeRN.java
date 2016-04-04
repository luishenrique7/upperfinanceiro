package br.com.upperfinanceiro.RN;

import br.com.upperfinanceiro.DAO.ChequeDAO;
import br.com.upperfinanceiro.model.Cheque;
import br.com.upperfinanceiro.model.ChequeId;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Lancamento;
import br.com.upperfinanceiro.util.DAOFactory;
import br.com.upperfinanceiro.util.RNException;
import java.util.Date;
import java.util.List;

public class ChequeRN 
{
    private ChequeDAO chequeDAO;

    public ChequeRN()
    {
        this.chequeDAO = DAOFactory.criarChequeDAO();
    }
    
    public void salvar(Cheque cheque)
    {
        this.chequeDAO.salvar(cheque);
    }
    
    //Método para definir a sequencia de cheques
    public int salvarSequencia(Conta conta, Integer chequeInicial, Integer chequeFinal)
    {
        Cheque cheque = null;
        ChequeId chequeId = null;
        
        int contaTotal = 0;
        
        for (int i = chequeInicial; i <= chequeFinal; i++)
        {
            chequeId = new ChequeId(conta.getConta(), i);
            cheque = this.carregar(chequeId);
            
            if (cheque == null)
            {
                cheque = new Cheque();
                cheque.setChequeId(chequeId);
                cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
                cheque.setDataCadastro(new Date());
                this.salvar(cheque);
                contaTotal++;
            }
        }
        return contaTotal;
    }
    
    //Regra que só exclui cheque não emitidos.
    public void excluir(Cheque cheque) throws Exception
    {
        if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO)
        {
            this.chequeDAO.excluir(cheque);
        } else
        {
            throw new RNException("IMPOSSÍVEL EXCLUIR! Só podem ser excluídos cheques não emitidos!");
        }
    }
    
    //Um cheque é carregado conforme sua chave, que no caso vem da classe ChequeId.
    public Cheque carregar(ChequeId chequeId)
    {
        return this.chequeDAO.carregar(chequeId);
    }
    
    public List<Cheque> listar(Conta conta)
    {
        return this.listar(conta);
    }
    
    //Só pode ser cancelados cheques não baixados.
    public void cancelarCheque(Cheque cheque) throws Exception
    {
        if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO || 
            cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO)
        {
            cheque.setSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO);
            this.chequeDAO.salvar(cheque);
        } else
        {
            throw new RNException("IMPOSSÍVEL CANCELAR! Só podem ser cancelados cheques não emitidos!");
        }
    }
    
    // Primeiro é carregado o Id do cheque, se ele for nulo ele é baixado, lançado e salvo. 
    public void baixarCheque(ChequeId chequeId, Lancamento lancamento)
    {
        Cheque cheque = this.carregar(chequeId);
        
        if (cheque != null)
        {
            cheque.setSituacao(Cheque.SITUACAO_CHEQUE_BAIXADO);
            cheque.setLancamento(lancamento);
            this.chequeDAO.salvar(cheque);
        }
    }
    
    //Desvincula um cheque de um lançamento editado e outro numero de cheque lançado no lugar.
    public void desvinculaLancamento(Conta conta, Integer numeroCheque) throws Exception
    {
        ChequeId chequeId = new ChequeId(conta.getConta(), numeroCheque);
        Cheque cheque = this.carregar(chequeId);
        
        if (cheque == null)
        {
            return;
        }
        if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO)
        {
            throw new RNException("ERRO: Não é possível desvincular cheque cancelado!");
        } else
        {
            cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
            cheque.setLancamento(null);
            this.salvar(cheque);
        }
    }
    
    
    
    
}
