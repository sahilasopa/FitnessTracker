/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sahilasopa.fitnesstracker.helpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

/**
 * Graphic instance for rendering inference info (latency, FPS, resolution) in an overlay view.
 */
public class InferenceInfoGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR = Color.WHITE;
    private static final float TEXT_SIZE = 50.0f;
    private final Paint textPaint;
    private final GraphicOverlay overlay;
    private final double latency;
    // Only valid when a stream of input images is being processed. Null for single image mode.
    @Nullable
    private final Integer framesPerSecond;
    private final Integer pushupCount;
    private final Integer squatsCount;

    public InferenceInfoGraphic(
            GraphicOverlay overlay, double latency, @Nullable Integer framesPerSecond, Integer pushupsCount, Integer squatsCount) {
        super(overlay);
        this.overlay = overlay;
        this.latency = latency;
        this.pushupCount = pushupsCount;
        this.squatsCount = squatsCount;
        this.framesPerSecond = framesPerSecond;
        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        postInvalidate();
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        float x = TEXT_SIZE * 0.5f;
        float y = TEXT_SIZE * 1.5f;

        canvas.drawText("Push-ups detected (Lifetime): " + pushupCount, x, y, textPaint);
        canvas.drawText("Squats Detected (Lifetime): " + squatsCount, x, y + TEXT_SIZE, textPaint);
        // Draw FPS (if valid) and inference latency
        if (framesPerSecond != null) {
            canvas.drawText(
                    "FPS: " + framesPerSecond + ", latency: " + latency + " ms", x, y + (TEXT_SIZE * 2), textPaint);
        } else {
            canvas.drawText("Latency: " + latency + " ms", x, y + (TEXT_SIZE * 2), textPaint);
        }
    }
}
