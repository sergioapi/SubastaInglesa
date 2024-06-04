package vendedor;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import subasta.Subasta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SubastarLibro extends Behaviour {


    // mapa para almacenar las pujas recibidas de los Compradores
    private Map<AID, Integer> pujasRecibidas = new HashMap<>();
    private MessageTemplate mt;
    private AID pujadorMax = null;
    private int pujaMax = 0;
    private int incremento = 0;
    private int pujasEsperadas = 0;

    private int etapa = 0;
    private Subasta subasta;

    private ArrayList<AID> compradores;

    public SubastarLibro(Subasta subasta) {
        this.subasta = subasta;
    }

    @Override
    public void action() {
        switch (etapa) {
            // Inicio de la subasta
            case 0:
                ((Vendedor) myAgent).actualizarInterfaz();
                buscarCompradores();

                if (!compradores.isEmpty()) {
                    // Creo un msj (Call for Proposals) para solicitar las ofertas
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

                    // Añado como receptores del msj a los compradores
                    for (int i = 0; i < compradores.size(); i++) {
                        cfp.addReceiver(compradores.get(i));
                    }

                    // Configuro el contenido del mensaje con el nombre y precio del Libro
                    cfp.setContent(subasta.getTituloLibro() + "||" + subasta.getPrecioLibro());
                    // Establezco identificadores para el msj y su respuesta
                    cfp.setConversationId("" + subasta.getIdSubasta());
                    cfp.setReplyWith("" + subasta.getIdSubasta() + System.currentTimeMillis());

                    // Envio el msj
                    myAgent.send(cfp);

                    // Configuro la plantilla de msjs para filtrar respuestas
                    mt = MessageTemplate.and(
                            MessageTemplate.MatchConversationId("" + subasta.getIdSubasta()),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));

                    etapa = 1;
                    break;
                } else {
                    block(3000);
                }

            // Recepcion de respuestas
            case 1:
                ACLMessage respuesta = myAgent.receive(mt);
                if (respuesta != null) {
                    if (respuesta.getPerformative() == ACLMessage.PROPOSE) {
                        // Almaceno la puja recibida
                        pujasRecibidas.put(respuesta.getSender(), Integer.parseInt(respuesta.getContent()));

                        System.out.println(respuesta.getSender().getName() + " puja: " + respuesta.getContent());


                    }
                    if (pujasRecibidas.size() == pujasEsperadas) {
                        // Paso a la siguiente etapa al recibir todas las pujas esperadas
                        etapa = 2;
                    }
                } else {
                    // Espero a recibir mensajes que coincidan con la Plantilla de respuestas
                    block();
                }
                break;
            // Determinar la puja más alta
            case 2:
                // Itero sobre las pujas recibidas para encontrar la mas alta
                Iterator<Map.Entry<AID, Integer>> iter = pujasRecibidas.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<AID, Integer> item = iter.next();
                    if (item.getValue() != null && pujaMax < item.getValue()) {
                        pujadorMax = item.getKey();
                        pujaMax = item.getValue();
                    }
                }

                // Envio un mensaje de aceptacion al comprador.Comprador que hizo la puja mas alta y rechazo las demas
                ACLMessage accept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                accept.addReceiver(pujadorMax);
                accept.setContent(subasta.getTituloLibro() + "||" + pujaMax);
                // Establezco identificadores para el msj y su respuesta
                accept.setConversationId("" + subasta.getIdSubasta());
                accept.setReplyWith("puja-aceptada" + System.currentTimeMillis());

                myAgent.send(accept);

                // Envio un msj de rechazo a los demas Compradores

                etapa = 3;
                break;
        }
    }

    private void buscarCompradores() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("subasta-comprador");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            compradores.clear();
            DFAgentDescription[] result = DFService.search(myAgent, dfd);
            for (DFAgentDescription df : result) {
                compradores.add(df.getName());
            }
        } catch (FIPAException ex) {
            System.out.println();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
