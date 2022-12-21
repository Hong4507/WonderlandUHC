package org.mcwonderland.uhc.scenario.impl.special.mole;

import org.bukkit.entity.Player;
import org.mcwonderland.uhc.scenario.impl.special.mole.commands.MoleCommand;

import java.util.Arrays;
import java.util.List;

public class MoleCommandHandler {

    private List<MoleCommand> commands;

    public MoleCommandHandler(List<MoleCommand> commands) {
        this.commands = commands;
    }

    public void handle(Player player, String message) {
        if (!message.startsWith("mole"))
            return;

        String[] words = message.split(" ");
        String label = words[1];

        for (MoleCommand command : commands) {
            if (command.getLabel().equalsIgnoreCase(label)) {
                command.onCommand(player, Arrays.asList(words).subList(1, words.length));
                return;
            }
        }
    }

}
