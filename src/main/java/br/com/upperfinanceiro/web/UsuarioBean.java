package br.com.upperfinanceiro.web;

import br.com.upperfinanceiro.RN.ContaRN;
import br.com.upperfinanceiro.RN.UsuarioRN;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Usuario;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean
{

    private Usuario usuario = new Usuario();
    private String confirmarSenha;
    private List<Usuario> lista;
    private String destinoSalvar;
    private Conta conta = new Conta();

    public String novo()
    {
        //comentar sobre destino salvar
        this.destinoSalvar = "usuariosucesso";
        this.usuario = new Usuario();
        this.usuario.setAtivo(true);
        return "/publico/usuario";
    }

    public String editar()
    {
        //copia a senha do usuário para o campo confirmar senha.
        this.confirmarSenha = this.usuario.getSenha();
        return "/publico/usuario";
    }

    public String salvar()
    {
        FacesContext context = FacesContext.getCurrentInstance();

        String senha = this.usuario.getSenha();
        if (!senha.equals(this.confirmarSenha))
        {
            FacesMessage facesMessage = new FacesMessage("Confirmação de senha não confere!");
            context.addMessage(null, facesMessage);
            // Return null apenas recarrega a página e permanece na mesma.
            return null;
        }

        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.salvar(this.usuario);
        
        //Salvar a conta preenchida jutamente com o cadastro do usuário.
        if (this.conta.getDescricao() != null)
        {
            //Atribuir o usuário que acabou de ser cadastrado.
            this.conta.setUsuario(this.usuario);
            //Marcar a conta como favorita.
            this.conta.setFavorita(true);
            ContaRN contaRN = new ContaRN();
            contaRN.salvar(this.conta);
        }
        
        return this.destinoSalvar;
    }

    public String excluir()
    {
        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.excluir(this.usuario);
        //Chama a lista para forçar a recargados usuários
        this.lista = null;
        return null;
    }

    public String ativar()
    {
        // Se o usuário estiver ativo desativa, e se estiver desativado ativa.
        if (this.usuario.isAtivo())
            this.usuario.setAtivo(false);
        else
            this.usuario.setAtivo(true);
        
        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.salvar(this.usuario);
        
        return null;
    }
    
    //Método que seleciona o usuário em questão, obtém o Set de permissões, e adiciona ou remove permissão.
    public String atribuiPermissao(Usuario usuario, String permissao)
    {
        this.usuario = usuario;
        Set<String> permissoes = this.usuario.getPermissao();
        if (permissoes.contains(permissao))
        {
            permissoes.remove(permissao);
        } else
        {
            permissoes.add(permissao);
        }
        return null;
    }
    
    public List<Usuario> getLista()
    {
        if (this.lista == null)
        {
            UsuarioRN usuarioRN = new UsuarioRN();
            this.lista = usuarioRN.listar();
        }
        return this.lista;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public String getConfirmarSenha()
    {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha)
    {
        this.confirmarSenha = confirmarSenha;
    }

    public String getDestinoSalvar()
    {
        return destinoSalvar;
    }

    public void setDestinoSalvar(String destinoSalvar)
    {
        this.destinoSalvar = destinoSalvar;
    }

    public Conta getConta()
    {
        return conta;
    }

    public void setConta(Conta conta)
    {
        this.conta = conta;
    }

    
    
}
