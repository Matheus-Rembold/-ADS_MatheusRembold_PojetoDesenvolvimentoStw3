package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "professorController")
@SessionScoped
public class ProfessorController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.ProfessorFacade ejbFacade;

    private ProfessorEntity professor = new ProfessorEntity();
    private List<ProfessorEntity> professorList = new ArrayList<>();
    private ProfessorEntity selected;

    public ProfessorEntity getSelected() {
        return selected;
    }

    public void setSelected(ProfessorEntity selected) {
        this.selected = selected;
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }

    public List<ProfessorEntity> getProfessorList() {
        return ejbFacade.buscarTodos();
    }

    public void setProfessorList(List<ProfessorEntity> professorList) {
        this.professorList = professorList;
    }

    /**
     * Prepara um novo objeto professor antes de abrir o formulário de criação.
     * @return 
     */
    public ProfessorEntity prepareAdicionar() {
        professor = new ProfessorEntity();
        return professor;
    }

    public void adicionarProfessor() {
        persist(PersistAction.CREATE, "Professor cadastrado com sucesso!");
    }

    public void editarProfessor() {
        persist(PersistAction.UPDATE, "Professor alterado com sucesso!");
    }

    public void deletarProfessor() {
        persist(PersistAction.DELETE, "Professor excluído com sucesso!");
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
                        ejbFacade.createReturn(professor);
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

}
