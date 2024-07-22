package tkcy.simpleaddon.api.utils;

public interface MassiveItemStackHandler {

    int getItemStackAmount();

    void setItemStackAmount(int itemStackAmount);

    int getMaxItemStackAmount();

    void setMaxItemStackAmount(int maxItemStackAmount);

    boolean isFilled();

    boolean isEmpty();
}
