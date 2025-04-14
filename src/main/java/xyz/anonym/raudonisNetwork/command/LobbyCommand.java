package xyz.anonym.raudonisNetwork.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;

import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

public final class LobbyCommand {

    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> raudonisNode = BrigadierCommand.literalArgumentBuilder("lobby")
                .requires(source -> source.hasPermission("raudonisnetwork.command.lobby"))
                .executes(context -> {
                    CommandSource source = context.getSource();
                    proxy.getPlayer(source.get(Identity.NAME).orElse("UNKNOWN")).ifPresent(player ->
                            proxy.getServer("lobby").ifPresent(registeredServer -> {
                                player.getCurrentServer().ifPresent(serverConnection -> {
                                    if (registeredServer == serverConnection.getServer()) {
                                        source.sendMessage(Component.text("[§6§lRaudonisNetwork§r]§4 You are already connected to the lobby!"));
                                    } else {
                                        source.sendMessage(Component.text("[§6§lRaudonisNetwork§r] Sending you to lobby..."));
                                        player.createConnectionRequest(registeredServer).fireAndForget();
                                    }
                                });
                            }));
                    return Command.SINGLE_SUCCESS;
                })
                .build();

        return new BrigadierCommand(raudonisNode);
    }
}