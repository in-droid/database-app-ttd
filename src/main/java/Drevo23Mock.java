import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Drevo23Mock<Tip> implements Seznam<Tip> {


    @Override
    public void add(Tip e) {
        throw new java.lang.OutOfMemoryError();
    }

    @Override
    public Tip removeFirst() {
        return null;
    }

    @Override
    public Tip remove(Tip e) {
        return null;
    }

    @Override
    public Tip getFirst() {
        return null;
    }

    @Override
    public boolean exists(Tip e) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void print() {

    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        throw new IOException();

    }

    @Override
    public void restore(InputStream inputStream) throws IOException, ClassNotFoundException {

    }

    @Override
    public Tip getElement(Tip e) {
        return null;
    }
}
