import java.awt.*;

import javax.swing.ImageIcon;

public class Tile extends Rectangle {
    private Coordinate coordinate;
    private Piece piece;
    private boolean lightSquare;

    public Tile(int x, int y, int tileWidth, int tileHeight, Coordinate coordinate, Piece piece, boolean lightSquare) {
        super(x, y, tileWidth, tileHeight);
        this.coordinate = coordinate;
        this.piece = piece;
        this.lightSquare = lightSquare;
    }

    public int getXCoordinate() {
        return this.coordinate.getX();
    }

    public int getYCoordinate() {
        return this.coordinate.getY();
    }

    public boolean isLightSquare() {
        return lightSquare;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public void draw(Graphics g) {
        if (this.lightSquare) {
            g.setColor(new Color(238, 238, 210));
        } else {
            g.setColor(new Color(118, 150, 86));
        }
        g.fillRect(this.x, this.y, this.width, this.height);
        if (this.piece != null) {
            this.piece.draw(g, this);
        }

    }

    public ImageIcon scaleImage(ImageIcon image, int sideLength) {
        Image original = image.getImage();
        Image scaledImage = original.getScaledInstance(sideLength, sideLength, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

}
