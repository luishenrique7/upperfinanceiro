package br.com.upperfinanceiro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{
    @Id
    @GeneratedValue
    private Integer codigo;
    @Column(/*name = "usuario_nome",*/ nullable = true)
    private String nome;
    @Column(nullable = true)
    private String email;
    @Column(nullable = true)
    private String cpf;
    @NaturalId
    @Column(nullable = true)
    private String login;
    @Column(nullable = true)
    private String senha;
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date nascimento;
    @Column(nullable = false)
    private String celular;
    @Column(nullable = false)
    private String idioma;
    @Column(nullable = false)
    private boolean ativo;
    //Declara qual tipo de classe será carregada no conjunto (Set) de valores, no caso é String.
    @ElementCollection(targetClass = String.class)
    /*JoinTable é muito útil para relacionar informações simples a uma tabela.
    name: descreve o nome da tabela do lado "muitos" do relacionamento que é um-para-muitos.
    uniqueConstraints: cria um índice único na tabela usuario_permissao entre os campos usuario e permissao.
    joinColumns: Informa quais colunas da tabela pai e filho vão se ligar.*/
    @JoinTable(name = "usuario_permissao",
               uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario", "permissao"})},
               joinColumns = @JoinColumn(name = "usuario"))
    //Traz informações da tabela usuario_permissao
    @Column(name = "permissao", length = 50)
    //Campo do tipo Set para guardar as permissões, pois ele não admite itens repetidos.
    private Set<String> permissao = new HashSet<>();

    public Usuario()
    {
    }

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo(Integer codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }
    
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public Date getNascimento()
    {
        return nascimento;
    }

    public void setNascimento(Date nascimento)
    {
        this.nascimento = nascimento;
    }

    public String getCelular()
    {
        return celular;
    }

    public void setCelular(String celular)
    {
        this.celular = celular;
    }

    public String getIdioma()
    {
        return idioma;
    }

    public void setIdioma(String idioma)
    {
        this.idioma = idioma;
    }

    public boolean isAtivo()
    {
        return ativo;
    }

    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }

    public Set<String> getPermissao()
    {
        return permissao;
    }

    public void setPermissao(Set<String> permissao)
    {
        this.permissao = permissao;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.login);
        hash = 17 * hash + Objects.hashCode(this.senha);
        hash = 17 * hash + Objects.hashCode(this.nascimento);
        hash = 17 * hash + Objects.hashCode(this.celular);
        hash = 17 * hash + Objects.hashCode(this.idioma);
        hash = 17 * hash + (this.ativo ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.permissao);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.codigo, other.codigo))
        {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome))
        {
            return false;
        }
        if (!Objects.equals(this.email, other.email))
        {
            return false;
        }
        if (!Objects.equals(this.login, other.login))
        {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha))
        {
            return false;
        }
        if (!Objects.equals(this.nascimento, other.nascimento))
        {
            return false;
        }
        if (!Objects.equals(this.celular, other.celular))
        {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma))
        {
            return false;
        }
        if (this.ativo != other.ativo)
        {
            return false;
        }
        if (!Objects.equals(this.permissao, other.permissao))
        {
            return false;
        }
        return true;
    }

}
