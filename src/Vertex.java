
class Vertex {

// Attributes
    private String Label;
    private double Key;
    private Vertex Pi;

// Constructors
    public Vertex(String Label) {
        this.Label = Label;
    }

// Setters
    public void setKey(int Key) {
        this.Key = Key;
    }

    public void setPi(Vertex Pi) {
        this.Pi = Pi;
    }

// Getters
    public String getLabel() {
        return Label;
    }

    public double getKey() {
        return Key;
    }

    public Vertex getPi() {
        return Pi;
    }
}
