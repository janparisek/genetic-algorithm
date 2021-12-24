package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Aminoacid;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ListIterator;

public class Renderer {
    private Graphics2D g2 = null;
    private static final Integer height = 640;
    private static final Integer width = 800;
    private static final Integer imageCenterX = width / 2;
    private static final Integer imageCenterY = height / 2;
    private static final Integer gridSize = 30;
    private static final Integer cellSize = 20;
    private static final Color hydrophilic = new Color(0, 128, 0);
    private static final Color hydrophobic = new Color(128, 0, 0);

    public void renderProtein(Protein protein) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // White background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        renderProteinData(protein);

        // Save data
        saveToDisk(image);

    }


    private void renderProteinData(Protein protein) {

        // Render useful data
        Integer x = 0;
        Integer y = 0;

        ListIterator<Aminoacid> iter = protein.elements.listIterator();
        while(iter.hasNext()) {
            Aminoacid current = iter.next();
            drawAmino(x, y, current.isHydrophilic, iter.nextIndex());

            Integer deltaX = switch (current.nextDirection) {
                case EAST -> 1;
                case WEST -> -1;
                default -> 0;
            };
            Integer deltaY = switch (current.nextDirection) {
                case SOUTH -> 1;
                case NORTH -> -1;
                default -> 0;
            };
            if(iter.hasNext()) {
                drawDash(x, y, deltaX, deltaY);
            }
            x += deltaX;
            y += deltaY;

        }

    }


    private void drawDash(Integer x, Integer y, Integer deltaX, Integer deltaY) {
        Integer gridCenterX = imageCenterX + (x * gridSize);
        Integer gridCenterY = imageCenterY + (y * gridSize);

        Integer newGridCenterX = gridCenterX + (deltaX * gridSize);
        Integer newGridCenterY = gridCenterY + (deltaY * gridSize);

        g2.setColor(Color.BLACK);
        g2.drawLine(gridCenterX, gridCenterY, newGridCenterX, newGridCenterY);
    }

    private void drawAmino(Integer x, Integer y, Boolean isHydrophilic, Integer index) {
        Integer gridCenterX = imageCenterX + (x * gridSize);
        Integer gridCenterY = imageCenterY + (y * gridSize);
        Integer topLeftX = gridCenterX - (cellSize / 2);
        Integer topLeftY = gridCenterY - (cellSize / 2);

        if(isHydrophilic) {
            g2.setColor(hydrophilic);
        } else {
            g2.setColor(hydrophobic);
        }
        g2.drawRect(topLeftX, topLeftY, cellSize, cellSize);
        g2.drawString(index.toString(), topLeftX, gridCenterY);
    }

    private void saveToDisk(BufferedImage image) {
        String folder = "logs";
        String filename = "candidate.png";
        if (new File(folder).exists() == false) new File(folder).mkdirs();

        try {
            File f = new File(folder + File.separator + filename);
            ImageIO.write(image, "png", f);
            System.out.println("Saved at " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
