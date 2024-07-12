package SimpleNightmares.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class GetSleepPercentage extends SleepPercentageCommand {
    private static final String NAME = "getsleeppercentage";
    private static final String USAGE = "/getsleeppercentage";

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
        if (args.length > 0) {
            throw new WrongUsageException(USAGE);
        }

        if (sleepPlayers == -1) {
            ITextComponent message = new TextComponentString("Current percentage of players required to sleep: " + sleepPercentage + "%");
            server.getPlayerList().getPlayers().forEach(player -> player.sendMessage(message));
        } else {
            ITextComponent message = new TextComponentString("Current players required to sleep: " + sleepPlayers + " (Or if greater than current player count, all players.)");
            server.getPlayerList().getPlayers().forEach(player -> player.sendMessage(message));
        }
    }
}