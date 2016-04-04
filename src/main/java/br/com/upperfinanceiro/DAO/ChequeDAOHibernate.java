package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Cheque;
import br.com.upperfinanceiro.model.ChequeId;
import br.com.upperfinanceiro.model.Conta;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ChequeDAOHibernate implements ChequeDAO
{
    private Session session;

    public void setSession(Session session)
    {
        this.session = session;
    }
    

    @Override
    public void salvar(Cheque cheque)
    {
        this.session.saveOrUpdate(cheque);
    }

    @Override
    public void excluir(Cheque cheque)
    {
        this.session.delete(cheque);
    }

    //Por ser chave composta passamos a variável do tipo ChequeId que representa od @Id.
    @Override
    public Cheque carregar(ChequeId chequeId)
    {
        return (Cheque) this.session.get(Cheque.class, chequeId);
    }
    
    //Em listar passamos o parametro Conta já que queremos listar todos Cheques vinculados a uma conta.
    @Override
    public List<Cheque> listar(Conta conta)
    {
        Criteria criteria = this.session.createCriteria(Cheque.class);
        criteria.add(Restrictions.eq("conta", conta));
        return criteria.list();
    }
    
}
