package tkcy.simpleaddon.api.metatileentities.cleanroom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.Getter;

@Getter
public class AdvancedCleanroomType extends CleanroomType {

    public static final List<AdvancedCleanroomType> ADVANCED_CLEANROOM_TYPES = new ArrayList<>();

    public static final AdvancedCleanroomType NITROGEN_CLEANROOM = new AdvancedCleanroomType("nitrogen_cleanroom",
            "gregtech.recipe.cleanroom_nitrogen.display_name", Materials.Nitrogen, TextFormatting.AQUA);

    public static final AdvancedCleanroomType ARGON_CLEANROOM = new AdvancedCleanroomType("argon_cleanroom",
            "gregtech.recipe.cleanroom_argon.display_name", Materials.Argon, TextFormatting.GREEN);

    private final Material intertGasMaterial;
    private final TextFormatting displayColor;

    public AdvancedCleanroomType(@NotNull String name, @NotNull String translationKey, Material intertGasMaterial,
                                 TextFormatting displayColor) {
        super(name, translationKey);
        this.intertGasMaterial = intertGasMaterial;
        this.displayColor = displayColor;
        ADVANCED_CLEANROOM_TYPES.add(this);
    }
}
