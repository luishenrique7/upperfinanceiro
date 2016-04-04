package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Categoria;
import br.com.upperfinanceiro.model.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CategoriaDAOHibernate implements CategoriaDAO
{
    private Session session;
    
    public void setSession(Session session)
    {
        this.session = session;
    }

    @Override
    public Categoria salvar(Categoria categoria)
    {
        //Salvar a entidade categoria. O merge faz a 'fusão' da instância da categoria, uma vez que o Hibernate não permite 2 instância do mesmo obj.
        Categoria merged = (Categoria) this.session.merge(categoria);
        //Força a sincronização dos objetos em memória com o bd.
        this.session.flush();
        //Remove da memória do Hibernate todos os objetos carregados.
        this.session.clear();
        
        return merged;
    }

    @Override
    public void excluir(Categoria categoria)
    {
        //Como pode ocorrer a exclusão em cascata, temos que carregar todos pais filhos e netos para que se for preciso sejam excluídos.
        //Dessa forma todos os filhos e netos estarão carregados e a exclusão em cascata funcionará
        categoria = (Categoria) this.carregar(categoria.getCodigo());
        this.session.delete(categoria);
        this.session.flush();
        this.session.clear();
    }

    @Override
    public Categoria carregar(Integer categoria)
    {
        return (Categoria) this.session.get(Categoria.class, categoria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Categoria> listar(Usuario usuario)
    {
        //Somente os primeiros níveis de categoria será carregado. Os demais será feito pelo relacionamento OneToMany dos filhos.
        String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";
        Query query = this.session.createQuery(hql);
        query.setInteger("usuario", usuario.getCodigo());
        
        List<Categoria> lista = query.list();
        
        return lista;
    }
    
    
    
    
    
    
    
    
    
    
    
}
