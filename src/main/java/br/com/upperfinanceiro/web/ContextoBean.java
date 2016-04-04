package br.com.upperfinanceiro.web;

import br.com.upperfinanceiro.RN.ContaRN;
import br.com.upperfinanceiro.RN.UsuarioRN;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable
{
    //gerar serialVersionUID
    private int codigoContaAtiva = 0;

    //Obtém o login do usuário remoto e executa a carga desse usuário usando o método da classe UsuarioRN.
    public Usuario getUsuarioLogado()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        String login = external.getRemoteUser();

        if (login != null)
        {
            UsuarioRN usuarioRN = new UsuarioRN();
            return usuarioRN.buscarPorLogin(login);
        }
        return null;
    }
    
    //Fornece a conta ativa no momento.
    public Conta getContaAtiva()
    {
        Conta contaAtiva = null;
        //Se não tiver conta ativa, obtém a conta favorita do usuário logado, ou a primeira cadastrada.
        if (this.codigoContaAtiva == 0)
        {
            contaAtiva = this.getContaAtivaPadrao();
        } else
        {
            ContaRN contaRN = new ContaRN();
            contaAtiva = contaRN.carregar(this.codigoContaAtiva);
        }

        if (contaAtiva != null)
        {
            this.codigoContaAtiva = contaAtiva.getConta();
            return contaAtiva;
        }
        return null;
    }

    //Lógica para determinar qual será a conta padrão.
    private Conta getContaAtivaPadrao()
    {
        ContaRN contaRN = new ContaRN();
        Conta contaAtiva = null;
        //Pega o usuário logado.
        Usuario usuario = this.getUsuarioLogado();
        //Seta pra contaAtiva a conta favorita.
        contaAtiva = contaRN.buscarFavorita(usuario);
        //Se o usuário não tive nenhuma conta ativa, ele coloca por padrão a primeira.
        if (contaAtiva == null)
        {
            List<Conta> contas = contaRN.listar(usuario);
            if (contas != null && contas.size() > 0)
            {
                //Seta a primeira conta da lista como padrão.
                contaAtiva = contas.get(0);
            }
        }
        return contaAtiva;
    }
    
    //Método para exibir os itens do comboBox
    public void changeContaAtiva(ValueChangeEvent event)
    {
        this.codigoContaAtiva = (Integer) event.getNewValue();
    }
}
