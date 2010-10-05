/* 
This file is part of the Jabbah Framework, a software that is able to
carry out a transformation between Business Process Models into 
Hierarchical Task Networks Planning domains.

    Copyright (C) 2010
    	      Arturo Gonzalez-Ferrer, arturogf@decsai.ugr.es

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los t�rminos de la Academic Free License ("AFL") v. 3.0).

    Este programa se distribuye con la esperanza de que sea �til, pero 
    SIN GARANT�A ALGUNA; ni siquiera la garant�a impl�cita 
    MERCANTIL o de APTITUD PARA UN PROP�SITO DETERMINADO. 
    Consulte los detalles de la Academic Free License ("AFL) v 3.0 para obtener 
    una informaci�n m�s detallada. 

    Deber�a haber recibido una copia de la Academic Free License ("AFL") v. 3.0
    junto a este programa. 


*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jabbah;

import java.util.Vector;

/**
 *
 * @author arturogf
 */
public class PDDLExpression {

    String expression = "";

    @Override
    public String toString()
    {
        return expression;
    }

    /**
     *
     * @param arguments the predicates to add as arguments of 'and' condition
     */
    public void AND(PDDLExpression...arguments)
    {
        if (arguments.length > 1)
            expression = expression + "(and ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.length;i++)
        {
            expression = expression + arguments[i].toString();
        }

        expression = expression + ")";

    }

    public void AND(Vector arguments)
    {
        if (arguments.size() > 1)
            expression = expression + "(and ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.size(); i++)
        {
            expression = expression + arguments.get(i).toString();
        }

        expression = expression + ")";

    }


    public void OR(PDDLExpression...arguments)
    {
        if (arguments.length > 1)
            expression = expression + "(or ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.length;i++)
        {
            expression = expression + arguments[i].toString();
        }

            expression = expression + ")";

    }
     public void OR(Vector arguments)
    {
        if (arguments.size() > 1)
            expression = expression + "(or ";
        else
            expression = expression + "(";


        for (int i=0; i<arguments.size(); i++)
        {
            expression = expression + arguments.get(i).toString();
        }

        expression = expression + ")";

    }


    public String predicate(String name, String...arguments)
    {
        String expr = "(" + name + " ";

         for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i].toString() + " ";
        }

        expr = expr + ")";
        
        return expr;
    }

    public void setPredicate(String name, String...arguments)
    {
        String expr = "(" + name + " ";

         for (int i=0; i<arguments.length;i++)
        {
            expr = expr + arguments[i].toString() + " ";
        }

        expr = expr + ")";

        this.expression = expr;
    }

}



