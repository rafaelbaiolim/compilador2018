package uem.visitors;

import uem.antlr.GraceParser;
import uem.antlr.GraceParserBaseVisitor;
import uem.ast.expr.*;
import uem.ast.stmt.Statement;

public class ExpressionVisitor extends GraceParserBaseVisitor<Expression> {

    public Expression visitBinaryOperation(GraceParser.BinaryOperationContext ctx) {
        String op = ctx.operator.getText();
        switch (op) {
            case "+":
                return new SumExpression(visit(ctx.left), visit(ctx.right));
            case "-":
                return new SubtractionExpression(visit(ctx.left), visit(ctx.right));
            case "*":
                return new MultiplicationExpression(visit(ctx.left), visit(ctx.right));
            case "/":
                return new DivisionExpression(visit(ctx.left), visit(ctx.right));
        }
        throw new UnsupportedOperationException(ctx.getText());
    }


}
