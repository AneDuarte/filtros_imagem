import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Projeto {

    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("src\\image4.jpg"));
        BufferedImage newImg = ImageIO.read(new File("src\\image4.jpg"));

        int altura = img.getHeight();
        int largura = img.getWidth();

        System.out.println("Menu de opções");
        System.out.println("1 - Aumento de tonalidade");
        System.out.println("2 - Escala de cinza");
        System.out.println("3 - Filtro negativo");
        System.out.println("4 - Binarização/Limiarização");
        System.out.println("5 - RGB -> YIQ");
        System.out.println("6 - YIQ -> RGB");
        System.out.println("7 - Aumento do brilho RGB. Aditivo ou multiplicativo");
        System.out.println("8 - Aumento do brilho na banda Y. Aditivo ou multiplicativo");
        System.out.println("9 - Negativo em Y");
        System.out.println("");

        System.out.println("Digite a opção de filtro: ");
        Scanner sc = new Scanner(System.in);
        int resposta = sc.nextInt();
        sc.nextLine();
        String tipo;
        double[][][] imgYiq;

        switch(resposta)
        {
            case 1:
                System.out.println("Qual a quantidade do aumento? ");
                int qtdAumen = sc.nextInt();
                sc.nextLine();

                System.out.println("Qual a banda? Vermelho, verde ou azul? ");
                String respBanda = sc.nextLine();
                respBanda = respBanda.toUpperCase();

                newImg = Filtros.AumentoTonalidade(img, qtdAumen, respBanda);
                break;

            case 2:
                System.out.println("Qual o tipo? Vermelho, verde, azul ou cinza? ");
                tipo = sc.nextLine();
                tipo = tipo.toUpperCase();

                newImg = Filtros.EscalaCinza(img, tipo);
                break;

            case 3:
                newImg = Filtros.EscalaNegativa(img);
                break;

            case 4:
                System.out.println("Qual o valor do limiar? ");
                int limiar = sc.nextInt();
                sc.nextLine();

                newImg = Filtros.Limiarizacao(img, limiar);
                break;

            case 5:
                imgYiq = Filtros.RgbToYiq(img);
                break;

            case 6:
                imgYiq = Filtros.RgbToYiq(img);
                newImg = Filtros.YiqToRgb(imgYiq);
                break;

            case 7:
                System.out.println("Qual a quantidade de brilho? ");
                double qtdBrilhoRgb = sc.nextDouble();
                sc.nextLine();

                System.out.println("Qual o tipo? (adicao/multiplicacao): ");
                tipo = sc.nextLine();
                tipo = tipo.toUpperCase();

                while (!tipo.equals("ADICAO") && !tipo.equals("MULTIPLICACAO"))
                {
                    System.out.println("Tipo inválido. Digite novamente: ");
                    tipo = sc.nextLine();
                    tipo = tipo.toUpperCase();
                }

                newImg = Filtros.AumentoBrilhoRgb(img, qtdBrilhoRgb, tipo);
                break;

            case 8:
                System.out.println("Qual a quantidade de brilho? ");
                double qtdBrilhoYiq = sc.nextDouble();
                sc.nextLine();

                System.out.println("Qual o tipo? (adicao/multiplicacao): ");
                tipo = sc.nextLine();
                tipo = tipo.toUpperCase();

                while (!tipo.equals("ADICAO") && !tipo.equals("MULTIPLICACAO"))
                {
                    System.out.println("Tipo inválido. Digite novamente: ");
                    tipo = sc.nextLine();
                    tipo = tipo.toUpperCase();
                }

                imgYiq = Filtros.RgbToYiq(img);
                imgYiq = Filtros.AumentoBrilhoYiq(imgYiq, qtdBrilhoYiq, tipo);

                newImg = Filtros.YiqToRgb(imgYiq);
                break;

            case 9:
                imgYiq = Filtros.RgbToYiq(img);
                imgYiq = Filtros.NegativoEmY(imgYiq);

                newImg = Filtros.YiqToRgb(imgYiq);
                break;

            default:
                System.out.println("Opção inválida, digite outro valor.");
                break;
        }

        ExibirImagem(img, altura + 50, largura + 50);
        ExibirImagem(newImg, altura + 50, largura + 50);

        sc.close();
    }

    public static void ExibirImagem(BufferedImage img, int altura, int largura)
    {
        ImageIcon imagemAberta = new ImageIcon(img);
        JFrame janela = new JFrame();
        janela.setLayout(new FlowLayout());

        janela.setSize(largura, altura);

        JLabel jLabel = new JLabel();
        jLabel.setIcon(imagemAberta);
        janela.add(jLabel);
        janela.setVisible(true);

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}