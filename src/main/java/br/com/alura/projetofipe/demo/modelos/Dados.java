package br.com.alura.projetofipe.demo.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record Dados(String codigo, String nome) {
}
