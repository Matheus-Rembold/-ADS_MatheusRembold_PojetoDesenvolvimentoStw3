package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.ParecerEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "parecerController")
@SessionScoped
public class ParecerController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.ParecerFacade ejbFacade;

    private ParecerEntity parecer = new ParecerEntity();
    private List<ParecerEntity> parecerList = new ArrayList<>();
    private ParecerEntity selected;

    public ParecerEntity getSelected() {
        return selected;
    }

    public void setSelected(ParecerEntity selected) {
        this.selected = selected;
    }

    public ParecerEntity getParecer() {
        return parecer;
    }

    public void setParecer(ParecerEntity parecer) {
        this.parecer = parecer;
    }

    public List<ParecerEntity> getParecerList() {
        return ejbFacade.buscarTodos();
    }

    public void setParecerList(List<ParecerEntity> parecerList) {
        this.parecerList = parecerList;
    }

    /**
     * Lista de períodos disponíveis para seleção no formulário.
     * @return
     */
    public List<String> getPeriodos() {
        List<String> periodos = new ArrayList<>();
        periodos.add("1º Bimestre");
        periodos.add("2º Bimestre");
        periodos.add("3º Bimestre");
        periodos.add("4º Bimestre");
        return periodos;
    }

    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de um parecer.
     * @return
     */
    public ParecerEntity prepareAdicionar() {
        parecer = new ParecerEntity();
        return parecer;
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
                        ejbFacade.createReturn(parecer);
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
        persist(PersistAction.CREATE, "Parecer cadastrado com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Parecer alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Parecer excluído com sucesso!");
    }

}
