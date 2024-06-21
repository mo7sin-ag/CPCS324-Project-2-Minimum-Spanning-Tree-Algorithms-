
class Node {

// Attributes
    private Vertex V;
    private Node Next;

// Constructors
    public Node(Vertex V) {
        this(V, null);
    }

    public Node(Vertex V, Node Next) {
        this.V = V;
        this.Next = Next;
    }

// Setters
    public void setV(Vertex V) {
        this.V = V;
    }

    public void setNext(Node Next) {
        this.Next = Next;
    }

// Getters
    public Vertex getV() {
        return V;
    }

    public Node getNext() {
        return Next;
    }
}

public class Queue {

// Attributes
    private Node Head;

// Constructors
    public Queue() {
        Head = null;
    }

// Methods
        public void Insert(Vertex V) {
        if (Head == null) {
            Head = new Node(V);
        } else {
            Node HelpPTR = Head;

            while (HelpPTR.getNext() != null) {
                HelpPTR = HelpPTR.getNext();
            }

            HelpPTR.setNext(new Node(V, HelpPTR.getNext()));
        }
    }
    
    public boolean IsEmpty() {
        return Head == null;
    }

    public Vertex Delete(Node Node) {
        Vertex ReturnVertex = null;
        
        if (!IsEmpty()) {
            if (Head.getV().getLabel().equals(Node.getV().getLabel())) {
                ReturnVertex = Head.getV();
                Head = (Head.getNext());
            } else {
                Node HelpPTR = Head;

                while (HelpPTR.getNext() != null) {
                    if (HelpPTR.getNext().getV().getLabel().equals(Node.getV().getLabel())) {
                        ReturnVertex = HelpPTR.getNext().getV();
                        HelpPTR.setNext(HelpPTR.getNext().getNext());
                        break;
                    }

                    HelpPTR = HelpPTR.getNext();
                }
            }
        }
        return ReturnVertex;
    }

    public boolean FindNode(Vertex V) {
        Node HelpPTR = Head;

        while (HelpPTR != null) {
            if (HelpPTR.getV().getLabel().equals(V.getLabel())) {
                return true;
            }
            HelpPTR = HelpPTR.getNext();
        }

        return false;
    }

    public Node FindMinimum() {
        Node HelpPTR = Head, MinimumNode = null;
        double MinimumKey = 1000;

        while (HelpPTR != null) {
            if (HelpPTR.getV().getKey() < MinimumKey) {
                MinimumNode = HelpPTR;
                MinimumKey = HelpPTR.getV().getKey();
            }

            HelpPTR = HelpPTR.getNext();
        }

        return MinimumNode;
    }

    public void SetNodeKey(Vertex V, int Key) {
        Node HelpPTR = Head;

        while (HelpPTR != null) {
            if (HelpPTR.getV().getLabel().equals(V.getLabel())) {
                HelpPTR.getV().setKey(Key);
            }
            HelpPTR = HelpPTR.getNext();
        }
    }
}
