package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Lancamento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LancamentoDAOHibernate implements LancamentoDAO
{

    private Session session;

    public void setSession(Session session)
    {
        this.session = session;
    }

    @Override
    public void salvar(Lancamento lancamento)
    {
        this.session.saveOrUpdate(lancamento);
    }

    @Override
    public void excluir(Lancamento lancamento)
    {
        this.session.delete(lancamento);
    }

    @Override
    public Lancamento carregar(Integer lancamento)
    {
        return (Lancamento) this.session.get(Lancamento.class, lancamento);
    }

    @Override
    public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim)
    {
        Criteria criteria = this.session.createCriteria(Lancamento.class);

        if (dataInicio != null && dataFim != null)
        {
            criteria.add(Restrictions.between("data", dataInicio, dataFim));
        } 
        else if (dataInicio != null)
        {
            criteria.add(Restrictions.ge("data", dataInicio));
        } 
        else if (dataFim != null)
        {
            criteria.add(Restrictions.le("data", dataFim));
        }
        
        //Filtro da consulta realizando o .add para cada condição.
        criteria.add(Restrictions.eq("conta", conta));
        //Ordena da mais antiga à atual.
        criteria.addOrder(Order.asc("data"));
        return criteria.list();
    }

    //Método para calcular o total dos lançamentos até a data recebida pelo parâmetro.
    @Override
    public float saldo(Conta conta, Date data)
    {
        StringBuffer sql = new StringBuffer();
        //Na SQL é feito o somatório de todos os lançamentos até a data especificada e multiplicado cada lançamento pelo seu fator.
        sql.append("select sum(l.valor * c.fator)");
        sql.append(" from LANCAMENTO l,");
        sql.append(" CATEGORIA c");
        sql.append(" where l.categoria = c.codigo");
        sql.append(" and l.conta = :conta");
        sql.append(" and l.data <= :data");
        //Faz o cálculo isando uma SQL pura
        SQLQuery query = this.session.createSQLQuery(sql.toString());
        query.setParameter("conta", conta.getConta());
        query.setParameter("data", data);
        BigDecimal saldo = (BigDecimal) query.uniqueResult();
        if (saldo != null) 
        {
            return saldo.floatValue();
        }
        
        return 0f;
        
        
    }

}
