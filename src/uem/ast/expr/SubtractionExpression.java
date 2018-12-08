package uem.ast.expr;

import org.antlr.v4.runtime.Token;
import org.bytedeco.javacpp.LLVM;
import uem.IR.LLVMEmitter;
import uem.ast.Position;
import uem.ast.type.IntegerType;
import org.antlr.symtab.Type;

import static org.bytedeco.javacpp.LLVM.LLVMBuildSub;

public class SubtractionExpression implements BinaryExpression {

    private final Expression left;
    private final Expression right;
    private final Position position;
    private Token symToken;
    private Type type = new IntegerType();

    @Override
    public Expression getLeft() {
        return this.left;
    }

    @Override
    public Expression getRight() {
        return this.right;
    }

    public SubtractionExpression(Expression left, Expression right) {
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
    public void setSymbol(Token sym) {
        this.symToken = sym;
    }

    @Override
    public Token getSymbol() {
        return this.symToken;
    }

    @Override
    public LLVM.LLVMValueRef getLLVMValue() {
        try {
            LLVM.LLVMValueRef leftExp = this.left.getLLVMValue();
            LLVM.LLVMValueRef rightExp = this.right.getLLVMValue();

            LLVM.LLVMValueRef result = LLVMBuildSub(LLVMEmitter.getInstance().builder,
                    leftExp, rightExp, "sub"
            );
            return result;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return this.type;
    }
}
