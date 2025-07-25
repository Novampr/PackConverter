/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.converter.texture.transformer;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.texture.TextureMappings;
import org.geysermc.pack.converter.data.TextureConversionData;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransformContext {
    private final PackConversionContext<TextureConversionData> conversionContext;
    private final TextureMappings mappings;
    private final Collection<Texture> textures;
    private final BedrockResourcePack bedrockPack;
    private final ResourcePack javaPack;
    private final Map<Key, Texture> byKey = new HashMap<>();

    public TransformContext(
            PackConversionContext<TextureConversionData> conversionContext,
            TextureMappings mappings,
            Collection<Texture> textures,
            BedrockResourcePack bedrockPack,
            ResourcePack javaPack
    ) {
        this.conversionContext = conversionContext;
        this.mappings = mappings;
        this.textures = textures;
        this.bedrockPack = bedrockPack;
        this.javaPack = javaPack;

        for (Texture texture : textures) {
            this.byKey.put(texture.key(), texture);
        }
    }

    public TextureMappings mappings() {
        return this.mappings;
    }

    public BedrockResourcePack bedrockResourcePack() {
        return this.bedrockPack;
    }

    public ResourcePack javaResourcePack() {
        return this.javaPack;
    }

    public ResourcePack vanillaPack() {
        return this.conversionContext.data().vanillaPack();
    }

    /**
     * Removes the texture from the list of textures and returns it.
     *
     * @param key the key of the texture to remove
     * @return the texture that was removed, or null if it didn't exist
     */
    @Nullable
    public Texture poll(@NotNull Key key) {
        Texture remove = this.byKey.remove(key);
        if (remove == null) {
            return null;
        }

        this.textures.remove(remove);
        return remove;
    }

    /**
     * Returns the texture with the given key, but does not remove it from the list of textures.
     *
     * @param key the key of the texture to get
     * @return the texture with the given key, or null if it doesn't exist
     */
    @Nullable
    public Texture peek(@NotNull Key key) {
        return this.byKey.get(key);
    }

    /**
     * Removes the texture from the list of textures and returns it.
     * Or will peek the vanilla texture if it does not exist in the pack.
     * If it still does not exist, we return null
     *
     * @param key the key of the texture to remove
     * @return the texture that was removed, or the vanilla one if it didn't exist, or null
     */
    @Nullable
    public Texture pollOrPeekVanilla(@NotNull Key key) {
        Texture remove = this.byKey.remove(key);
        if (remove == null) {
            // This *shouldn't* be null, but if a bad key is inputted, it is possible this value is null
            return this.conversionContext.data().vanillaPack().texture(key);
        }

        this.textures.remove(remove);
        return remove;
    }

    /**
     * Gets the texture from the list of textures and returns it.
     * Or will peek the vanilla texture if it does not exist in the pack.
     * If it still does not exist, we return null
     *
     * @param key the key of the texture to get
     * @return the texture that was removed, or the vanilla one if it didn't exist, or null
     */
    @Nullable
    public Texture peekOrVanilla(@NotNull Key key) {
        Texture texture = this.byKey.get(key);
        if (texture == null) {
            // This *shouldn't* be null, but if a bad key is inputted, it is possible this value is null
            return this.conversionContext.data().vanillaPack().texture(key);
        }

        return texture;
    }

    /**
     * Returns whether the pack has a certain texture
     *
     * @param key the key of the texture to check
     * @return true if the texture is present, else false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted") // It just doesn't make sense.
    public boolean isTexturePresent(@NotNull Key key) {
        return this.byKey.containsKey(key);
    }

    /**
     * Adds the given texture to the list of textures.
     *
     * @param key the key of the texture to add
     * @param image the image of the texture to add
     * @param format the format of the image
     * @throws IOException if an error occurs while converting the image to bytes
     */
    public void offer(@NotNull Key key, @NotNull BufferedImage image, @NotNull String format) throws IOException {
        this.offer(Texture.texture(key, Writable.bytes(ImageUtil.toByteArray(image, format))));
    }

    /**
     * Adds the given texture to the list of textures.
     *
     * @param texture the texture to add
     */
    public void offer(@NotNull Texture texture) {
        this.textures.add(texture);
        this.byKey.put(texture.key(), texture);
    }

    public void debug(@NotNull String message) {
        this.conversionContext.debug(message);
    }

    public void info(@NotNull String message) {
        this.conversionContext.info(message);
    }

    public void warn(@NotNull String message) {
        this.conversionContext.warn(message);
    }

    public void error(@NotNull String message) {
        this.conversionContext.error(message);
    }

    public void error(@NotNull String message, @NotNull Throwable throwable) {
        this.conversionContext.error(message, throwable);
    }
}
