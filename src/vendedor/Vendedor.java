package vendedor;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import subasta.Subasta;

import java.util.HashMap;

public class Vendedor extends Agent {

    private AID[] agentesCompradores;
    private HashMap<Integer, Subasta> subastas;
    private GUIVendedor gui;

    private int idSubasta;

    @Override
    protected void setup() {
        subastas = new HashMap<>();
        idSubasta = 0;

        gui = new GUIVendedor(this);
        gui.setTitle(getLocalName());
        gui.setVisible(true);

        //System.out.println("Auctioneer: The English Auction will start with the starting price of " + libro.getPrecio());

        // Implemento un comportamiento que se ejecuta una sola vez para registrar a los compradores
       /* addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                // Conseguimos los agentes del tipo subasta-comprador
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("subasta-comprador");
                template.addServices(sd);

                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);

                    agentesCompradores = new AID[result.length];
                    for (int i = 0; i < result.length; i++) {
                        System.out.println("comprador.Comprador: " + result[i].getName());

                        agentesCompradores[i] = result[i].getName();
                    }

                } catch (FIPAException e) {
                    e.printStackTrace();
                }
                myAgent.addBehaviour(new SubastarLibro(subasta));
            }
        });*/
    }

    public Subasta subastarLibro(String titulo, float precio, float incremento) {
        Subasta subasta = new Subasta(idSubasta, titulo, precio, incremento);
        subastas.put(idSubasta, subasta);

        addBehaviour(new SubastarLibro(subasta));

        idSubasta++;
        return subasta;
    }

    public void actualizarInterfaz(){

    }
}
