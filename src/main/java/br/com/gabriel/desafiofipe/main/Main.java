package br.com.gabriel.desafiofipe.main;

import br.com.gabriel.desafiofipe.model.Dados;
import br.com.gabriel.desafiofipe.model.Modelos;
import br.com.gabriel.desafiofipe.model.Veiculo;
import br.com.gabriel.desafiofipe.service.ConsumoApi;
import br.com.gabriel.desafiofipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner scan = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void start(){
        var menu = """
                ***** OPÇÕES *****
                Carros
                Motos
                Caminhões
                
                
                Digite a opção:
                """;
        System.out.println(menu);
        var opcao = scan.nextLine();
        String endereco = "";

        if(opcao.toLowerCase().contains("car")){
            endereco = URL_BASE+"carros/marcas";
        } else if (opcao.toLowerCase().contains("mo")) {
            endereco = URL_BASE+"motos/marcas";
        } else if (opcao.toLowerCase().contains("cam")) {
            endereco = URL_BASE+"caminhoes/marcas";
        }else {
            endereco = "Opção Invalida";
        }
        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Qual o codigo da marca que você deseja consultar");
        var codMarca = scan.nextLine();

        endereco = endereco + "/" + codMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca:\n");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro para pesquisa");
        var nomeVeiculo = scan.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o codigo do modelo para consulta");
        var codModelo = scan.nextLine();
        endereco = endereco + "/" + codModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nVeiculos filtrados: ");
        veiculos.forEach(System.out::println);

    }
}
