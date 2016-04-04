package br.com.upperfinanceiro.util;

import br.com.upperfinanceiro.DAO.CategoriaDAO;
import br.com.upperfinanceiro.DAO.CategoriaDAOHibernate;
import br.com.upperfinanceiro.DAO.ChequeDAO;
import br.com.upperfinanceiro.DAO.ChequeDAOHibernate;
import br.com.upperfinanceiro.DAO.ContaDAO;
import br.com.upperfinanceiro.DAO.ContaDAOHibernate;
import br.com.upperfinanceiro.DAO.LancamentoDAO;
import br.com.upperfinanceiro.DAO.LancamentoDAOHibernate;
import br.com.upperfinanceiro.DAO.UsuarioDAO;
import br.com.upperfinanceiro.DAO.UsuarioDAOHibernate;

//Classe construtora de DAOs. Único ponto onde as classes DAOs devem ser instanciadas
public class DAOFactory
{

    public static UsuarioDAO criarUsuarioDAO()
    {
        UsuarioDAOHibernate usuarioDAOH = new UsuarioDAOHibernate();
        usuarioDAOH.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return usuarioDAOH;
    }

    public static ContaDAO criarContaDAO()
    {
        ContaDAOHibernate contaDAOH = new ContaDAOHibernate();
        contaDAOH.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return contaDAOH;
    }
    
    public static CategoriaDAO criarCategoriaDAO()
    {
        CategoriaDAOHibernate categoriaDAOH = new CategoriaDAOHibernate();
        categoriaDAOH.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return categoriaDAOH;
    }
    
    public static LancamentoDAO criarLancamentoDAO()
    {
        LancamentoDAOHibernate lancamentoDAOH = new LancamentoDAOHibernate();
        lancamentoDAOH.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return lancamentoDAOH;
    }
    
    public static ChequeDAO criarChequeDAO()
    {
        ChequeDAOHibernate chequeDAO = new ChequeDAOHibernate();
        chequeDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return chequeDAO;
    }
    
    
}
