package venomhack.utils;

import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.entity.LivingEntity;
import venomhack.modules.combat.AutoAnchor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedUtils {
    public static ExecutorService aaExecutor;
    public static ExecutorService antiCityExecutor;

    public static void init() {
        aaExecutor = Executors.newSingleThreadExecutor();
        antiCityExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public enum CalcType {
        Place, Break
    }

    public static class AaCalcs implements Runnable {
        private static final AutoAnchor aar = Modules.get().get(AutoAnchor.class);
        public static int placeDelayLeft = 0;
        public static int breakDelayLeft = 0;
        private final CalcType type;

        public AaCalcs(CalcType type) {
            this.type = type;
        }

        public static void resetDelays(LivingEntity targ) {
            if (UtilsPlus.isSurrounded(targ, true, true) && aar.holeDelays.get()) {
                placeDelayLeft = aar.holePlaceDelay.get();
                breakDelayLeft = aar.holeBreakDelay.get();
            } else {
                placeDelayLeft = aar.placeDelay.get();
                breakDelayLeft = aar.breakDelay.get();
            }
        }

        @Override
        public void run() {
            switch (this.type) {
                case Place:
                    --placeDelayLeft;
                    if (placeDelayLeft <= 0) {
                        aar.doPlaceThreaded();
                    }
                case Break:
                    --breakDelayLeft;
                    if (breakDelayLeft <= 0) {
                        aar.doBreak();
                    }
            }
        }
    }
}
