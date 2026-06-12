package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.TurmaEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "turmaController")
@SessionScoped
public class TurmaController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.TurmaFacade ejbFacade;

    private TurmaEntity turma = new TurmaEntity();
    private List<TurmaEntity> turmaList = new ArrayList<>();
    private TurmaEntity selected;

    public TurmaEntity getSelected() {
        return selected;
    }

    public void setSelected(TurmaEntity selected) {
        this.selected = selected;
    }

    public TurmaEntity getTurma() {
        return turma;
    }

    public void setTurma(TurmaEntity turma) {
        this.turma = turma;
    }

    public List<TurmaEntity> getTurmaList() {
        return ejbFacade.buscarTodos();
    }

    public void setTurmaList(List<TurmaEntity> turmaList) {
        this.turmaList = turmaList;
    }

    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de uma turma.
     * @return
     */
    public TurmaEntity prepareAdicionar() {
        turma = new TurmaEntity();
        return turma;
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
                        ejbFacade.createReturn(turma);
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
        persist(PersistAction.CREATE, "Turma cadastrada com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Turma alterada com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Turma excluída com sucesso!");
    }

}
