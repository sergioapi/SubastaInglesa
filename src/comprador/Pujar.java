package comprador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import subasta.Subasta;

public class Pujar extends Behaviour {
    private Subasta subasta;
    private float maxPuja;

    public Pujar(Subasta subasta, float maximo) {
        this.subasta = subasta;
        this.maxPuja = maximo;
    }

    @Override
    public void action() {
        // Define un patrón de mensaje que coincide con mensajes de tipo CFP (Call For Proposals)
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

        // Intenta recibir un mensaje que coincida con el patrón
        ACLMessage msj = myAgent.receive();

        if (msj != null) {
            // Interpreta el contenido del mensaje recibido
            interpretarContenido(msj.getContent());

            // Crea una respuesta al mensaje recibido
            ACLMessage respuesta = msj.createReply();
            float puja;

            // Decide si pujar o rechazar basándose en el precio actual del libro y la máxima puja permitida
            if (subasta.getPrecioLibro() <= maxPuja + 1) {
                puja = subasta.getPrecioLibro() + 1;

                // Configura la respuesta como una propuesta de puja
                respuesta.setPerformative(ACLMessage.PROPOSE);
                respuesta.setContent(String.valueOf(puja));
            } else {
                // Si no está dispuesto a pujar más, rechaza la oferta
                respuesta.setPerformative(ACLMessage.REFUSE);
            }

            // Envía la respuesta al agente que inició la subasta
            myAgent.send(respuesta);
        } else {
            // Si el mensaje es nulo, se bloquea a la espera de que llegue un mensaje
            block();
        }
    }

    private void interpretarContenido(String contenido) {
        // Divide el contenido del mensaje utilizando "||" como separador
        String[] split = contenido.split("\\|\\|");

        // Asigna el nombre y precio del libro a la instancia de Libro
        String nombre = split[0];
        int precio = Integer.parseInt(split[1]);

    }


    @Override
    public boolean done() {
        // Al devolver false, el método action se ejecutará constantemente
        return false;
    }
}
