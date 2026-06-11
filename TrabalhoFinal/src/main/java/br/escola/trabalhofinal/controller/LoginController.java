package br.escola.trabalhofinal.controller;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private br.escola.trabalhofinal.facade.ProfessorFacade ejbFacade;

    private ProfessorEntity professor;

    public LoginController() {
    }

    /**
     * Inicializa um novo objeto professor ao instanciar o controller.
     */
    @PostConstruct
    public void init() {
        prepareAutenticar();
    }

    public void prepareAutenticar() {
        professor = new ProfessorEntity();
    }

    /**
     * Valida o login do professor buscando no banco de dados.
     * Se válido, cria a sessão e redireciona para a área administrativa.
     * @return 
     */
    public String validarLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        ProfessorEntity professorDB = ejbFacade.buscarPorNomeSenha(
                professor.getNome(), professor.getSenha());

        if (professorDB != null && professorDB.getId() != null) {
            session.setAttribute("professorLogado", professorDB);
            return "/admin/professor.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Nome ou senha incorretos!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    /**
     * Invalida a sessão e redireciona para o login.
     * @return 
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }

}
