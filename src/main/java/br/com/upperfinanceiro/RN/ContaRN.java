package br.com.upperfinanceiro.RN;

import br.com.upperfinanceiro.DAO.ContaDAO;
import br.com.upperfinanceiro.model.Conta;
import br.com.upperfinanceiro.model.Usuario;
import br.com.upperfinanceiro.util.DAOFactory;
import java.util.Date;
import java.util.List;

public class ContaRN
{

    private ContaDAO contaDAO;

    public ContaRN()
    {
        this.contaDAO = DAOFactory.criarContaDAO();
    }

    //Lista de todas as contas de um determinado uauário.
    public List<Conta> listar(Usuario usuario)
    {
        return this.contaDAO.listar(usuario);
    }

    public Conta carregar(Integer conta)
    {
        return this.contaDAO.carregar(conta);
    }

    public void salvar(Conta conta)
    {
        //define a data atual para data de cadastro
        conta.setCadastro(new Date());
        this.contaDAO.salvar(conta);
    }

    public void excluir(Conta conta)
    {
        this.contaDAO.excluir(conta);
    }

    //Busca a conta bancária que ficará imediatamente ativa no sistema.
    public Conta buscarFavorita(Usuario usuario)
    {
        return this.contaDAO.buscarFavorita(usuario);
    }

    //Registrar uma determinada conta como favorita, somente uma.
    public void tornarFavorita(Conta contaFavorita)
    {
        //Obtém a conta favorita atual
        Conta conta = this.buscarFavorita(contaFavorita.getUsuario());
        if (conta != null)
        {
            //Seta a conta favorita atual como false e salva.
            conta.setFavorita(false);
            this.contaDAO.salvar(conta);
        }
        //Marca a conta recebida no parâmetro (que será a nova favorita) como true e salva.
        contaFavorita.setFavorita(true);
        this.contaDAO.salvar(conta);
    }

}
