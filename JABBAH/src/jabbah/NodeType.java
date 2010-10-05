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
package jabbah;

/**
 *
 * Define some constants that represents the type of node  
 * @param DEFAULT is an activity
 * @param GATEWAY is a Route activity
 * @param SERIAL is used in the block detection algorithm
 * @param PARALLEL is used in the block detection algorithm
 *  
 */
public class NodeType
{
    public static final int DEFAULT = 0;
    public static final int SERIAL = 1;
    public static final int PARALLEL = 2;
    public static final int GATEWAY = 3;
    public static final int START = 4;
    public static final int END = 5;
    public static final int SUBPROCESS = 6;
}

