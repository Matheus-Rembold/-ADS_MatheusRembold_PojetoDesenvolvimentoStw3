package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
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
     * Busca um professor pelo id. Utilizado pelo converter para reconstruir
     * o objeto ProfessorEntity a partir do valor selecionado no selectOneMenu.
     */
    public ProfessorEntity getProfessor(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * 
     */
    @FacesConverter(forClass = ProfessorEntity.class)
    public static class ProfessorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfessorController controller
                    = (ProfessorController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "professorController");
            return controller.getProfessor(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProfessorEntity) {
                ProfessorEntity o = (ProfessorEntity) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }
    }

    /**
     * 
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
