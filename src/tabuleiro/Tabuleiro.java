package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1)
			throw new ExcecaoTabuleiro("Erro ao criar tabuleiro: precisa ter ao menos 1 coluna e 1 linha");
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Peca peca(int linha, int coluna) {
		if (!existePosicao(linha, coluna))
			throw new ExcecaoTabuleiro("Posicao nao esta no tabuleiro");

		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!existePosicao(posicao))
			throw new ExcecaoTabuleiro("Posicao nao esta no tabuleiro");

		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarPeca(Peca peca, Posicao posicao) {
		if (jaExistePeca(posicao))
			throw new ExcecaoTabuleiro("Ja existe uma peca nessa posicao: " + posicao);

		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removePeca(Posicao posicao) {
		if(!existePosicao(posicao))
			throw new ExcecaoTabuleiro("Posicao nao existe");
		
		if(peca(posicao) == null) {
			return null;
		}
		
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		
		return aux;
		
	}

	private boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(), posicao.getColuna());
	}

	public boolean jaExistePeca(Posicao posicao) {
		if (!existePosicao(posicao))
			throw new ExcecaoTabuleiro("Posicao nao esta no tabuleiro");

		return peca(posicao) != null;
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
}
