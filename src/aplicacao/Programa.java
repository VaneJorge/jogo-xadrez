package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();

		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limpaTela();

				UI.printPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

				boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.limpaTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partidaXadrez.mudancaDePeca(origem, destino);

				if (pecaCapturada != null)
					capturadas.add(pecaCapturada);

				if (partidaXadrez.getPromovida() != null) {
					System.out.println("Entre com a peca pra promocao (B/N/R/Q): ");
					String tipo = sc.nextLine();
					partidaXadrez.trocaPecaPromocao(tipo);
				}

			} catch (ExcecaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limpaTela();
		UI.printPartida(partidaXadrez, capturadas);
	}

}
