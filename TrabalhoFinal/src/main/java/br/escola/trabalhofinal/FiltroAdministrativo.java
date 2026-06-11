package br.escola.trabalhofinal;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FiltroAdministrativo implements Filter {

    private FilterConfig filterConfig = null;

    public FiltroAdministrativo() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        ProfessorEntity professorLogado = (ProfessorEntity) session.getAttribute("professorLogado");

        if (professorLogado == null) {
            ((HttpServletResponse) response).sendRedirect("../login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FiltroAdministrativo()");
        }
        StringBuffer sb = new StringBuffer("FiltroAdministrativo(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

}
