package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().jaExistePeca(p))
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().jaExistePeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().jaExistePeca(p2) && getContadorMovimento() == 0)
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && jaExisteOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && jaExisteOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// #movimentoespecial en passant branco
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && jaExisteOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable())
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && jaExisteOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable())
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
			}
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().jaExistePeca(p))
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().jaExistePeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().jaExistePeca(p2) && getContadorMovimento() == 0)
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && jaExisteOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && jaExisteOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// #movimentoespecial en passant preto
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && jaExisteOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable())
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && jaExisteOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable())
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
			}
		}

		return mat;
	}
}
