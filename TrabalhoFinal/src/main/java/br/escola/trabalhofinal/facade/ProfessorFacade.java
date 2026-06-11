package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import br.escola.trabalhofinal.entity.ProfessorEntity;
import br.escola.trabalhofinal.facade.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProfessorFacade extends AbstractFacade<ProfessorEntity> {

    @PersistenceContext(unitName = "PareceresEscolarPU")
    private EntityManager em;
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfessorFacade() {
        super(ProfessorEntity.class);
    }

    private List<ProfessorEntity> entityList;

    /**
     * Busca todos os professores cadastrados ordenados por nome.
     * @return 
     */
    public List<ProfessorEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM ProfessorEntity p ORDER BY p.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<ProfessorEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar professores: " + e);
        }
        return entityList;
    }

    /**
     * Busca um professor pelo nome e senha para validar o login.
     * @param nome
     * @param senha
     * @return 
     */
    public ProfessorEntity buscarPorNomeSenha(String nome, String senha) {
        ProfessorEntity professor = new ProfessorEntity();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM ProfessorEntity p WHERE p.nome = :nome AND p.senha = :senha");
            query.setParameter("nome", nome);
            query.setParameter("senha", senha);
            if (!query.getResultList().isEmpty()) {
                professor = (ProfessorEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar professor por login: " + e);
        }
        return professor;
    }

}
