package tkcy.simpleaddon.api.utils;

import java.util.function.Function;

import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.number.Numbers;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.api.utils.units.UnitsConversions;

@Getter
public class StorageUtils<T> {

    private int contentAmount;
    private String contentLocalizedName;

    private final CommonUnits baseContentUnit;
    private final int maxAmount;
    private final Function<T, String> contentLocalizedNameProvider;
    private final Function<T, Integer> contentAmountProvider;
    private final MetaTileEntityStorageFormat<T> mte;

    public StorageUtils(MetaTileEntityStorageFormat<T> mte, int maxAmount, CommonUnits baseContentUnit,
                        Function<T, String> contentLocalizedNameProvider,
                        Function<T, Integer> contentAmountProvider) {
        this.mte = mte;
        this.maxAmount = maxAmount;
        this.baseContentUnit = baseContentUnit;
        this.contentLocalizedNameProvider = contentLocalizedNameProvider;
        this.contentAmountProvider = contentAmountProvider;
    }

    @Override
    public String toString() {
        return "content amount : " + contentAmount + "contentLocalizedName : " + contentLocalizedName + "maxAmount : " +
                maxAmount + "baseContentUnit : " + baseContentUnit;
    }

    public void updateContent(@Nullable T content) {
        if (content == null) return;
        this.contentAmount = this.contentAmountProvider.apply(content);
        this.contentLocalizedName = this.contentLocalizedNameProvider.apply(content);
    }

    public void updateContent() {
        updateContent(this.mte.getContent());
    }

    public void setEmpty() {
        this.contentAmount = 0;
    }

    private boolean isEmpty() {
        return this.contentAmount == 0 || this.mte.getContent() == null;
    }

    public String getContentFormatted() {
        return isEmpty() ? "Empty" : String.format("%s of %s",
                UnitsConversions.convertAndFormatToSizeOfOrder(this.contentAmount, this.baseContentUnit),
                this.contentLocalizedName);
    }

    public String updateAndgetContentFormatted(T content) {
        updateContent(content);
        return getContentFormatted();
    }

    protected String getFillPercentage() {
        return isEmpty() ? "Empty" :
                Numbers.getQuotientPercentage(this.contentAmount, this.maxAmount) + "% filled";
    }

    public TextComponentTranslation getFillPercentageTextTranslation() {
        return new TextComponentTranslation(this.mte.getPercentageTranslationKey(), getFillPercentage());
    }

    public TextComponentTranslation getCapacityTextTranslation() {
        return new TextComponentTranslation(this.mte.getCapacityTranslationKey(),
                UnitsConversions.convertAndFormatToSizeOfOrder(this.maxAmount, this.baseContentUnit));
    }

    public TextComponentTranslation getContentTextTranslation() {
        return new TextComponentTranslation(
                this.mte.getContentTextTranslationKey(), getContentFormatted());
    }

    public static void initOrUpdate(MetaTileEntityStorageFormat<?> mte) {
        if (mte.getStorageUtil() == null) {
            mte.initStorageUtil();
        } else mte.getStorageUtil().updateContent();
    }
}
