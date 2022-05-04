import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filtros
{
    public static BufferedImage AumentoTonalidade (BufferedImage img, int quantidadeAumento, String banda)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        BufferedImage imgFiltrada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);

                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                if (banda.equals("VERMELHO"))
                {
                    vermelho = vermelho + quantidadeAumento;
                    if(vermelho > 255)
                        vermelho = 255;

                    if(vermelho < 0)
                        vermelho = 0;
                }

                if (banda.equals("VERDE"))
                {
                    verde = verde + quantidadeAumento;
                    if(verde > 255)
                        verde = 255;

                    if(verde < 0)
                        verde = 0;
                }

                if (banda.equals("AZUL"))
                {
                    azul = azul + quantidadeAumento;
                    if(azul > 255)
                        azul = 255;

                    if(azul < 0)
                        azul = 0;
                }

                Color novaCor = new Color(vermelho, verde, azul);
                imgFiltrada.setRGB(coluna, linha, novaCor.getRGB());
            }
        }

        return imgFiltrada;
    }

    public static BufferedImage EscalaCinza(BufferedImage img, String tipo)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        BufferedImage imgProcessada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);
                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                Color cinza = new Color(0, 0, 0);

                if (tipo.equals("VERMELHO"))
                {
                    cinza = new Color(vermelho, vermelho, vermelho);
                }

                if (tipo.equals("VERDE"))
                {
                    cinza = new Color(verde, verde, verde);
                }

                if (tipo.equals("AZUL"))
                {
                    cinza = new Color(azul, azul, azul);
                }

                if (tipo.equals("CINZA"))
                {
                    int cinzaRgb = (vermelho + verde + azul)/3;
                    cinza = new Color(cinzaRgb, cinzaRgb, cinzaRgb);
                }

                imgProcessada.setRGB(coluna, linha, cinza.getRGB());
            }
        }

        return imgProcessada;
    }

    public static BufferedImage EscalaNegativa(BufferedImage img)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        BufferedImage imgProcessada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);
                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                Color corNegativa = new Color(255 - vermelho, 255 - verde, 255 - azul);

                imgProcessada.setRGB(coluna, linha, corNegativa.getRGB());
            }
        }

        return imgProcessada;
    }

    public static BufferedImage Limiarizacao(BufferedImage img, int limiar)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        BufferedImage imgProcessada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);

                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                int media = (vermelho + verde + azul)/3;

                Color corLimiarizada = new Color(0, 0, 0);

                if  (media > limiar)
                    corLimiarizada = new Color(255, 255, 255);

                imgProcessada.setRGB(coluna, linha, corLimiarizada.getRGB());
            }
        }

        return imgProcessada;
    }

    public static double[][][] RgbToYiq(BufferedImage img)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        double[][][] imageYIQ = new double[altura][largura][3];

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);

                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                double y = (double) (0.299*vermelho + 0.587*verde + 0.114*azul);
                double i = (double) (0.596*vermelho - 0.274*verde - 0.322*azul);
                double q = (double) (0.211*vermelho - 0.523*verde + 0.312*azul);

                imageYIQ[linha][coluna][0] = y;
                imageYIQ[linha][coluna][1] = i;
                imageYIQ[linha][coluna][2] = q;
            }
        }

        return imageYIQ;
    }

    public static BufferedImage YiqToRgb(double[][][] imagemYiq)
    {
        int altura = imagemYiq.length;
        int largura = imagemYiq[0].length;

        BufferedImage imgProcessada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int vermelho = (int) (1.000 * imagemYiq[linha][coluna][0] + 0.956 * imagemYiq[linha][coluna][1] + 0.621 * imagemYiq[linha][coluna][2]);
                int verde = (int) (1.000 * imagemYiq[linha][coluna][0] + 0.272 * imagemYiq[linha][coluna][1] + 0.647 * imagemYiq[linha][coluna][2]);
                int azul = (int) (1.000 * imagemYiq[linha][coluna][0] + 1.106 * imagemYiq[linha][coluna][1] + 1.703 * imagemYiq[linha][coluna][2]);

                if (vermelho > 255)
                    vermelho = 255;

                if(vermelho < 0)
                    vermelho = 0;

                if (verde > 255)
                    verde = 255;

                if(verde < 0)
                    verde = 0;

                if (azul > 255)
                    azul = 255;

                if(azul < 0)
                    azul = 0;

                Color cor = new Color(vermelho, verde, azul);

                imgProcessada.setRGB(coluna, linha, cor.getRGB());
            }
        }

        return imgProcessada;
    }

    public static BufferedImage AumentoBrilhoRgb(BufferedImage img, double quantidadeBrilho, String tipo)
    {
        int largura = img.getWidth();
        int altura = img.getHeight();

        BufferedImage imgProcessada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                int rgb = img.getRGB(coluna,linha);
                Color cor = new Color(rgb);

                int vermelho = cor.getRed();
                int verde = cor.getGreen();
                int azul = cor.getBlue();

                if (tipo.equals("ADICAO"))
                {
                    vermelho = vermelho + (int)quantidadeBrilho;
                    if (vermelho > 255)
                        vermelho = 255;

                    if(vermelho < 0)
                        vermelho = 0;

                    verde = verde + (int)quantidadeBrilho;
                    if (verde > 255)
                        verde = 255;

                    if(verde < 0)
                        verde = 0;

                    azul = azul + (int)quantidadeBrilho;
                    if (azul > 255)
                        azul = 255;

                    if(azul < 0)
                        azul = 0;
                }

                if (tipo.equals("MULTIPLICACAO"))
                {
                    vermelho = vermelho * (int)quantidadeBrilho;
                    if (vermelho > 255)
                        vermelho = 255;

                    if(vermelho < 0)
                        vermelho = 0;

                    verde = verde * (int)quantidadeBrilho;
                    if (verde > 255)
                        verde = 255;

                    if(verde < 0)
                        verde = 0;

                    azul = azul * (int)quantidadeBrilho;
                    if (azul > 255)
                        azul = 255;

                    if(azul < 0)
                        azul = 0;
                }

                Color novaCor = new Color(vermelho, verde, azul);

                imgProcessada.setRGB(coluna, linha, novaCor.getRGB());
            }
        }

        return imgProcessada;
    }

    public static double[][][] AumentoBrilhoYiq(double[][][] imagemYiq, double quantidadeBrilho, String tipo)
    {
        int altura = imagemYiq.length;
        int largura = imagemYiq[0].length;

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                double y = imagemYiq[linha][coluna][0];

                if (tipo.equals("ADICAO"))
                {
                    y = y + quantidadeBrilho;
                }

                if (tipo.equals("MULTIPLICACAO"))
                {
                    y = y * quantidadeBrilho;
                }

                imagemYiq[linha][coluna][0] = y;
            }
        }

        return imagemYiq;
    }

    public static double[][][] NegativoEmY(double[][][] imagemYiq)
    {
        int altura = imagemYiq.length;
        int largura = imagemYiq[0].length;

        for (int linha = 0; linha < altura; linha++)
        {
            for (int coluna = 0; coluna < largura; coluna++)
            {
                imagemYiq[linha][coluna][0] = 255 - imagemYiq[linha][coluna][0];
            }
        }

        return imagemYiq;
    }

}
