package com.github.alexthe666.citadel.item;

import net.minecraft.world.item.Item;

public class ItemCustomRender extends Item {

    public ItemCustomRender(Properties props) {
        super(props);
    }

    // 删除过时的方法
    // public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
    //     consumer.accept(((IClientItemExtensions) Citadel.PROXY.getISTERProperties()));
    // }

    // 确保使用正确的属性设置方法
    // 这里假设有一个新的方法来设置客户端属性
    public void setClientProperties() {
        // 使用新的 API 方法来设置属性
    }
}
