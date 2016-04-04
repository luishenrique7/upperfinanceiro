package br.com.upperfinanceiro.RN;

import br.com.upperfinanceiro.DAO.CategoriaDAO;
import br.com.upperfinanceiro.model.Categoria;
import br.com.upperfinanceiro.model.Usuario;
import br.com.upperfinanceiro.util.DAOFactory;
import java.util.List;

public class CategoriaRN
{
    private CategoriaDAO categoriaDAO;
    
    public CategoriaRN()
    {
        this.categoriaDAO = DAOFactory.criarCategoriaDAO();
    }
    
    public List<Categoria> listar(Usuario usuario)
    {
        return this.categoriaDAO.listar(usuario);
    }
    
    public Categoria salvar(Categoria categoria)
    {
        Categoria pai = categoria.getPai();
        //Verifica se a categoria tem um pai.
        if(pai == null)
        {
            String msg = "A Categoria "+ categoria.getDescricao() + " deve ter um pai definido!";
            throw new IllegalArgumentException(msg);
        }
        
        //
        boolean mudouFator = pai.getFator() != categoria.getFator();
        
        categoria.setFator(pai.getFator());
        categoria = this.categoriaDAO.salvar(categoria);
        
        //Verifica se mudou de categoria (no caso de RECEITA para DESPESA). Muda só o fator da categoria atual.
        //Só entra no processo se  for true.
        if (mudouFator)
        {
            //Carrega toda a estrutura, pois o objeto não vem com os 'filhos' carregados.
            categoria = this.carregar(categoria.getCodigo());
            //Aplica a categoria e pega o fator da mesma.
            this.replicarFator(categoria, categoria.getFator());
        }
        
        return categoria;
    }
    
    //Método para repassar a mudança de fator para todas as categorias da hierarquia abaixo da categoria alterada.
    private void replicarFator(Categoria categoria, int fator)
    {
        if(categoria.getFilhos() != null)
        {
            for (Categoria filho : categoria.getFilhos())
            {
                filho.setFator(fator);
                this.categoriaDAO.salvar(filho);
                this.replicarFator(filho, fator);
                       
            }
        }
    }
    
    public void excluir(Categoria categoria)
    {
        this.categoriaDAO.excluir(categoria);
    }
    
    //Realiza a exclusão por usuário.
    public void excluir(Usuario usuario)
    {
        List<Categoria> lista = this.listar(usuario);
        for (Categoria categoria : lista)
        {
            this.categoriaDAO.excluir(categoria);
        }
    }
    
    public Categoria carregar(Integer categoria)
    {
        return this.categoriaDAO.carregar(categoria);
    }
    
    public void salvarEstruturaPadrao(Usuario usuario)
    {
        Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
        despesas = this.categoriaDAO.salvar(despesas);
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Moradia", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Alimentação", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Roupas", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Transporte", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Educação", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Saúde", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Lazer", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Pagamentos", -1));
        this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Outros", -1));
        
        
        Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
        receitas = this.categoriaDAO.salvar(receitas);
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Salário", 1));
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Extras", 1));
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Investimentos", 1));
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Prêmio", 1));
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Presente", 1));
        this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Outros", 1));
        
    }
    
    
    
    
}
