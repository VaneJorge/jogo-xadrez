package xadrez;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		setupInicial();
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaXadrez mudancaDePeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		Peca pecaCapturada = realizaMovimento(origem, destino);
		
		if(testaCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode se colocar em check");
		}
		
		check = (testaCheck(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}

	private Peca realizaMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.jaExistePeca(posicao))
			throw new ExcecaoXadrez("Nao ha essa posicao no tabuleiro");

		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor())
			throw new ExcecaoXadrez("A peca escolhida nao eh sua");

		if (!tabuleiro.peca(posicao).existePossibilidadeDeMover())
			throw new ExcecaoXadrez("Nao eh possivel mover essa peca");

	}

	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possiveisMovimentos(destino))
			throw new ExcecaoXadrez("A peca escolhida nao pode se mover pra posicao de destino");
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE ? Cor.BLACK : Cor.WHITE);
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE ? Cor.BLACK : Cor.WHITE);
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		
		throw new IllegalStateException("Nao tem a cor " + cor + " no tabuleiro");
	}
	
	private boolean testaCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void setupInicial() {
		lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.WHITE));
		lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.WHITE));
		lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.WHITE));
		lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.WHITE));
		lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.WHITE));
		lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.WHITE));

		lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.BLACK));
		lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.BLACK));
		lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.BLACK));
		lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.BLACK));
		lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.BLACK));
		lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.BLACK));
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
}
