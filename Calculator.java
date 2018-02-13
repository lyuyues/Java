import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Calculator {
    public int calculate(String s) {
        char[] c = s.toCharArray();
        Queue<Element> elements = new LinkedList<>();
        Stack<Op> opStack = new Stack<>();
        int num = 0;
        for (int i = 0; i < c.length; i++) {
            int ch = c[i];
            if (ch == ' ')
                continue;
            if (ch >= '0' && ch <= '9') {
                num = num * 10 + ch - '0';
            } else {
                elements.offer(new Num(num));
                num = 0;

                Op op;
                if (ch == '+')
                    op = new Plus();
                else if (ch == '-')
                    op = new Minus();
                else if (ch == '*')
                    op = new Times();
                else
                    op = new Divide();
                while (!opStack.isEmpty() && op.compare(opStack.peek()) <= 0)
                    elements.offer(opStack.pop());
                opStack.push(op);
            }
        }
        elements.offer(new Num(num));
        while (!opStack.isEmpty())
            elements.offer(opStack.pop());

        Stack<Num> nums = new Stack<>();
        while (!elements.isEmpty()) {
            Element element = elements.poll();
            if (element.isNum()) {
                nums.push((Num) element);
                continue;
            }

            Op op = (Op) element;
            Num r = nums.pop();
            Num l = nums.pop();
            nums.push(op.cal(l, r));
        }
        return nums.peek().getN();
    }

    interface Element {
        boolean isNum();
    }

    class Num implements Element {
        private int n;

        Num(int n) {
            this.n = n;
        }

        int getN() {
            return n;
        }

        public boolean isNum() {
            return true;
        }
    }

    abstract class Op implements Element {
        public boolean isNum() {
            return false;
        }

        abstract int compare(Op other);
        abstract Num cal(Num l, Num r);
    }

    class Plus extends Op {
        int compare(Op other) {
            if ((other instanceof Plus) || (other instanceof Minus))
                return 0;
            return -1;
        }
        Num cal(Num l, Num r) {
            return new Num(l.getN() + r.getN());
        }
    }

    class Minus extends Op {
        int compare(Op other) {
            if ((other instanceof Plus) || (other instanceof Minus))
                return 0;
            return -1;
        }

        Num cal(Num l, Num r) {
            return new Num(l.getN() - r.getN());
        }
    }

    class Times extends Op {
        int compare(Op other) {
            if ((other instanceof Plus) || (other instanceof Minus))
                return 1;
            return 0;
        }

        Num cal(Num l, Num r) {
            return new Num(l.getN() * r.getN());
        }
    }

    class Divide extends Op {
        int compare(Op other) {
            if ((other instanceof Plus) || (other instanceof Minus))
                return 1;
            return 0;
        }

        Num cal(Num l, Num r) {
            return new Num(l.getN() / r.getN());
        }
    }
}
