package com.deloitte.cadastro;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Cliente> bancoDeDados = new ArrayList<>();
        Scanner leitor = new Scanner(System.in);
        int contadorId = 1;
        int opcao = 0;

        while (opcao != 5) {
            System.out.println("\n===== CRUD CLIENTES (JAVA PURO) =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Todos");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Deletar Cliente");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = leitor.nextInt();
            leitor.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1: // CREATE
                    System.out.print("Nome: ");
                    String nome = leitor.nextLine();
                    System.out.print("Email: ");
                    String email = leitor.nextLine();
                    
                    Cliente novo = new Cliente(contadorId++, nome, email);
                    bancoDeDados.add(novo);
                    System.out.println("Cliente cadastrado!");
                    break;

                case 2: // READ
                    System.out.println("\n--- Lista de Clientes ---");
                    if (bancoDeDados.isEmpty()) System.out.println("Nenhum cliente encontrado.");
                    for (Cliente c : bancoDeDados) {
                        System.out.println(c);
                    }
                    break;

                case 3: // UPDATE (Simplificado)
                    System.out.print("Digite o ID do cliente para editar: ");
                    int idBusca = leitor.nextInt();
                    leitor.nextLine();
                    
                    for (Cliente c : bancoDeDados) {
                        if (c.getId() == idBusca) {
                            System.out.print("Novo Nome: ");
                            c.setNome(leitor.nextLine());
                            System.out.print("Novo Email: ");
                            c.setEmail(leitor.nextLine());
                            System.out.println("Cliente atualizado!");
                        }
                    }
                    break;

                case 4: // DELETE
                    System.out.print("Digite o ID para remover: ");
                    int idRemover = leitor.nextInt();
                    bancoDeDados.removeIf(c -> c.getId() == idRemover);
                    System.out.println("Cliente removido (se existia).");
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;
            }
        }
        leitor.close();
    }
}