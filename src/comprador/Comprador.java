package comprador;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import subasta.Subasta;

import javax.swing.*;
import java.util.HashMap;
import java.util.Random;

public class Comprador extends Agent {
    private HashMap<String, Float> libros;
    private GUIComprador gui;

    private HashMap<Integer, Subasta> subastas;

    private float maxPuja;

    @Override
    protected void setup() {
        gui = new GUIComprador(this);
        gui.setTitle(getLocalName());
        gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        gui.setVisible(true);

        // Agrega el comportamiento SubastaBehaviour al agente comprador
        addBehaviour(new Pujar(subastas.get(1), maxPuja));

        // Registra el agente comprador en las páginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("subasta-comprador");
        sd.setName("SistemaMultiAgente-subastas");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println(getAID().getName() + ": Estoy listo. (Estoy dispuesto a pujar como máximo: " + maxPuja + ")");
    }

    public void anadirLibro(String nombre, float precio) {
        libros.put(nombre, precio);

    }

    @Override
    protected void takeDown() {
        try {
            // Deregistra el agente comprador de las páginas amarillas antes de terminar
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        System.out.println("comprador.Comprador " + getAID().getName() + " saliendo");
    }

}
