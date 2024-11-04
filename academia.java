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

public class academia {
    public static Aluno encontrarAluno(ArrayList<Aluno> alunos, String nomeProcurado){
        for (Aluno aluno : alunos){
            if (aluno.nome.equals(nomeProcurado)){
                return aluno;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Instrutor> instrutores = new ArrayList<>();
        ArrayList<Aula> aulas = new ArrayList<>();

        menu:
        while (true) {
            System.out.println("1. Cadastrar Aluno \n2. Cadastrar Instrutor \n3. Cadastrar Aula \n4. Alterar Status do Aluno \n5. Sair");
            int opcao = scan.nextInt(); scan.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("Preencha o campo com o seu nome completo: ");
                    String nome = scan.nextLine();
                    System.out.println("Insira a sua data de nascimento, no estilo (dd/mm/aaaa): ");
                    String dataNascimento = scan.nextLine();
                    Aluno aluno = new Aluno(nome, dataNascimento, "ATIVO");
                    System.out.println("Aluno cadastrado!!");
                    alunos.add(aluno);
                    break;
                case 2:
                    System.out.println("Preencha o campo com o seu nome completo: ");
                    String nomeIns = scan.nextLine();
                    System.out.println("Insira a sua especialidade no campo: ");
                    String especialidade = scan.nextLine();
                    Instrutor instrutor = new Instrutor(nomeIns, especialidade);
                    System.out.println("Instrutor cadastrado!!!");
                    instrutores.add(instrutor);
                    break;
                case 3:
                    System.out.println("Preencha o campo com um breve resumo sobre a aula: ");
                    String aula = scan.nextLine();
                    System.out.println("Alunos presentes: ");
                    String alunosPresentes = scan.nextLine();
                    System.out.println("Instrutor(es) responsável(eis): ");
                    String instrutorPresente = scan.nextLine();
                    Aula aulass = new Aula(aula, alunosPresentes, instrutorPresente);
                    System.out.println("Aula salva com sucesso!!");
                    aulas.add(aulass);
                    break;
                case 4:
                    System.out.println("Selecione o aluno que terá o status alterado: ");
                    String nomeProcurado = scan.nextLine();
                    Aluno alunoProcurado = encontrarAluno(alunos, nomeProcurado);
                    if (alunoProcurado != null) {
                        System.out.println("Aluno " + alunoProcurado.nome + " encontrado!");                    
                        System.out.println("Data de nascimento: " + alunoProcurado.dataNascimento);                    
                        System.out.println("Mude o status do aluno " + alunoProcurado.nome);
                        String novoStatus = scan.nextLine();
                        alunoProcurado.status = novoStatus;
                    } else {
                        System.out.println("Aluno não encontrado.");
                    }
                    break;
                case 5:
                    System.out.println("Encerrando...");
                    break menu;
                default:
                    System.out.println("O código inserido é inválido!!");
                    break;
            }
        }

        scan.close();
    }
}
