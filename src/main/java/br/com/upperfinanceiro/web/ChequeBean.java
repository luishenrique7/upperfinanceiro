//package br.com.upperfinanceiro.web;
//
//import br.com.upperfinanceiro.RN.ChequeRN;
//import br.com.upperfinanceiro.model.Cheque;
//import br.com.upperfinanceiro.model.Conta;
//import br.com.upperfinanceiro.util.RNException;
//import java.util.List;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.context.FacesContext;
//
//@ManagedBean(name = "chequeBean")
//@RequestScoped
//public class ChequeBean
//{
//
//    private Cheque selecionado = new Cheque();
//    private List<Cheque> lista = null;
//    private Integer chequeInicial;
//    private Integer chequeFinal;
//    @ManagedProperty(value = "#{contextoBean}")
//    private ContextoBean contextoBean;
//
//    public void salvar()
//    {
//        FacesContext context = FacesContext.getCurrentInstance();
//        Conta conta = contextoBean.getContaAtiva();
//
//        if (this.chequeInicial == null || this.chequeFinal == null)
//        {
//            //MensagemUtil é necessária para a internacionalização do sistema.
//            String texto = MensagemUtil.getMensagem("cheque_erro_sequencia");
//            //Aqui é exibido a mensagem para o usuário.
//            context.addMessage(null, new FacesMessage(texto));
//        } else if (this.chequeFinal < this.chequeInicial)
//        {
//            String texto = MensagemUtil.getMensagem("cheque_erro_inicial_final", this.chequeInicial, this.chequeFinal);
//            context.addMessage(null, new FacesMessage(texto));
//        } else
//        {
//            ChequeRN chequeRN = new ChequeRN();
//            int totalCheques = chequeRN.salvarSequencia(conta, this.chequeInicial, this.chequeFinal);
//            String texto = MensagemUtil.getMensagem("cheque_info_cadastro", this.chequeFinal, totalCheques);
//            context.addMessage(null, new FacesMessage(texto));
//            
//            this.lista = null;
//        }
//    }
//    
//    //
//    public void excluir()
//    {
//        ChequeRN chequeRN = new ChequeRN();
//        
//        try
//        {
//            chequeRN.excluir(this.selecionado);
////        } catch (RNException e)
//        } catch (Exception e)
//        {
//            FacesContext context = FacesContext.getCurrentInstance();
//            String texto = MensagemUtil.getMensagem("cheque_erro_excluir");
//            FacesMessage msg = new FacesMessage(texto);
//            msg.setSeverity(FacesMessage.SEVERITY_WARN);
//            context.addMessage(null, msg);
//        }
//        this.lista = null;
//    }
//    
//    public void cancelar()
//    {
//        ChequeRN chequeRN = new ChequeRN();
//        
//        try
//        {
//            chequeRN.cancelarCheque(this.selecionado);
//        } catch (Exception e)
//        {
//            FacesContext context = FacesContext.getCurrentInstance();
//            String texto = MensagemUtil.getMensagem("cheque_erro_cancelar");
//            FacesMessage msg = new FacesMessage(texto);
//            msg.setSeverity(FacesMessage.SEVERITY_WARN);
//            context.addMessage(null, msg);
//        }
//        this.lista = null;
//    }
//    
//    public List<Cheque> getLista()
//    {
//        if (this.lista == null)
//        {
//            Conta conta = contextoBean.getContaAtiva();
//            ChequeRN chequeRN = new ChequeRN();
//            this.lista = chequeRN.listar(conta);
//        }
//        return this.lista;
//    }
//
//    public Cheque getSelecionado()
//    {
//        return selecionado;
//    }
//
//    public void setSelecionado(Cheque selecionado)
//    {
//        this.selecionado = selecionado;
//    }
//
//    public Integer getChequeInicial()
//    {
//        return chequeInicial;
//    }
//
//    public void setChequeInicial(Integer chequeInicial)
//    {
//        this.chequeInicial = chequeInicial;
//    }
//
//    public Integer getChequeFinal()
//    {
//        return chequeFinal;
//    }
//
//    public void setChequeFinal(Integer chequeFinal)
//    {
//        this.chequeFinal = chequeFinal;
//    }
//
//    public ContextoBean getContextoBean()
//    {
//        return contextoBean;
//    }
//
//    public void setContextoBean(ContextoBean contextoBean)
//    {
//        this.contextoBean = contextoBean;
//    }
//}
