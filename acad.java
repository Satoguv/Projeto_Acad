import java.util.Scanner;
import java.util.ArrayList;

class Aluno{
    String nome;
    String dataNascimento;
    String status;

    public Aluno(String nome, String dataNascimento, String status){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.status = status;
    }
}

class Instrutor{
    String nomeIns;
    String especialidade;

    public Instrutor(String nomeIns, String especialidade){
        this.nomeIns = nomeIns;
        this.especialidade = especialidade;
    }
}

class Aula{
    String descricao;
    String alunosPresentes;
    String instrutorPresente;

    public Aula(String descricao, String alunosPresentes, String instrutorPresente){
        this.descricao = descricao;
        this.alunosPresentes = alunosPresentes;
        this.instrutorPresente = instrutorPresente; 
    }
}

public class acad {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Instrutor> instrutores = new ArrayList<>();
        ArrayList<Aula> aulas = new ArrayList<>();

        menu:
        while (true) {
            System.out.println("1. Cadastrar Aluno \n2. Cadastrar Instrutor \n3. Cadastrar Aula \n4. Alterar Status de Aluno \n5. Sair");
            int opcao = in.nextInt();
            
            switch (opcao) {
                case 1:
                    System.out.println("Preencha o campo com o seu nome completo: ");
                    String nome = in.nextLine();
                    System.out.println("Insira sua data de nascimento, com o formato (dd/mm/aaaa)");
                    String dataNascimento = in.nextLine();
                    Aluno aluno = new Aluno(nome, dataNascimento, dataNascimento);
                    System.out.println("Aluno cadastrado!!");
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
                    System.out.println("Preencha o campo com a descrição sobre a aula");
                    String descricao = in.nextLine();
                    System.out.println("Alunos presentes");
                    String alunosPresentes = in.nextLine();
                    Aula aula = new Aula(descricao, alunosPresentes, alunosPresentes);
                    System.out.println("Aula cadastrado!!");
                    aulas.add(aula);
                    break;
                case 4:
                System.out.println("Selecione o aluno para alterar seus status");
                    break;
                case 5:
                System.out.println("Encerrando o programa...");    
                    break menu;
                default:
                System.out.println("Opção inválida!!!");
                    break;
            }
        }
        in.close();
    }
}