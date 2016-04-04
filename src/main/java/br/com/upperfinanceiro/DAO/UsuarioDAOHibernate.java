package br.com.upperfinanceiro.DAO;

import br.com.upperfinanceiro.model.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO
{
    //método Session é responsável por fazer as operações do Hibernate chegarem ao bd.
    private Session session;

    public void setSession(Session session)
    {
        this.session = session;
    }

    @Override
    public void salvar(Usuario usuario)
    {
        //cadastrar um novo usuário
        this.session.save(usuario);
    }

    @Override
    public void atualizar(Usuario usuario)
    {
        //Verificar se o usuário tem alguma permissão.
        if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0)
        {
            //Caso as permissões estejam vazias, é recaregadao jogando o objeto en uma referência a parte.
            Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
            //Transfere as permissões originais para o objeto usuario a ser salvo.
            usuario.setPermissao(usuarioPermissao.getPermissao());
            //Chama o evict para retirar do contexo persistente o objeto usuarioPermissao.
            this.session.evict(usuarioPermissao);
        }
        this.session.update(usuario);
    }

    
    @Override
    public void excluir(Usuario usuario)
    {
        //excluir um usuário
        this.session.delete(usuario);
    }

    @Override
    public Usuario carregar(Integer codigo)
    {
        //carrega todos os dados do usuário fitrando pelo parâmetro
        return (Usuario) this.session.get(Usuario.class, codigo);
    }
    
    @Override
    public List<Usuario> listar()
    {
        //consultar todos os usuários do banco devolvendo uma Lista de Usuários
        return this.session.createCriteria(Usuario.class).list();
    }

    @Override
    public Usuario buscarPorLogin(String login)
    {
        //consultar com HQL, linguagem própria do Hibernate
        String hql = "select u from Usuario u where u.login = :login";
        Query query = this.session.createQuery(hql);
        query.setString("login", login);
        //se o resultado trazer apenas uma linha usar .uniqueResult(), senão usar o método .list()
        //obs: se a consultar trazer mais de um resultado e estiver usando o .uniqueResult(), será gerada a exceção NonUniqueResultException
        return (Usuario) query.uniqueResult();
    }

    
}
