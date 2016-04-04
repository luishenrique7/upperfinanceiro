package br.com.upperfinanceiro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name =  "cheque")
public class Cheque implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static final char SITUACAO_CHEQUE_BAIXADO =     'B';
    public static final char SITUACAO_CHEQUE_CANCELADO =   'C';
    public static final char SITUACAO_CHEQUE_NAO_EMITIDO = 'N';
    
    //Mapeamento da chave primária composta
    @EmbeddedId
    private ChequeId chequeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conta", referencedColumnName = "conta", insertable = false, 
                updatable = false, foreignKey = @ForeignKey(name = "fk_cheque_conta"))
    private Conta conta;
    
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    @Column(nullable = false, precision = 1)
    private Character situacao;
    
    // Mapeamento um-para-um pois no caso o relacionamento é direto, o lançamento poderá ter apenas um cheque vinculado.
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lancamento", referencedColumnName = "codigo", nullable = true,
                foreignKey = @ForeignKey(name = "fk_cheque_lancamento"))
    private Lancamento lancamento;

    
    public Cheque()
    {
    }

    public ChequeId getChequeId()
    {
        return chequeId;
    }

    public void setChequeId(ChequeId chequeId)
    {
        this.chequeId = chequeId;
    }

    public Conta getConta()
    {
        return conta;
    }

    public void setConta(Conta conta)
    {
        this.conta = conta;
    }

    public Date getDataCadastro()
    {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

    public Character getSituacao()
    {
        return situacao;
    }

    public void setSituacao(Character situacao)
    {
        this.situacao = situacao;
    }

    public Lancamento getLancamento()
    {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento)
    {
        this.lancamento = lancamento;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.chequeId);
        hash = 97 * hash + Objects.hashCode(this.conta);
        hash = 97 * hash + Objects.hashCode(this.dataCadastro);
        hash = 97 * hash + Objects.hashCode(this.situacao);
        hash = 97 * hash + Objects.hashCode(this.lancamento);
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
        final Cheque other = (Cheque) obj;
        if (!Objects.equals(this.chequeId, other.chequeId))
        {
            return false;
        }
        if (!Objects.equals(this.conta, other.conta))
        {
            return false;
        }
        if (!Objects.equals(this.dataCadastro, other.dataCadastro))
        {
            return false;
        }
        if (!Objects.equals(this.situacao, other.situacao))
        {
            return false;
        }
        if (!Objects.equals(this.lancamento, other.lancamento))
        {
            return false;
        }
        return true;
    }
    
}
