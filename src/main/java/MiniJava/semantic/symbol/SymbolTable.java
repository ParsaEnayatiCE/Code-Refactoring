package MiniJava.semantic.symbol;

import MiniJava.codeGenerator.Address;
import MiniJava.codeGenerator.Memory;
import MiniJava.codeGenerator.TypeAddress;
import MiniJava.codeGenerator.varType;
import MiniJava.errorHandler.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Klass> klasses;
    private Map<String, Address> keyWords;
    private Memory mem;
    private SymbolType lastType;

    public SymbolTable(Memory memory) {
        mem = memory;
        klasses = new HashMap<>();
        keyWords = new HashMap<>();
        keyWords.put("true", new Address(1, varType.Bool, TypeAddress.Imidiate));
        keyWords.put("false", new Address(0, varType.Bool, TypeAddress.Imidiate));
    }

    public SymbolType getLastType() {
        return lastType;
    }

    public void setLastType(SymbolType type) {
        lastType = type;
    }

    public void addClass(String className) {
        if (klasses.containsKey(className)) {
            ErrorHandler.printError("This class already defined");
        }
        klasses.put(className, new Klass());
    }

    public void addField(String fieldName, String className) {
        klasses.get(className).Fields.put(fieldName, new Symbol(getLastType(), mem.getDateAddress()));
    }

    public void addMethod(String className, String methodName, int address) {
        if (klasses.get(className).Methodes.containsKey(methodName)) {
            ErrorHandler.printError("This method already defined");
        }
        klasses.get(className).Methodes.put(methodName, new Method(new MethodDescriptor(address, getLastType())));
    }

    public void addMethodParameter(String className, String methodName, String parameterName) {
        klasses.get(className).Methodes.get(methodName).addParameter(parameterName);
    }

    public void addMethodLocalVariable(String className, String methodName, String localVariableName) {
        if (klasses.get(className).Methodes.get(methodName).localVariable.containsKey(localVariableName)) {
            ErrorHandler.printError("This variable already defined");
        }
        klasses.get(className).Methodes.get(methodName).localVariable.put(localVariableName, new Symbol(getLastType(), mem.getDateAddress()));
    }

    public void setSuperClass(String superClass, String className) {
        klasses.get(className).superClass = klasses.get(superClass);
    }

    public Address get(String keywordName) {
        return keyWords.get(keywordName);
    }

    public Symbol get(String fieldName, String className) {
        return klasses.get(className).getField(fieldName);
    }

    public Symbol get(String className, String methodName, String variable) {
        Symbol res = klasses.get(className).Methodes.get(methodName).getVariable(variable);
        if (res == null) res = get(variable, className);
        return res;
    }

    public Symbol getNextParam(String className, String methodName) {
        return klasses.get(className).Methodes.get(methodName).getNextParameter();
    }

    public void startCall(String className, String methodName) {
        klasses.get(className).Methodes.get(methodName).reset();
    }

    public int getMethodCallerAddress(String className, String methodName) {
        return klasses.get(className).Methodes.get(methodName).callerAddress;
    }

    public int getMethodReturnAddress(String className, String methodName) {
        return klasses.get(className).Methodes.get(methodName).returnAddress;
    }

    public SymbolType getMethodReturnType(String className, String methodName) {
        return klasses.get(className).Methodes.get(methodName).returnType;
    }

    public int getMethodAddress(String className, String methodName) {
        return klasses.get(className).Methodes.get(methodName).codeAddress;
    }

    class Klass {
        public Map<String, Symbol> Fields;
        public Map<String, Method> Methodes;
        public Klass superClass;

        public Klass() {
            Fields = new HashMap<>();
            Methodes = new HashMap<>();
        }

        public Symbol getField(String fieldName) {
            if (Fields.containsKey(fieldName)) {
                return Fields.get(fieldName);
            }
            return superClass.getField(fieldName);
        }
    }

    class MethodDescriptor {
        public int codeAddress;
        public SymbolType returnType;
        public MethodDescriptor(int codeAddress, SymbolType returnType) {
            this.codeAddress = codeAddress;
            this.returnType = returnType;
        }
    }

    class Method {
        public int codeAddress;
        public Map<String, Symbol> parameters;
        public Map<String, Symbol> localVariable;
        private ArrayList<String> orderdParameters;
        public int callerAddress;
        public int returnAddress;
        public SymbolType returnType;
        private int index;

        public Method(MethodDescriptor descriptor) {
            this.codeAddress = descriptor.codeAddress;
            this.returnType = descriptor.returnType;
            this.orderdParameters = new ArrayList<>();
            this.returnAddress = mem.getDateAddress();
            this.callerAddress = mem.getDateAddress();
            this.parameters = new HashMap<>();
            this.localVariable = new HashMap<>();
        }

        public Symbol getVariable(String variableName) {
            if (parameters.containsKey(variableName)) return parameters.get(variableName);
            if (localVariable.containsKey(variableName)) return localVariable.get(variableName);
            return null;
        }

        public void addParameter(String parameterName) {
            parameters.put(parameterName, new Symbol(getLastType(), mem.getDateAddress()));
            orderdParameters.add(parameterName);
        }

        private void reset() {
            index = 0;
        }

        private Symbol getNextParameter() {
            return parameters.get(orderdParameters.get(index++));
        }
    }
}

//class Symbol{
//    public SymbolType type;
//    public int address;
//    public Symbol(SymbolType type , int address)
//    {
//        this.type = type;
//        this.address = address;
//    }
//}
//enum SymbolType{
//    Int,
//    Bool
//}