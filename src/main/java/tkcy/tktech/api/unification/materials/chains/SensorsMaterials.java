package tkcy.tktech.api.unification.materials.chains;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import tkcy.tktech.api.unification.materials.TkTechMaterials;
import tkcy.tktech.api.utils.TkTechUtil;

public class SensorsMaterials {

    public static int register(int id) {
        TkTechMaterials.GalliumPhosphate = new Material.Builder(id++, TkTechUtil.tktech("gallium_phosphate"))
                .dust()
                .gem()
                .components(Materials.Gallium, 1, Materials.Phosphorus, 1, Materials.Oxygen, 4)
                .colorAverage()
                .build();

        TkTechMaterials.LithiumNiobate = new Material.Builder(id++, TkTechUtil.tktech("lithium_niobate"))
                .dust()
                .gem()
                .components(Materials.Lithium, 1, Materials.Niobium, 1, Materials.Oxygen, 3)
                .colorAverage()
                .build();

        TkTechMaterials.LeadZirconateTitanate = new Material.Builder(id++, TkTechUtil.tktech("lead_zirconate_titanate"))
                .dust()
                .gem()
                .components(Materials.Lead, 2, Materials.Zirconium, 1, Materials.Titanium, 1, Materials.Oxygen, 6)
                .colorAverage()
                .build();

        return id;
    }
}
