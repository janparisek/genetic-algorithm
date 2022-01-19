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
    private static final Integer IMAGE_WIDTH = 800;
    private static final Integer IMAGE_HEIGHT = 640;
    private static final Integer IMAGE_CENTER_X = IMAGE_WIDTH / 2;
    private static final Integer IMAGE_CENTER_Y = IMAGE_HEIGHT / 2;
    private static final Integer GRID_SIZE = 30;
    private static final Integer CELL_SIZE = 20;
    private static final Color HYDROPHILIC_COLOR = new Color(0, 192, 0);
    private static final Color HYDROPHOBIC_COLOR = new Color(192, 0, 0);

    public void renderProtein(Protein protein) {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // White background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        renderProteinSequence(protein);
        renderProteinData(protein);

        // Save data
        saveToDisk(image);

    }

    private void renderProteinData(Protein protein) {
        String a = "Fitness: " + protein.getFitness();
        String b = "Hp Bonds: " + protein.getHhBonds();
        String c = "Overlaps: " + protein.getOverlaps();

        g2.setColor(Color.BLACK);
        g2.drawString(a, 0, 10);
        g2.drawString(b, 0, 20);
        g2.drawString(c, 0, 30);
    }


    private void renderProteinSequence(Protein protein) {

        for(int i = 0; i < protein.getPhenotype().size(); ++i) {
            // Draw current amino acid
            Aminoacid current = protein.getPhenotype().get(i);
            drawAminoacid(current.getPosition(), current.getHydrophobic(), String.valueOf(i));

            // Attempt to draw connection with next amino acid (if it exists)
            try {
                Aminoacid next = protein.getPhenotype().get(i + 1);
                drawConnection(current.getPosition(), next.getPosition());
            } catch (IndexOutOfBoundsException ignored) {}

        }

    }


    private void drawConnection(Position origin, Position destination) {

        Position imageOrigin = new Position(
            IMAGE_CENTER_X + (origin.x * GRID_SIZE),
            IMAGE_CENTER_Y + (origin.y * GRID_SIZE)
        );
        Position imageDestination = new Position(
            IMAGE_CENTER_X + (destination.x * GRID_SIZE),
            IMAGE_CENTER_Y + (destination.y * GRID_SIZE)
        );

        g2.setColor(Color.BLACK);
        g2.drawLine(imageOrigin.x, imageOrigin.y, imageDestination.x, imageDestination.y);
    }

    private void drawAminoacid(Position position, Boolean isHydrophobic, String label) {
        Position aminoacidCenter = new Position(
            IMAGE_CENTER_X + (position.x * GRID_SIZE),
            IMAGE_CENTER_Y + (position.y * GRID_SIZE)
        );
        Position aminoacidTopLeft = new Position(
            aminoacidCenter.x - (CELL_SIZE / 2),
            aminoacidCenter.y - (CELL_SIZE / 2)
        );

        if(isHydrophobic) {
            g2.setColor(HYDROPHOBIC_COLOR);
        } else {
            g2.setColor(HYDROPHILIC_COLOR);
        }
        g2.drawRect(aminoacidTopLeft.x, aminoacidTopLeft.y, CELL_SIZE, CELL_SIZE);
        g2.drawString(label, aminoacidTopLeft.x, aminoacidCenter.y);
    }

    private void saveToDisk(BufferedImage image) {
        String folder = "logs";
        String filename = "candidate.png";
        if (!new File(folder).exists()) { new File(folder).mkdirs(); }

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
