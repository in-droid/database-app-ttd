import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Drevo23<Tip> implements Seznam<Tip> {
    class Element23 {
        public Tip[] values;
        public Element23 left, middle, right, parent;
        private int placeOfInsert;

        public Element23(Tip firstValue, Tip secondValue, Element23 left,
                         Element23 middle, Element23 right, Element23 parent) {

            this.values = (Tip[]) new Object[2];
            this.values[0] = firstValue;
            this.values[1] = secondValue;
            this.left = left;
            this.right = right;
            this.middle = middle;
            this.parent = parent;
            this.placeOfInsert = -1;
        }

        public Element23(Tip firstValue, Tip secondValue) {
            this(firstValue, secondValue, null, null, null, null);
        }

        public Element23(Tip firstValue, Tip secondValue, Element23 parent) {
            this(firstValue, secondValue, null, null, null, parent);

        }

        public Element23(Element23 element23) {

            this.values = element23.values;
            this.left = element23.left;
            this.right = element23.right;
            this.middle = element23.middle;
            this.parent = element23.parent;
            this.placeOfInsert = -1;
        }
    }

    private Element23 rootNode;
    private Comparator<Tip> comparator;

    public Drevo23(Comparator<Tip> comparator) {
        rootNode = null;
        this.comparator = comparator;
    }

    private Element23 insertIntoNode(Tip e, Element23 node) {
        if (comparator.compare(node.values[0], e) < 0) {
            node.values[1] = e;
            node.placeOfInsert = 1;
        } else if (comparator.compare(node.values[0], e) > 0) {
            Tip tmp = node.values[0];
            node.values[0] = e;
            node.values[1] = tmp;
            node.placeOfInsert = 0;
        }
        return node;
    }

    private ArrayList<Element23> getDummyChildren(Element23 node) {
        ArrayList<Element23> result = new ArrayList<>();
        result.add(node.left);
        result.add(node.right);
        return result;
    }

    private void setParent(ArrayList<Element23> nodes, Element23 parent) {
        for (Element23 node: nodes) {
            node.parent = parent;
        }
    }

    private Element23 restructureNodes(Element23 node, Tip middleEl) {
        Element23 newNode;

        if (node.parent == null) {
            newNode = new Element23(middleEl, null);
        }
        else {
            newNode = new Element23(null, null, node.parent);
        }
        newNode.left = new Element23(node.values[0], null, newNode);
        newNode.right = new Element23(node.values[1], null, newNode);

        if (newNode.parent == null) {
            rootNode = newNode;
        }
        else {
            Element23 parent = newNode.parent;
            if (parent.right == node) {
                parent.right = null;
                parent.right = newNode;
            } else if (parent.left == node) {
                parent.left = null;
                parent.left = newNode;
            } else if (parent.middle == node) {
                parent.middle = newNode;
            }
        }
        if (node.left == null) {
            return newNode;
        }
        if (node.left.values[0] == null) {
            newNode.left.left = node.left.left;
            newNode.left.right = node.left.right;
            node.left.left.parent = newNode.left;
            node.left.right.parent = newNode.left;

            newNode.right.left = node.middle;
            newNode.right.right = node.right;
            node.middle.parent = newNode.right;
            node.right.parent = newNode.right;
        }
        else if (node.middle.values[0] == null) {
            newNode.left.left = node.left;
            newNode.left.right = node.middle.left;
            newNode.right.left = node.middle.right;
            newNode.right.right = node.right;

            node.left.parent = newNode.left;
            node.middle.left.parent = newNode.left;
            node.middle.right.parent = newNode.right;
            node.right.parent = newNode.right;
        }
        else {
            newNode.left.left = node.left;
            newNode.left.right = node.middle;
            newNode.right.left = node.right.left;
            newNode.right.right = node.right.right;

            node.left.parent = newNode.left;
            node.middle.parent = newNode.left;
            node.right.left.parent = newNode.right;
            node.right.right.parent = newNode.right;
        }
        return newNode;
    }

    private void propagateUpStream(Element23 node, Tip e) {
        if (node.parent != null && node.parent.values[1] == null) {
            Tip middleEl = null;
            if (comparator.compare(e, node.values[0]) < 0) {
                middleEl = node.values[0];
                node.values[0] = e;

            } else if (comparator.compare(node.values[0], e) < 0 &&
                    comparator.compare(e, node.values[1]) < 0) {
                middleEl = e;

            } else if (comparator.compare(node.values[1], e) < 0) {
                middleEl = node.values[1];
                node.values[1] = e;
            }

            insertIntoNode(middleEl, node.parent);
            Element23 parent = node.parent;
            if (node.parent.placeOfInsert == 0) {
                parent.middle = null;
                parent.left = new Element23(node.values[0], null, parent);
                parent.middle = new Element23(node.values[1], null, node.parent);

                if(node.left == null) {
                    return;
                }
                if (node.left.values[0] == null) {
                    ArrayList<Element23> leftChildren = getDummyChildren(node.left);
                    setParent(leftChildren, parent.left);
                    parent.left.left = leftChildren.get(0);
                    parent.left.right = leftChildren.get(1);
                    parent.middle.left = node.middle;
                    parent.middle.right = node.right;

                    node.middle.parent = parent.middle;
                    node.right.parent = parent.middle;
                }
                else if (node.middle.values[0] == null) {
                    ArrayList<Element23> middleChildren = getDummyChildren(node.middle);
                    parent.left.left = node.left;
                    parent.left.right = middleChildren.get(0);
                    parent.middle.left = middleChildren.get(1);
                    parent.middle.right = node.right;

                    node.left.parent = parent.left;
                    middleChildren.get(0).parent = parent.left;
                    middleChildren.get(1).parent = parent.middle;
                    node.right.parent = parent.middle;
                }
                else if (node.right.values[0] == null) {
                    ArrayList<Element23> rightChildren = getDummyChildren(node.right);
                    setParent(rightChildren, parent.middle);
                    parent.left.left = node.left;
                    parent.left.right = node.middle;
                    parent.middle.left = rightChildren.get(0);
                    parent.middle.right = rightChildren.get(1);

                    node.left.parent = parent.left;
                    node.middle.parent = parent.left;
                }
            }
            else if (node.parent.placeOfInsert == 1) {
                parent.middle = null;
                parent.middle = new Element23(node.values[0], null, node.parent);
                parent.right = new Element23(node.values[1], null, node.parent);
                if (node.left == null) {
                    return;
                }
                if (node.left.values[0] == null) {
                    ArrayList<Element23> leftChildren = getDummyChildren(node.left);
                    setParent(leftChildren, parent.middle);
                    parent.middle.left = leftChildren.get(0);
                    parent.middle.right = leftChildren.get(1);
                    parent.right.left = node.middle;
                    parent.right.right = node.right;
                    node.right.parent = parent.right;
                    node.middle.parent = parent.right;
                }
                else if(node.middle.values[0] == null){
                    ArrayList<Element23> middleChildren = getDummyChildren(node.middle);
                    parent.middle.left =  node.left;
                    parent.middle.right = middleChildren.get(0);
                    middleChildren.get(0).parent = parent.middle;
                    parent.right.left = middleChildren.get(1);
                    middleChildren.get(1).parent = parent.right;
                    parent.right.right = node.right;

                    node.right.parent = parent.right;
                    node.left.parent = parent.middle;
                }
                else if (node.right.values[0] == null) {
                    ArrayList<Element23> rightChildren = getDummyChildren(node.right);
                    setParent(rightChildren, parent.right);
                    parent.middle.left = node.left;
                    parent.middle.right = node.middle;
                    parent.right.left = rightChildren.get(0);
                    parent.right.right = rightChildren.get(1);

                    node.left.parent = parent.middle;
                    node.middle.parent = parent.middle;
                }
            }
        }
        else {
            Tip middleEl = null;
            if (comparator.compare(e, node.values[0]) < 0) {
                middleEl = node.values[0];
                node.values[0] = e;

            } else if (comparator.compare(node.values[0], e) < 0 &&
                    comparator.compare(e, node.values[1]) < 0) {

                middleEl = e;

            } else if (comparator.compare(node.values[1], e) < 0) {
                middleEl = node.values[1];
                node.values[1] = e;
            }
            Element23 newNode = restructureNodes(node, middleEl);
            if (newNode.parent == null) {
                return;
            }
            propagateUpStream(newNode.parent, middleEl);
        }
    }

    private Element23 findPlace(Tip e, Element23 node) {
        if (node.left == null) {
            if (comparator.compare(e, node.values[0]) == 0 ) {
                throw new IllegalArgumentException();
            }
            if (node.values[1] != null && comparator.compare(e, node.values[1]) == 0) {
                throw new IllegalArgumentException();
            }
            return node;
        }

        if (comparator.compare(e, node.values[0]) < 0) {
            return findPlace(e, node.left);
        }
        else if (node.values[1] == null) {
            if (comparator.compare(e, node.values[0]) > 0) {
                return findPlace(e, node.right);
            }
            else throw new IllegalArgumentException();
        }
        else {
            if (comparator.compare(e, node.values[1]) < 0) {
                return findPlace(e, node.middle);
            }
            else if (comparator.compare(e, node.values[1]) > 0) {
                return findPlace(e, node.right);
            }
            else throw new IllegalArgumentException();
        }
    }

    private void insert(Tip e) {
        if (rootNode == null) {
            rootNode = new Element23(e, null);
            return;
        }
        Element23 place = findPlace(e, rootNode);
        if (place.values[1] == null) {
            insertIntoNode(e, place);
        } else {
            propagateUpStream(place, e);

        }
    }

    /// ***************** CHANGE THIS ***********************//////////////
    private List<Tip> preOrder(Element23 node, ArrayList<Tip> elements) {
        if (node == null) {
            return elements;
        }

        if (node.values[1] == null) {
            elements.add((Tip) ("(" + node.values[0] + ")"));
        }
        else {
            elements.add((Tip) ("(" + node.values[0] + "," + node.values[1] +")"));
        }
        elements.addAll(preOrder(node.left, new ArrayList<>()));
        elements.addAll(preOrder(node.middle, new ArrayList<>()));
        elements.addAll(preOrder(node.right, new ArrayList<>()));
        return elements;
    }

    private List<Tip> inOrder(Element23 node, ArrayList<Tip> elements) {
        if (node == null) {
            return elements;
        }

        elements.addAll(inOrder(node.left, new ArrayList<>()));
        elements.add(node.values[0]);
        if (node.values[1] != null) {
            elements.addAll(inOrder(node.middle, new ArrayList<>()));
            elements.add(node.values[1]);
        }
        elements.addAll(inOrder(node.right, new ArrayList<>()));
        return elements;
    }

    @Override
    public void add(Tip e) {
        insert(e);
    }

    @Override
    public Tip removeFirst() {
        if (rootNode == null) {
            throw new java.util.NoSuchElementException();
        }
        return remove(rootNode.values[0]);
    }

    @Override
    public Tip getFirst() {
        if (rootNode == null) {
            throw new java.util.NoSuchElementException();
        }
        return rootNode.values[0];
    }

    @Override
    public int size() {
        return size(rootNode);
    }

    private int size(Element23 node) {
        if (node == null) {
            return 0;
        }
        if (node.values[1] != null) {
            return 2 + size(node.left) + size(node.middle) + size(node.right);
        }
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public int depth() {
        return depth(rootNode);
    }

    private int depth(Element23 node) {
        if (node == null) {
            return 0;
        }
        return 1 + depth(node.left);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void print() {
        asListString();

    }

    public List<Tip> inorder() {
        return inOrder(rootNode, new ArrayList<>());
    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        //out.writeInt(this.size());
        save(rootNode, out);
        out.writeInt(-1);
        out.close();
    }

    private void save(Element23 node, ObjectOutput out) throws IOException {

        if (null == node) {
            out.writeInt(-1);
            return;
        }
        if (node.values[1] == null) {
            out.writeInt(2);
            out.writeObject(node.values[0]);
            save(node.left, out);
        }
        else {
            out.writeInt(3);
            out.writeObject(node.values[0]);
            out.writeObject(node.values[1]);
            save(node.left, out);
            save(node.middle, out);
        }
        save(node.right, out);

    }

    @Override
    public void restore(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        rootNode = restoreNode(in);
    }

    private Element23 restoreNode(ObjectInput in) throws IOException, ClassNotFoundException {
        int typeOfNode = in.readInt();
        Element23 node, nodeLeft, nodeRight, nodeMiddle;
        if (typeOfNode == -1) {
            return null;
        }
        else if (typeOfNode == 2) {
            node = new Element23((Tip) in.readObject(), null);
            nodeLeft = restoreNode(in);
            nodeRight = restoreNode(in);
            node.left = nodeLeft;
            node.right = nodeRight;

        }
        else if (typeOfNode == 3) {
            node = new Element23((Tip) in.readObject(), (Tip) in.readObject());
            nodeLeft = restoreNode(in);
            nodeMiddle = restoreNode(in);
            nodeRight = restoreNode(in);
            node.left = nodeLeft;
            node.middle = nodeMiddle;
            node.right = nodeRight;
            if (nodeLeft != null) {
                nodeMiddle.parent = node;
            }
        }
        else  {
            throw new java.util.NoSuchElementException();
        }
        if (nodeLeft != null) {
            nodeLeft.parent = node;
            nodeRight.parent = node;
        }
        return node;

    }

    @Override
    public Tip getElement(Tip e) {
        Element23 node = getNode(e, rootNode);
        if (node == null) {
            return null;
        }
        if(node.values[1] == null) {
            return node.values[0];
        }
        if (comparator.compare(e, node.values[0]) == 0) {
            return node.values[0];
        }
        return node.values[1];
    }


    private Tip remove(Tip e, Element23 node) {
        node = getNode(e, node);
        if (node.left == null) {
            if(node.values[1] == null) {
                node.values[0] = null;
                boolean rotationSuccessful = rotate(node);
                if (rotationSuccessful) {
                    return e;
                }
                propagateRemove(node.parent);
            }
            else {
                if (comparator.compare(e, node.values[0]) == 0) {
                    node.values[0] = node.values[1];
                }
                node.values[1] = null;
            }
            //   return e;
        }
        else {
            Tip smallestValue;
            if (comparator.compare(e, node.values[0]) == 0) {
                if (node.middle != null) {
                    smallestValue = findSmallestValue(node.middle);
                    node.values[0] = smallestValue;
                    remove(smallestValue, node.middle);
                }
                else {
                    smallestValue = findSmallestValue(node.right);
                    node.values[0] = smallestValue;
                    remove(smallestValue, node.right);
                }
            }
            else {
                smallestValue = findSmallestValue(node.right);
                node.values[1] = smallestValue;
                remove(smallestValue, node.right);
            }
        }
        return e;
    }

    private boolean rotate(Element23 node) {
        boolean cond = true;
        if (node.parent == null) {
            return false;
        }
        if (node.parent.right == node || node.parent.middle == node) {
            cond = rightRotate(node.parent);
        }
        if (node.parent.left == node || !cond) {
            cond = leftRotate(node.parent);
        }
        return cond;
    }

    private boolean leftRotate(Element23 node) {
        if (node.values[1] == null && node.right.values[1] == null) {
            return false;
        }
        /*
        if (node.values[1] != null && node.left.values[1] != null && node.middle.values[0] == null) {
            return false;
        }
         */
        if (node.middle != null) {
            if (node.left.values[0] == null) {
                if(node.middle.values[1] == null) {
                    node.left.values[0] = node.values[0];
                    node.values[0] = node.values[1];
                    node.left.values[1] = node.middle.values[0];
                    node.values[1] = null;
                    if (node.middle.left != null) {
                        node.left.middle = new Element23(node.middle.left);
                        node.left.right = new Element23(node.middle.right);
                        node.left.middle.parent = node.left;
                        node.left.right.parent = node.left;
                    }
                    node.middle = null;
                }
                else {
                    node.left.values[0] = node.values[0];
                    node.values[0] = node.middle.values[0];
                    node.middle.values[0] = node.middle.values[1];
                    node.middle.values[1] = null;
                    if (node.middle.left != null) {
                        node.left.right = new Element23(node.middle.left);
                        node.left.right.parent = node.left;
                        node.middle.left = node.middle.middle;
                        node.middle.middle = null;
                    }
                }
            }
            else if (node.middle.values[0] == null && node.right.values[1] != null) {
                node.middle.values[0] = node.values[1];
                node.values[1] = node.right.values[0];
                node.right.values[0] = node.right.values[1];
                node.right.values[1] = null;
                if (node.middle.left != null) {
                    node.middle.right = new Element23(node.right.left);
                    node.middle.right.parent = node.middle.right;
                    node.right.left = node.right.middle;
                    node.right.middle = null;
                }
            }
        }
        else {
            node.left.values[0] = node.values[0];
            node.values[0] = node.right.values[0];
            node.right.values[0] = node.right.values[1];
            node.right.values[1] = null;
            if (node.left.left != null) {
                node.left.right = new Element23(node.right.left);
                node.left.right.parent = node.left;
                node.right.left = node.right.middle;
                node.right.middle = null;
            }
        }
        return true;
    }

    private boolean rightRotate(Element23 node) {
        if (node.values[1] == null && node.left.values[1] == null) {
            return false;
        }
        if (node.values[1] != null && node.middle.values[0] == null && node.right.values[1] != null
                && node.left.values[1] == null) {
            return false;
        }
        if (node.values[1] != null) {
            if (node.middle.values[0] == null) {
                if (node.left.values[1] != null) {
                    node.middle.values[0] = node.values[0];
                    node.values[0] = node.left.values[1];
                    node.left.values[1] = null;
                    if (node.middle.left != null) {
                        node.middle.right = new Element23(node.middle.left);
                        node.middle.left = new Element23(node.left.right);
                        node.middle.left.parent = node.middle;
                        node.left.right = node.left.middle;
                        node.left.middle = null;
                    }
                }
                else {
                    node.right.values[1] = node.right.values[0];
                    node.right.values[0] = node.values[1];
                    node.values[1] = null;
                    if (node.middle.left != null) {
                        node.right.middle = node.right.left;
                        node.right.left = new Element23(node.middle.left);
                        node.right.left.parent = node.right;
                    }
                    node.middle = null;
                }
            } else {
                if (node.middle.values[1] == null) {
                    node.right.values[0] = node.middle.values[0];
                    node.right.values[1] = node.values[1];
                    node.values[1] = null;
                    if (node.right.left != null) {
                        node.right.right = node.right.left;
                        node.right.left = new Element23(node.middle.left);
                        node.right.middle = new Element23(node.middle.right);
                        node.right.left.parent = node.right;
                        node.right.middle.parent = node.right;
                    }
                    node.middle = null;
                }
                else {
                    node.right.values[0] = node.values[1];
                    node.values[1] = node.middle.values[1];
                    node.middle.values[1] = null;
                    if (node.right.left != null) {
                        node.right.right = new Element23(node.right.left);
                        node.right.left =  new Element23(node.middle.right);
                        node.right.left.parent = node.right;
                        node.middle.right = node.middle.middle;
                        node.middle.middle = null;
                    }
                }
            }
        }
        else {
            node.right.values[0] = node.values[0];
            node.values[0] = node.left.values[1];
            node.left.values[1] = null;
            if (node.right.left != null) {
                node.right.right = new Element23(node.right.left);
                node.right.left = new Element23(node.left.right);
                node.right.left.parent = node.right;
                node.left.right = node.left.middle;
            }
            node.left.middle = null;
        }
        return true;
    }

    private Tip findSmallestValue(Element23 element) {
        if (element.left == null) {
            return element.values[0];
        }
        return findSmallestValue(element.left);
    }

    private void propagateRemove(Element23 node) {
        if (node == null) {
            rootNode = rootNode.left;
            return;
        }
        else if (node.left.values[0] == null) {
            node.left.values[0] = node.values[0];
            node.left.values[1] = node.right.values[0];
            node.values[0] = null;
            if (node.left.left != null) {
                node.left.middle = new Element23(node.right.left);
                node.left.right = new Element23(node.right.right);
                node.left.middle.parent = node.left;
                node.left.right.parent = node.left;
                node.right = null;
            }
        }
        else if (node.right.values[0] == null) {
            node.left.values[1] = node.values[0];
            node.values[0] = null;
            if (node.left.left != null) {
                node.left.middle = new Element23(node.left.right);
                node.left.right = new Element23(node.right.left);
                node.left.right.parent = node.left;
            }
        }
        boolean rotationSuccessful = rotate(node);
        if (rotationSuccessful) {
            return;
        }
        propagateRemove(node.parent);
    }

    @Override
    public Tip remove(Tip e) {
        if(!exists(e)) {
            throw new java.util.NoSuchElementException();
        }
        return remove(e, rootNode);
    }

    private boolean member(Tip e, Element23 node) {
        if (node == null) {
            return false;
        } else if (comparator.compare(e, node.values[0]) == 0) {
            return true;
        } else if (comparator.compare(e, node.values[0]) < 0) {
            return member(e, node.left);
        } else if (node.values[1] == null) {
            return member(e, node.right);
        } else {
            if (comparator.compare(e, node.values[1]) == 0) {
                return true;
            } else if (comparator.compare(e, node.values[0]) > 0 &&
                    comparator.compare(e, node.values[1]) < 0) {
                return member(e, node.middle);
            } else return member(e, node.right);
        }
    }
    private Element23 getNode(Tip e, Element23 node) {
        if (node == null) {
            return null; //never executed, because before execution we check if the node exists
        } else if (comparator.compare(e, node.values[0]) == 0) {
            return node;
        } else if (comparator.compare(e, node.values[0]) < 0) {
            return getNode(e, node.left);
        } else if (node.values[1] == null) {
            return getNode(e, node.right);
        } else {
            if (comparator.compare(e, node.values[1]) == 0) {
                return node;
            } else if (comparator.compare(e, node.values[0]) > 0 &&
                    comparator.compare(e, node.values[1]) < 0) {
                return getNode(e, node.middle);
            } else return getNode(e, node.right);
        }
    }

    @Override
    public boolean exists(Tip e) {
        return member(e, rootNode);
    }

    public List<Tip> asList() {
        return preOrder(rootNode, new ArrayList<>());
    }


    public List<Tip> asListString() {
        return preOrder(rootNode, new ArrayList<>());

    }

    public void reset() {
        rootNode = null;
    }
}
