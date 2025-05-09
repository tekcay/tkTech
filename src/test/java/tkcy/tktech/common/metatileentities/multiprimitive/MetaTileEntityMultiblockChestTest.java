package tkcy.tktech.common.metatileentities.multiprimitive;

import org.junit.jupiter.api.Test;

import gregtech.api.util.GTLog;

class MetaTileEntityMultiblockChestTest {

    @Test
    void test() {
        int page = 1;
        int slotsPerPage = 100 / page;

        int factor = slotsPerPage / 9 > 8 ? 18 : 9;

        GTLog.logger.info("factor : " + factor);
        GTLog.logger.info("slotsPerPage :" + slotsPerPage);
    }
}
