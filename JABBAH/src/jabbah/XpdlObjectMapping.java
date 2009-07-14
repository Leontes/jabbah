/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jabbah;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 *  Object filled with the corresponding values from the source XPDL
 * file, so that we maintain an OO structure that is going to be used everywhere
 */
public class XpdlObjectMapping
{

    Participant[] Participants;
    Parameter[] Parameters;
    Transition[] Transitions;
    Activity[] Activities;
    Lane[] Lanes;

    public void XpdlObjectMapping()
    {
    }

    public void normalize()
    {
    }

    public MyWeightedVertex findActivityNode(String id)
    {
        for (int i=0; i< this.Activities.length; i++)
            if (this.Activities[i].id.equalsIgnoreCase(id))
                return this.Activities[i].node;
        
        return null;
    }

    public void parse(String source_file)
            throws SAXException, XPathExpressionException,
            IOException, ParserConfigurationException
    {
        // TODO code application logic here

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(source_file);

        XPathFactory xfactory = XPathFactory.newInstance();

        XPath xpath = xfactory.newXPath();
        xpath.setNamespaceContext(new XpdlNamespaceContext());

        this.parseLanes(doc, xpath);
        this.parseParticipants(doc, xpath);

        this.parseActivities(doc, xpath);
        this.parseTransitions(doc, xpath);
    }

    private void parseLanes(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_lane = null;
        try
        {
            exp_lane = xpath.compile("//xpdl2:Lane");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_lane = null;
        try
        {
            res_lane = exp_lane.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes = (NodeList) res_lane;

        Lanes = new Lane[nodes.getLength()];

        // in this loop, the "Lanes" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Lanes[i] = new Lane();

            Node l_name, l_id;
            try
            {
                l_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                Lanes[i].name = l_name.getNodeValue();

                l_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                Lanes[i].id = l_id.getNodeValue();

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void parseParticipants(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_participant = null;
        try
        {
            exp_participant = xpath.compile("//xpdl2:Participant");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_act = null;
        try
        {
            res_act = exp_participant.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes = (NodeList) res_act;

        Participants = new Participant[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Participants[i] = new Participant();

            Node a_name, a_id, a_extended, a_lane;
            try
            {
                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (a_name != null)
                {
                    Participants[i].name = a_name.getNodeValue();
                }

                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (a_id != null)
                {
                    Participants[i].id = a_id.getNodeValue();
                }

                // check if the participant is assigned to a specific Lane, by using
                // an extended Attribute called "Lane"
                a_extended = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Name", nodes.item(i),
                        XPathConstants.NODE);
                if (a_extended != null)
                {
                    if (a_extended.getNodeValue().equalsIgnoreCase("Lane"))
                    {

                        a_lane = (Node) xpath.evaluate("xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Value", nodes.item(i),
                                XPathConstants.NODE);
                        if (a_lane != null)
                        {
                            Participants[i].lane = a_lane.getNodeValue();
                        }
                    }
                }


            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void parseActivities(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_activity = null;
        try
        {
            exp_activity = xpath.compile("//xpdl2:Activity");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_act = null;
        try
        {
            res_act = exp_activity.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes = (NodeList) res_act;

        Activities = new Activity[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Activities[i] = new Activity();

            Node a_name, a_id, a_gateway, g_type;
            try
            {
                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                if (a_name != null)
                {
                    Activities[i].name = a_name.getNodeValue();
                }

                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                if (a_id != null)
                {
                    Activities[i].id = a_id.getNodeValue();
                }

                a_gateway = (Node) xpath.evaluate("xpdl2:Route/@GatewayType", nodes.item(i),
                        XPathConstants.NODE);
                // the node is a GATEWAY node
                if (a_gateway != null)
                {
                    Activities[i].type = NodeType.GATEWAY;

                    g_type = (Node) xpath.evaluate(
                            "xpdl2:TransitionRestrictions/xpdl2:TransitionRestriction/xpdl2:Split/@Type",
                            nodes.item(i),
                            XPathConstants.NODE);
                    // the node is surely a JOIN
                    if (g_type == null)
                    {
                        g_type = (Node) xpath.evaluate(
                                "xpdl2:TransitionRestrictions/xpdl2:TransitionRestriction/xpdl2:Join/@Type",
                                nodes.item(i),
                                XPathConstants.NODE);
                        if (g_type != null)
                        {
                            if (g_type.getNodeValue().equalsIgnoreCase("Exclusive"))
                            {
                                Activities[i].restriction = TransitionRestriction.JOIN_EXCLUSIVE;
                            }
                            else if (g_type.getNodeValue().equalsIgnoreCase("Inclusive"))
                            {
                                Activities[i].restriction = TransitionRestriction.JOIN_INCLUSIVE;
                            }
                        }
                    }
                    // the node is a SPLIT
                    else
                    {
                        if (g_type.getNodeValue().equalsIgnoreCase("Parallel"))
                            {
                                Activities[i].restriction = TransitionRestriction.SPLIT_PARALLEL;
                            }
                            else if (g_type.getNodeValue().equalsIgnoreCase("Exclusive"))
                            {
                                Activities[i].restriction = TransitionRestriction.SPLIT_EXCLUSIVE;
                            }
                    }

                }

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void parseTransitions(org.w3c.dom.Document doc, XPath xpath)
    {
        XPathExpression exp_trans = null;
        try
        {
            exp_trans = xpath.compile("//xpdl2:Transition");
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object res_trans = null;
        try
        {
            res_trans = exp_trans.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex)
        {
            Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
        }

        NodeList nodes = (NodeList) res_trans;

        Transitions = new Transition[nodes.getLength()];

        // in this loop, the "Activities" array is populated, using xpath parsing on the context node
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Transitions[i] = new Transition();

            Node a_name, a_id, a_from, a_to;
            try
            {
                a_id = (Node) xpath.evaluate("@Id", nodes.item(i), XPathConstants.NODE);
                Transitions[i].id = a_id.getNodeValue();

                a_name = (Node) xpath.evaluate("@Name", nodes.item(i), XPathConstants.NODE);
                Transitions[i].name = a_name.getNodeValue();

                a_from = (Node) xpath.evaluate("@From", nodes.item(i), XPathConstants.NODE);
                Transitions[i].from = a_from.getNodeValue();

                a_to = (Node) xpath.evaluate("@To", nodes.item(i), XPathConstants.NODE);
                Transitions[i].to = a_to.getNodeValue();

            } catch (XPathExpressionException ex)
            {
                Logger.getLogger(XpdlObjectMapping.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}