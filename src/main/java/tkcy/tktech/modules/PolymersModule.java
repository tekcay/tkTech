package tkcy.tktech.modules;

import static gregtech.api.unification.material.Materials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PolymersModule {

    public static final List<Material> GTCEu_POLYMERS = new ArrayList<Material>() {

        {
            add(Polybenzimidazole);
            add(Polycaprolactam);
            add(Polydimethylsiloxane);
            add(PolyphenyleneSulfide);
            add(Polytetrafluoroethylene);
            add(PolyvinylAcetate);
            add(PolyvinylButyral);
            add(PolyvinylChloride);
            add(Polyethylene);
            add(StyreneButadieneRubber);
            add(SiliconeRubber);
        }
    };
}
