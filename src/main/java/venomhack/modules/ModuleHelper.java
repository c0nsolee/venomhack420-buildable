package venomhack.modules;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import meteordevelopment.meteorclient.gui.utils.StarscriptTextBoxRenderer;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.misc.MyPotion;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModuleHelper extends Module {
    protected final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    public ModuleHelper(Category category, String name, String description) {
        super(category, name, description);
    }

    public String title() {
        return this.title;
    }

    //-----------------------most-simple---------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<String> setting(String name, String description, String defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue) {
        return this.setting(name, description, defaultValue, sgGeneral);
    }

    //-------------------------------------------------------------//

    //-----------------------visible-without-group----------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<String> setting(String name, String description, String defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, IVisible visible) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, null);
    }

    //-----------------------------------------------------//

    //--------------------full-without-group-------------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, IVisible visible, Consumer<MyPotion> onChanged, Consumer<Setting<MyPotion>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, IVisible visible, Consumer<T> onChanged, Consumer<Setting<T>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, IVisible visible, Consumer<Boolean> onChanged, Consumer<Setting<Boolean>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, IVisible visible, Consumer<SettingColor> onChanged, Consumer<Setting<SettingColor>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, IVisible visible, Consumer<Keybind> onChanged, Consumer<Setting<Keybind>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<String> setting(String name, String description, String defaultValue, IVisible visible, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, IVisible visible, Consumer<BlockPos> onChanged, Consumer<Setting<BlockPos>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, IVisible visible, Consumer<Object2IntMap<StatusEffect>> onChanged, Consumer<Setting<Object2IntMap<StatusEffect>>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, IVisible visible, Consumer<Integer> onChanged, Consumer<Setting<Integer>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, IVisible visible, Consumer<Double> onChanged, Consumer<Setting<Double>> onModuleActivated) {
        return this.setting(name, description, defaultValue, sgGeneral, visible, onChanged, onModuleActivated);
    }

    //-----------------------------------------------------//

    //---------------------------GroupSimple--------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<String> setting(String name, String description, String defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group) {
        return this.setting(name, description, defaultValue, group, null, null);
    }

    //-----------------------------------------------------//

    //--------------------------Visible---------------------------// MyPotion   Enum    Boolean   SettingColor   Keybind    String    BlockPos    Object2IntMap<StatusEffect>   Integer    Double

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<String> setting(String name, String description, String defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, defaultValue, group, visible, null);
    }
    //-----------------------------------------------------//

    //----------------------onChanged-------------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, SettingGroup group, IVisible visible, Consumer<MyPotion> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, SettingGroup group, IVisible visible, Consumer<T> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, SettingGroup group, IVisible visible, Consumer<Boolean> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, SettingGroup group, IVisible visible, Consumer<SettingColor> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, SettingGroup group, IVisible visible, Consumer<Keybind> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<String> setting(String name, String description, String defaultValue, SettingGroup group, IVisible visible, Consumer<String> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, SettingGroup group, IVisible visible, Consumer<BlockPos> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, SettingGroup group, IVisible visible, Consumer<Object2IntMap<StatusEffect>> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, Consumer<Integer> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, Consumer<Double> onChanged) {
        return this.setting(name, description, defaultValue, group, visible, onChanged, null);
    }

    //-----------------------------------------------------//

    //------------------full-values------------------------//

    public Setting<MyPotion> setting(String name, String description, MyPotion defaultValue, SettingGroup group, IVisible visible, Consumer<MyPotion> onChanged, Consumer<Setting<MyPotion>> onModuleActivated) {
        return group.add(new PotionSetting(name, description, defaultValue, onChanged, onModuleActivated, visible));
    }

    public <T extends Enum<?>> Setting<T> setting(String name, String description, T defaultValue, SettingGroup group, IVisible visible, Consumer<T> onChanged, Consumer<Setting<T>> onModuleActivated) {
        return group.add(new EnumSetting<>(name, description, defaultValue, onChanged, onModuleActivated, visible));
    }

    public Setting<Boolean> setting(String name, String description, Boolean defaultValue, SettingGroup group, IVisible visible, Consumer<Boolean> onChanged, Consumer<Setting<Boolean>> onModuleActivated) {
        return group.add(new BoolSetting.Builder().name(name).description(description).defaultValue(defaultValue).visible(visible).onChanged(onChanged).onModuleActivated(onModuleActivated).build());
    }

    public Setting<SettingColor> setting(String name, String description, SettingColor defaultValue, SettingGroup group, IVisible visible, Consumer<SettingColor> onChanged, Consumer<Setting<SettingColor>> onModuleActivated) {
        return group.add(new ColorSetting(name, description, defaultValue, onChanged, onModuleActivated, visible));
    }

    public Setting<Keybind> setting(String name, String description, Keybind defaultValue, SettingGroup group, IVisible visible, Consumer<Keybind> onChanged, Consumer<Setting<Keybind>> onModuleActivated) {
        return group.add(new KeybindSetting(name, description, defaultValue, onChanged, onModuleActivated, visible, null));
    }

    public Setting<String> setting(String name, String description, String defaultValue, SettingGroup group, IVisible visible, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated) {
        return group.add(new StringSetting(name, description, defaultValue, onChanged, onModuleActivated, visible, StarscriptTextBoxRenderer.class, null, false));
    }

    public Setting<BlockPos> setting(String name, String description, BlockPos defaultValue, SettingGroup group, IVisible visible, Consumer<BlockPos> onChanged, Consumer<Setting<BlockPos>> onModuleActivated) {
        return group.add(new BlockPosSetting(name, description, defaultValue, onChanged, onModuleActivated, visible));
    }

    public Setting<Object2IntMap<StatusEffect>> setting(String name, String description, Object2IntMap<StatusEffect> defaultValue, SettingGroup group, IVisible visible, Consumer<Object2IntMap<StatusEffect>> onChanged, Consumer<Setting<Object2IntMap<StatusEffect>>> onModuleActivated) {
        return group.add(new StatusEffectAmplifierMapSetting(name, description, defaultValue, onChanged, onModuleActivated, visible));
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, Consumer<Integer> onChanged, Consumer<Setting<Integer>> onModuleActivated) {
        return group.add(new IntSetting.Builder().name(name).description(description).defaultValue(defaultValue).visible(visible).onChanged(onChanged).onModuleActivated(onModuleActivated).build());
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, Consumer<Double> onChanged, Consumer<Setting<Double>> onModuleActivated) {
        return group.add(new DoubleSetting.Builder().name(name).description(description).defaultValue(defaultValue).visible(visible).onChanged(onChanged).onModuleActivated(onModuleActivated).build());
    }

    //-----------------------------------------------------//

    //-------------------simple-list-----------------------//

    public final Setting<List<String>> setting(String name, String description, SettingGroup group, IVisible visible, String... defaultValue) {
        return setting(name, description, group, visible, null, defaultValue);
    }

    public final Setting<List<Enchantment>> setting(String name, String description, SettingGroup group, IVisible visible, Enchantment... defaultValue) {
        return setting(name, description, group, visible, null, defaultValue);
    }

    public final Setting<List<Module>> setting(String name, String description, SettingGroup group, IVisible visible, Module... defaultValue) {
        return setting(name, description, group, visible, null, defaultValue);
    }

    public final Setting<List<Block>> setting(String name, String description, SettingGroup group, IVisible visible, Block... defaultValue) {
        return setting(name, description, group, visible, null, defaultValue);
    }

    public final Setting<List<SoundEvent>> setting(String name, String description, SettingGroup group, IVisible visible, SoundEvent... defaultValue) {
        return setting(name, description, group, visible, null, defaultValue);
    }

    //-------------------------------------------//

    //------------------without-activated-list---------------------//

    public final Setting<List<String>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<String>> onChanged, String... defaultValue) {
        return setting(name, description, group, visible, onChanged, null, defaultValue);
    }

    public final Setting<List<Enchantment>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Enchantment>> onChanged, Enchantment... defaultValue) {
        return setting(name, description, group, visible, onChanged, null, defaultValue);
    }

    public final Setting<List<Module>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Module>> onChanged, Module... defaultValue) {
        return setting(name, description, group, visible, onChanged, null, defaultValue);
    }

    public final Setting<List<Block>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Block>> onChanged, Block... defaultValue) {
        return setting(name, description, group, visible, onChanged, null, defaultValue);
    }

    public final Setting<List<SoundEvent>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<SoundEvent>> onChanged, SoundEvent... defaultValue) {
        return setting(name, description, group, visible, onChanged, null, defaultValue);
    }

    //-------------------------------------------//

    //------------------full-values-list-----------------------//

    public final Setting<List<String>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<String>> onChanged, Consumer<Setting<List<String>>> onModuleActivated, String... defaultValue) {
        return group.add(new StringListSetting(name, description, List.of(defaultValue), onChanged, onModuleActivated, visible, StarscriptTextBoxRenderer.class, null));
    }

    public final Setting<List<Enchantment>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Enchantment>> onChanged, Consumer<Setting<List<Enchantment>>> onModuleActivated, Enchantment... defaultValue) {
        return group.add(new EnchantmentListSetting(name, description, List.of(defaultValue), onChanged, onModuleActivated, visible));
    }

    public final Setting<List<Module>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Module>> onChanged, Consumer<Setting<List<Module>>> onModuleActivated, Module... defaultValue) {
        return group.add(new ModuleListSetting(name, description, List.of(defaultValue), onChanged, onModuleActivated, visible));
    }

    public final Setting<List<Block>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<Block>> onChanged, Consumer<Setting<List<Block>>> onModuleActivated, Block... defaultValue) {
        return group.add(new BlockListSetting(name, description, List.of(defaultValue), onChanged, onModuleActivated, Block -> true, visible));
    }

    public final Setting<List<SoundEvent>> setting(String name, String description, SettingGroup group, IVisible visible, Consumer<List<SoundEvent>> onChanged, Consumer<Setting<List<SoundEvent>>> onModuleActivated, SoundEvent... defaultValue) {
        return group.add(new SoundEventListSetting(name, description, List.of(defaultValue), onChanged, onModuleActivated, visible));
    }

    //-------------------------------------------//

    public Setting<Integer> setting(String name, String description, int defaultValue, SettingGroup group, int sliderMax) {
        return group.add(new IntSetting.Builder(

        ).name(name).description(description).defaultValue(defaultValue).sliderMax(sliderMax).build());
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, double sliderMin, double sliderMax, int min, int max) {
        return this.setting(name, description, defaultValue, group, null, null, null, sliderMin, sliderMax, min, max, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, double sliderMin, double sliderMax, int min, int max) {
        return this.setting(name, description, defaultValue, group, null, null, null, sliderMin, sliderMax, min, max, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, double sliderMax) {
        return this.setting(name, description, defaultValue, group, visible, null, null, 0.0, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, double sliderMax) {
        return this.setting(name, description, defaultValue, group, visible, null, null, 0.0, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, Consumer<Integer> onChanged, double sliderMax) {
        return this.setting(name, description, defaultValue, group, null, onChanged, null, 0.0, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, Consumer<Double> onChanged, double sliderMax) {
        return this.setting(name, description, defaultValue, group, null, onChanged, null, 0.0, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, this.sgGeneral, null, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, this.sgGeneral, null, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, double sliderMin, double sliderMax, int min, int max) {
        return this.setting(name, description, defaultValue, group, visible, null, null, sliderMin, sliderMax, min, max, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, double sliderMin, double sliderMax, int min, int max) {
        return this.setting(name, description, defaultValue, group, visible, null, null, sliderMin, sliderMax, min, max, 3);
    }

    public Setting<Double> setting(String name, String description, double defaultValue, SettingGroup group, double sliderMin, double sliderMax, int min, int max, int decimalPlaces) {
        return group.add(new DoubleSetting.Builder(

        ).name(name).description(description).defaultValue(defaultValue).sliderRange(sliderMin, sliderMax).range(min, max).decimalPlaces(decimalPlaces).build());
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, group, null, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, group, null, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, group, visible, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, double sliderMin, double sliderMax) {
        return this.setting(name, description, defaultValue, group, visible, null, null, sliderMin, sliderMax, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);
    }

    public Setting<Double> setting(String name, String description, double defaultValue, SettingGroup group, IVisible visible, double sliderMin, double sliderMax, int decimalPlaces) {
        return group.add(new DoubleSetting.Builder(

        ).name(name).description(description).defaultValue(defaultValue).visible(visible).sliderRange(sliderMin, sliderMax).decimalPlaces(decimalPlaces).build());
    }

    public Setting<Double> setting(String name, String description, double defaultValue, SettingGroup group, double sliderMin, double sliderMax, int decimalPlaces) {
        return group.add(new DoubleSetting.Builder(

        ).name(name).description(description).defaultValue(defaultValue).sliderRange(sliderMin, sliderMax).decimalPlaces(decimalPlaces).build());
    }

    //---------------------Numbers----------------------//

    public Setting<Integer> setting(String name, String description, Integer defaultValue, SettingGroup group, IVisible visible, Consumer<Integer> onChanged, Consumer<Setting<Integer>> onModuleActivated, double sliderMin, double sliderMax, int min, int max, int decimalPlaces) {
        return group.add(new IntSetting.Builder().name(name).description(description).defaultValue(defaultValue).visible(visible).onChanged(onChanged).onModuleActivated(onModuleActivated).sliderRange((int) sliderMin, (int) sliderMax).range(min, max).build());
    }

    public Setting<Double> setting(String name, String description, Double defaultValue, SettingGroup group, IVisible visible, Consumer<Double> onChanged, Consumer<Setting<Double>> onModuleActivated, double sliderMin, double sliderMax, int min, int max, int decimalPlaces) {
        return group.add(new DoubleSetting.Builder().name(name).description(description).defaultValue(defaultValue).visible(visible).onChanged(onChanged).onModuleActivated(onModuleActivated).sliderRange((int) sliderMin, (int) sliderMax).range(min, max).build());
    }

    //-------------------------------------------//

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, int alpha) {
        return this.setting(name, description, red, green, blue, alpha, false, this.sgGeneral, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, SettingGroup group) {
        return this.setting(name, description, red, green, blue, 255, false, group, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, int alpha, SettingGroup group) {
        return this.setting(name, description, red, green, blue, alpha, false, group, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, SettingGroup group, IVisible visible) {
        return this.setting(name, description, red, green, blue, 255, false, group, visible, null, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, int alpha, SettingGroup group, IVisible visible) {
        return this.setting(name, description, red, green, blue, alpha, false, group, visible, null, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, int alpha, boolean rainbow, SettingGroup group, IVisible visible) {
        return this.setting(name, description, red, green, blue, alpha, rainbow, group, visible, null, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, SettingGroup group, IVisible visible, Consumer<SettingColor> onChanged) {
        return this.setting(name, description, red, green, blue, 255, false, group, visible, onChanged, null);
    }

    public Setting<SettingColor> setting(String name, String description, int red, int green, int blue, int alpha, boolean rainbow, SettingGroup group, IVisible visible, Consumer<SettingColor> onChanged, Consumer<Setting<SettingColor>> onModuleActivated) {
        return group.add(new ColorSetting(name, description, new SettingColor(red, green, blue, alpha, rainbow), onChanged, onModuleActivated, visible));
    }

    public Setting<Object2BooleanMap<EntityType<?>>> setting(String name, String description, SettingGroup group, boolean onlyAttackable, IVisible visible, Consumer<Object2BooleanMap<EntityType<?>>> onChanged, Consumer<Setting<Object2BooleanMap<EntityType<?>>>> onModuleActivated, EntityType<?>... defaultValue) {
        return group.add(new EntityTypeListSetting.Builder().name(name).description(description).defaultValue(defaultValue).onChanged(onChanged).onModuleActivated(onModuleActivated).visible(visible).build());
    }

    public Setting<List<Item>> setting(String name, String description, SettingGroup group, boolean bypassFilterWhenSavingAndLoading, Predicate<Item> filter, IVisible visible, Consumer<List<Item>> onChanged, Consumer<Setting<List<Item>>> onModuleActivated, Item... defaultValue) {
        return group.add(new ItemListSetting(name, description, Arrays.asList(defaultValue), onChanged, onModuleActivated, visible, filter, bypassFilterWhenSavingAndLoading));
    }

    public SettingGroup group(String name) {
        return this.settings.createGroup(name);
    }

    public void toggleWithInfo(String message, Object... args) {
        this.info(message, args);
        this.toggle();
    }

    public void info(int id, String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(id, this.title(), Formatting.LIGHT_PURPLE, Formatting.GRAY, message, args);
    }

    public void info(int id, Text message) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(id, this.title(), Formatting.LIGHT_PURPLE, message);
    }

    public void warning(int id, String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(id, this.title(), Formatting.LIGHT_PURPLE, Formatting.YELLOW, message, args);
    }

    public void error(int id, String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(id, this.title(), Formatting.LIGHT_PURPLE, Formatting.RED, message, args);
    }

    public void error(int id, MutableText message) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(id, this.title(), Formatting.LIGHT_PURPLE, message.formatted(Formatting.RED));
    }

    public void info(MutableText message) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.sendMsg(this.title(), message.formatted(Formatting.GRAY));
    }

    public void info(String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.info(this.title(), new Object[]{message, args});
    }

    public void warning(String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.warning(this.title(), new Object[]{message, args});
    }

    public void error(String message, Object... args) {
        ChatUtils.forceNextPrefixClass(this.getClass());
        ChatUtils.error(this.title(), new Object[]{message, args});
    }
}
