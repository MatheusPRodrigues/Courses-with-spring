package com.br.courses.dto.address;

public record AddressResponseDto(
        String cep,
        String logradouro,
        String complemento,
        String unidade,
        String bairro,
        String localidade,
        String uf,
        String estado,
        String regiao,
        String ibge,
        String gia,
        String ddd,
        String siafi
) {

    @Override
    public String cep() {
        return cep;
    }

    @Override
    public String logradouro() {
        return logradouro;
    }

    @Override
    public String complemento() {
        return complemento;
    }

    @Override
    public String unidade() {
        return unidade;
    }

    @Override
    public String bairro() {
        return bairro;
    }

    @Override
    public String localidade() {
        return localidade;
    }

    @Override
    public String uf() {
        return uf;
    }

    @Override
    public String estado() {
        return estado;
    }

    @Override
    public String regiao() {
        return regiao;
    }

    @Override
    public String ibge() {
        return ibge;
    }

    @Override
    public String gia() {
        return gia;
    }

    @Override
    public String ddd() {
        return ddd;
    }

    @Override
    public String siafi() {
        return siafi;
    }
}
