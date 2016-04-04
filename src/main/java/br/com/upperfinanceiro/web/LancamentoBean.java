package br.com.upperfinanceiro.web;

import br.com.upperfinanceiro.RN.LancamentoRN;
import br.com.upperfinanceiro.model.Categoria;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Lancamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @crete for Luis Henrique at 20/01/2016
 */
@ManagedBean
//Mantém a mesma instância da classe Bean enquanto o usuário estiver na mesma tela.
@ViewScoped
public class LancamentoBean implements Serializable
{
//    private static final long serialVersionUD = -3050807461213326560L;

    private List<Lancamento> lista;
    private Conta conta;
    private List<Double> saldos;
    private float saldoGeral;
    private Lancamento editado = new Lancamento();
    @ManagedProperty(value = "#{contextoBean}")
    private ContextoBean contextoBean;

    public LancamentoBean()
    {
        this.novo();
    }

    public String novo()
    {
        this.editado = new Lancamento();
        this.editado.setData(new Date());
        return null;
    }

    public void editar()
    {

    }

    public void salvar()
    {
        this.editado.setUsuario(this.contextoBean.getUsuarioLogado());
        this.editado.setConta(this.contextoBean.getContaAtiva());

        LancamentoRN lancamentoRN = new LancamentoRN();
        lancamentoRN.salvar(this.editado);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lançamento inserido!", "Lançamento inserido!"));

        this.novo();
        this.lista = null;
    }

    public void excluir()
    {
        LancamentoRN lancamentoRN = new LancamentoRN();
        lancamentoRN.excluir(this.editado);
        this.lista = null;
    }

    public List<Lancamento> getLista()
    {
        //Esse if garante que a listagem seja recarregada se o usuário trocar de conta ativa.
        if (this.lista == null || this.conta != this.contextoBean.getContaAtiva())
        {
            this.conta = this.contextoBean.getContaAtiva();

            Calendar dataSaldo = new GregorianCalendar();
            dataSaldo.add(Calendar.MONTH, -1);
            dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

            Calendar inicio = new GregorianCalendar();
            inicio.add(Calendar.MONTH, -1);

            LancamentoRN lancamentoRN = new LancamentoRN();
            this.saldoGeral = lancamentoRN.saldo(this.conta, dataSaldo.getTime());
            this.lista = lancamentoRN.listar(this.conta, inicio.getTime(), null);

            Categoria categoria = null;
            double saldo = this.saldoGeral;
            this.saldos = new ArrayList<>();
            for (Lancamento lancamento : this.lista)
            {
                categoria = lancamento.getCategoria();
                saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
                this.saldos.add(saldo);
            }
        }
        return this.lista;
    }

    public Conta getConta()
    {
        return conta;
    }

    public void setConta(Conta conta)
    {
        this.conta = conta;
    }

    public List<Double> getSaldos()
    {
        return saldos;
    }

    public void setSaldos(List<Double> saldos)
    {
        this.saldos = saldos;
    }

    public float getSaldoGeral()
    {
        return saldoGeral;
    }

    public void setSaldoGeral(float saldoGeral)
    {
        this.saldoGeral = saldoGeral;
    }

    public Lancamento getEditado()
    {
        return editado;
    }

    public void setEditado(Lancamento editado)
    {
        this.editado = editado;
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
