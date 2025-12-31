
import java.util.Objects;

public class Move {

    private final Coordinate SOURCE;
    private final Coordinate DESTINATION;

    public Move(final Coordinate SOURCE,
            final Coordinate DESTINATION) {
        this.SOURCE = SOURCE;
        this.DESTINATION = DESTINATION;
    }

    public Coordinate getSource() {
        return this.SOURCE;
    }

    public Coordinate getDestination() {
        return this.DESTINATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Move)) {
            return false;
        }
        Move m = (Move) o;
        return this.SOURCE.equals(m.SOURCE)
                && this.DESTINATION.equals(m.DESTINATION);
    }

    @Override
    public int hashCode() {
        return Objects.hash(SOURCE, DESTINATION);
    }


}
