import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe Aluno
class Aluno {
    String nome;
    String dataNascimento;
    String status;

    public Aluno(String nome, String dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.status = "ativo";
    }

    public boolean isAtivo() {
        return this.status.equalsIgnoreCase("ativo");
    }
}

// Classe Instrutor
class Instrutor {
    String nomeIns;
    String especialidade;

    public Instrutor(String nomeIns, String especialidade) {
        this.nomeIns = nomeIns;
        this.especialidade = especialidade;
    }
}

// Classe Aula
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

// Classe MarcadorAula
class MarcadorAula {
    public void verificarAlunoAtivo(Aluno aluno) {
        if (aluno.isAtivo()) {
            System.out.println("O aluno " + aluno.nome + " está ativo e pode participar da aula.");
        } else {
            System.out.println("O aluno " + aluno.nome + " está inativo e não pode participar da aula.");
        }
    }
}

// Classe AlunoDAO
class AlunoDAO {
    private Connection connection;

    public AlunoDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (nome, data_nascimento, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, aluno.nome);
            pstmt.setString(2, aluno.dataNascimento);
            pstmt.setString(3, aluno.status);
            pstmt.executeUpdate();
        }
    }

    public List<Aluno> listarAlunos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("data_nascimento"));
                aluno.status = rs.getString("status");
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public void atualizarStatusAluno(String nome, String novoStatus) throws SQLException {
        String sql = "UPDATE alunos SET status = ? WHERE nome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, novoStatus);
            pstmt.setString(2, nome);
            pstmt.executeUpdate();
        }
    }
}

// Classe InstrutorDAO
class InstrutorDAO {
    private Connection connection;

    public InstrutorDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarInstrutor(Instrutor instrutor) throws SQLException {
        String sql = "INSERT INTO instrutores (nome, especialidade) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, instrutor.nomeIns);
            pstmt.setString(2, instrutor.especialidade);
            pstmt.executeUpdate();
        }
    }

    public List<Instrutor> listarInstrutores() throws SQLException {
        List<Instrutor> instrutores = new ArrayList<>();
        String sql = "SELECT * FROM instrutores";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Instrutor instrutor = new Instrutor(rs.getString("nome"), rs.getString("especialidade"));
                instrutores.add(instrutor);
            }
        }
        return instrutores;
    }
}

// Classe AulaDAO
class AulaDAO {
    private Connection connection;

    public AulaDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarAula(Aula aula) throws SQLException {
        String sql = "INSERT INTO aulas (descricao, alunos_presentes, instrutor_presente) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, aula.descricao);
            pstmt.setString(2, aula.alunosPresentes);
            pstmt.setString(3, aula.instrutorPresente);
            pstmt.executeUpdate();
        }
    }

    public List<Aula> listarAulas() throws SQLException {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM aulas";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Aula aula = new Aula(rs.getString("descricao"), rs.getString("alunos_presentes"), rs.getString("instrutor_presente"));
                aulas.add(aula);
            }
        }
        return aulas;
    }
}

// Classe Principal
public class AcademiaApp {
    private static final String URL = "jdbc:mysql://localhost:3306/academia";
    private static final String USER = "root";
    private static final String PASSWORD = "sua_senha";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); // Desativa o auto-commit para usar transações

            AlunoDAO alunoDAO = new AlunoDAO(connection);
            InstrutorDAO instrutorDAO = new InstrutorDAO(connection);
            AulaDAO aulaDAO = new AulaDAO(connection);
            MarcadorAula marcadorAula = new MarcadorAula();

            Scanner in = new Scanner(System.in);
            System.out.println("=== Bem-vindo ao Sistema Acadêmico ===");

            menu:
            while (true) {
                System.out.println("\n1. Cadastrar Aluno");
                System.out.println("2. Cadastrar Instrutor");
                System.out.println("3. Cadastrar Aula");
                System.out.println("4. Alterar Status de Aluno");
                System.out.println("5. Marcar Aula para um Aluno");
                System.out.println("6. Sair");
                System.out.print("Escolha uma opção: ");

                int opcao;
                try {
                    opcao = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Por favor, insira um número válido.");
                    continue;
                }

                switch (opcao) {
                    case 1:
                        System.out.print("Nome completo do aluno: ");
                        String nome = in.nextLine();
                        System.out.print("Data de nascimento (dd/mm/aaaa): ");
                        String dataNascimento = in.nextLine();
                        alunoDAO.cadastrarAluno(new Aluno(nome, dataNascimento));
                        connection.commit(); // Confirma a transação
                        System.out.println("Aluno cadastrado com sucesso!");
                        break;

                    case 2:
                        System.out.print("Nome completo do instrutor: ");
                        String nomeIns = in.nextLine();
                        System.out.print("Especialidade: ");
                        String especialidade = in.nextLine();
                        instrutorDAO.cadastrarInstrutor(new Instrutor(nomeIns, especialidade));
                        connection.commit(); // Confirma a transação
                        System.out.println("Instrutor cadastrado com sucesso!");
                        break;

                    case 3:
                        System.out.print("Descrição da aula: ");
                        String descricao = in.nextLine();
                        aulaDAO.cadastrarAula(new Aula(descricao, "Alunos ativos", "Instrutor presente"));
                        connection.commit(); // Confirma a transação
                        System.out.println("Aula cadastrada com sucesso!");
                        break;

                    case 4:
                        System.out.print("Nome do aluno para alterar o status: ");
                        String nomeAluno = in.nextLine();
                        System.out.print("Novo status (ativo/inativo): ");
                        String novoStatus = in.nextLine();
                        alunoDAO.atualizarStatusAluno(nomeAluno, novoStatus);
                        connection.commit(); // Confirma a transação
                        System.out.println("Status atualizado com sucesso!");
                        break;

                    case 5:
                        System.out.print("Nome do aluno para marcar a aula: ");
                        String nomeAlunoAula = in.nextLine();
                        Aluno aluno = alunoDAO.listarAlunos().stream()
                                .filter(a -> a.nome.equals(nomeAlunoAula))
                                .findFirst()
                                .orElse(null);
                        if (aluno != null) {
                            marcadorAula.verificarAlunoAtivo(aluno);
                        } else {
                            System.out.println("Aluno não encontrado.");
                        }
                        break;

                    case 6:
                        System.out.println("Encerrando o programa...");
                        break menu;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}