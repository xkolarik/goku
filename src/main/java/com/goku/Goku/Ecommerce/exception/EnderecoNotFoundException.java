package com.goku.goku.ecommerce.exception;

public class EnderecoNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4523434129058044727L;

    public EnderecoNotFoundException() {
        super("Endereço não encontrado!");
    }
}
