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

package org.geysermc.pack.converter.data;

import org.geysermc.pack.converter.converter.texture.transformer.TransformedTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextureConversionData extends BaseConversionData {
    private final List<TransformedTexture> transformedTextures = new ArrayList<>();
    private final String textureSubdirectory;

    public TextureConversionData(@NotNull Path inputDirectory, @NotNull Path outputDirectory, @Nullable String textureSubdirectory, @NotNull ResourcePack vanillaPack) {
        super(inputDirectory, outputDirectory, vanillaPack);

        this.textureSubdirectory = textureSubdirectory;
    }

    public void addTransformedTexture(@NotNull TransformedTexture transformedTexture) {
        this.transformedTextures.add(transformedTexture);
    }

    @NotNull
    public List<TransformedTexture> transformedTextures() {
        return this.transformedTextures;
    }

    @Nullable
    public String textureSubdirectory() {
        return this.textureSubdirectory;
    }
}
