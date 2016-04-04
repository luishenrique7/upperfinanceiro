package br.com.upperfinanceiro.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OrderBy;

@Entity
public class Categoria implements Serializable
{
    @Id
    @GeneratedValue
    private Integer codigo;
    
    @ManyToOne
    //Atributo name refere ao nome da coluna da tabela e o 2º name refere ao nome da fk.
    @JoinColumn(name = "categoria_pai", nullable = true, 
                foreignKey = @ForeignKey(name = "fk_categoria_categoria"))
    private Categoria pai;
    
    @ManyToOne
    @JoinColumn(name = "usuario", foreignKey = @ForeignKey(name = "fk_categoria_usuario"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;
    
    private String descricao;
    
    private int fator;
    
    //Carregar a lista de categorias filhas. EAGER faz a carga imediata no momento da consulta. REMOVE excluirá os filhos caso a categoria pai for excluída.
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    //Carregar todas as categorias cujo o campo 'name' seja igual o cod. da categoria atual.
    @JoinColumn(name = "categoria_pai", updatable = false)
    //Especifica a ordenação para os filhos.
    @OrderBy(clause = "descricao asc")
    private List<Categoria> filhos;

    public Categoria()
    {
    }

    //Construtor completo para criar as categorias padrão do sistema.
    public Categoria(Categoria pai, Usuario usuario, String descricao, int fator)
    {
        this.pai = pai;
        this.usuario = usuario;
        this.descricao = descricao;
        this.fator = fator;
    }

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo(Integer codigo)
    {
        this.codigo = codigo;
    }

    public Categoria getPai()
    {
        return pai;
    }

    public void setPai(Categoria pai)
    {
        this.pai = pai;
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

    public int getFator()
    {
        return fator;
    }

    public void setFator(int fator)
    {
        this.fator = fator;
    }

    public List<Categoria> getFilhos()
    {
        return filhos;
    }

    public void setFilhos(List<Categoria> filhos)
    {
        this.filhos = filhos;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.codigo, other.codigo))
        {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
