package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.AlunoEntity;
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

@Named(value = "alunoController")
@SessionScoped
public class AlunoController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.AlunoFacade ejbFacade;

    private AlunoEntity aluno = new AlunoEntity();
    private List<AlunoEntity> alunoList = new ArrayList<>();
    private AlunoEntity selected;

    public AlunoEntity getSelected() {
        return selected;
    }

    public void setSelected(AlunoEntity selected) {
        this.selected = selected;
    }

    public AlunoEntity getAluno() {
        return aluno;
    }

    public void setAluno(AlunoEntity aluno) {
        this.aluno = aluno;
    }

    public List<AlunoEntity> getAlunoList() {
        return ejbFacade.buscarTodos();
    }

    public void setAlunoList(List<AlunoEntity> alunoList) {
        this.alunoList = alunoList;
    }

    /**
     * Busca um aluno pelo id. Utilizado pelo converter para reconstruir
     * o objeto AlunoEntity a partir do valor selecionado no selectOneMenu.
     */
    public AlunoEntity getAluno(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * 
     */
    @FacesConverter(forClass = AlunoEntity.class)
    public static class AlunoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlunoController controller
                    = (AlunoController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "alunoController");
            return controller.getAluno(getKey(value));
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
            if (object instanceof AlunoEntity) {
                AlunoEntity o = (AlunoEntity) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }
    }

    /**
    
     * @return
     */
    public AlunoEntity prepareAdicionar() {
        aluno = new AlunoEntity();
        return aluno;
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
                        ejbFacade.createReturn(aluno);
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
        persist(PersistAction.CREATE, "Aluno cadastrado com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Aluno alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Aluno excluído com sucesso!");
    }

}
