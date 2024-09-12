package SimpleNightmares.commands;

import SimpleNightmares.config.Config;
import SimpleNightmares.proxy.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

import static SimpleNightmares.config.Config.*;
import static SimpleNightmares.proxy.CommonProxy.config;

public class SleepPercentageCommand extends CommandBase {
    private static final String NAME = "setsleeppercentage";
    private static final String USAGE = "/setsleeppercentage <percentage> or /setsleeppercentage <number>P";

    public static int sleepPercentage = CommonProxy.config.getInt("sleepPercentage", Config.CATEGORY_SLEEP, 100, 0, 100, "The percentage of players that needs to sleep to skip the night, if this option is selected.");

    public static int sleepPlayers = CommonProxy.config.getInt("sleepPlayers", Config.CATEGORY_SLEEP, -1, -1, 99,"The amount of players that need to sleep to skip the night, if this option is selected.");

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(USAGE);
        }

        if (args[0].endsWith("P") || args[0].endsWith("p")) {
            int sleepPlayersInt = parseInt(args[0].substring(0, args[0].length() - 1), 1, 99);
            config.get(CATEGORY_SLEEP, "sleepPlayers", -1).set(sleepPlayersInt);
            sleepPlayers = sleepPlayersInt;
            notifyCommandListener(sender, this, "commands.sleepPercentage.success", sleepPlayers + " players");
        } else {
            int sleepPercentageInt = parseInt(args[0], 0, 100);
            config.get(CATEGORY_SLEEP, "sleepPercentage", 100).set(sleepPercentageInt);
            sleepPercentage = sleepPercentageInt;
            config.get(CATEGORY_SLEEP, "sleepPlayers", -1).set(-1);
            sleepPlayers = -1;
            notifyCommandListener(sender, this, "commands.sleepPercentage.success", sleepPercentage + "%");
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void onPlayerSleep(MinecraftServer server, String playername) {
        if (server == null) {
            server = FMLCommonHandler.instance().getMinecraftServerInstance();
        }

        int sleepingPlayers = countSleepingPlayers(server);
        int onlinePlayers = server.getCurrentPlayerCount();

        if (sleepPlayers != -1) {
            if (sleepingPlayers >= sleepPlayers || sleepingPlayers == server.getCurrentPlayerCount() && onlinePlayers != 1) {
                long currentTime = server.getWorld(0).getWorldTime();
                long timeToAdd = (24000 - (currentTime % 24000)) % 24000;
                server.getWorld(0).setWorldTime(currentTime + timeToAdd);
            }
        } else if (((float) sleepingPlayers / onlinePlayers) >= ((float) sleepPercentage / 100) && onlinePlayers != 1) {
            long currentTime = server.getWorld(0).getWorldTime();
            long timeToAdd = (24000 - (currentTime % 24000)) % 24000;
            server.getWorld(0).setWorldTime(currentTime + timeToAdd);
        }


        int onplayers = 2;
        // Only send messages if there are two or more players online
        if (enableSinglePlayerDebug) {onplayers = 1;}


        if (onlinePlayers >= onplayers) {
            int percentage = Math.round(((float) sleepingPlayers /(float) onlinePlayers)*100);
            int togo = sleepPlayers-sleepingPlayers;

            if (sleepPlayers > server.getCurrentPlayerCount()) {
                togo = server.getCurrentPlayerCount() - sleepingPlayers;
            }

            if (enableChatFeedback) {
                if (sleepPlayers != -1) {
                    ITextComponent message = new TextComponentString(TextFormatting.getValueByName(sleepMessageColor.toUpperCase()) + playername + " is now sleeping (" + sleepingPlayers + "/" + onlinePlayers + ")" + " (" + togo + ")" + TextFormatting.RESET);
                    server.getPlayerList().getPlayers().forEach(player -> player.sendMessage(message));
                } else {
                    ITextComponent message = new TextComponentString(TextFormatting.getValueByName(sleepMessageColor.toUpperCase()) + playername + " is now sleeping (" + sleepingPlayers + "/" + onlinePlayers + ")" + " (" + percentage + "%)" + TextFormatting.RESET);
                    server.getPlayerList().getPlayers().forEach(player-> player.sendMessage(message));
                }
            }
            if (enableChatRemarks) {
                if (sleepPlayers != -1) {
                    if (sleepingPlayers >= sleepPlayers) {
                        server.getPlayerList().getPlayers().forEach(player -> {
                            if (player.isPlayerSleeping()) {
                                ITextComponent dayMessage = new TextComponentString(TextFormatting.getValueByName(wakeUpMessageColor.toUpperCase()) + getRandomMessage() + TextFormatting.RESET);
                                player.sendMessage(dayMessage);
                            }
                        });
                    }
                } else if (((float) sleepingPlayers / onlinePlayers) >= ((float) sleepPercentage / 100)) {
                    server.getPlayerList().getPlayers().forEach(player -> {
                        if (player.isPlayerSleeping()) {
                            ITextComponent dayMessage = new TextComponentString(TextFormatting.getValueByName(wakeUpMessageColor.toUpperCase()) + getRandomMessage() + TextFormatting.RESET);
                            player.sendMessage(dayMessage);
                        }
                    });
                }
            }

        }
    }

    private int countSleepingPlayers(MinecraftServer server) {
        return (int) server.getPlayerList().getPlayers().stream()
                .filter(player -> player.isPlayerSleeping() || player.isPlayerFullyAsleep())
                .count();
    }

    private String getRandomMessage() {
        int index = new Random().nextInt(chatFeedback.size());
        return chatFeedback.get(index);
    }
}