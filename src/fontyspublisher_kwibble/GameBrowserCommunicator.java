package fontyspublisher_kwibble;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Created by Max on 23-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameBrowserCommunicator
        extends UnicastRemoteObject
        implements IRemotePropertyListener {

    // Remote publisher
    private IRemotePublisherForDomain publisherForDomain;
    private IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private static String bindingName = "publisher";
    private boolean connected = false;

    public String findServers() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            System.out.println(Arrays.toString(registry.list()));
            return registry.toString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameBrowserCommunicator() throws RemoteException {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }
}
