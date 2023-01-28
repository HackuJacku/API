package com.envyful.api.forge.config;

import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.server.UtilForgeServer;
import com.envyful.api.text.Placeholder;
import com.envyful.api.text.PlaceholderFactory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class ConfigReward {

    private List<String> commands;
    private List<String> messages;

    public ConfigReward(List<String> commands, List<String> messages) {
        this.commands = commands;
        this.messages = messages;
    }

    public ConfigReward() {
    }

    public void execute(ServerPlayerEntity player, Placeholder... placeholders) {
        if (this.commands != null && !this.commands.isEmpty()) {
            for (String command : PlaceholderFactory.handlePlaceholders(this.commands, placeholders)) {
                UtilForgeServer.executeCommand(command.replace("%player%", player.getName().getString()));
            }
        }

        if (this.messages != null && !this.messages.isEmpty()) {
            for (String message : PlaceholderFactory.handlePlaceholders(this.messages, placeholders)) {
                player.sendMessage(UtilChatColour.colour(message), Util.NIL_UUID);
            }
        }
    }
}