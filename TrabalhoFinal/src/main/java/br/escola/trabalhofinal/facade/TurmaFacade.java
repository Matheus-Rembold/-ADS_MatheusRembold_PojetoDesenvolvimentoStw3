package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.TurmaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TurmaFacade extends AbstractFacade<TurmaEntity> {

    @PersistenceContext(unitName = "PareceresEscolarPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TurmaFacade() {
        super(TurmaEntity.class);
    }

    private List<TurmaEntity> entityList;

    /**
     * Busca todas as turmas cadastradas ordenadas por série.
     * @return
     */
    public List<TurmaEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT t FROM TurmaEntity t ORDER BY t.serie");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<TurmaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar turmas: " + e);
        }
        return entityList;
    }

}
