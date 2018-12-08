package uem.ast.type;

import org.antlr.v4.runtime.Token;
import org.bytedeco.javacpp.LLVM;
import uem.IR.LLVMEmitter;
import uem.antlr.GraceParser;
import uem.ast.Position;

public class IntegerType implements Type {

    private final Position position;
    private Token symbol;

    public Position getPosition() {
        return this.position;
    }

    public IntegerType(Position position) {
        this.position = position;
    }

    public IntegerType() {
        this.position = null;
    }

    @Override
    public int getType() {
        return GraceParser.T_INT;
    }

    @Override
    public String getName() {
        return LLVMEmitter.FORMAT_NUMBER;
    }

    @Override
    public int getTypeIndex() {
        return 0;
    }

    @Override
    public void setSymbol(Token sym) {
        this.symbol = sym;
    }

    @Override
    public Token getSymbol() {
        return null;
    }

    @Override
    public LLVM.LLVMValueRef getLLVMValue() {
        return null;
    }

    @Override
    public String toString(){
        return "int";
    }
}
