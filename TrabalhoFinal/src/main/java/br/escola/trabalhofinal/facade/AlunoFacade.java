package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.AlunoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AlunoFacade extends AbstractFacade<AlunoEntity> {

    @PersistenceContext(unitName = "PareceresEscolarPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(AlunoEntity.class);
    }

    private List<AlunoEntity> entityList;

    /**
     * Busca todos os alunos cadastrados ordenados por nome.
     * @return
     */
    public List<AlunoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT a FROM AlunoEntity a ORDER BY a.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<AlunoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar alunos: " + e);
        }
        return entityList;
    }

}
