/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.converter.texture.transformer.type.ui;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@AutoService(TextureTransformer.class)
public class LocatorTransformer implements TextureTransformer {
    private static final Map<String, String> DOT_MAPPING = Map.of(
        "gui/sprites/hud/locator_bar_dot/default_0.png", "ui/locator_bar_dot_0.png",
        "gui/sprites/hud/locator_bar_dot/default_1.png", "ui/locator_bar_dot_1.png",
        "gui/sprites/hud/locator_bar_dot/default_2.png", "ui/locator_bar_dot_2.png",
        "gui/sprites/hud/locator_bar_dot/default_3.png", "ui/locator_bar_dot_3.png"
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (Map.Entry<String, String> mapping : DOT_MAPPING.entrySet()) {
            Texture dotTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, mapping.getKey()));
            if (dotTexture == null) continue;

            BufferedImage dotImage = this.readImage(dotTexture);

            // The default size for java is 9x9, so a 18x18 texture would have a 2 scale
            int scale = dotImage.getWidth() / 9;

            // The default size for bedrock is 7x7, so a 18x18 java texture would mean a 14x14 size for bedrock
            int bedrockSize = scale * 7;

            BufferedImage bedrockDotImage = new BufferedImage(bedrockSize, bedrockSize, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockDotImage.getGraphics();

            // Crops the java image in order to trim the transparent pixels that are on the vanilla texture by default
            // Also accounts for the scale a resource pack may offer
            g.drawImage(ImageUtil.crop(dotImage, scale, scale, bedrockSize, bedrockSize), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, mapping.getValue()), bedrockDotImage, "png");
        }

        Texture upArrowTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/locator_bar_arrow_up.png"));
        if (upArrowTexture != null) {
            BufferedImage javaImage = this.readImage(upArrowTexture);

            int scale = javaImage.getWidth() / 7;

            BufferedImage bedrockImage = new BufferedImage(javaImage.getWidth(), scale * 4, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(ImageUtil.crop(javaImage, 0, 1, javaImage.getWidth(), scale * 4), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/locator_arrow_up.png"), bedrockImage, "png");
        }

        Texture downArrowTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/locator_bar_arrow_down.png"));
        if (downArrowTexture != null) {
            BufferedImage javaImage = this.readImage(downArrowTexture);

            int scale = javaImage.getWidth() / 7;

            BufferedImage bedrockImage = new BufferedImage(javaImage.getWidth(), scale * 4, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(ImageUtil.crop(javaImage, 0, 0, javaImage.getWidth(), scale * 4), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/locator_arrow_down.png"), bedrockImage, "png");
        }

        Texture bgTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/locator_bar_background.png"));
        if (bgTexture != null) {
            BufferedImage javaImage = this.readImage(bgTexture);

            int scale = javaImage.getWidth() / 12;

            BufferedImage bedrockImage = new BufferedImage(scale * 182, javaImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(ImageUtil.crop(javaImage, 0, 0, scale * 5, javaImage.getHeight()), 0, 0, null);

            for (int i = 0; i < 86; i++) {
                g.drawImage(ImageUtil.crop(javaImage, scale * 5, 0, scale * 2, javaImage.getHeight()), (i * scale * 2) + scale * 5, 0, null);
            }

            g.drawImage(ImageUtil.crop(javaImage, javaImage.getWidth() - (scale * 5), 0, scale * 5, javaImage.getHeight()), (scale * 182) - scale * 5, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/locator_bg.png"), bedrockImage, "png");
        }
    }
}
