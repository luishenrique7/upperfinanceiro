package br.com.upperfinanceiro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Conta implements Serializable 
{
    @Id
    @GeneratedValue
    private Integer conta;
    /*Usuário é a chave estrangeira (muitas contas para um usuário).
    ManyToOne e JoinColumn carregará todos os dados do usuário quando a conta do mesmo for carregada.
    OnDelete exclui todas as contas do usuário se ele for excluído.*/
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Usuario usuario;
    private String descricao;
    //Campo não pode ser nulo nem alterado posteriormente.
    @Column( nullable = false, updatable = false)
    private Date cadastro;
    private float saldoInicial;
    private boolean favorita;

    public Integer getConta()
    {
        return conta;
    }

    public void setConta(Integer conta)
    {
        this.conta = conta;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public Date getCadastro()
    {
        return cadastro;
    }

    public void setCadastro(Date cadastro)
    {
        this.cadastro = cadastro;
    }

    public float getSaldoInicial()
    {
        return saldoInicial;
    }

    public void setSaldoInicial(float saldoInicial)
    {
        this.saldoInicial = saldoInicial;
    }

    public boolean isFavorita()
    {
        return favorita;
    }

    public void setFavorita(boolean favorita)
    {
        this.favorita = favorita;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.conta);
        hash = 67 * hash + Objects.hashCode(this.usuario);
        hash = 67 * hash + Objects.hashCode(this.descricao);
        hash = 67 * hash + Objects.hashCode(this.cadastro);
        hash = 67 * hash + Float.floatToIntBits(this.saldoInicial);
        hash = 67 * hash + (this.favorita ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Conta other = (Conta) obj;
        if (!Objects.equals(this.conta, other.conta))
        {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario))
        {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao))
        {
            return false;
        }
        if (!Objects.equals(this.cadastro, other.cadastro))
        {
            return false;
        }
        if (Float.floatToIntBits(this.saldoInicial) != Float.floatToIntBits(other.saldoInicial))
        {
            return false;
        }
        if (this.favorita != other.favorita)
        {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
}
