package br.com.upperfinanceiro.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Lancamento implements Serializable
{
    @Id
    @GeneratedValue
    private Integer lancamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_lancamento_usuario"))
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conta", nullable = false, foreignKey = @ForeignKey(name = "fk_lancamento_conta"))
    private Conta conta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categoria", nullable = false, foreignKey = @ForeignKey(name = "fk_lancamento_categoria"))
    private Categoria categoria;
    
    //Define o campo para gerar apenas a data no banco. Por padrão é DateTime.
    @Temporal(TemporalType.DATE)
    private Date data;
    
    private String descricao;
    
    //Define que o campo terá 10 digitos sendo 2 decimais.
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;
    
    //Novamente mapeamos um-pra-um indicando a parte de mapeamento de dados será na classe Cheque.
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lancamento")
    private Cheque cheque;
    

    public Lancamento()
    {
    }

    public Lancamento(Integer lancamento, Usuario usuario, Conta conta, Categoria categoria, Date data, String descricao, BigDecimal valor)
    {
        this.lancamento = lancamento;
        this.usuario = usuario;
        this.conta = conta;
        this.categoria = categoria;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Integer getLancamento()
    {
        return lancamento;
    }

    public void setLancamento(Integer lancamento)
    {
        this.lancamento = lancamento;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public Conta getConta()
    {
        return conta;
    }

    public void setConta(Conta conta)
    {
        this.conta = conta;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public BigDecimal getValor()
    {
        return valor;
    }

    public void setValor(BigDecimal valor)
    {
        this.valor = valor;
    }

    public Cheque getCheque()
    {
        return cheque;
    }

    public void setCheque(Cheque cheque)
    {
        this.cheque = cheque;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.lancamento);
        hash = 71 * hash + Objects.hashCode(this.usuario);
        hash = 71 * hash + Objects.hashCode(this.conta);
        hash = 71 * hash + Objects.hashCode(this.categoria);
        hash = 71 * hash + Objects.hashCode(this.data);
        hash = 71 * hash + Objects.hashCode(this.descricao);
        hash = 71 * hash + Objects.hashCode(this.valor);
        hash = 71 * hash + Objects.hashCode(this.cheque);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Lancamento other = (Lancamento) obj;
        if (!Objects.equals(this.descricao, other.descricao))
        {
            return false;
        }
        if (!Objects.equals(this.lancamento, other.lancamento))
        {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario))
        {
            return false;
        }
        if (!Objects.equals(this.conta, other.conta))
        {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria))
        {
            return false;
        }
        if (!Objects.equals(this.data, other.data))
        {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor))
        {
            return false;
        }
        if (!Objects.equals(this.cheque, other.cheque))
        {
            return false;
        }
        return true;
    }
}
