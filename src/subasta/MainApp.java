package subasta;

import jade.core.ProfileImpl;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;


public class MainApp {

    public static void main(String[] args) throws ControllerException {

        Properties p = new ExtendedProperties();
        p.setProperty("gui", "true");
        ProfileImpl perfil = new ProfileImpl(p);
        AgentContainer contenedor = jade.core.Runtime.instance().createMainContainer(perfil);
        contenedor.start();

        AgentController agenteVendedor = contenedor.createNewAgent("vendedor.Vendedor", "vendedor.Vendedor", new Object[]{});
        agenteVendedor.start();
        AgentController agenteComprador1 = contenedor.createNewAgent("Comprador1", "comprador.Comprador", new Object[]{});
        agenteComprador1.start();
        AgentController agenteComprador2 = contenedor.createNewAgent("Comprador2", "comprador.Comprador", new Object[]{});
        agenteComprador2.start();

    }
}
