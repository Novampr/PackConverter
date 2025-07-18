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

import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

import java.nio.file.Path;

public class BaseConversionData implements ConversionData {
    private final Path inputDirectory;
    private final Path outputDirectory;
    private final ResourcePack vanillaPack;

    public BaseConversionData(@NotNull Path inputDirectory, @NotNull Path outputDirectory, @NotNull ResourcePack vanillaPack) {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        this.vanillaPack = vanillaPack;
    }

    @NotNull
    @Override
    public Path inputDirectory() {
        return this.inputDirectory;
    }

    @NotNull
    @Override
    public Path outputDirectory() {
        return this.outputDirectory;
    }

    @NotNull
    @Override
    public ResourcePack vanillaPack() {
        return this.vanillaPack;
    }
}
