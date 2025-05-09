package tkcy.tktech.api.utils;

import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.Nullable;

import tkcy.tktech.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.tktech.api.utils.number.Numbers;
import tkcy.tktech.api.utils.units.CommonUnits;
import tkcy.tktech.api.utils.units.UnitsConversions;

public class StorageUtils<T> {

    private int contentAmount;
    private String contentLocalizedName;
    private final CommonUnits baseContentUnit;
    private final int maxAmount;
    private final MetaTileEntityStorageFormat<T> mte;

    public StorageUtils(MetaTileEntityStorageFormat<T> mte) {
        this.mte = mte;
        this.maxAmount = mte.getMaxCapacity();
        this.baseContentUnit = mte.getBaseContentUnit();
        T content = mte.getContent();
        updateContent(content);
    }

    public void updateContent(@Nullable T content) {
        if (content == null) return;
        this.contentAmount = this.mte.getContentAmountProvider().apply(content);
        this.contentLocalizedName = this.mte.getContentLocalizedNameProvider().apply(content);
    }

    private boolean isEmpty() {
        return this.contentAmount == 0 || this.mte.getContent() == null;
    }

    public String getContentFormatted() {
        if (isEmpty()) return "Empty";

        return String.format("%s%s%s",
                UnitsConversions.convertAndFormatToSizeOfOrder(this.baseContentUnit, this.contentAmount),
                mte.getLinkingWordForContentDisplay(),
                this.contentLocalizedName);
    }

    protected String getFillPercentage() {
        return isEmpty() ? "" : Numbers.getQuotientPercentage(this.contentAmount, this.maxAmount) + "% filled";
    }

    public TextComponentTranslation getFillPercentageTextTranslation() {
        return new TextComponentTranslation(this.mte.getPercentageTranslationKey(), getFillPercentage());
    }

    public TextComponentTranslation getCapacityTextTranslation() {
        return new TextComponentTranslation(this.mte.getCapacityTranslationKey(),
                UnitsConversions.convertAndFormatToSizeOfOrder(this.baseContentUnit, this.maxAmount));
    }

    public TextComponentTranslation getContentTextTranslation() {
        return new TextComponentTranslation(
                this.mte.getContentTextTranslationKey(), getContentFormatted());
    }
}
