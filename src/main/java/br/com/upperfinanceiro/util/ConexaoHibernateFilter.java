package br.com.upperfinanceiro.util;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//Configura o tipo de requisição web que a classe Filter vai interceptar
@WebFilter(urlPatterns = {"*.jsf"})
public class ConexaoHibernateFilter implements Filter
{
    private SessionFactory sf;

    //Método é executado quando o aplicativo é colocado no ar.
    public void init(FilterConfig config) throws ServletException
    {
        //Criação do sessionFactory, que cria todas as sessões do Hibernate a cada requisição.
        this.sf = HibernateUtil.getSessionFactory();
    }

    //Método onde toda requisição web pode ser interceptada
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException
    {   
        Session currentSession = this.sf.getCurrentSession();
        
        Transaction transaction = null;
        
        try
        {
            //Iniciando uma transação de banco de dados.
            transaction = currentSession.beginTransaction();
            //Processamento é passado adiante, para a execução normal. Se não executar essa linha a requisição não será lançada.
            chain.doFilter(servletRequest, servletResponse);
            //Conclui a transação do banco dando commit nos dados.
            transaction.commit();
            //Se a sessão estiver aberta fecha a sessão.
            if (currentSession.isOpen())
            {
                currentSession.close();
            }
        } catch (Throwable ex)
        {
            //Se qualquer erro for lançado no processamento o catch trata o(s) erro(s) e da um rollback na transação.
            try
            {
                if(transaction.isActive())
                {
                    transaction.rollback();
                }
            } catch (Throwable t)
            {
                t.printStackTrace();
            }
            throw  new ServletException(ex);
        }
    }

    //É executado quando o aplicativo web é desativado ou o Tomcat é retirado do ar.
    public void destroy(){}
    
}
