package main.NetworkingBasics.ResultStructure;

public class MultipleLineStructure<T> {
    int total;
    int num;
    T item;

    @Override
    public String toString() {
        return "MultipleLineStructure{" +
                "total=" + total +
                ", num=" + num +
                ", item=" + item +
                '}';
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
