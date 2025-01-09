import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;
    String nome;
    String dataNascimento;
    String status;

    public Aluno(String nome, String dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.status = "ativo"; // Status padrão definido como "ativo" no cadastro
    }

    public boolean isAtivo() {
        return this.status.equalsIgnoreCase("ativo");
    }
}

class Instrutor implements Serializable {
    private static final long serialVersionUID = 1L;
    String nomeIns;
    String especialidade;

    public Instrutor(String nomeIns, String especialidade) {
        this.nomeIns = nomeIns;
        this.especialidade = especialidade;
    }
}

class Aula implements Serializable {
    private static final long serialVersionUID = 1L;
    String descricao;
    String alunosPresentes;
    String instrutorPresente;

    public Aula(String descricao, String alunosPresentes, String instrutorPresente) {
        this.descricao = descricao;
        this.alunosPresentes = alunosPresentes;
        this.instrutorPresente = instrutorPresente;
    }
}

class Persistencia {
    public static <T> void salvarDados(ArrayList<T> lista, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> carregarDados(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado. Criando novo.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

public class acad {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Aluno> alunos = Persistencia.carregarDados("alunos.dat");
        ArrayList<Instrutor> instrutores = Persistencia.carregarDados("instrutores.dat");
        ArrayList<Aula> aulas = Persistencia.carregarDados("aulas.dat");

        menu:
        while (true) {
            try {
                System.out.println("1. Cadastrar Aluno \n2. Cadastrar Instrutor \n3. Cadastrar Aula \n4. Alterar Status de Aluno \n5. Sair");
                int opcao = Integer.parseInt(in.nextLine().trim());

                switch (opcao) {
                    case 1:
                        System.out.println("Preencha o campo com o seu nome completo: ");
                        String nome = in.nextLine();
                        System.out.println("Insira sua data de nascimento (dd/mm/aaaa): ");
                        String dataNascimento = in.nextLine();
                        Aluno aluno = new Aluno(nome, dataNascimento);
                        alunos.add(aluno);
                        Persistencia.salvarDados(alunos, "alunos.dat");
                        System.out.println("Aluno cadastrado e salvo!");
                        break;
                    case 2:
                        System.out.println("Preencha o campo com o seu nome completo: ");
                        String nomeIns = in.nextLine();
                        System.out.println("Conte-nos a sua especialidade: ");
                        String especialidade = in.nextLine();
                        Instrutor instrutor = new Instrutor(nomeIns, especialidade);
                        instrutores.add(instrutor);
                        Persistencia.salvarDados(instrutores, "instrutores.dat");
                        System.out.println("Instrutor cadastrado e salvo!");
                        break;
                    case 3:
                        if (alunos.isEmpty()) {
                            System.out.println("Erro: Nenhum aluno cadastrado.");
                            break;
                        }
                        if (instrutores.isEmpty()) {
                            System.out.println("Erro: Nenhum instrutor cadastrado.");
                            break;
                        }
                        System.out.println("Descrição da aula: ");
                        String descricao = in.nextLine();
                        Aula aula = new Aula(descricao, "Alunos ativos", "Instrutor presente");
                        aulas.add(aula);
                        Persistencia.salvarDados(aulas, "aulas.dat");
                        System.out.println("Aula cadastrada e salva!");
                        break;
                    case 4:
                        if (alunos.isEmpty()) {
                            System.out.println("Erro: Nenhum aluno cadastrado.");
                            break;
                        }
                        System.out.println("Selecione o aluno para alterar o status:");
                        for (int i = 0; i < alunos.size(); i++) {
                            System.out.println(i + ". " + alunos.get(i).nome + " - Status: " + alunos.get(i).status);
                        }
                        int alunoIndex = Integer.parseInt(in.nextLine().trim());
                        if (alunoIndex < 0 || alunoIndex >= alunos.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        System.out.println("Novo status (ativo/inativo): ");
                        alunos.get(alunoIndex).status = in.nextLine();
                        Persistencia.salvarDados(alunos, "alunos.dat");
                        System.out.println("Status atualizado e salvo!");
                        break;
                    case 5:
                        System.out.println("Encerrando o programa...");
                        break menu;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Insira um número.");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        in.close();
    }
}
