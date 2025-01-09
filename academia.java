import java.util.Scanner;
import java.util.ArrayList;

class Aluno {
    String nome;
    String dataNascimento;
    String status;

    public Aluno(String nome, String dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.status = "ativo"; // Status padrão definido como "ativo" no cadastro
    }

    // Método para verificar se o aluno está ativo
    public boolean isAtivo() {
        return this.status.equalsIgnoreCase("ativo");
    }
}

class Instrutor {
    String nomeIns;
    String especialidade;

    public Instrutor(String nomeIns, String especialidade) {
        this.nomeIns = nomeIns;
        this.especialidade = especialidade;
    }
}

class Aula {
    String descricao;
    String alunosPresentes;
    String instrutorPresente;

    public Aula(String descricao, String alunosPresentes, String instrutorPresente) {
        this.descricao = descricao;
        this.alunosPresentes = alunosPresentes;
        this.instrutorPresente = instrutorPresente; 
    }
}

class MarcadorAula {
    public void verificarAlunoAtivo(Aluno aluno) {
        if (aluno.isAtivo()) {
            System.out.println("O aluno " + aluno.nome + " está ativo e pode participar da aula.");
        } else {
            throw new IllegalArgumentException("O aluno " + aluno.nome + " está inativo e não pode participar da aula.");
        }
    }
}

public class acad {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Instrutor> instrutores = new ArrayList<>();
        ArrayList<Aula> aulas = new ArrayList<>();
        MarcadorAula marcadorAula = new MarcadorAula();

        menu:
        while (true) {
            try {
                System.out.println("1. Cadastrar Aluno \n2. Cadastrar Instrutor \n3. Cadastrar Aula \n4. Alterar Status de Aluno \n5. Marcar Aula para um Aluno \n6. Sair");
                int opcao = Integer.parseInt(in.nextLine().trim());

                switch (opcao) {
                    case 1:
                        System.out.println("Preencha o campo com o seu nome completo: ");
                        String nome = in.nextLine();
                        System.out.println("Insira sua data de nascimento, com o formato (dd/mm/aaaa)");
                        String dataNascimento = in.nextLine();
                        Aluno aluno = new Aluno(nome, dataNascimento);
                        System.out.println("Aluno cadastrado com status ativo!");
                        alunos.add(aluno);
                        break;
                    case 2:
                        System.out.println("Preencha o campo com o seu nome completo: ");
                        String nomeIns = in.nextLine();
                        System.out.println("Conte-nos a sua especialidade");
                        String especialidade = in.nextLine();
                        Instrutor instrutor = new Instrutor(nomeIns, especialidade);
                        System.out.println("Instrutor cadastrado!!");
                        instrutores.add(instrutor);
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
                        System.out.println("Preencha o campo com a descrição sobre a aula");
                        String descricao = in.nextLine();
                        System.out.println("Alunos presentes:");
                        contarAlunosAtivos(alunos);
                        System.out.println("Instrutores disponíveis:");
                        contarInstrutores(instrutores);
                        Aula aula = new Aula(descricao, "Alunos ativos", "Instrutor presente");
                        System.out.println("Aula cadastrada!!");
                        aulas.add(aula);
                        break;
                    case 4:
                        if (alunos.isEmpty()) {
                            System.out.println("Erro: Nenhum aluno cadastrado.");
                            break;
                        }
                        System.out.println("Selecione o aluno para alterar seu status");
                        for (int i = 0; i < alunos.size(); i++) {
                            System.out.println(i + ". " + alunos.get(i).nome + " - Status: " + alunos.get(i).status);
                        }
                        int alunoIndex = Integer.parseInt(in.nextLine().trim());
                        if (alunoIndex < 0 || alunoIndex >= alunos.size()) {
                            System.out.println("Erro: Índice inválido para o aluno.");
                            break;
                        }
                        System.out.println("Novo status (ativo/inativo): ");
                        String novoStatus = in.nextLine();
                        alunos.get(alunoIndex).status = novoStatus;
                        System.out.println("Status atualizado!");
                        break;
                    case 5:
                        if (alunos.isEmpty()) {
                            System.out.println("Erro: Nenhum aluno cadastrado.");
                            break;
                        }
                        System.out.println("Selecione o aluno para marcar a aula:");
                        for (int i = 0; i < alunos.size(); i++) {
                            System.out.println(i + ". " + alunos.get(i).nome + " - Status: " + alunos.get(i).status);
                        }
                        int alunoEscolhido = Integer.parseInt(in.nextLine().trim());
                        if (alunoEscolhido < 0 || alunoEscolhido >= alunos.size()) {
                            System.out.println("Erro: Índice inválido para o aluno.");
                            break;
                        }
                        try {
                            marcadorAula.verificarAlunoAtivo(alunos.get(alunoEscolhido));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.println("Encerrando o programa...");
                        break menu;
                    default:
                        System.out.println("Opção inválida!!!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        in.close();
    }

    public static void contarAlunosAtivos(ArrayList<Aluno> alunos) {
        int contador = 0;
        for (Aluno aluno : alunos) {
            if (aluno.isAtivo()) {
                contador++;
                System.out.println("Aluno: " + aluno.nome + " - Status: Ativo");
            }
        }
        System.out.println("Total de alunos ativos: " + contador);
    }

    public static void contarInstrutores(ArrayList<Instrutor> instrutores) {
        int contador = 0;
        for (Instrutor instrutor : instrutores) {
            contador++;
            System.out.println("Instrutor: " + instrutor.nomeIns + " - Especialidade: " + instrutor.especialidade);
        }
        System.out.println("Total de instrutores: " + contador);
    }
}
