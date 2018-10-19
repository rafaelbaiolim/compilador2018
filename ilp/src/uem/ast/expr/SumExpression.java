package uem.ast.expr;

import org.antlr.v4.runtime.Token;
import uem.ast.Position;

public class SumExpression implements BinaryExpression {

    private final Expression left;
    private final Expression right;
    private final Position position;
    private Token symToken;

    @Override
    public Expression getLeft() {
        return this.left;
    }

    @Override
    public Expression getRight() {
        return this.right;
    }

    public SumExpression(Expression left, Expression right, Position position) {
        super();
        this.left = left;
        this.right = right;
        this.position = position;
    }

    public SumExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
        this.position = null;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Token setSymbol(Token sym) {
        return this.symToken = sym;
    }

    @Override
    public Token getSymbol() {
        return this.symToken;
    }
}
