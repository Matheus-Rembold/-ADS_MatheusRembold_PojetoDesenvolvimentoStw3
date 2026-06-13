package br.escola.trabalhofinal.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "parecer")
public class ParecerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "conteudo")
    private String conteudo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "periodo")
    private String periodo;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "dataemissao")
    private Date dataEmissao;

    // mapeamento (n:1) - vários pareceres podem pertencer ao mesmo aluno
    @ManyToOne(optional = false)
    @JoinColumn(name = "idaluno", referencedColumnName = "id")
    private AlunoEntity idAluno;

    // mapeamento (n:1) - vários pareceres podem ser escritos pelo mesmo professor
    @ManyToOne(optional = false)
    @JoinColumn(name = "idprofessor", referencedColumnName = "id")
    private ProfessorEntity idProfessor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public AlunoEntity getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(AlunoEntity idAluno) {
        this.idAluno = idAluno;
    }

    public ProfessorEntity getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(ProfessorEntity idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParecerEntity other = (ParecerEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Parecer " + periodo;
    }

}
