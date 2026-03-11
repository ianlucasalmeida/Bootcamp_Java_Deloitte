package com.deloitte.cadastro;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Listas globais para todos os menus enxergarem
    static ArrayList<Cliente> clientes = new ArrayList<>();
    static ArrayList<Produto> catalogo = new ArrayList<>(); 
    static ArrayList<Venda> historicoVendas = new ArrayList<>(); 
    static Scanner leitor = new Scanner(System.in);
    
    static int contadorClienteId = 1;
    static int contadorProdutoId = 1;
    static int contadorVendaId = 1;

    public static void main(String[] args) {
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n===== SISTEMA OFICINA PEÇA JÁ =====");
            System.out.println("1. Módulo de Clientes");
            System.out.println("2. Módulo de Catálogo (Peças e Serviços)");
            System.out.println("3. Módulo de Vendas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma área: ");
            
            try { opcao = Integer.parseInt(leitor.nextLine()); } 
            catch (Exception e) { opcao = 0; }

            switch (opcao) {
                case 1: menuClientes(); break;
                case 2: menuCatalogo(); break;
                case 3: menuVendas(); break;
                case 4: System.out.println("Encerrando o sistema..."); break;
                default: System.out.println("Opção inválida.");
            }
        }
        leitor.close();
    }

    // ==========================================
    // MÓDULO 1: CLIENTES
    // ==========================================
    public static void menuClientes() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n--- MÓDULO DE CLIENTES ---");
            System.out.println("1. Cadastrar Cliente (CREATE)");
            System.out.println("2. Listar Clientes (READ)");
            System.out.println("3. Atualizar Cliente (UPDATE)");
            System.out.println("4. Deletar Cliente (DELETE)");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            
            try { opcao = Integer.parseInt(leitor.nextLine()); } 
            catch (Exception e) { opcao = 0; }

            switch (opcao) {
                case 1: // CREATE
                    System.out.print("Nome: ");
                    String nome = leitor.nextLine();
                    System.out.print("Email: ");
                    String email = leitor.nextLine();
                    clientes.add(new Cliente(contadorClienteId++, nome, email));
                    System.out.println("Cliente cadastrado!");
                    break;
                case 2: // READ
                    if (clientes.isEmpty()) System.out.println("Vazio.");
                    for (Cliente c : clientes) System.out.println(c.toString());
                    break;
                case 3: // UPDATE (Acesso direto às variáveis public)
                    System.out.print("Digite o ID do cliente para editar: ");
                    try {
                        int idEdit = Integer.parseInt(leitor.nextLine());
                        boolean achou = false;
                        for (Cliente c : clientes) {
                            if (c.id == idEdit) { 
                                System.out.print("Novo Nome: ");
                                c.nome = leitor.nextLine();
                                System.out.print("Novo Email: ");
                                c.email = leitor.nextLine();
                                System.out.println("Cliente atualizado!");
                                achou = true;
                                break;
                            }
                        }
                        if (!achou) System.out.println("Cliente não encontrado.");
                    } catch (Exception e) { System.out.println("Erro na digitação."); }
                    break;
                case 4: // DELETE (Acesso direto a c.id)
                    System.out.print("Digite o ID para remover: ");
                    try {
                        int idDel = Integer.parseInt(leitor.nextLine());
                        if (clientes.removeIf(c -> c.id == idDel)) System.out.println("Removido com sucesso!");
                        else System.out.println("Cliente não encontrado.");
                    } catch (Exception e) { System.out.println("Erro na digitação."); }
                    break;
                case 5: break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    // ==========================================
    // MÓDULO 2: CATÁLOGO (PEÇAS E SERVIÇOS)
    // ==========================================
    public static void menuCatalogo() {
        int opcao = 0;
        while (opcao != 6) {
            System.out.println("\n--- MÓDULO DE CATÁLOGO ---");
            System.out.println("1. Cadastrar Peça Automotiva (CREATE)");
            System.out.println("2. Cadastrar Serviço (CREATE)");
            System.out.println("3. Listar Catálogo (READ)");
            System.out.println("4. Atualizar Preço/Descrição (UPDATE)");
            System.out.println("5. Deletar Item do Catálogo (DELETE)");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            
            try { opcao = Integer.parseInt(leitor.nextLine()); } 
            catch (Exception e) { opcao = 0; }

            switch (opcao) {
                case 1: // CREATE PEÇA
                    try {
                        System.out.print("Descrição da Peça: ");
                        String desc = leitor.nextLine();
                        System.out.print("Preço Base (ex: 45.00): ");
                        double preco = Double.parseDouble(leitor.nextLine().replace(",", "."));
                        System.out.print("Marca: ");
                        String marca = leitor.nextLine();
                        System.out.print("Código: ");
                        String cod = leitor.nextLine();
                        catalogo.add(new Peca(contadorProdutoId++, desc, preco, marca, cod));
                        System.out.println("Peça cadastrada!");
                    } catch (Exception e) { System.out.println("Erro nos valores digitados."); }
                    break;
                case 2: // CREATE SERVIÇO
                    try {
                        System.out.print("Descrição do Serviço: ");
                        String desc = leitor.nextLine();
                        System.out.print("Preço Mão de Obra: ");
                        double preco = Double.parseDouble(leitor.nextLine().replace(",", "."));
                        System.out.print("Horas Estimadas: ");
                        double horas = Double.parseDouble(leitor.nextLine().replace(",", "."));
                        catalogo.add(new Servico(contadorProdutoId++, desc, preco, horas));
                        System.out.println("Serviço cadastrado!");
                    } catch (Exception e) { System.out.println("Erro nos valores digitados."); }
                    break;
                case 3: // READ
                    if (catalogo.isEmpty()) System.out.println("Vazio.");
                    for (Produto p : catalogo) System.out.println(p.toString());
                    break;
                case 4: // UPDATE (Acesso direto a p.descricao e p.precoBase)
                    System.out.print("Digite o ID do Produto/Serviço para editar: ");
                    try {
                        int idEdit = Integer.parseInt(leitor.nextLine());
                        boolean achou = false;
                        for (Produto p : catalogo) {
                            if (p.id == idEdit) {
                                System.out.print("Nova Descrição: ");
                                p.descricao = leitor.nextLine();
                                System.out.print("Novo Preço Base: ");
                                p.precoBase = Double.parseDouble(leitor.nextLine().replace(",", "."));
                                System.out.println("Produto atualizado!");
                                achou = true;
                                break;
                            }
                        }
                        if (!achou) System.out.println("Item não encontrado.");
                    } catch (Exception e) { System.out.println("Erro na digitação."); }
                    break;
                case 5: // DELETE (Acesso direto a p.id)
                    System.out.print("Digite o ID do item para remover: ");
                    try {
                        int idDel = Integer.parseInt(leitor.nextLine());
                        if (catalogo.removeIf(p -> p.id == idDel)) System.out.println("Item removido!");
                        else System.out.println("Item não encontrado.");
                    } catch (Exception e) { System.out.println("Erro na digitação."); }
                    break;
                case 6: break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    // ==========================================
    // MÓDULO 3: VENDAS
    // ==========================================
    public static void menuVendas() {
        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n--- MÓDULO DE VENDAS ---");
            System.out.println("1. Realizar Nova Venda / OS");
            System.out.println("2. Ver Histórico de Vendas");
            System.out.println("3. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            
            try { opcao = Integer.parseInt(leitor.nextLine()); } 
            catch (Exception e) { opcao = 0; }

            switch (opcao) {
                case 1:
                    if (clientes.isEmpty() || catalogo.isEmpty()) {
                        System.out.println("Necessário ter cliente e produto cadastrados para vender.");
                        break;
                    }
                    System.out.print("Digite o ID do Cliente: ");
                    try {
                        int idCliente = Integer.parseInt(leitor.nextLine());
                        Cliente cli = null;
                        for (Cliente c : clientes) if (c.id == idCliente) cli = c;
                        
                        if (cli == null) { System.out.println("Cliente não encontrado."); break; }

                        Venda novaVenda = new Venda(contadorVendaId++, cli);
                        boolean comprando = true;

                        while (comprando) {
                            System.out.print("ID do Produto/Serviço a adicionar (0 para finalizar): ");
                            int idProd = Integer.parseInt(leitor.nextLine());
                            if (idProd == 0) { comprando = false; } 
                            else {
                                Produto prod = null;
                                for (Produto p : catalogo) if (p.id == idProd) prod = p;
                                
                                if (prod != null) {
                                    novaVenda.adicionarItem(prod);
                                    System.out.println(" + " + prod.descricao + " adicionado!");
                                } else System.out.println("Produto não encontrado.");
                            }
                        }
                        historicoVendas.add(novaVenda);
                        System.out.println("\nVenda finalizada!");
                        System.out.println(novaVenda.toString());
                    } catch (Exception e) { System.out.println("Erro na digitação."); }
                    break;
                case 2:
                    if (historicoVendas.isEmpty()) System.out.println("Nenhuma venda realizada.");
                    for (Venda v : historicoVendas) System.out.println(v.toString());
                    break;
                case 3: break;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}