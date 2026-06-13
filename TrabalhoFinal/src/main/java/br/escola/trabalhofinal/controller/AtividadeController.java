package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.AtividadeEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "atividadeController")
@SessionScoped
public class AtividadeController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.AtividadeFacade ejbFacade;

    private AtividadeEntity atividade = new AtividadeEntity();
    private List<AtividadeEntity> atividadeList = new ArrayList<>();
    private AtividadeEntity selected;

    public AtividadeEntity getSelected() {
        return selected;
    }

    public void setSelected(AtividadeEntity selected) {
        this.selected = selected;
    }

    public AtividadeEntity getAtividade() {
        return atividade;
    }

    public void setAtividade(AtividadeEntity atividade) {
        this.atividade = atividade;
    }

    public List<AtividadeEntity> getAtividadeList() {
        return ejbFacade.buscarTodos();
    }

    public void setAtividadeList(List<AtividadeEntity> atividadeList) {
        this.atividadeList = atividadeList;
    }

    /**
     
     * @return
     */
    public AtividadeEntity prepareAdicionar() {
        atividade = new AtividadeEntity();
        return atividade;
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(atividade);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void adicionar() {
        persist(PersistAction.CREATE, "Atividade cadastrada com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Atividade alterada com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Atividade excluída com sucesso!");
    }

}
