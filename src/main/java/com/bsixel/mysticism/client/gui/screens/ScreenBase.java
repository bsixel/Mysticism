package com.bsixel.mysticism.client.gui.screens;

import com.bsixel.mysticism.MysticismMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ScreenBase extends Screen { // TODO: I'm sure we'll need something in here

    protected int guiLeft = 0; // Screens are weird, start top left, like reading a page I guess
    protected int guiTop = 0;

    protected int sizeX = 0;
    protected int sizeY = 0;

    protected ScreenBase(ITextComponent title) { // TODO, this is really just a test for now
        super(title);
    }

    @Nonnull
    public <T extends Widget> T addButton(@Nonnull T button) {
        return super.addButton(button);
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    protected double getGuiScaleFactor() {
        return minecraft.getMainWindow().getGuiScaleFactor();
    }

    public FontRenderer getFontRenderer() {
        return this.font;
    }

}
// Most reasonable blit explanation I've seen: https://forums.minecraftforge.net/topic/77532-1144solved-scale-image-with-blit/
//Some blit param namings , thank you Mekanism
//blit(int x, int y, int textureX, int textureY, int width, int height);
//blit(int x, int y, TextureAtlasSprite icon, int width, int height);
//blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
//blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
//blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
//innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);