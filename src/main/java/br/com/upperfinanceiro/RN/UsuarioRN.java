package br.com.upperfinanceiro.RN;

import br.com.upperfinanceiro.DAO.UsuarioDAO;
import br.com.upperfinanceiro.model.Usuario;
import br.com.upperfinanceiro.util.DAOFactory;
import java.util.List;

public class UsuarioRN
{
    private UsuarioDAO usuarioDAO;

    public UsuarioRN()
    {
        //Instância da propriedade DAO no construtor.
        this.usuarioDAO = DAOFactory.criarUsuarioDAO();
    }
    
    public Usuario carregar(Integer codigo)
    {
        return this.usuarioDAO.carregar(codigo);
    }
    
    public Usuario buscarPorLogin(String login)
    {
        return this.usuarioDAO.buscarPorLogin(login);
    }
    
    public void salvar(Usuario usuario)
    {
        Integer codigo = usuario.getCodigo();
        if(codigo == null || codigo == 0)
        {
            usuario.getPermissao().add("ROLE_USUARIO");
            this.usuarioDAO.salvar(usuario);
            //Quando o usuário for registrado no sistema, já é criado a estrutura padrão de Categorias para ele.
            CategoriaRN categoriaRN = new CategoriaRN();
            categoriaRN.salvarEstruturaPadrao(usuario);
        } else
        {
            this.usuarioDAO.atualizar(usuario);
        }
    }
    
    public void excluir(Usuario usuario)
    {
        CategoriaRN categoriaRN = new CategoriaRN();
        //Quando o usuário for excluído, exclui as categorias que ele tinha também.
        categoriaRN.excluir(usuario);
        this.usuarioDAO.excluir(usuario);
    }
    
    public List<Usuario> listar()
    {
        return this.usuarioDAO.listar();
    }
     
    
}
