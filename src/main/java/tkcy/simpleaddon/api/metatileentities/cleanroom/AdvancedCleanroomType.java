package tkcy.simpleaddon.api.metatileentities.cleanroom;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class AdvancedCleanroomType extends CleanroomType {

    public static final AdvancedCleanroomType NITROGEN_CLEANROOM = new AdvancedCleanroomType("nitrogen_cleanroom",
            "gregtech.recipe.cleanroom_nitrogen.display_name", Materials.Nitrogen);

    public static final AdvancedCleanroomType ARGON_CLEANROOM = new AdvancedCleanroomType("argon_cleanroom",
            "gregtech.recipe.cleanroom_argon.display_name", Materials.Argon);

    private final Material intertGasMaterial;

    public AdvancedCleanroomType(@NotNull String name, @NotNull String translationKey, Material intertGasMaterial) {
        super(name, translationKey);
        this.intertGasMaterial = intertGasMaterial;
    }
}
