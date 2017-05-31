package fontyspublisher_kwibble;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;
import gui.controller.GameRoomController;
import gui.controller.MainMenuController;
import models.questions.Question;
import models.questions.SerQuestion;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by Max on 24-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * Handles communication at Game room level.
 * Code has been copied and modified from our own use from: https://git.fhict.nl/I889854/FontysPublisherWhiteboard
 *
 */
public class GameRoomCommunicator
        extends UnicastRemoteObject
        implements IRemotePropertyListener {
    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    GameRoomController controller = null;

    // Remote publisher
    private IRemotePublisherForDomain publisherForDomain;
    private IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private String bindingName = "";
    private boolean connected = false;

    // Thread pool
    private final int nrThreads = 20;
    private ExecutorService threadPool = null;

    public GameRoomCommunicator(GameRoomController controller) throws RemoteException {
        threadPool = Executors.newFixedThreadPool(nrThreads);
        this.controller = controller;
    }

    /**
     * Connect to a server via a binding name.
     * Note that the connection is currently hardcode to localhost.
     * @param bindingName The name of the server to connect to.
     */
    public void connectToServer(String ip ,String bindingName) {
        try {
            this.bindingName = bindingName;
            //System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost()));
            //System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
            Registry registry = LocateRegistry.getRegistry(ip, portNumber);
            publisherForDomain = (IRemotePublisherForDomain) registry.lookup(bindingName);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            connected = true;
            System.out.println("Connection with server: " + bindingName + " has been established");
        } catch ( Exception re) {
            connected = false;
            System.out.println("Connection could not be established see log files for more information.");
            LOGGER.severe(String.valueOf(re));
        }
    }

    /**
     * Finds all servers on a address.
     *
     * @return An array of registry bindingnames found on the specific address
     */
    public String[] findServers(String ip) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip);
            String[] registries = registry.list();
            for (String s : registries) {
                System.out.println(s);
            }
            return registries;
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        } catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }
    /**
     * Register property at remote publisher.
     * @param property  property to be registered
     */
    public void register(String property) {
        if (connected) {
            try {
                // System.out.println("Registering property: " +  property);
                // Nothing changes at remote publisher in case property was already registered
                publisherForDomain.registerProperty(property);
            } catch (RemoteException ex) {
            }
        }
    }

    /**
     * Subscribe to property.
     * @param property property to subscribe to
     */
    public void subscribe(String property) {
        if (connected) {
            final IRemotePropertyListener listener = this;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("Subscribing to property: " + property);
                        publisherForListener.subscribeRemoteListener(listener, property);
                    } catch (RemoteException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                }
            });
        }
    }

    public void broadcast(String property, Object newValue) {
        if (connected) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("Broadcasting: " + property + " at value: " + newValue.toString());
                        publisherForDomain.inform(property,null, newValue);
                    } catch (RemoteException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                }
            });
        }
    }

    // Don't forget the break statements you twat.
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        // System.out.println("Property: " + evt.getPropertyName() + " has changed to: " + evt.getNewValue());
        switch(evt.getPropertyName()) {
            case "difficulty":
                controller.setCbDifficulty(evt.getNewValue());
                break;
            case "numberOfQuestions":
                controller.setSpin(evt.getNewValue());
                break;
            case "playlistUri":
                controller.setUriText(evt.getNewValue());
                break;
            case "room":
                controller.setRoom(evt.getNewValue());
                break;
            case "join":
                controller.addPlayer(evt.getNewValue());
                break;
            case "leave":
                controller.removePlayer(evt.getNewValue());
                break;
            case "playQuestion":
                controller.playQuestion((SerQuestion) evt.getNewValue());
                break;
            case"answer":
                controller.answerQuestion((SerQuestion) evt.getNewValue());
                break;
            default:
                System.out.println("Property: " + evt.getPropertyName() + " could not be found.");
        }
    }
}
