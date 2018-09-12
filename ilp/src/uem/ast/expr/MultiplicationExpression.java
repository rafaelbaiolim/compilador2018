package uem.ast.expr;

import uem.ast.Position;

public class MultiplicationExpression implements BinaryExpression {

    private final Expression left;
    private final Expression right;
    private final Position position;

    @Override
    public Expression getLeft() {
        return this.left;
    }

    @Override
    public Expression getRight() {
        return this.right;
    }

    public MultiplicationExpression(Expression left, Expression right, Position position) {
        super();
        this.left = left;
        this.right = right;
        this.position = position;
    }

    public MultiplicationExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
        this.position = null;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
