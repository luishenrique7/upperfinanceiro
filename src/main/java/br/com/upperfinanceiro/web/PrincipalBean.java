package br.com.upperfinanceiro.web;

import br.com.upperfinanceiro.RN.LancamentoRN;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Lancamento;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class PrincipalBean
{
    private List<Lancamento> listaAtehoje;
    private List<Lancamento> listaFuturos;
    @ManagedProperty(value = "#{contextoBean}")
    private ContextoBean contextoBean;

    public List<Lancamento> getListaAtehoje()
    {
        if (this.listaAtehoje == null)
        {
            Conta conta = this.contextoBean.getContaAtiva();
            
            Calendar hoje = new GregorianCalendar();
            
            LancamentoRN lancamentoRN = new LancamentoRN();
            this.listaAtehoje = lancamentoRN.listar(conta, null, hoje.getTime());
        }
        
        return this.listaAtehoje;
    }

    public List<Lancamento> getListaFuturos()
    {
        if (this.listaFuturos == null)
        {
            Conta conta = this.contextoBean.getContaAtiva();
            
            Calendar amanha = new GregorianCalendar();
            amanha.add(Calendar.DAY_OF_MONTH, 1);
            
            LancamentoRN lancamentoRN = new LancamentoRN();
            this.listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);
        }
        
        return listaFuturos;
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
