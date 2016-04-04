package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ContaDAOHibernate implements ContaDAO
{
    private Session session;

    public void setSession(Session session)
    {
        this.session = session;
    }
    
    @Override
    public void salvar(Conta conta)
    {
        this.session.saveOrUpdate(conta);
    }

    @Override
    public void excluir(Conta conta)
    {
        this.session.delete(conta);
    }

    @Override
    public Conta carregar(Integer conta)
    {
        return (Conta) this.session.get(Conta.class, conta);
    }

    @Override
    public List<Conta> listar(Usuario usuario)
    {
        //Faz a consulta das contas de um determinado usuário, como tem um filtro de outra tabela utiliza o Criteria.
        Criteria criteria = this.session.createCriteria(Conta.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        return criteria.list();
        
    }

    @Override
    public Conta buscarFavorita(Usuario usuario)
    {
        Criteria criteria = this.session.createCriteria(Conta.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.add(Restrictions.eq("favorita", true));
        return (Conta) criteria.uniqueResult();
    }
    
}
