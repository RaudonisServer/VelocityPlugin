package xyz.anonym.raudonisNetwork;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(id = "raudonisnetwork", name = "RaudonisNetwork", version = "1.0-SNAPSHOT", authors = {"TheAnonym3000"})
public class RaudonisNetwork {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
