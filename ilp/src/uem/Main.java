package uem;

import org.antlr.v4.runtime.CommonTokenStream;
import uem.antlr.IlpParser;
import uem.ast.AstMap;
import uem.parser.ParseTree;
import uem.parser.TreeToAst;

import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException {
        uem.Lexer lex = new uem.Lexer();
        CommonTokenStream cm = new CommonTokenStream(lex.lexFromCode("var a = 1 + 5:int;"));
        ParseTree pt = new ParseTree();
        try {
         new TreeToAst().map(new IlpParser(cm).ilpFile());

        } catch (Exception ex) {

        }

    }
}
