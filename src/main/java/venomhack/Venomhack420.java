package venomhack;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.config.Config;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomhack.commands.HeadItemCommand;
import venomhack.commands.LogoutSpotsCommand;
import venomhack.modules.chat.*;
import venomhack.modules.combat.*;
import venomhack.modules.hud.ItemHud;
import venomhack.modules.hud.StatsHud;
import venomhack.modules.misc.*;
import venomhack.modules.movement.Anchor;
import venomhack.modules.movement.Moses;
import venomhack.modules.movement.PacketFly;
import venomhack.modules.movement.Scaffold;
import venomhack.modules.movement.speed.Speed;
import venomhack.modules.player.FloRida;
import venomhack.modules.player.XpThrower;
import venomhack.modules.render.*;
import venomhack.modules.world.*;
import venomhack.modules.world.villager_trader.VillagerTrader;
import venomhack.utils.*;

public class Venomhack420 extends MeteorAddon {
   public static final Logger LOG = LogManager.getLogger();
   public static final Category CATEGORY = new Category("Venomhack420", Items.COMMAND_BLOCK_MINECART.getDefaultStack());
   public static final HudGroup HUD_GROUP = new HudGroup("Venomhack420");
   public static final String VERSION = "0.0.1v";
   public static final Statistics STATS = Statistics.get();

   public void onInitialize() {
      LOG.info("Initializing Venomhack420");
      DamageCalcUtils.init();
      ThreadedUtils.init();
      PlayerUtils2.init();
      PingUtils.init();
      this.initModules();
      MeteorClient.EVENT_BUS.subscribe(STATS);
      if (Config.get().customWindowTitle.get()) {
         MeteorClient.mc.getWindow().setTitle((String)Config.get().customWindowTitleText.get());
      }
   }

   private void initModules() {
      Modules modules = Modules.get();
      modules.add(new ArmorMessage());
      modules.add(new AutoEz());
      modules.add(new AutoCope());
      modules.add(new ChatControl());
      modules.add(new Greeter());
      modules.add(new LogDetection());
      modules.add(new Notifier());
      modules.add(new AutoAnchor());
      modules.add(new AutoBed());
      modules.add(new AutoCity());
      modules.add(new AutoCrystal());
      modules.add(new AutoFunnyCrystal());
      modules.add(new AutoTrap());
      modules.add(new Burrow());
      modules.add(new HoleFill());
      modules.add(new Offhand());
      modules.add(new OneShot());
      modules.add(new SelfTrap());
      modules.add(new Surround());
      modules.add(new TotemLog());
      modules.add(new AutoChase());
      modules.add(new PistonAura());
      modules.add(new AutoCrafter());
      modules.add(new AutoSort());
      modules.add(new DiscordPresence());
      modules.add(new PacketMine());
      modules.add(new PacketPlace());
      modules.add(new PearlPredict());
      modules.add(new PingSpoof());
      modules.add(new Anchor());
      modules.add(new Speed());
      modules.add(new Moses());
      modules.add(new PacketFly());
      modules.add(new Scaffold());
      modules.add(new FloRida());
      modules.add(new XpThrower());
      modules.add(new BetterChams());
      modules.add(new BetterPops());
      modules.add(new BurrowEsp());
      modules.add(new DroppedItemsView());
      modules.add(new HoleEsp());
      modules.add(new LogoutSpotsRewrite());
      modules.add(new SoundEsp());
      modules.add(new TanukiOutline());
      modules.add(new KillEffects());
      modules.add(new AutoBamboo());
      modules.add(new AutoWatercube());
      modules.add(new EgapFinder());
      modules.add(new ObsidianFarm());
      modules.add(new VillagerTrader());
      modules.add(new WaypointDeleter());
      modules.add(new ItemDropper());
      Commands.add(new LogoutSpotsCommand());
      Commands.add(new HeadItemCommand());
      Hud hud = Hud.get();
      hud.register(ItemHud.INFO);
      hud.register(StatsHud.INFO);
   }

   public void onRegisterCategories() {
      Modules.registerCategory(CATEGORY);
   }

   public String getPackage() {
      return "venomhack";
   }
}
