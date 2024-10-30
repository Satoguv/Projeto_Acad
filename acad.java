import java.util.Scanner;

public class acad {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        menu:
        while (true) {
            System.out.println("1. Cadastrar Aluno \n2. Cadastrar Instrutor \n3. Cadastrar Aula \n 4. Sair");
            int opcao = scan.nextInt();
            
            switch (opcao) {
                case 1:
                System.out.println("Preencha o campo com o seu nome completo: ");
                String nome = scan.nextLine();
                
                    
                    break;
            
                default:
                    break;
            }
        }


        
    }
}