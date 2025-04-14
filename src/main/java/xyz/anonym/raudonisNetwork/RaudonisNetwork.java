package xyz.anonym.raudonisNetwork;

import com.google.inject.Inject;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import xyz.anonym.raudonisNetwork.command.LobbyCommand;

import java.nio.file.Path;

@Plugin(id = "raudonisnetwork", name = "RaudonisNetwork", version = "1.0-SNAPSHOT", authors = {"TheAnonym3000"})
public class RaudonisNetwork {

    private final ProxyServer server;
    private Logger logger;
    private final Path dataDirectory;
    private final ProxyServer proxy;

    @Inject
    public RaudonisNetwork(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, ProxyServer proxy) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.proxy = proxy;

        logger.info("Starting RaudonisNetwork...");
    }
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("lobby")
                .aliases("l", "hub")
                .plugin(this)
                .build();

        BrigadierCommand commandToRegister = LobbyCommand.createBrigadierCommand(proxy);

        commandManager.register(commandMeta, commandToRegister);
    }
}