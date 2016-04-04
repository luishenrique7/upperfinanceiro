package br.com.upperfinanceiro.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

//Indica que a classe é um objeto e não precisa de um identificador próprio
@Embeddable
public class ChequeId implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    //Essa tag indica que essa propriedade é obrigatória
    @Basic
    @Column(name = "cheque", nullable = false)
    private Integer cheque;
    
    @Basic(optional = false)
    @Column(name = "conta", nullable = false)
    private Integer conta;

    /*Construtor vazio é obrigatório quando se cria um construtor que 
    recebe parametros, pois quando criamos um construtor com parametros o padrão deixa de existir*/
    public ChequeId()
    {
    }

    //Construtor para facilitar a criação de uma instancia da classe
    public ChequeId(Integer cheque, Integer conta)
    {
        this.cheque = cheque;
        this.conta = conta;
    }

    public Integer getCheque()
    {
        return cheque;
    }

    public void setCheque(Integer cheque)
    {
        this.cheque = cheque;
    }

    public Integer getConta()
    {
        return conta;
    }

    public void setConta(Integer conta)
    {
        this.conta = conta;
    }
    
}
