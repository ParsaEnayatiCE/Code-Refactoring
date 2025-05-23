package MiniJava.semantic.symbol;

import MiniJava.codeGenerator.varType;

/**
 * Created by mohammad hosein on 6/28/2015.
 */

public enum SymbolType {
    Int, Bool;

    public varType toVarType() {
        switch (this) {
            case Int:
                return varType.Int;
            case Bool:
                return varType.Bool;
            default:
                throw new IllegalArgumentException();
        }
    }
}
