package br.com.upperfinanceiro.web;

import br.com.upperfinanceiro.RN.ContaRN;
import br.com.upperfinanceiro.model.Conta;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ContaBean
{

    private Conta selecionada = new Conta();
    private List<Conta> lista = null;
    @ManagedProperty(value = "#{contextoBean}")
    private ContextoBean contextoBean;

    public String salvar()
    {
        //Atribui a conta o usuário que está logado, pois toda conta relaciona com um usuário.
        this.selecionada.setUsuario(this.contextoBean.getUsuarioLogado());
        ContaRN contaRN = new ContaRN();
        Conta conta = new Conta();
        //Mensagem de Salvamento!
        FacesContext context = FacesContext.getCurrentInstance();
        contaRN.salvar(this.selecionada);
        //mensagem estava dando problema na dialog
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", "Conta inserida!"));

        //"limpar" o formulário quando ele for recarregado.
        this.selecionada = new Conta();
        //forçar a recarga da lista.
        this.lista = null;
        return null;
    }

    public String excluir()
    {
        ContaRN contaRN = new ContaRN();
        //Obtém a conta selecionada e faz a exclusão.
        contaRN.excluir(this.selecionada);
        //"limpar" o formulário quando ele for recarregado.
        this.selecionada = new Conta();
        //forçar a recarga da lista.
        this.lista = null;
        return null;
    }

    public String tornarFavorita()
    {
        ContaRN contaRN = new ContaRN();
        contaRN.tornarFavorita(this.selecionada);
        this.selecionada = new Conta();
        return null;
    }

    public Conta getSelecionada()
    {
        return selecionada;
    }

    public void setSelecionada(Conta selecionada)
    {
        this.selecionada = selecionada;
    }

    public List<Conta> getLista()
    {
        if (this.lista == null)
        {
            ContaRN contaRN = new ContaRN();
            //Obtém uma instância do usuário conectado para consultar as suas contas.
            this.lista = contaRN.listar(this.contextoBean.getUsuarioLogado());
        }
        return this.lista;
    }

    public ContextoBean getContextoBean()
    {
        return contextoBean;
    }

    public void setContextoBean(ContextoBean contextoBean)
    {
        this.contextoBean = contextoBean;
    }

}
