package xyz.anonym.raudonisNetwork.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;

public class LobbyCommynd {
    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> helloNode = BrigadierCommand.literalArgumentBuilder("test")
                // Here you can filter the subjects that can execute the command.
                // This is the ideal place to do "hasPermission" checks
                .requires(source -> source.hasPermission("test.permission"))
                // Here you can add the logic that will be used in
                // the execution of the "/test" command without any argument
                .executes(context -> {
                    // Here you get the subject that executed the command
                    CommandSource source = context.getSource();

                    Component message = Component.text("Hello World", NamedTextColor.AQUA);
                    source.sendMessage(message);

                    // Returning Command.SINGLE_SUCCESS means that the execution was successful
                    // Returning BrigadierCommand.FORWARD will send the command to the server
                    return Command.SINGLE_SUCCESS;
                })
                // Using the "then" method, you can add sub-arguments to the command.
                // For example, this subcommand will be executed when using the command "/test <some argument>"
                // A RequiredArgumentBuilder is a type of argument in which you can enter some undefined data
                // of some kind. For example, this example uses a StringArgumentType.word() that requires
                // a single word to be entered, but you can also use different ArgumentTypes provided by Brigadier
                // that return data of type Boolean, Integer, Float, other String types, etc
                .then(BrigadierCommand.requiredArgumentBuilder("argument", StringArgumentType.word())
                        // Here you can define the hints to be provided in case the ArgumentType does not provide them.
                        // In this example, the names of all connected players are provided
                        .suggests((ctx, builder) -> {
                            // Here we provide the names of the players along with a tooltip,
                            // which can be used as an explanation of a specific argument or as a simple decoration
                            proxy.getAllPlayers().forEach(player -> builder.suggest(
                                    player.getUsername(),
                                    // A VelocityBrigadierMessage takes a component.
                                    // In this case, the player's name is provided with a rainbow
                                    // gradient created using MiniMessage.
                                    VelocityBrigadierMessage.tooltip(
                                            MiniMessage.miniMessage().deserialize("<rainbow>" + player.getUsername())
                                    )
                            ));
                            // If you do not need to add a tooltip to the hint
                            // or your command is intended only for versions lower than Minecraft 1.13,
                            // you can omit adding the tooltip, since for older clients,
                            // the tooltip will not be displayed.
                            builder.suggest("all");
                            return builder.buildFuture();
                        })
                        // Here the logic of the command "/test <some argument>" is executed
                        .executes(context -> {
                            // Here you get the argument that the CommandSource has entered.
                            // You must enter exactly the name as you have named the argument
                            // and you must provide the class of the argument you expect, in this case... a String
                            String argumentProvided = context.getArgument("argument", String.class);
                            // This method will check if the given string corresponds to a
                            // player's name and if it does, it will send a message to that player
                            proxy.getPlayer(argumentProvided).ifPresent(player ->
                                    player.sendMessage(Component.text("Hello!"))
                            );
                            // Returning Command.SINGLE_SUCCESS means that the execution was successful
                            // Returning BrigadierCommand.FORWARD will send the command to the server
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();

        // BrigadierCommand implements Command
        return new BrigadierCommand(helloNode);
    }
}
