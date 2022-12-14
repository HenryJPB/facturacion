/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package vistas;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JComponent;

/**
 * A sample that demonstrates how to draw and paint shapes, apply visual
 * effects, blend colors in overlapping objects, and animate objects.
 *
 * @see javafx.scene.canvas.Canvas
 * @see javafx.scene.canvas.GraphicsContext
 * @see javafx.scene.effect.BlendMode
 * @see javafx.scene.effect.BoxBlur
 * @see javafx.scene.shape.Circle
 * @see javafx.scene.Group
 * @see javafx.scene.paint.LinearGradient
 * @see javafx.animation.Timeline
 */
public final class FireworksComponente extends JComponent {

    private final SanFranciscoFireworks sanFranciscoFireworks = new SanFranciscoFireworks();
    Stage primaryStage;
    
    // Constructor de esta clase: 
    public FireworksComponente() throws Exception {
        //start(primaryStage); 
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        root.getChildren().add(sanFranciscoFireworks);
    }

    public void stop() {
        sanFranciscoFireworks.stop();
    }

    public void play() {
        sanFranciscoFireworks.start();
    }

    /**
     * A Simple Particle that draws its self as a circle.
     */
    public static class Particle {

        private static final double GRAVITY = 0.06;
        // properties for animation
        // and colouring
        double alpha;
        final double easing;
        double fade;
        double posX;
        double posY;
        double velX;
        double velY;
        final double targetX;
        final double targetY;
        final Paint color;
        final int size;
        final boolean usePhysics;
        final boolean shouldExplodeChildren;
        final boolean hasTail;
        double lastPosX;
        double lastPosY;

        public Particle(double posX, double posY, double velX, double velY, double targetX, double targetY,
                Paint color, int size, boolean usePhysics, boolean shouldExplodeChildren, boolean hasTail) {
            this.posX = posX;
            this.posY = posY;
            this.velX = velX;
            this.velY = velY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.color = color;
            this.size = size;
            this.usePhysics = usePhysics;
            this.shouldExplodeChildren = shouldExplodeChildren;
            this.hasTail = hasTail;
            this.alpha = 1;
            this.easing = Math.random() * 0.02;
            this.fade = Math.random() * 0.1;
        }

        public boolean update() {
            lastPosX = posX;
            lastPosY = posY;
            if (this.usePhysics) { // on way down
                velY += GRAVITY;
                posY += velY;
                this.alpha -= this.fade; // fade out particle
            } else { // on way up
                double distance = (targetY - posY);
                // ease the position
                posY += distance * (0.03 + easing);
                // cap to 1
                alpha = Math.min(distance * distance * 0.00005, 1);
            }
            posX += velX;
            return alpha < 0.005;
        }

        public void draw(GraphicsContext context) {
            final double x = Math.round(posX);
            final double y = Math.round(posY);
            final double xVel = (x - lastPosX) * -5;
            final double yVel = (y - lastPosY) * -5;
            // set the opacity for all drawing of this particle
            context.setGlobalAlpha(Math.random() * this.alpha);
            // draw particle
            context.setFill(color);
            context.fillOval(x - size, y - size, size + size, size + size);
            // draw the arrow triangle from where we were to where we are now
            if (hasTail) {
                context.setFill(Color.rgb(255, 255, 255, 0.3));
                context.fillPolygon(new double[]{posX + 1.5, posX + xVel, posX - 1.5},
                        new double[]{posY, posY + yVel, posY}, 3);
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        play();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    
    /*
    public static void main(String[] args) {
        launch(args);
    }
    */ 
}  // Fireworks(). 
