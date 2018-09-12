package com.luoromeo.stresstester.entity;

public final class Parameter {

    private final String paramType;

    private final Object object;

    public Parameter(String paramType, Object object) {
        try {
            Class.forName(paramType);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("paramType error: class not found !");
        }
        this.paramType = paramType;
        this.object = object;
    }

    public String getParamType() {
        return paramType;
    }

    public Object getObject() {
        return object;
    }

}
