package tkcy.simpleaddon.api.unification.materials.chains;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.api.utils.TKCYSAUtil;

public class SensorsMaterials {

    public static int register(int id) {

        TKCYSAMaterials.GalliumPhosphate = new Material.Builder(id++, TKCYSAUtil.tkcysa("gallium_phosphate"))
                .dust()
                .gem()
                .components(Materials.Gallium, 1, Materials.Phosphorus, 1, Materials.Oxygen, 4)
                .colorAverage()
                .build();

        TKCYSAMaterials.LithiumNiobate = new Material.Builder(id++, TKCYSAUtil.tkcysa("lithium_niobate"))
                .dust()
                .gem()
                .components(Materials.Lithium, 1, Materials.Niobium, 1, Materials.Oxygen, 3)
                .colorAverage()
                .build();

        TKCYSAMaterials.LeadZirconateTitanate = new Material.Builder(id++, TKCYSAUtil.tkcysa("lead_zirconate_titanate"))
                .dust()
                .gem()
                .components(Materials.Lead, 2, Materials.Zirconium, 1, Materials.Titanium, 1, Materials.Oxygen, 6)
                .colorAverage()
                .build();

        return id;
    }
}
