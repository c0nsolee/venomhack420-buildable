package venomhack.events;

import net.minecraft.client.network.PlayerListEntry;

public class PlayerListChangeEvent {
   protected PlayerListEntry player;

   public PlayerListEntry getPlayer() {
      return this.player;
   }

   public static class Join extends PlayerListChangeEvent {
      private static final Join INSTANCE = new Join();

      public static Join get(PlayerListEntry player) {
         INSTANCE.player = player;
         return INSTANCE;
      }
   }

   public static class Leave extends PlayerListChangeEvent {
      private static final Leave INSTANCE = new Leave();

      public static Leave get(PlayerListEntry player) {
         INSTANCE.player = player;
         return INSTANCE;
      }
   }
}
