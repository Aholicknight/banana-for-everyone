package bplusdevelopment.settings;

import meteordevelopment.meteorclient.settings.IVisible;
import meteordevelopment.meteorclient.settings.Setting;
import net.minecraft.nbt.NbtCompound;

import java.util.function.Consumer;

public class FloatSetting extends Setting<Float> {
    public final float min, max;
    public final float sliderMin, sliderMax;
    public final boolean onSliderRelease;
    public final int decimalPlaces;
    public final boolean noSlider;

    private FloatSetting(String name, String description, float defaultValue, Consumer<Float> onChanged, Consumer<Setting<Float>> onModuleActivated, IVisible visible, float min, float max, float sliderMin, float sliderMax, boolean onSliderRelease, int decimalPlaces, boolean noSlider) {
        super(name, description, defaultValue, onChanged, onModuleActivated, visible);

        this.min = min;
        this.max = max;
        this.sliderMin = sliderMin;
        this.sliderMax = sliderMax;
        this.decimalPlaces = decimalPlaces;
        this.onSliderRelease = onSliderRelease;
        this.noSlider = noSlider;
    }

    @Override
    protected Float parseImpl(String str) {
        try {
            return Float.parseFloat(str.trim());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    @Override
    protected boolean isValueValid(Float value) {
        return value >= min && value <= max;
    }

    @Override
    protected NbtCompound save(NbtCompound tag) {
        tag.putFloat("value", get());

        return tag;
    }

    @Override
    public Float load(NbtCompound tag) {
        set(tag.getFloat("value"));

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Float, FloatSetting> {
        public float min = Float.NEGATIVE_INFINITY, max = Float.POSITIVE_INFINITY;
        public float sliderMin = 0, sliderMax = 10;
        public boolean onSliderRelease = false;
        public int decimalPlaces = 3;
        public boolean noSlider = false;

        public Builder() {
            super(0f);
        }

        public Builder defaultValue(float defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder min(float min) {
            this.min = min;
            return this;
        }

        public Builder max(float max) {
            this.max = max;
            return this;
        }

        public Builder range(float min, float max) {
            this.min = Math.min(min, max);
            this.max = Math.max(min, max);
            return this;
        }

        public Builder sliderMin(float min) {
            sliderMin = Math.max(min, this.min);
            return this;
        }

        public Builder sliderMax(float max) {
            sliderMax = Math.min(max, this.max);
            return this;
        }

        public Builder sliderRange(float min, float max) {
            sliderMin = Math.max(min, this.min);
            sliderMax = Math.min(max, this.max);
            return this;
        }

        public Builder onSliderRelease() {
            onSliderRelease = true;
            return this;
        }

        public Builder decimalPlaces(int decimalPlaces) {
            this.decimalPlaces = decimalPlaces;
            return this;
        }

        public Builder noSlider() {
            noSlider = true;
            return this;
        }

        public FloatSetting build() {
            return new FloatSetting(name, description, defaultValue, onChanged, onModuleActivated, visible, min, max, sliderMin, sliderMax, onSliderRelease, decimalPlaces, noSlider);
        }
    }
}