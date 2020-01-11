package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] peca;

	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		peca = new Peca[linhas][colunas];
	}

	public Peca peca(int linha, int coluna) {
		return peca[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		return peca[posicao.getLinha()][posicao.getColuna()];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

}
