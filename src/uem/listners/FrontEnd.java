package uem.listners;

import org.antlr.symtab.*;
import uem.antlr.GraceParser;
import uem.antlr.GraceParserBaseListener;
import uem.ast.expr.BinaryExpression;
import uem.ast.expr.Expression;
import uem.ast.stmt.DeclVar;
import uem.ast.stmt.Statement;
import uem.semantic.CheckSymbols;
import uem.visitors.DeclVarVisitor;
import uem.visitors.ExpressionVisitor;
import uem.visitors.ListSpecParamVisitor;

import java.util.List;

public class FrontEnd extends GraceParserBaseListener {
    Scope currentScope;

    /**
     * Estrutura para Escopo
     */

    private void pushScope(Scope s) {
        currentScope = s;
        System.out.println("entering: " + currentScope.getName() + ":" + s);
    }

    private void popScope() {
        System.out.println("leaving: " + currentScope.getName() + ":" + currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    /**
     * Escopo Global
     */
    public void enterGraceFile(GraceParser.GraceFileContext ctx) {
        GlobalScope globalScope = new GlobalScope(null);
        ctx.scope = globalScope;
        pushScope(globalScope);
    }

    public void exitGraceFile(GraceParser.GraceFileContext ctx) {
        popScope();
    }

    /**
     * Escopos de Função / Procedure
     */

    public void enterDecFunc(GraceParser.DecFuncContext funCtx) {
        FunctionSymbol fSymbol = new FunctionSymbol(funCtx.ID().getText());
        fSymbol.setEnclosingScope(currentScope);
        currentScope.define(fSymbol);
        funCtx.scope = fSymbol;
        pushScope(fSymbol);
    }

    public void enterDecProc(GraceParser.DecProcContext procCtx) {
        FunctionSymbol fSymbol = new FunctionSymbol(procCtx.ID().getText());
        fSymbol.setEnclosingScope(currentScope);
        currentScope.define(fSymbol);
        procCtx.scope = fSymbol;
        pushScope(fSymbol);
    }


    public void exitFunction(GraceParser.FunctionContext funCtx) {
        popScope();
    }

    public void exitProcedure(GraceParser.ProcedureContext procCtx) {
        popScope();
    }

    /**
     * Argumentos de Função / Procedure
     */

    public void enterLstParam(GraceParser.LstParamContext ctxLstParam) {
        try {
            ListSpecParamVisitor listParamVisit = new ListSpecParamVisitor();
            List<Statement> lst = listParamVisit.visit(ctxLstParam);
            lst.forEach(stmt -> {
                VariableSymbol v = new VariableSymbol(stmt.getVarName());
                currentScope.define(v);
            });
        } catch (Exception ex) {
            //Erro sintático
        }
    }

    /**
     * Escopos de blocos (Locais)
     */

    public void enterBlock(GraceParser.BlockContext blkCtx) {
        LocalScope locScope = new LocalScope(currentScope);
        blkCtx.scope = locScope;
        pushScope(locScope);
    }

    public void exitBlock(GraceParser.BlockContext blkCtx) {
        popScope();

    }

    /**
     * Escopo de Variáveis
     */

    public void exitDeclVar(GraceParser.DeclVarContext declVarCtx) {
        try {
            DeclVar declVar = new DeclVarVisitor().visit(declVarCtx);
            declVar.getListStmt().forEach(stmt -> {
                {
                    VariableSymbol v = new VariableSymbol(stmt.getVarName());
                    v.setType(v.getType());
                    currentScope.define(v);
                }
            });

        } catch (Exception ex) {
            //Erro sintático
        }
    }

    public void enterVarReference(GraceParser.VarReferenceContext ctx) {
        Symbol sym = currentScope.resolve(ctx.ID().getText());
        if (sym == null) {
            CheckSymbols.error(ctx.ID().getSymbol(), "variável não declarada: " + ctx.getText());
        }
    }

    /**
     * Escopo para operações Binárias
     */

    public void exitBinaryOperation(GraceParser.BinaryOperationContext binCtx) {
        Expression exp = new ExpressionVisitor().visit(binCtx);
        if (exp instanceof BinaryExpression) {
            Expression left = ((BinaryExpression) exp).getLeft();
            Expression right = ((BinaryExpression) exp).getRight();
        }
    }


}
