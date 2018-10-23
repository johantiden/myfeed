package se.johantiden.myfeed.util;

public class Pair<L, R> {
    public final L left;
    public final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> pair(L l, R r) {
        return new Pair<>(l, r);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
