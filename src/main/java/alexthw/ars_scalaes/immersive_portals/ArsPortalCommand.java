package alexthw.ars_scalaes.immersive_portals;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class ArsPortalCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        //get two entities from the command and pass them to the portal creation method
        dispatcher.register(Commands.literal("ars_portal")
                .requires((player) -> player.hasPermission(2))
                .then(Commands.argument("portal", EntityArgument.entity())
                        .executes((command) -> {
                            Entity portal = EntityArgument.getEntity(command, "portal");
                            IPCompat.createPortal(portal);
                            return 1;
                        }))


        );
    }
}
