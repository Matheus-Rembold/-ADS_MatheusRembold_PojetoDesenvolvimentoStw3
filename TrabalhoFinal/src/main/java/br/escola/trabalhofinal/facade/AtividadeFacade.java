package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.AtividadeEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AtividadeFacade extends AbstractFacade<AtividadeEntity> {

    @PersistenceContext(unitName = "PareceresEscolarPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtividadeFacade() {
        super(AtividadeEntity.class);
    }

    private List<AtividadeEntity> entityList;

    /**
     * Busca todas as atividades cadastradas ordenadas por descrição.
     * @return
     */
    public List<AtividadeEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT a FROM AtividadeEntity a ORDER BY a.descricao");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<AtividadeEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar atividades: " + e);
        }
        return entityList;
    }

}
