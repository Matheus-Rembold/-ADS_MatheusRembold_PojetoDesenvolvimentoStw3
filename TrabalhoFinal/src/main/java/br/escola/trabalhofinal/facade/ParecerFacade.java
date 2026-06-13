package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.ParecerEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ParecerFacade extends AbstractFacade<ParecerEntity> {

    @PersistenceContext(unitName = "PareceresEscolarPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParecerFacade() {
        super(ParecerEntity.class);
    }

    private List<ParecerEntity> entityList;

    /**
     * Busca todos os pareceres cadastrados ordenados pela data de emissão
     * (mais recentes primeiro).
     * @return
     */
    public List<ParecerEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM ParecerEntity p ORDER BY p.dataEmissao DESC");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<ParecerEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar pareceres: " + e);
        }
        return entityList;
    }

}
