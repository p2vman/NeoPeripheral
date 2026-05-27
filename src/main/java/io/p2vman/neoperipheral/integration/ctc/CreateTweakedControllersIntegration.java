package io.p2vman.neoperipheral.integration.ctc;

import io.p2vman.neoperipheral.integration.Integration;
import net.neoforged.bus.api.IEventBus;

@Integration(modid = "create_tweaked_controllers", depends = "create")
public class CreateTweakedControllersIntegration {
    public CreateTweakedControllersIntegration(IEventBus bus) {

    }
}
