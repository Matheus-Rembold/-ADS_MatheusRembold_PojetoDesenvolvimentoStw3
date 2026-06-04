/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.escola.trabalhofinal.facade;

import br.escola.trabalhofinal.entity.ProfessorEntity;
import br.upf.projetojfprimefaces.facade.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;


@Stateless
public class PessoaFacade extends AbstractFacade<ProfessorEntity> {

    public PessoaFacade(Class<ProfessorEntity> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
