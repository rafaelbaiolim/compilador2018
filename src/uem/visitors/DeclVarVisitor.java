package uem.visitors;

import org.antlr.symtab.VariableSymbol;
import uem.antlr.GraceParser;
import uem.antlr.GraceParserBaseVisitor;
import uem.ast.VarStatement;
import uem.ast.stmt.DeclVar;
import uem.ast.stmt.SpecVar;
import uem.ast.stmt.SpecVarArr;
import uem.ast.stmt.Statement;
import uem.ast.type.Type;
import uem.listners.FrontEnd;

import java.util.List;

public class DeclVarVisitor extends GraceParserBaseVisitor<DeclVar> {

    public DeclVar visitDeclVar(GraceParser.DeclVarContext ctx) {

        GraceParser.ListSpecVarsContext listSpecVarsContext = ctx.listSpecVars();
        //lista de vars
        List<Statement> lst = new ListSpecVarVisitor().visit(listSpecVarsContext);

        //tipo
        Type type = new ListTypeVisitor().visit(ctx.lstType());
        for (Statement stmt : lst) {
            VarStatement currentStmt;
            if (stmt instanceof SpecVar) {
                currentStmt = (SpecVar) stmt;
            } else {
                currentStmt = (SpecVarArr) stmt;
            }
            VariableSymbol v = new VariableSymbol(stmt.getVarName());
            v.setType(type);
            FrontEnd.currentScope.define(v);
            currentStmt.getLLVMValue(type);
        }

        DeclVar declVar = new DeclVar(lst, type);

        return declVar;
    }


}
