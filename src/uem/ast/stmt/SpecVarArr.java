package uem.ast.stmt;

import org.antlr.v4.runtime.Token;
import org.bytedeco.javacpp.LLVM;
import uem.IR.LLVMEmitter;
import uem.ast.Position;
import uem.ast.VarStatement;
import uem.ast.expr.Expression;
import uem.ast.type.Type;
import uem.listners.FrontEnd;

import static org.bytedeco.javacpp.LLVM.*;

public class SpecVarArr implements VarStatement {

    private final String varName;
    private Expression value;
    private Expression length;
    private final Position position;
    private Token symToken;
    LLVMValueRef llvmValRef;

    public SpecVarArr(String varName, Expression length, Expression value, Position position) {
        this.varName = varName;
        this.length = length;
        this.value = value;
        this.position = position;
        this.getLLVMValue();

    }

    public SpecVarArr(String varName) {
        this.varName = varName;
        this.value = null;
        this.position = null;
    }

    public SpecVarArr(String varName, Expression length) {
        this.varName = varName;
        this.length = length;
        this.position = null;
    }

    @Override
    public Expression getValue() {
        return value;
    }

    @Override
    public Expression setValue(Expression exp) {
        return this.value = exp;
    }

    public Expression getLength() {
        return this.length;
    }

    public Expression setLength(Expression expLen) {
        return this.length = expLen;
    }

    @Override
    public String getVarName() {
        return varName;
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
    public LLVMValueRef getLLVMValue() {
        return this.llvmValRef;
    }

    @Override
    public LLVMValueRef getLLVMValue(Type type) {
        //Considere que um array sempre será de Number -> int
        LLVMGetDataLayout(LLVMEmitter.getInstance().mod);

        LLVM.LLVMTypeRef typeArray = LLVMArrayType(
                LLVMEmitter.getInstance().types.i32(),
                10
        );

//        LLVM.LLVMValueRef varAlloc = LLVMBuildArrayAlloca(
//                LLVMEmitter.getInstance().builder,
//                typeArray,
//                LLVMConstInt(
//                        LLVMEmitter.getInstance().types.i32(),
//                        10, 1
//                ),
//                "arr");


        LLVM.LLVMValueRef varAlloc = LLVMBuildAlloca(
                LLVMEmitter.getInstance().builder,
                typeArray,
                this.varName);

        if (this.value != null) {
            LLVMBuildStore(
                    LLVMEmitter.getInstance().builder,
                    this.value.getLLVMValue(),
                    varAlloc
            );
        }

        FrontEnd.currentScope.setLLVMSymRef(this.varName, varAlloc);
        return this.llvmValRef = varAlloc;

    }

    @Override
    public Position getPosition() {
        return this.position;
    }

}
