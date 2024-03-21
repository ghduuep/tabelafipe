package br.com.alura.projetofipe.demo.principal;

import br.com.alura.projetofipe.demo.modelos.Dados;
import br.com.alura.projetofipe.demo.modelos.Modelos;
import br.com.alura.projetofipe.demo.modelos.Veiculo;
import br.com.alura.projetofipe.demo.service.ConsumoApi;
import br.com.alura.projetofipe.demo.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private String enderecoVeiculo;
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {

        var menu = """
                O que você deseja consultar?
                1- Carro
                2- Moto
                3- Caminhão
                Escolha uma opção:""";
        System.out.println(menu);

        var opcao = leitura.nextInt();

        if (opcao == 1) {
            enderecoVeiculo = ENDERECO + "carros/marcas";
        } else if (opcao == 2) {
            enderecoVeiculo = ENDERECO + "motos/marcas";
        } else if (opcao == 3) {
            enderecoVeiculo = ENDERECO + "caminhoes/marcas";
        }

        var json = consumoApi.obterDados(enderecoVeiculo);
        //System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                        .sorted(Comparator.comparing(Dados::codigo))
                                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta:");
        var codigoMarca = leitura.next();

        enderecoVeiculo = enderecoVeiculo + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(enderecoVeiculo);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca:\n");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado:");
        var nomeVeiculo = leitura.next();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());


        System.out.println("Modelos filtrados:\n");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o código do modelo que deseja consultar:");
        var codigoModelo = leitura.next();

        enderecoVeiculo = enderecoVeiculo + "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(enderecoVeiculo);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = enderecoVeiculo + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println);

    }
}
